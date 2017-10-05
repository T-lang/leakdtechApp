package com.leakdtech.maintenanceapp.Home.Categories.Residential;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.leakdtech.maintenanceapp.PostAJob.PostAJobActivity;
import com.leakdtech.maintenanceapp.R;
import com.leakdtech.maintenanceapp.Utils.BottomNavigationViewHelper;

import java.util.ArrayList;

/**
 * Created by LYB OJO on 9/5/2017.
 */

public class GardenAreaLeaks extends AppCompatActivity{

    private static final String TAG = "GardenAreaLeaks";
    private Context mContext = GardenAreaLeaks.this;
    private  static final int ACTIVITY_NUM = 0;
    ArrayList<String> gardenAreaSelection = new ArrayList<String>();
    TextView gardenAreaFinalSelected;
    Button nextButtonToPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.residential_garden_area_lv_selected);
        Log.d(TAG, "onCreate: starting");
        initialiseWidgets();
        gardenAreaFinalSelected = (TextView) findViewById(R.id.gardenAreaFinalResult);
        gardenAreaFinalSelected.setVisibility(View.INVISIBLE);
        setupBottomNavigationView();
    }
    public void selectItemGAL(View v){
    boolean checked = ((CheckBox) v).isChecked();
        switch (v.getId()){
            case R.id.gALLandscaping:
                    if(checked) {
                        gardenAreaSelection.add("Landscaping");
                    }else{
                        gardenAreaSelection.remove("Landscaping");
                    }break;
            case R.id.gALDomesticWAter:
                if(checked) {
                    gardenAreaSelection.add("Domestic Water");
                }else{
                    gardenAreaSelection.remove("Domestic Water");
                }break;
            case R.id.gALIrrigation:
                if(checked) {
                    gardenAreaSelection.add("Irrigation");
                }else{
                    gardenAreaSelection.remove("Irrigation");
                }break;
            case R.id.gALSwimmingPools:
                if(checked) {
                    gardenAreaSelection.add("Swimming Pool");
                }else{
                    gardenAreaSelection.remove("Swimming Pool");
                }break;
            case R.id.gALJacuzzi:
                if(checked) {
                    gardenAreaSelection.add("Jacuzzi");
                }else{
                    gardenAreaSelection.remove("Jacuzzi");
                }break;
            case R.id.gALWaterTanks:
                if(checked) {
                    gardenAreaSelection.add("Water Tanks");
                }else{
                    gardenAreaSelection.remove("Water Tanks");
                }break;
            case R.id.gALOther:
                if(checked) {
                    gardenAreaSelection.add("Other");
                }else{
                    gardenAreaSelection.remove("Other");
                }break;


        }
    }

    public void initialiseWidgets(){
        nextButtonToPost = (Button) findViewById(R.id.nextButton);
        nextButtonToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String final_category_selection = "";
                for (String Selections :gardenAreaSelection){
                    final_category_selection = final_category_selection + Selections + ", ";
                }
                gardenAreaFinalSelected.setText(final_category_selection);
                Log.v(TAG,"gotten text: " + final_category_selection);
                String selectedChoices = gardenAreaFinalSelected.getText().toString();
                Intent startChildActivityIntent = new Intent(GardenAreaLeaks.this, PostAJobActivity.class);
                Log.v(TAG,"started intent : " + selectedChoices);

                startChildActivityIntent.hasExtra(selectedChoices);
                startActivity(startChildActivityIntent);
            }
        });
    }


    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up bottom nav view");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(mContext,GardenAreaLeaks.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
