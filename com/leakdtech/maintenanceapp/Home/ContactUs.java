package com.leakdtech.maintenanceapp.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leakdtech.maintenanceapp.R;

import static android.R.attr.name;

/**
 * Created by LYB OJO on 9/11/2017.
 */

public class ContactUs extends AppCompatActivity {


    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_contact_us);
        final EditText nameField = (EditText) findViewById(R.id.inputNameContactUs);
        final String name = nameField.getText().toString();

        final EditText emailField = (EditText) findViewById(R.id.inputEmailContactUs);
        String email = emailField.getText().toString();

        final EditText messageField = (EditText) findViewById(R.id.inputMessageContactUs);
        String message = messageField.getText().toString();

        final EditText phoneField = (EditText) findViewById(R.id.inputPhoneContactUs);
        String phone = phoneField.getText().toString();

        mSubmitButton = (Button) findViewById(R.id.submitButtonContactUs);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactUs.this, HomeActivity.class);
                Toast.makeText(ContactUs.this, "Thank you " + name +
                        ",We would get back to you shortly.", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

    }

    public void infoFromEditText(){
        EditText Chrome = (EditText) findViewById(R.id.inputPhoneContactUs);
    }
}

