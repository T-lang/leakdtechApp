package com.leakdtech.maintenanceapp.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.leakdtech.maintenanceapp.Home.HomeActivity;
import com.leakdtech.maintenanceapp.R;
import com.leakdtech.maintenanceapp.customfonts.MyEditText;
import com.leakdtech.maintenanceapp.customfonts.MyTextView;

import static com.leakdtech.maintenanceapp.R.id.sback;
import static com.leakdtech.maintenanceapp.R.id.sin;

/**
 * Created by LYB OJO on 9/11/2017.
 */

public class SignIn extends AppCompatActivity {

    private static final String TAG = "SignIn";
    private Context mContext = SignIn.this;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private ProgressBar mProgressBar;
    private MyEditText mPassword, usernameOrEmail;
    private MyTextView signInButton;
    private ImageView sback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mPassword = (MyEditText) findViewById(R.id.password);
        usernameOrEmail = (MyEditText) findViewById(R.id.usernameOrEmail);

        sback = (ImageView)findViewById(R.id.sinb);
        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SignIn.this,LoginOrSignupMainActivity.class);
                startActivity(it);
            }
        });
        mProgressBar.setVisibility(View.GONE);
        setupFirebaseAuth();
        init();
    }
    private boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: checking string if null.");

        if(string.equals("")){
            return true;
        } else{
            return false;
        }
    }


    private void init() {
        //initialize the button for logging
        signInButton = (MyTextView) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to Log in");

                String email = usernameOrEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (isStringNull(email) && isStringNull(password)) {
                    Toast.makeText(mContext, "FIll in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                                        Toast.makeText(SignIn.this, getString(R.string.auth_failed),
                                                Toast.LENGTH_SHORT).show();
                                        mProgressBar.setVisibility(View.GONE);
                                    } else {
                                        try {
                                            if (user.isEmailVerified()) {
                                                Log.d(TAG, "onComplete: success. email is verified.");
                                                Intent intent = new Intent(SignIn.this, HomeActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(mContext, "Email is not verified \n check your mail inbox.", Toast.LENGTH_SHORT).show();
                                                mProgressBar.setVisibility(View.GONE);
                                                mAuth.signOut();
                                            }

                                        } catch (NullPointerException e) {
                                            Log.e(TAG, "onComplete: NullPointerException: " + e.getMessage());

                                        }
                                    }

                                    // ...
                                }
                            });
                }
            }
        });
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(SignIn.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
    //firebase

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth:setting up firebase auth");

        mAuth = FirebaseAuth.getInstance();
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
