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

public class InteriorPlumbingLeaks extends AppCompatActivity{

    private static final String TAG = "InteriorPlumbingLeaks";
    private Context mContext = InteriorPlumbingLeaks.this;
    private  static final int ACTIVITY_NUM = 0;
    ArrayList<String> interiorPlumbingLeaksSelection = new ArrayList<String>();
    TextView interiorPlumbingLeaksSelected;
    Button nextButtonToPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.residential_interior_plumbing_lv_selected);
        Log.d(TAG, "onCreate: starting");
        initialiseWidgets();
        interiorPlumbingLeaksSelected = (TextView) findViewById(R.id.interiorPlumbingLeaksFinalResult);
        interiorPlumbingLeaksSelected.setVisibility(View.INVISIBLE);
        setupBottomNavigationView();
    }

    public void initialiseWidgets(){
        nextButtonToPost = (Button) findViewById(R.id.nextButton);
        nextButtonToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String final_category_selection = "";
                for (String Selections : interiorPlumbingLeaksSelection){
                    final_category_selection = final_category_selection + Selections + ", ";
                }
                interiorPlumbingLeaksSelected.setText(final_category_selection);
                Log.v(TAG,"gotten text: " + final_category_selection);
                String selectedChoices = interiorPlumbingLeaksSelected.getText().toString();
                Intent startChildActivityIntent = new Intent(InteriorPlumbingLeaks.this, PostAJobActivity.class);
                Log.v(TAG,"started intent : " + selectedChoices);

                startChildActivityIntent.hasExtra(selectedChoices);
                startActivity(startChildActivityIntent);
            }
        });

    }
    public void selectItemIPL(View v){
    boolean checked = ((CheckBox) v).isChecked();
        switch (v.getId()){
            case R.id.iPLPlumber:
                    if(checked) {
                        interiorPlumbingLeaksSelection.add("Plumber");
                    }else{
                        interiorPlumbingLeaksSelection.remove("Plumber");
                    }break;
            case R.id.iPLDomesticWAter:
                if(checked) {
                    interiorPlumbingLeaksSelection.add("Domestic Water");
                }else{
                    interiorPlumbingLeaksSelection.remove("Domestic Water");
                }break;
            case R.id.iPLIrrigation:
                if(checked) {
                    interiorPlumbingLeaksSelection.add("Irrigation");
                }else{
                    interiorPlumbingLeaksSelection.remove("Irrigation");
                }break;
            case R.id.iPLJacuzzi:
                if(checked) {
                    interiorPlumbingLeaksSelection.add("Jacuzzi");
                }else{
                    interiorPlumbingLeaksSelection.remove("Jacuzzi");
                }break;
            case R.id.iPLKitchenPlumbing:
                if(checked) {
                    interiorPlumbingLeaksSelection.add("Kitchen Plumbing");
                }else{
                    interiorPlumbingLeaksSelection.remove("Kitchen Plumbing");
                }break;
            case R.id.iPLBathroomPlumbing:
                if(checked) {
                    interiorPlumbingLeaksSelection.add("Bathroom Plumbing");
                }else{
                    interiorPlumbingLeaksSelection.remove("Bathroom Plumbing");
                }break;
            case R.id.iPLRoof:
                if(checked) {
                    interiorPlumbingLeaksSelection.add("Roof");
                }else{
                    interiorPlumbingLeaksSelection.remove("Roof");
                }break;
            case R.id.iPLAirCondition:
                if(checked) {
                    interiorPlumbingLeaksSelection.add("Air Condition");
                }else{
                    interiorPlumbingLeaksSelection.remove("Air Condition");
                }break;
            case R.id.iPLOther:
                if(checked) {
                    interiorPlumbingLeaksSelection.add("Other");
                }else{
                    interiorPlumbingLeaksSelection.remove("Other");
                }break;



        }
    }
//    public void finalSelection(View v){
//        String final_category_selection = "";
//        for (String Selections : interiorPlumbingLeaksSelection){
//            final_category_selection = final_category_selection + Selections + "\n";
//        }
//        interiorPlumbingLeaksSelected.setText(final_category_selection);
//        interiorPlumbingLeaksSelected.setVisibility(View.VISIBLE);
//    }


    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up bottom nav view");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(mContext,InteriorPlumbingLeaks.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
