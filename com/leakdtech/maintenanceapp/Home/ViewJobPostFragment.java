package com.leakdtech.maintenanceapp.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.leakdtech.maintenanceapp.PostAJob.JobPhoto;
import com.leakdtech.maintenanceapp.R;
import com.leakdtech.maintenanceapp.Utils.BottomNavigationViewHelper;
import com.leakdtech.maintenanceapp.Utils.ImageAdapter;
import com.leakdtech.maintenanceapp.Utils.UniversalImageLoader;
import com.leakdtech.maintenanceapp.Utils.UserAccountSettings;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.leakdtech.maintenanceapp.R.id.gridView;

/**
 * Created by LYB OJO on 10/2/2017.
 */

public class ViewJobPostFragment extends Fragment {

    private static final String TAG = "ViewJobPostFragment";

    public ViewJobPostFragment(){
        super();
        setArguments(new Bundle());
    }

    //widgets
    private BottomNavigationView bottomNavigationView;
    private CircleImageView mUserPhoto;
    private TextView mJobDescription, mJobDate, mjobLocation, mJobTitle, mUsername, mTimeStamp, mTapTobid;
    private ImageView mJobPostImage;
    private String jobPostUsername;
    private String jobPostUrl;

    //vars
    private JobPhoto mJobPhoto;
    private int mActivityNumber = 0;
    private String photoUsername = "";
    private String profilePhotoUrl = "";
    private UserAccountSettings mUserAccountSettings;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_job_post, container, false);
        Log.d(TAG, "onCreateView: opening layout chosen");
        mUserPhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottomNavigationViewBar);
        mJobDescription = (TextView) view.findViewById(R.id.nameJobDescription);
        mJobTitle = (TextView) view.findViewById(R.id.nameJobTitle);
        mJobDate = (TextView) view.findViewById(R.id.jobDate);
        mUsername = (TextView) view.findViewById(R.id.username);
        mTimeStamp = (TextView) view.findViewById(R.id.tv_job_post_time);
        mJobPostImage = (ImageView) view.findViewById(R.id.iv_job_post_image);
        mTapTobid = (TextView) view.findViewById(R.id.tapToBid);

        try{
            mJobPhoto = getPhotoFromBundle();
            UniversalImageLoader.setImage(mJobPhoto.getImage_path(), mJobPostImage, null, "");
            mActivityNumber = getActivityNumFromBundle();

        }catch (NullPointerException e){
            Log.e(TAG, "onCreateView: NullPointerException: " + e.getMessage() );
        }

        setupBottomNavigationView();
        getJobDetails();
//        setupWidgets();
        return view;
    }

    private void getJobDetails(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_user_account_settings))
                .orderByChild(getString(R.string.field_user_id))
                .equalTo(mJobPhoto.getUser_id());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    mUserAccountSettings = singleSnapshot.getValue(UserAccountSettings.class);
                }
                setupWidgets();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }


    private void setupWidgets(){
        String timestampDiff = getTimestampDifference();
        if(!timestampDiff.equals("0")){
            mTimeStamp.setText(timestampDiff + " DAYS AGO");
        }else{
            mTimeStamp.setText("TODAY");
        }
        UniversalImageLoader.setImage(mUserAccountSettings.getUser_photo(), mUserPhoto, null, "");
        mUsername.setText(mUserAccountSettings.getUsername());
        //JA
        mJobDescription.setText(mJobPhoto.getJob_description());
        mJobTitle.setText(mJobPhoto.getJob_title());
        mJobDate.setText(mJobPhoto.getJob_date());
        mJobDescription.setText(mJobPhoto.getJob_description());
        mJobDescription.setText(mJobPhoto.getJob_description());
        //JA


    }

    /**
     * Returns a string representing the number of days ago the post was made
     * @return
     */
    private String getTimestampDifference(){
        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");

        String difference = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Dubai"));//google 'android list of timezones'
        Date today = c.getTime();
        sdf.format(today);
        Date timestamp;
        final String photoTimestamp = mJobPhoto.getDate_created();
        try{
            timestamp = sdf.parse(photoTimestamp);
            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24 )));
        }catch (ParseException e){
            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage() );
            difference = "0";
        }
        return difference;
    }



    /**
     * retrieve the activity number from the incoming bundle from profileActivity interface
     * @return
     */
    private int getActivityNumFromBundle(){
        Log.d(TAG, "getActivityNumFromBundle: arguments: " + getArguments());

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            return bundle.getInt(getString(R.string.activity_number));
        }else{
            return 0;
        }
    }

    /**
     * retrieve the photo from the incoming bundle from profileActivity interface
     * @return
     */


    private JobPhoto getPhotoFromBundle(){
        Log.d(TAG, "getPhotoFromBundle: arguments: " + getArguments());

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            return bundle.getParcelable(getString(R.string.job_photo));
        }else{
            return null;
        }
    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up bottom nav view");
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(getActivity(),getActivity(), bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(mActivityNumber);
        menuItem.setChecked(true);
    }
}

