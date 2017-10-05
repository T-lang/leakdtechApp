package com.leakdtech.maintenanceapp.Home.Categories.Commercial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.leakdtech.maintenanceapp.R;
import com.leakdtech.maintenanceapp.Utils.BottomNavigationViewHelper;

public class CommercialActivity extends AppCompatActivity {

    private static final String TAG = "CommercialActivity";
    private Context mContext = CommercialActivity.this;
    private  static final int ACTIVITY_NUM = 0;

    private TextView mUndergroundLeaks, mUndergroundLeaksFindExperts,
            mUndergroundLeaksPreviousBid, mBMEPLeaks, mBMEPLeaksFindExperts,
            mBMEPLeaksPreviousBids,mLandscapeLeaks,mLandscapeLeaksFindExperts,
            mLandscapeLeaksPreviousBids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial);
        Log.d(TAG, "onCreate: starting");
        assignViewsAndHandleIntent();
        setupBottomNavigationView();
    }

    /**
     * Bottom Navigation view setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up bottom nav view");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(mContext,this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
    public void assignViewsAndHandleIntent(){
        mUndergroundLeaks =(TextView) findViewById(R.id.undergroundLeaks);
        mUndergroundLeaks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UndergroundLeaks.class);
                startActivity(intent);
            }
        });

        mUndergroundLeaksFindExperts= (TextView) findViewById(R.id.undergroundLeaksFindExperts);
        mUndergroundLeaksPreviousBid= (TextView) findViewById(R.id.previousBidsUndergroundLeaks);
        mBMEPLeaks =(TextView) findViewById(R.id.bMEP);
        mBMEPLeaks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BuildingMEPLeaks.class);
                startActivity(intent);
            }
        });
        mBMEPLeaksFindExperts= (TextView) findViewById(R.id.bMEPFindExperts);
        mBMEPLeaksPreviousBids= (TextView) findViewById(R.id.bMEPPreviousBids);
        mLandscapeLeaks =(TextView) findViewById(R.id.landscapeLeaks);
        mLandscapeLeaks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LandscapeLeaks.class);
                startActivity(intent);
            }
        });
        mLandscapeLeaksFindExperts= (TextView) findViewById(R.id.landscapeLeaksFindExperts);
        mLandscapeLeaksPreviousBids= (TextView) findViewById(R.id.landscapeLeaksPreviousBids);
    }
}
