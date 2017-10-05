package com.leakdtech.maintenanceapp.Home.Categories.Commercial;

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

public class BuildingMEPLeaks extends AppCompatActivity{

    private static final String TAG = "InteriorPlumbingLeaks";
    private Context mContext = BuildingMEPLeaks.this;
    private  static final int ACTIVITY_NUM = 0;
    ArrayList<String> bMEPLeaksSelection = new ArrayList<String>();
    TextView bMEPLeaksSelected;
    Button nextButtonToPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commercial_building_civile_mep_leaks_selection);
        Log.d(TAG, "onCreate: starting");
        initialisewidgets();
        bMEPLeaksSelected = (TextView) findViewById(R.id.buildingCivilMEPFinalResult);
        bMEPLeaksSelected.setVisibility(View.INVISIBLE);
        setupBottomNavigationView();
    }

    public void initialisewidgets(){
        nextButtonToPost = (Button) findViewById(R.id.nextButton);
        nextButtonToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String final_category_selection = "";
                for (String Selections : bMEPLeaksSelection){
                    final_category_selection = final_category_selection + Selections + ", ";
                }
                bMEPLeaksSelected.setText(final_category_selection);
                Log.v(TAG,"gotten text: " + final_category_selection);
                String selectedChoices = bMEPLeaksSelected.getText().toString();
                Intent startChildActivityIntent = new Intent(BuildingMEPLeaks.this, PostAJobActivity.class);
                Log.v(TAG,"started intent : " + selectedChoices);

                startChildActivityIntent.hasExtra(selectedChoices);
                startActivity(startChildActivityIntent);
            }
        });
    }
    public void selectItemBMEP(View v){
    boolean checked = ((CheckBox) v).isChecked();
        switch (v.getId()){
            case R.id.bMEPPlumber:
                    if(checked) {
                        bMEPLeaksSelection.add("Plumber");
                    }else{
                        bMEPLeaksSelection.remove("Plumber");
                    }break;
            case R.id.bMEPLandscaping:
                if(checked) {
                    bMEPLeaksSelection.add("Landscaping");
                }else{
                    bMEPLeaksSelection.remove("Landscaping");
                }break;
            case R.id.bMEPFireLine:
                if(checked) {
                    bMEPLeaksSelection.add("Fire Line");
                }else{
                    bMEPLeaksSelection.remove("Fire Line");
                }break;
            case R.id.bMEPChilledWaterLine:
                if(checked) {
                    bMEPLeaksSelection.add("Chilled Water Lines");
                }else{
                    bMEPLeaksSelection.remove("Chilled Water Lines");
                }break;
            case R.id.bMEPCommercialKitchen:
                if(checked) {
                    bMEPLeaksSelection.add("Commercial Kitchen");
                }else{
                    bMEPLeaksSelection.remove("Commercial Kitchen");
                }break;
            case R.id.bMEPOther:
                if(checked) {
                    bMEPLeaksSelection.add("Other");
                }else{
                    bMEPLeaksSelection.remove("Other");
                }break;
            case R.id.bMEPRoof:
                if(checked) {
                    bMEPLeaksSelection.add("Roof");
                }else{
                    bMEPLeaksSelection.remove("Roof");
                }break;
            case R.id.bMEPAirCondition:
            if(checked) {
                bMEPLeaksSelection.add("Air Condition");
            }else{
                bMEPLeaksSelection.remove("Air Condition");
            }break;



        }
    }
//    public void finalSelection(View v){
//        String final_category_selection = "";
//        for (String Selections : bMEPLeaksSelection){
//            final_category_selection = final_category_selection + Selections + "\n";
//        }
//        bMEPLeaksSelected.setText(final_category_selection);
//        bMEPLeaksSelected.setVisibility(View.VISIBLE);
//    }


    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up bottom nav view");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(mContext,BuildingMEPLeaks.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
