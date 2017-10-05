package com.leakdtech.maintenanceapp.Home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.leakdtech.maintenanceapp.PostAJob.JobPhoto;
import com.leakdtech.maintenanceapp.R;
import com.leakdtech.maintenanceapp.Utils.Bids;
import com.leakdtech.maintenanceapp.Utils.BidsListAdapter;
import com.leakdtech.maintenanceapp.Utils.BottomNavigationViewHelper;

import java.util.ArrayList;

/**
 * Created by LYB OJO on 10/2/2017.
 */

public class ViewBidsActivity extends AppCompatActivity {

    private static final String TAG = "ViewBidsActivity";
    private  static final int ACTIVITY_NUM = 0;


    private Context mContext = ViewBidsActivity.this;

    private JobPhoto jobPhoto;
    private ArrayList<Bids>mBids;

    private ListView mListView;
    private ImageView mBackArrow, mSendBid;
    private TextView mBidAmount, mBidText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids);
        Log.d(TAG, "onCreate: starting");
        mBidAmount = (TextView) findViewById(R.id.bidAmount);
        mBidText = (TextView) findViewById(R.id.bidText);
        mSendBid = (ImageView) findViewById(R.id.ivPostBid);
        mBackArrow = (ImageView) findViewById(R.id.backArrow);
        mListView = (ListView)findViewById(R.id.listView);
        mBids = new ArrayList<>();
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try{


        }catch (NullPointerException e){
            Log.e(TAG, "onCreate: NullPointerException" + e.getMessage());
            Bids firstBid = new Bids();
            firstBid.setBid(jobPhoto.getJob_description());
            firstBid.setDate_created(jobPhoto.getDate_created());
            firstBid.setUser_id(jobPhoto.getDate_created());

            BidsListAdapter adapter = new BidsListAdapter(mContext, R.layout.snippet_bids, mBids );
            mListView.setAdapter(adapter);
        }


    }


}
