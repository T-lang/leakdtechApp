package com.leakdtech.maintenanceapp.Utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.leakdtech.maintenanceapp.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by LYB OJO on 10/3/2017.
 */

public class BidsListAdapter extends ArrayAdapter<Bids> {

    private static final String TAG = "BidsListAdapter";

    private LayoutInflater mInflater;
    private int layoutResource;
    private Context mContext;

    public BidsListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Bids> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        layoutResource = resource;
    }

    private static class ViewHolder {
        TextView bidText, username, timestamp, reply, bidAmount;
        CircleImageView profileImage;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            holder.bidText = (TextView) convertView.findViewById(R.id.bid_text);
            holder.username = (TextView) convertView.findViewById(R.id.bid_username);
            holder.timestamp = (TextView) convertView.findViewById(R.id.bid_time_posted);
            holder.reply = (TextView) convertView.findViewById(R.id.bid_reply);
            holder.bidAmount = (TextView) convertView.findViewById(R.id.bid_amount);
            holder.profileImage = (CircleImageView)convertView.findViewById(R.id.bid_user_image);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //set the bid
        holder.bidText.setText(getItem(position).getBid());
        holder.bidAmount.setText(getItem(position).getBidAmount());

        //set the timestamp difference
        String timestampDifference = getTimestampDifference(getItem(position));
        if(!timestampDifference.equals("0")){
            holder.timestamp.setText(timestampDifference + " d");
        }else{
            holder.timestamp.setText("today");
        }
        //set the username and  image
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(mContext.getString(R.string.dbname_user_account_settings))
                .orderByChild(mContext.getString(R.string.field_user_id))
                .equalTo(getItem(position).getUser_id());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    holder.username.setText(
                            singleSnapshot.getValue(UserAccountSettings.class).getUsername());

                    ImageLoader imageLoader = ImageLoader.getInstance();

                    imageLoader.displayImage(
                            singleSnapshot.getValue(UserAccountSettings.class).getUser_photo(), holder.profileImage);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        try{
            if (position == 0){
                holder.bidAmount.setVisibility(View.GONE);
                holder.reply.setVisibility(View.GONE);
            }
        }catch (NullPointerException e){
            Log.e(TAG, "onCreate: NullPointerException" + e.getMessage());

        }



        return convertView;
    }

    private String getTimestampDifference(Bids bids) {
        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");

        String difference = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Dubai"));//google 'android list of timezones'
        Date today = c.getTime();
        sdf.format(today);
        Date timestamp;
        final String jobPostTimeStamp = bids.getDate_created();
        try {
            timestamp = sdf.parse(jobPostTimeStamp);
            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24 )));
        }catch (ParseException e){
            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage() );
            difference = "0";
        }
        return difference;
    }
}




