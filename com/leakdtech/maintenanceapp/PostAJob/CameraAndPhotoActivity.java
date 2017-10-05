package com.leakdtech.maintenanceapp.PostAJob;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.leakdtech.maintenanceapp.Home.SectionPagerAdapter;
import com.leakdtech.maintenanceapp.R;
import com.leakdtech.maintenanceapp.Utils.Permissions;

/**
 * Created by LYB OJO on 9/25/2017.
 */

public class CameraAndPhotoActivity extends AppCompatActivity {
    private static final int VERIFY_PERMISSIONS_REQUEST = 1;
    private static final String TAG = "CameraAndPhotoActivity";

    private ViewPager mViewPager;
    private Context mContext = CameraAndPhotoActivity.this;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_photo);
        Log.d(TAG, "onCreate: starting");
        setupViewPager();

        if(checkPermissionsArray(Permissions.PERMISSIONS)){

        }else{
            verifyPermissions(Permissions.PERMISSIONS);
        }
    }

    public int getTask(){
      Log.d(TAG, "getTask: TASK: " + getIntent().getFlags());
       return getIntent().getFlags();
       }

    public int getCurrentTabNumber(){
        return mViewPager.getCurrentItem();
    }
    private void setupViewPager(){
        SectionPagerAdapter adapter =  new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GalleryFrag());
        adapter.addFragment(new PhotoFragment());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsBottom);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setText(getString(R.string.gallery));
        tabLayout.getTabAt(1).setText(getString(R.string.photo));
    }


    public void verifyPermissions(String[] permissions){
        Log.d(TAG, "verifyPermissions: verifying permissions.");

        ActivityCompat.requestPermissions(
                CameraAndPhotoActivity.this,
                permissions,
                VERIFY_PERMISSIONS_REQUEST
        );
    }

    public boolean checkPermissionsArray(String[] permissions){
        Log.d(TAG, "checkPermissionsArray: checking permissions array.");

        for(int i = 0; i< permissions.length; i++){
            String check = permissions[i];
            if(!checkPermissions(check)){
                return false;
            }
        }
        return true;
    }


    public boolean checkPermissions(String permission){
        Log.d(TAG, "checkPermissions: checking permission: " + permission);

        int permissionRequest = ActivityCompat.checkSelfPermission(CameraAndPhotoActivity.this, permission);

        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermissions: \n Permission was not granted for: " + permission);
            return false;
        }
        else{
            Log.d(TAG, "checkPermissions: \n Permission was granted for: " + permission);
            return true;
        }
    }

    public void getPostAjobIntentExtras(){
//        Intent intent1 = getIntent().getStringExtra(R.string.job_location);
//        Intent intent1 = getIntent().getStringExtra(R.string.job_description);
//        Intent intent1 = getIntent().getStringExtra(R.string.job_title);
//        Intent intent1 = getIntent().getStringExtra(R.string.job_date);



    }


}
