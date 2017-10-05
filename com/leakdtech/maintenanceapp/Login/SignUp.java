package com.leakdtech.maintenanceapp.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.leakdtech.maintenanceapp.customfonts.MyEditText;
import com.leakdtech.maintenanceapp.customfonts.MyTextView;

import static android.R.attr.name;
import static com.leakdtech.maintenanceapp.R.id.signUp;

/**
 * Created by LYB OJO on 9/11/2017.
 */

public class SignUp extends AppCompatActivity
{
    private static final String TAG = "SignUp";
    private Context mContext = SignUp.this;

  //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    private ProgressBar mProgressBar;
    private MyEditText mPassword, mEmail, mUsername, mFullName;
    private MyTextView signUp;
    private ImageView sback;
    private String email, password, username, fullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseMethods = new FirebaseMethods(mContext);
        sback = (ImageView)findViewById(R.id.sback);
        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(SignUp.this, LoginOrSignupMainActivity.class);
                startActivity(it);

            }
        });

        initWidgets();
        setupFirebaseAuth();
        init();
    }

    private void init(){
        signUp = (MyTextView)findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                username = mUsername.getText().toString();
                fullname = mFullName.getText().toString();
                password = mPassword.getText().toString();
                if(checkInputs(email,username,fullname,password)){
                    mProgressBar.setVisibility(View.VISIBLE);
                    firebaseMethods.registerNewEmail(email,password,fullname,username);
                }
            }
        });

    }

    private boolean checkInputs(String email, String password, String fullname, String username){
        Log.d(TAG, "checkingInputs: checking string if null.");

        if(email.equals("") || username.equals("") || fullname.equals("") ||password.equals("")){
            Toast.makeText(mContext, "Fill all fields and re-type password",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initWidgets(){
        mEmail = (MyEditText)findViewById(R.id.email);
        mPassword = (MyEditText)findViewById(R.id.password);
        mUsername = (MyEditText)findViewById(R.id.username);
        mFullName = (MyEditText)findViewById(R.id.fullName);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        signUp = (MyTextView)findViewById(R.id.signUp);


    }
    private boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: checking string if null.");

        if(string.equals("")){
            return true;
        } else{
            return false;
        }
    }

    //firebase
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth:setting up firebase auth");


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
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            firebaseMethods.addNewUser(email,"", username, fullname );
                            Toast.makeText(mContext, "Sign Up successful sending verification Email",Toast.LENGTH_SHORT).show();
                            mAuth.signOut();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    finish();
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
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
