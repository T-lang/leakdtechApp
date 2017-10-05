package com.leakdtech.maintenanceapp.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leakdtech.maintenanceapp.R;

/**
 * Created by LYB OJO on 9/11/2017.
 */

public class ScheduleACallBack extends AppCompatActivity {


    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_a_call_back);
        final EditText nameField = (EditText) findViewById(R.id.inputNameScheduleCallBack);
        final String name = nameField.getText().toString();

        final EditText lastOrderField = (EditText) findViewById(R.id.inputLastOrderScheduleCallBack);
        String lastOrderNumber = lastOrderField.getText().toString();

        final EditText messageField = (EditText) findViewById(R.id.inputMessageScheduleCallBack);
        String message = messageField.getText().toString();

        final EditText phoneField = (EditText) findViewById(R.id.inputPhoneScheduleCallBack);
        String phone = phoneField.getText().toString();

        mSubmitButton = (Button) findViewById(R.id.submitButtonScheduleACallBack);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleACallBack.this, HomeActivity.class);

                Toast.makeText(ScheduleACallBack.this, "Thank you " + name +
                        ",We would get back to you shortly.", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });



    }

    public void infoFromEditText(){
        EditText Chrome = (EditText) findViewById(R.id.inputPhoneContactUs);
    }
}