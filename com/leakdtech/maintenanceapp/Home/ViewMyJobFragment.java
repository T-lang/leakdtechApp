package com.leakdtech.maintenanceapp.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.leakdtech.maintenanceapp.PostAJob.JobPhoto;
import com.leakdtech.maintenanceapp.R;
import com.leakdtech.maintenanceapp.Utils.FirebaseMethods;
import com.leakdtech.maintenanceapp.Utils.ImageAdapter;
import com.leakdtech.maintenanceapp.Utils.UniversalImageLoader;
import com.leakdtech.maintenanceapp.Utils.User;
import com.leakdtech.maintenanceapp.Utils.UserAccountSettings;
import com.leakdtech.maintenanceapp.Utils.UserSettings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.leakdtech.maintenanceapp.R.id.gridView;
import static com.leakdtech.maintenanceapp.R.id.username;

/**
 * Created by LYB OJO on 8/28/2017.
 */

public class ViewMyJobFragment extends Fragment {
    private static final String TAG = "ViewMyJobFragment";
    private static final int NUM_GRID_COLUMNS = 1;
    private static final int ACTIVITY_NUM = 0;

    public interface OnBidThreadSelectedListener{
        void onBidThreadSelectedListener(JobPhoto jobPhoto);
    }
    OnBidThreadSelectedListener mOnBidThreadSelectedListener;

    //widgets
    private BottomNavigationView bottomNavigationView;
    private CircleImageView mUserPhoto;
    private TextView mJobDescription, mJobDate, mjobLocation, mJobTitle, mUsername, mTimeStamp, mTapTobid;
    private ImageView mJobPostImage;
    private String jobPostUsername;
    private String jobPostUrl;

    //vars
    private JobPhoto mJobPhoto;
    private int mActivityNumber = 86;
    private String photoUsername = "";
    private String profilePhotoUrl = "";
    private UserAccountSettings mUserAccountSettings;


    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference mStorageReference;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.snippet_job_post_full, container, false);
        Log.d(TAG, "onCreateView: starting");
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
        mTapTobid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewBidsActivity.class);
                startActivityForResult(intent, mActivityNumber );

            }
        });



        setupFirebaseAuth();
//      setupJobViewsGrid();
        return view;
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







    //----------------------- setup firebase auth object----------------
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up setupFirebaseAuth");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // retrieve user info from database
//                setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));

                // retrieve job Post info from database
//                setJobPost(mFirebaseMethods.getUserSettings(dataSnapshot));
//                mFirebaseMethods.addNewJobPost(job_date,job_description, job_title,user_name );

                //retrieve bids from database


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}


