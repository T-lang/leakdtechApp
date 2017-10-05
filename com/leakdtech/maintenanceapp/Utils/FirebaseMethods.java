package com.leakdtech.maintenanceapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.leakdtech.maintenanceapp.Home.HomeActivity;
import com.leakdtech.maintenanceapp.Home.HomeFragment;
import com.leakdtech.maintenanceapp.Home.ViewMyJobFragment;
import com.leakdtech.maintenanceapp.PostAJob.JobPhoto;
import com.leakdtech.maintenanceapp.R;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.R.attr.description;
import static com.leakdtech.maintenanceapp.PostAJob.LocationTools.Geofencing.TAG;
import static com.leakdtech.maintenanceapp.R.drawable.user;
import static com.leakdtech.maintenanceapp.R.id.jobTitle;

/**
 * Created by LYB OJO on 9/15/2017.
 */

public class FirebaseMethods {
    private static final String TAG = "FirebaseMethods";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference mStorageReference;
    private String userID;

    //vars
    private Context mContext;
    private double mPhotoUploadProgress = 0;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mContext = context;

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public void uploadNewJobPost(String photoType, final String job_date, final String job_title, final String job_description,
                                 final String job_location, int count,final String imgUrl, Bitmap bm){
        Log.d(TAG, "uploadNewJobPost: uploading new job post " );
        FilePaths filePaths = new FilePaths();
        //case 1 new job post
        if(photoType.equals(mContext.getString(R.string.new_job_post))){
            Log.d(TAG, "uploadNewJobPost: uploading new job post " );
            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = mStorageReference
                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/photo" + (count + 1));

            if(bm == null){
                bm = ImageManager.getBitmap(imgUrl);

            }
            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 80);

            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri firebaseUrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(mContext, "Your Job has been Posted", Toast.LENGTH_SHORT).show();

                    //add the new job to 'job_post' and user_job_posts node
                    addJobPhotoToDatabase(job_date ,job_title,job_description,job_location,firebaseUrl.toString(),job_title);

                    //navigate to view my job fragment so the user can see their posts
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    mContext.startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: Job upload failed.");
                Toast.makeText(mContext, "Your job could not be posted", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    if (progress - 15 > mPhotoUploadProgress) {
                        Toast.makeText(mContext, "photo upload progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }
                    Log.d(TAG, "onProgress: upload progress: " + progress + "% done");
                }
            });
        }

        //case two new profile photo

    }



    private void addJobPhotoToDatabase(String job_date, String job_title,  String job_description, String job_location,String url,String tags ){
        String newJobPostKey = myRef.child(mContext.getString(R.string.dbname_user_jobs_post)).push().getKey();

        JobPhoto jobPhoto = new JobPhoto();
        jobPhoto.setJob_date(job_date);
        Log.d(TAG, "setting:" + job_date + " to the database ");
        jobPhoto.setJob_description(job_description);
        jobPhoto.setJob_title(job_title);
        jobPhoto.setJob_location(job_location);
        jobPhoto.setDate_created(getTimeStamp());
        jobPhoto.setImage_path(url);
        jobPhoto.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
        jobPhoto.setTags(tags);
        jobPhoto.setPhoto_id(newJobPostKey);

        //insert into db
        myRef.child(mContext.getString(R.string.dbname_user_jobs_post)).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(newJobPostKey).setValue(jobPhoto);
        myRef.child(mContext.getString(R.string.dbname_jobs_post)).child(newJobPostKey).setValue(jobPhoto);

    }

    private String getTimeStamp() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Dubai"));
        return sdf.format(new Date());
    }

    public int getImageCount(DataSnapshot dataSnapshot){
        int count = 0;
        for (DataSnapshot ds : dataSnapshot
                .child(mContext.getString(R.string.dbname_user_jobs_post))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()){
            count++;

        }
        return count;
    }

    public void registerNewEmail(final String email, String password, final String username, final String fullname) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onComplete: Failed=" + task.getException().getMessage());

                        } else if (task.isSuccessful()) {
                            sendVerificationEmail();
                            userID = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: Authstate changed: " + userID);
                        }

                    }
                });
    }

    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                Toast.makeText(mContext, "couldn't send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void addNewUser(String email, String user_photo, String username, String fullname) {

        User user = new User(userID, email, username);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .setValue(user);


        UserAccountSettings settings = new UserAccountSettings(
                fullname,
                0,
                user_photo,
                username,
                userID
        );

        myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .setValue(settings);


    }


    public UserSettings getUserSettings(DataSnapshot dataSnapshot) {
        Log.d(TAG, "getUserSettings: retrieving user account settings from firebase.");

        UserAccountSettings settings = new UserAccountSettings();
        User user = new User();


        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            // user_account_settings node
            if (ds.getKey().equals(mContext.getString(R.string.dbname_user_account_settings))) {
                Log.d(TAG, "getUserSettings: datasnapshot: " + ds);

                try {
                    settings.setFullname(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getFullname()
                    );
                    settings.setUsername(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getUsername()
                    );
                    settings.setJobs_posted(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getJobs_posted()
                    );
                    settings.setUser_photo(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getUser_photo()
                    );

                    Log.d(TAG, "getUserSettings: retreived user_account_settings information " + settings.toString());
                } catch (NullPointerException e) {
                    Log.e(TAG, "getUserSettings: NullPointerException" + e.getMessage());
                }


            }
  // users node
            if (ds.getKey().equals(mContext.getString(R.string.dbname_users))) {
                Log.d(TAG, "getUserSettings: datasnapshot: " + ds);

                user.setUsername(
                        ds.child(userID)
                                .getValue(User.class)
                                .getUsername()
                );
                user.setEmail(
                        ds.child(userID)
                                .getValue(User.class)
                                .getEmail()
                );
                user.setUser_id(
                        ds.child(userID)
                                .getValue(User.class)
                                .getUser_id()
                );
                Log.d(TAG, "getUserSettings: retreived users information " + user.toString());

            }

        }
        return new UserSettings(user, settings);
    }

    public void updateUsername(String username){
        Log.d(TAG, "updateUsername: upadting username to: " + username);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);

        myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);

    }

    public void updateEmail(String email){
        Log.d(TAG, "updateEmail: upadting email to: " + email);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .child(mContext.getString(R.string.field_email))
                .setValue(email);

    }
}




    //    private void checkIfUsernameExists(final String username) {
//        Log.d(TAG,"checkIfUsernameExists: checking if" + username + "already exists in the database");
//
//        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
//        Query query = reference
//                .child(getString(R.string.dbname_users))
//                .orderByChild(getString(R.string.field_username))
//                .equalTo(username);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
//                    if(singleSnapshot.exists()){
//                        Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + singleSnapshot.getValue(User.class).getUsername());
//                        Toast.makeText(mContext, "Username already exists!", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//                String mUsername = "";
//                mUsername = username ;
//                //add new user to the database
//                firebaseMethods.addNewUser(mail, mUsername, "", "", "");//added ,""
//                Toast.makeText(mContext, "Signup successful. sending verification email ", Toast.LENGTH_SHORT).show();
//
//                mAuth.signOut();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

