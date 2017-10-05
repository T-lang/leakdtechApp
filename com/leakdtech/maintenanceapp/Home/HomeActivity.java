package com.leakdtech.maintenanceapp.Home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.leakdtech.maintenanceapp.Login.LoginOrSignupMainActivity;
import com.leakdtech.maintenanceapp.Login.SignIn;
import com.leakdtech.maintenanceapp.PostAJob.JobPhoto;
import com.leakdtech.maintenanceapp.R;
import com.leakdtech.maintenanceapp.Utils.BottomNavigationViewHelper;
import com.leakdtech.maintenanceapp.Utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import static com.leakdtech.maintenanceapp.R.id.container;

public class HomeActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private static final String TAG = "HomeActivity";

//    @Override
//    public void onGridImageSelected(JobPhoto jobPhoto, int activityNumber) {
//        Log.d(TAG, "onGridImageSelected:selected an image" + jobPhoto.toString());
//
//        ViewMyJobFragment fragment = new ViewMyJobFragment();
//        Bundle args = new Bundle();
//        args.putParcelable(getString(R.string.job_photo), jobPhoto);
//        args.putInt(getString(R.string.activity_number), activityNumber);
//        fragment.setArguments(args);
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.container, fragment);
//        transaction.addToBackStack(getString(R.string.view_job_post_fragment));
//        transaction.commit();
//
//    }
    private  static final int ACTIVITY_NUM = 0;
    private Context mContext = HomeActivity.this;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: starting");
        initImageLoader();
        setupBottomNavigationView();
        setupViewPager();
        setupFirebaseAuth();
    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }
    private void setupViewPager() {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new ViewMyJobFragment());//index 0
        adapter.addFragment(new HomeFragment()); // index 1
        adapter.addFragment(new SearchFragment()); //index2
        mViewPager  = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(1);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_view_my_job);
        tabLayout.getTabAt(1).setIcon(R.drawable.logo_alone);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_search);


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

    //firebase
    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in." );

        if(user == null){
            Intent intent = new Intent(mContext, LoginOrSignupMainActivity.class);
            startActivity(intent);
        }
    }
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth:setting up firebase auth");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                checkCurrentUser(user);
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mAuth.getCurrentUser());

    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