//package com.leakdtech.maintenanceapp.Home;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.StorageReference;
//import com.leakdtech.maintenanceapp.PostAJob.JobPhoto;
//import com.leakdtech.maintenanceapp.R;
//import com.leakdtech.maintenanceapp.Utils.FirebaseMethods;
//import com.leakdtech.maintenanceapp.Utils.ImageAdapter;
//import com.leakdtech.maintenanceapp.Utils.UniversalImageLoader;
//import com.leakdtech.maintenanceapp.Utils.User;
//import com.leakdtech.maintenanceapp.Utils.UserAccountSettings;
//import com.leakdtech.maintenanceapp.Utils.UserSettings;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Locale;
//import java.util.Map;
//import java.util.TimeZone;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//import static com.leakdtech.maintenanceapp.R.id.gridView;
//import static com.leakdtech.maintenanceapp.R.id.username;
//
///**
// * Created by LYB OJO on 8/28/2017.
// */
//
//public class ViewMyJobFragment extends Fragment {
//    private static final String TAG = "ViewMyJobFragment";
//    private static final int NUM_GRID_COLUMNS = 1;
//    private static final int ACTIVITY_NUM = 0;
//
//    public interface OnBidThreadSelectedListener{
//        void onBidThreadSelectedListener(JobPhoto jobPhoto);
//    }
//    OnBidThreadSelectedListener mOnBidThreadSelectedListener;
//
//    public interface OnGridImageSelectedListener{
//        void onGridImageSelected(JobPhoto photo, int activityNumber);
//    }
//   OnGridImageSelectedListener mOnGridImageSelectedListener;
//
//    //widgets
//    private BottomNavigationView bottomNavigationView;
//    private CircleImageView mUserPhoto;
//    private TextView mJobDescription, mJobDate, mjobLocation, mJobTitle, mUsername, mTimeStamp, mTapTobid;
//    private ImageView mJobPostImage;
//    private String jobPostUsername;
//    private String jobPostUrl;
//
//    //vars
//    private JobPhoto mJobPhoto;
//    private int mActivityNumber = 0;
//    private String photoUsername = "";
//    private String profilePhotoUrl = "";
//    private UserAccountSettings mUserAccountSettings;
//
//
//    //firebase
//    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;
//    private FirebaseDatabase mFirebaseDatabase;
//    private DatabaseReference myRef;
//    private StorageReference mStorageReference;
//
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.snippet_job_post_full, container, false);
//        Log.d(TAG, "onCreateView: starting");
//        Log.d(TAG, "onCreateView: opening layout chosen");
//        mUserPhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
//        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottomNavigationViewBar);
//        mJobDescription = (TextView) view.findViewById(R.id.nameJobDescription);
//        mJobTitle = (TextView) view.findViewById(R.id.nameJobTitle);
//        mJobDate = (TextView) view.findViewById(R.id.jobDate);
//        mUsername = (TextView) view.findViewById(R.id.username);
//        mTimeStamp = (TextView) view.findViewById(R.id.tv_job_post_time);
//        mJobPostImage = (ImageView) view.findViewById(R.id.iv_job_post_image);
//        mTapTobid = (TextView) view.findViewById(R.id.tapToBid);
//
//
//        try{
//            mJobPhoto = getPhotoFromBundle();
//            UniversalImageLoader.setImage(mJobPhoto.getImage_path(), mJobPostImage, null, "");
//            mActivityNumber = getActivityNumFromBundle();
//
//        }catch (NullPointerException e){
//            Log.e(TAG, "onCreateView: NullPointerException: " + e.getMessage() );
//        }
//        getJobDetails();
//
//
////      setupJobViewsGrid();
//        return view;
//    }
//
//
//    //    private void getJobDetails(){
////        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
////        Query query = reference
////                .child(getString(R.string.dbname_user_account_settings))
////                .orderByChild(getString(R.string.field_user_id))
////                .equalTo(mJobPhoto.getUser_id());
////        query.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
////                    mUserAccountSettings = singleSnapshot.getValue(UserAccountSettings.class);
////
////                }
////                setupWidgets();
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////                Log.d(TAG, "onCancelled: query cancelled.");
////            }
////        });
////    }
//
//    private void getJobDetails(){
//        Log.d(TAG, "getPhotoDetails: retrieving photo details.");
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        Query query = reference
//                .child(getString(R.string.dbname_user_jobs_post))
//                .orderByChild(getString(R.string.field_user_id))
//                .limitToLast(1)
//                ;
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                    mUserAccountSettings = singleSnapshot.getValue(UserAccountSettings.class);
//
//                }
//                setupWidgets();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d(TAG, "onCancelled: query cancelled.");
//            }
//        });
//    }
//
//    private void setupWidgets(){
//        String timestampDiff = getTimestampDifference();
//        if(!timestampDiff.equals("0")){
//            mTimeStamp.setText(timestampDiff + " DAYS AGO");
//        }else{
//            mTimeStamp.setText("TODAY");
//        }
//        UniversalImageLoader.setImage(mUserAccountSettings.getUser_photo(), mUserPhoto, null, "");
//        mUsername.setText(mUserAccountSettings.getUsername());
//    }
//
//    /**
//     * Returns a string representing the number of days ago the post was made
//     * @return
//     */
//    private String getTimestampDifference(){
//        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");
//
//        String difference = "";
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK);
//        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Dubai"));//google 'android list of timezones'
//        Date today = c.getTime();
//        sdf.format(today);
//        Date timestamp;
//        final String photoTimestamp = mJobPhoto.getDate_created();
//        try{
//            timestamp = sdf.parse(photoTimestamp);
//            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24 )));
//        }catch (ParseException e){
//            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage() );
//            difference = "0";
//        }
//        return difference;
//    }
//
//    private int getActivityNumFromBundle(){
//        Log.d(TAG, "getActivityNumFromBundle: arguments: " + getArguments());
//
//        Bundle bundle = this.getArguments();
//        if(bundle != null) {
//            return bundle.getInt(getString(R.string.activity_number));
//        }else{
//            return 0;
//        }
//    }
//
//    /**
//     * retrieve the photo from the incoming bundle from profileActivity interface
//     * @return
//     */
//
//
//    private JobPhoto getPhotoFromBundle(){
//        Log.d(TAG, "getPhotoFromBundle: arguments: " + getArguments());
//
//        Bundle bundle = this.getArguments();
//        if(bundle != null) {
//            return bundle.getParcelable(getString(R.string.job_photo));
//        }else{
//            return null;
//        }
//    }
//
////    private void setupJobViewsGrid(){
////        Log.d(TAG, "setupJobViewsGrid: starting");
////        final ArrayList<JobPhoto> photos = new ArrayList<>();
////        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
////        Query query = reference
////                .child(getString(R.string.dbname_user_jobs_post))
////                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
////        query.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                for ( DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
////
////    JobPhoto jobPhoto = new JobPhoto();
////    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
////                    jobPhoto.setImage_path(objectMap.get(getString(R.string.field_image_path)).toString());
////                    jobPhoto.setJob_date(objectMap.get(getString(R.string.field_job_date)).toString());
////                    jobPhoto.setPhoto_id(objectMap.get(getString(R.string.field_photo_id)).toString());
////                    jobPhoto.setJob_title(objectMap.get(getString(R.string.field_job_title)).toString());
////                    jobPhoto.setDate_created(objectMap.get(getString(R.string.field_date_created)).toString());
////                    jobPhoto.setJob_description(objectMap.get(getString(R.string.field_job_description)).toString());
////                    jobPhoto.setTags(objectMap.get(getString(R.string.field_tags)).toString());
////                    jobPhoto.setUser_id(objectMap.get(getString(R.string.field_user_id)).toString());
////
////                }
////                //setup our image grid
////                int gridWidth = getResources().getDisplayMetrics().widthPixels;
////                int imageWidth = gridWidth/NUM_GRID_COLUMNS;
////                gridView.setColumnWidth(imageWidth);
////
////                ArrayList<String> imgUrls = new ArrayList<String>();
////                for(int i = 0; i < photos.size(); i++){
////                    imgUrls.add(photos.get(i).getImage_path());
////                }
////                ImageAdapter adapter = new ImageAdapter(getActivity(),R.layout.layout_job_imageview,
////                        "", imgUrls);
////                gridView.setAdapter(adapter);
////                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                    @Override
////                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                     mOnGridImageSelectedListener.onGridImageSelected(photos.get(position), ACTIVITY_NUM);
////                    }
////                });
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////                Log.d(TAG, "onCancelled: query cancelled.");
////            }
////        });
////    }
//
//
//    @Override
//    public void onAttach(Context context) {
//        try{
//     mOnGridImageSelectedListener = (OnGridImageSelectedListener) getActivity();
//       }catch (ClassCastException e){
//         Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
//       }
//        super.onAttach(context);
//    }
//
//    //----------------------- setup firebase auth object----------------
//    private void setupFirebaseAuth(){
//        Log.d(TAG, "setupFirebaseAuth: setting up setupFirebaseAuth");
//
//        mAuth = FirebaseAuth.getInstance();
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        myRef = mFirebaseDatabase.getReference();
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//                // ...
//            }
//        };
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                // retrieve user info from database
////                setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));
//
//                // retrieve job Post info from database
////                setJobPost(mFirebaseMethods.getUserSettings(dataSnapshot));
////                mFirebaseMethods.addNewJobPost(job_date,job_description, job_title,user_name );
//
//                //retrieve bids from database
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
//}
//
