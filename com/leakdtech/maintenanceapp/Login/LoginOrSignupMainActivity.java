package com.leakdtech.maintenanceapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.leakdtech.maintenanceapp.R;

/**
 * Created by LYB OJO on 9/11/2017.
 */

public class LoginOrSignupMainActivity extends AppCompatActivity{
    TextView sin;
    LinearLayout circle;
    private ProgressBar mProgressBar;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_signup_source);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        circle = (LinearLayout)findViewById(R.id.circle);
        sin = (TextView)findViewById(R.id.sin);

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                Intent it = new Intent(LoginOrSignupMainActivity.this,SignUp.class);
                startActivity(it);

            }
        });
        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                Intent it = new Intent(LoginOrSignupMainActivity.this,SignIn.class);
                startActivity(it);
            }
        });

    }
}
