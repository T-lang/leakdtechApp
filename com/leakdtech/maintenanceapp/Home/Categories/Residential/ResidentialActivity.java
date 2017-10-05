package com.leakdtech.maintenanceapp.Home.Categories.Residential;

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

public class ResidentialActivity extends AppCompatActivity {

    private static final String TAG = "ResidentialActivity";
    private Context mContext = ResidentialActivity.this;
    private  static final int ACTIVITY_NUM = 0;

    private TextView mGardenArealeaks, mGardenAreaPreviousBids, mGardenAreaFindExpert,mInteriorPlumbingLeaks, mInteriorPlumbingPreviousBids,
    mInteriorGardensFindExperts, mPipesInspection, mPipesInspectionPreviousBids, mPipesinspectionFindExperts ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residential);

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
        mGardenArealeaks =(TextView) findViewById(R.id.gardenArealRes);
        mGardenArealeaks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GardenAreaLeaks.class);
                startActivity(intent);
            }
        });

        mGardenAreaPreviousBids= (TextView) findViewById(R.id.previousBidsGardenArea);
        mGardenAreaFindExpert= (TextView) findViewById(R.id.findExpertsGardAreaLeaks);
        mInteriorPlumbingLeaks =(TextView) findViewById(R.id.interiorPlumbingLeaks);
        mInteriorPlumbingLeaks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, InteriorPlumbingLeaks.class);
                startActivity(intent);
            }
        });
        mInteriorGardensFindExperts= (TextView) findViewById(R.id.findExpertsInteriorPlumbing);
        mInteriorPlumbingPreviousBids= (TextView) findViewById(R.id.previousBidsInteriorPlumbing);
        mPipesInspection =(TextView) findViewById(R.id.pipesInspection);
        mPipesInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PipesInspection.class);
                startActivity(intent);
            }
        });
        mPipesinspectionFindExperts= (TextView) findViewById(R.id.findExpertsPipesInspection);
        mPipesInspectionPreviousBids= (TextView) findViewById(R.id.previousBidsPipesInspection);
    }

}
