package com.leakdtech.maintenanceapp.PostAJob;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leakdtech.maintenanceapp.R;
import com.leakdtech.maintenanceapp.Utils.FirebaseMethods;
import com.leakdtech.maintenanceapp.Utils.UniversalImageLoader;


/**
 * Created by LYB OJO on 9/20/2017.
 */

public class JobSummary extends AppCompatActivity{
    private static final String TAG = "JobSummary";

    EditText mJobLocation, mJobTitle, mJobDescription, mDate;
    Button mPostaJob, mReturnBack;
    ImageView mJobImage;
    String jobLocation, jobTitle, jobDescription, jobDate, username;
    private Context mContext = JobSummary.this;


    //vars
    private String mAppend = "file:/";
    private int imageCount = 0;
    private String imgUrl;
    private Intent intent;
    private Bitmap bitmap;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_summary);
        Log.d(TAG, "onCreate: starting");
        mFirebaseMethods = new FirebaseMethods(mContext);
        setProfileWidgets();
        setupFirebaseAuth();
    }

    public void setProfileWidgets(){
        //initialising widgets
        Intent i= getIntent();
        mJobLocation = (EditText)findViewById(R.id.jobLocation);
        mJobTitle = (EditText)findViewById(R.id.jobTitle);
        mJobDescription = (EditText)findViewById(R.id.jobDescription);
        mDate = (EditText)findViewById(R.id.jobDate);
        //initialising
        jobLocation = mJobLocation.toString();
        jobLocation = i.getStringExtra("location");
        mJobLocation.setText(jobLocation);
        jobTitle = i.getStringExtra("jobTitle");
        Log.d(TAG, "setProfileWidgets: gotten Job title" + jobTitle + ", " + jobDescription +", " +jobDate+", " +jobLocation);
        mJobTitle.setText(jobTitle);
        jobDescription = i.getStringExtra("jobDescription");

        mJobDescription.setText(jobDescription);
        jobDate = i.getStringExtra("jobDate");
        mDate.setText(jobDate);

        mReturnBack = (Button) findViewById(R.id.goBackButton);
        mReturnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Closing Activity");
                finish();
            }
        });
        mPostaJob = (Button) findViewById(R.id.postAJobButton);
        mPostaJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Attempting to upload new photo");
                String jobDate = mDate.getText().toString();
                String jobTitle = mJobTitle.getText().toString();
                String jobDescription = mJobDescription.getText().toString();
                String jobLocation = mJobLocation.getText().toString();
                Toast.makeText(JobSummary.this, "Attempting to Post your job", Toast.LENGTH_SHORT).show();

                if (intent.hasExtra(getString(R.string.selected_image))){
                    imgUrl = intent.getStringExtra(getString(R.string.selected_image));
                    mFirebaseMethods.uploadNewJobPost(getString(R.string.new_job_post),jobDate,jobTitle,jobDescription,jobLocation,imageCount,imgUrl,null);
                }else if (intent.hasExtra(getString(R.string.selected_bitmap))){
                    bitmap = (Bitmap) intent.getParcelableExtra(getString(R.string.selected_bitmap));
                    mFirebaseMethods.uploadNewJobPost(getString(R.string.new_job_post),jobDate,jobTitle,jobDescription,jobLocation,imageCount,null,bitmap);

                }
            }
        });

        setImage();

    }

    public void setImage(){
        intent = getIntent();
        mJobImage = (ImageView)findViewById(R.id.jobImage);

        if (intent.hasExtra(getString(R.string.selected_image))){
            imgUrl = intent.getStringExtra(getString(R.string.selected_image));
            UniversalImageLoader.setImage(imgUrl, mJobImage,null,mAppend);
        }else if (intent.hasExtra(getString(R.string.selected_bitmap))){
            bitmap = (Bitmap) intent.getParcelableExtra(getString(R.string.selected_bitmap));
            jobTitle = intent.getStringExtra("jobTitle");
            jobLocation = intent.getStringExtra("location");
            jobDescription = intent.getStringExtra("jobDescription");
            jobDate = intent.getStringExtra("jobDate");
            Log.d(TAG, "setImage: setting new image bitmap");
            mJobImage.setImageBitmap(bitmap);
            mJobTitle.setText(jobTitle);
            mJobDescription.setText(jobDescription);
            mDate.setText(jobDate);
            mJobLocation.setText(jobLocation);

        }


    }

    //firebase

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth:setting up firebase auth");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        Log.d(TAG,"onDataChange: imageCount: " + imageCount );


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

                imageCount = mFirebaseMethods.getImageCount(dataSnapshot);
                Log.d(TAG,"onDataChange: imageCount: " + imageCount );
                // retrieve job post for user


                // retrieve user information
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

