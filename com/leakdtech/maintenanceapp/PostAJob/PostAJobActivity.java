package com.leakdtech.maintenanceapp.PostAJob;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leakdtech.maintenanceapp.PostAJob.LocationTools.LaunchMapsActivity;
import com.leakdtech.maintenanceapp.R;
import com.leakdtech.maintenanceapp.Utils.BottomNavigationViewHelper;
import com.leakdtech.maintenanceapp.customfonts.MyEditText;
import com.leakdtech.maintenanceapp.customfonts.MyTextView;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class PostAJobActivity extends AppCompatActivity {
    private static final String TAG = "PostAJobActivity";
    private final static int ACTIVITY_NUM = 1;
    private Context mContext = PostAJobActivity.this;
    ImageButton datePicker;
    private ImageView ivJobPost;
    TextView getPictureWithCameraOrGallery ;
    MyEditText jobTitle, jobDescription, jobLocation;
    FloatingActionButton nextButton;
    ImageButton mapsButton;
    String mlocation, mTitle, mSelectedImage, mJobDescription, mJobDate;
    MyTextView dateView;
    //nT
    private Bitmap mImageBitmap;
    static final int REQUEST_IMAGE_CAPTURE = 72;
    private String mCurrentPhotoPath;

    int year_x, month_x,day_x;
    static final int DIALOG_ID =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_a_job);
        Log.d(TAG, "onCreate: starting");
        ivJobPost = (ImageView)findViewById(R.id.ivJobPostImage);

//        ivJobPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent cameraIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
//                    // Create the File where the photo should go
//                    File photoFile = null;
//                    try {
//                        photoFile = createImageFile();
//                    } catch (IOException ex) {
//                        // Error occurred while creating the File
//                        Log.i(TAG, "IOException");
//                    }
//                    // Continue only if the File was successfully created
//                    if (photoFile != null) {
//                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
//                        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
//                    }
//                }
//            }
//        });
        getIntentExtras();


        mapsButton = (ImageButton) findViewById(R.id.mapButton);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LaunchMapsActivity.class);
                startActivity(intent);
            }
        });
        pipesInspectionExtra();
        initialiseCalendarWidgets();
        sendIntentExtras();
        showDatePickerDialogOnClick();
        setupBottomNavigationView();
    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==111){
//            if(resultCode== Activity.RESULT_OK){
//                jobLocation = (EditText) findViewById(R.id.location);
//                Intent intentLocation =getIntent();
//                location= intentLocation.getStringExtra("location");
//                jobLocation.setText(location);
//            }
//            if(resultCode==Activity.RESULT_CANCELED){
//
//            }
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void btnTakePhotoFromCamera(View v){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,REQUEST_IMAGE_CAPTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE){
                mImageBitmap = (Bitmap) data.getExtras().get("data");
                ivJobPost.setImageBitmap(mImageBitmap);
            }
        }
    }

    public void initialiseCalendarWidgets(){
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        dateView = (MyTextView) findViewById(R.id.dateShow);
        dateView.setText(cal.getTime().toString());
    }
    public void pipesInspectionExtra(){
        jobTitle = (MyEditText)findViewById(R.id.etJobTitle);
        Log.d(TAG, "found: " + jobTitle);
        Intent pipesInspectionIntent = getIntent();
        Log.d(TAG, "pipesInspection extra in home activity: " + mTitle);
        mTitle = pipesInspectionIntent.getStringExtra("pISelection");
        jobTitle.setText(mTitle);
    }
    public void getIntentExtras(){
        jobLocation = (MyEditText) findViewById(R.id.location);
        Intent intentLocation =getIntent();
        mlocation= intentLocation.getStringExtra("location");
        jobLocation.setText(mlocation);
//        getPictureWithCameraOrGallery = (TextView) findViewById(R.id.tvTakeAPicture);
//        getPictureWithCameraOrGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //added all this to carry intent from here to CameraAndPhotoAct to jobSummary
//                String jLocation = jobLocation.getText().toString();
//                String jDescription = jobDescription.getText().toString();
//                String jDate = dateView.getText().toString();
//                String jTitle = jobTitle.getText().toString();
//                Intent intent1 =  new Intent(PostAJobActivity.this, CameraAndPhotoActivity.class);
//                intent1.putExtra(jLocation, "jobLocation");
//                intent1.putExtra(jDescription, "jobDescription");
//                intent1.putExtra(jDate, "jobDate");
//                intent1.putExtra(jTitle, "jobTitle");
//                startActivity(intent1);
//            }
//        });

    }


    public void sendIntentExtras(){
        jobLocation = (MyEditText) findViewById(R.id.location);
        jobTitle = (MyEditText)findViewById(R.id.etJobTitle);
        jobDescription = (MyEditText) findViewById(R.id.input_job_description);
        dateView = (MyTextView) findViewById(R.id.dateShow);
        ivJobPost = (ImageView)findViewById(R.id.ivJobPostImage);

        nextButton =(FloatingActionButton) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, JobSummary.class);
                i.putExtra("location", jobLocation.getText().toString());
                i.putExtra("jobTitle", jobTitle.getText().toString());
                i.putExtra("jobDescription", jobDescription.getText().toString());
                i.putExtra("jobDate", dateView.getText().toString());
                i.putExtra(getString(R.string.selected_bitmap),mImageBitmap);
                startActivity(i);
            }
        });
    }
    public void showDatePickerDialogOnClick(){
        datePicker = (ImageButton) findViewById(R.id.dateOfActivity);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showDialog(DIALOG_ID);
            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id){
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        return null;
    }


    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month + 1;
            day_x = dayOfMonth;

            Toast.makeText(PostAJobActivity.this, year_x + "/" + month_x + "/" + day_x, Toast.LENGTH_LONG).show();
            String dateChosen = year_x + "/" + month_x + "/" + day_x;
            dateView = (MyTextView) findViewById(R.id.dateShow);
            dateView.setText(dateChosen);
        }
    };


//    public void showDatePickerDialog(View v) {
//        DialogFragment newFragment = new DatePickerFragment();
//        newFragment.show(getFragmentManager(), "datePicker");
//    }
//
//
//    public void onDatePicked(String date) {
//        dateView.setText(date);
//    }

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
}
