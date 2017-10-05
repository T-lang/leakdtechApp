package com.leakdtech.maintenanceapp.Home.Categories.PetroChemical;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.leakdtech.maintenanceapp.R;
import com.leakdtech.maintenanceapp.Utils.BottomNavigationViewHelper;

public class PetroChemicalActivity extends AppCompatActivity {
    private static final String TAG = "PetroChemicalActivity";
    private Context mContext = PetroChemicalActivity.this;
    private  static final int ACTIVITY_NUM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petrochemical);
        Log.d(TAG, "onCreate: starting");
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
}
