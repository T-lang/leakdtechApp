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

public class LandscapeLeaks extends AppCompatActivity{

    private static final String TAG = "InteriorPlumbingLeaks";
    private Context mContext = LandscapeLeaks.this;
    private  static final int ACTIVITY_NUM = 0;
    ArrayList<String> landscapeLeaksSelection = new ArrayList<String>();
    TextView landscapeLeaksSelected;
    Button nextButtonToPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commercial_landscape_leaks_selection);
        Log.d(TAG, "onCreate: starting");

        landscapeLeaksSelected = (TextView) findViewById(R.id.landscapeLeaksFinalResult);
        landscapeLeaksSelected.setVisibility(View.INVISIBLE);
        setupBottomNavigationView();
        initialisewidgets();
    }

    public void initialisewidgets(){
        nextButtonToPost = (Button) findViewById(R.id.nextButton);
        nextButtonToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String final_category_selection = "";
                for (String Selections : landscapeLeaksSelection){
                    final_category_selection = final_category_selection + Selections + ", ";
                }
                landscapeLeaksSelected.setText(final_category_selection);
                Log.v(TAG,"gotten text: " + final_category_selection);
                String selectedChoices = landscapeLeaksSelected.getText().toString();
                Intent startChildActivityIntent = new Intent(LandscapeLeaks.this, PostAJobActivity.class);
                Log.v(TAG,"started intent : " + selectedChoices);

                startChildActivityIntent.hasExtra(selectedChoices);
                startActivity(startChildActivityIntent);
            }
        });

    }


    public void selectItemLL(View v){
    boolean checked = ((CheckBox) v).isChecked();
        switch (v.getId()){
            case R.id.lLPlumbing:
                    if(checked) {
                        landscapeLeaksSelection.add("Plumber");
                    }else{
                        landscapeLeaksSelection.remove("Plumber");
                    }break;
            case R.id.lLLandscaping:
                if(checked) {
                    landscapeLeaksSelection.add("Landscaping");
                }else{
                    landscapeLeaksSelection.remove("Landscaping");
                }break;
            case R.id.lLIrrigation:
                if(checked) {
                    landscapeLeaksSelection.add("Irrigation");
                }else{
                    landscapeLeaksSelection.remove("Irrigation");
                }break;
            case R.id.lLJacuzzi:
                if(checked) {
                    landscapeLeaksSelection.add("Jacuzzi");
                }else{
                    landscapeLeaksSelection.remove("Jacuzzi");
                }break;
            case R.id.lLWaterTanks:
                if(checked) {
                    landscapeLeaksSelection.add("Water Tanks");
                }else{
                    landscapeLeaksSelection.remove("Water Tanks");
                }break;
            case R.id.lLChilledWaterLines:
                if(checked) {
                    landscapeLeaksSelection.add("Chilled Water Lines");
                }else{
                    landscapeLeaksSelection.remove("Chilled Water Lines");
                }break;
            case R.id.lLDomesticWaterLines:
                if(checked) {
                    landscapeLeaksSelection.add("Domestic Water Lines");
                }else{
                    landscapeLeaksSelection.remove("Domestic Water Lines");
                }break;
            case R.id.lLRoof:
                if(checked) {
                    landscapeLeaksSelection.add("Roof");
                }else{
                    landscapeLeaksSelection.remove("Roof");
                }break;
            case R.id.lLSwimmingPools:
                if(checked) {
                    landscapeLeaksSelection.add("Swimming Pools");
                }else{
                    landscapeLeaksSelection.remove("Swimming Pools");
                }break;
            case R.id.lLOther:
                if(checked) {
                    landscapeLeaksSelection.add("Other");
                }else{
                    landscapeLeaksSelection.remove("Other");
                }break;
            case R.id.lLAtrium:
                if(checked) {
                    landscapeLeaksSelection.add("Atrium");
                }else{
                    landscapeLeaksSelection.remove("Atrium");
                }break;
            case R.id.lLWaterFeature:
            if(checked) {
                landscapeLeaksSelection.add("Water Feature");
            }else{
                landscapeLeaksSelection.remove("Water Feature");
            }break;



        }
    }
//    public void finalSelection(View v){
//        String final_category_selection = "";
//        for (String Selections : landscapeLeaksSelection){
//            final_category_selection = final_category_selection + Selections + "\n";
//        }
//        landscapeLeaksSelected.setText(final_category_selection);
//        landscapeLeaksSelected.setVisibility(View.VISIBLE);
//    }


    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up bottom nav view");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(mContext,LandscapeLeaks.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
