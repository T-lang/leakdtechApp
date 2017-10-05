package com.leakdtech.maintenanceapp.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.leakdtech.maintenanceapp.Home.HomeActivity;
import com.leakdtech.maintenanceapp.Menu.MenuActivity;
import com.leakdtech.maintenanceapp.Menu.NavigationDrawr.NavigationDrawerMain;
import com.leakdtech.maintenanceapp.PostAJob.PostAJobActivity;
import com.leakdtech.maintenanceapp.R;


public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHelper";


    @SuppressLint("LongLogTag")
    public static void setupBottomNavigationView(BottomNavigationView bottomNavigationView) {
        Log.d(TAG, "setupBottomNavigationView: Setting up");
    }

    public static void enableNavigation(final Context context, final Activity callingActivity, BottomNavigationView view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        Intent intent1 = new Intent(context, HomeActivity.class);//ACTIVITY_NUM=0
                        context.startActivity(intent1);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                    case R.id.ic_post_a_job:
                        Intent intent2 = new Intent(context, PostAJobActivity.class);//ACTIVITY_NUM=1
                        context.startActivity(intent2);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                    case R.id.ic_menu:
                        Intent intent5 = new Intent(context, MenuActivity.class);//ACTIVITY_NUM=2
                        context.startActivity(intent5);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                }
                return false;
            }
        });
    }
}
