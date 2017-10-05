package com.leakdtech.maintenanceapp.Menu.NavigationDrawr;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.leakdtech.maintenanceapp.Home.ContactUs;
import com.leakdtech.maintenanceapp.Login.LoginOrSignupMainActivity;
import com.leakdtech.maintenanceapp.R;

import static com.leakdtech.maintenanceapp.PostAJob.LocationTools.Geofencing.TAG;

/**
 * Created by user on 12/31/15.
 */
public class SignOutFragment extends Fragment{

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressBar mProgressBar;
    private Context mContext;

    View myView;
    private Button signOutButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.third_layout, container, false);
        signOutButton = (Button) myView.findViewById(R.id.signOutButton);
        mProgressBar = (ProgressBar)myView.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);

                mAuth.signOut();
                getActivity().finish();
            }
        });
        setupFirebaseAuth();
        return myView;
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
                    Intent intent = new Intent(getActivity(), LoginOrSignupMainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
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
