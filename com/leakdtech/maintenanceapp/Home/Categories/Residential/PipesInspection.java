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

public class PipesInspection extends AppCompatActivity{

    private static final String TAG = "PipesInspection";
    private Context mContext = PipesInspection.this;
    private  static final int ACTIVITY_NUM = 0;
    ArrayList<String> pipesInspectionSelection = new ArrayList<String>();
    TextView pipesInspectionSelected;
    Button nextButtonToPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.residential_pipes_inspection_lv_selected);
        Log.d(TAG, "onCreate: starting");
        initialiseWidgets();


        pipesInspectionSelected = (TextView) findViewById(R.id.pipesInspectionFinalResult);
        pipesInspectionSelected.setVisibility(View.INVISIBLE);
        setupBottomNavigationView();
    }

    public void initialiseWidgets(){
        nextButtonToPost = (Button) findViewById(R.id.nextButton);
        nextButtonToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String final_category_selection = "";
                for (String Selections : pipesInspectionSelection){
                    final_category_selection = final_category_selection + Selections + ", ";
                    Log.d(TAG,"gotten text: " + final_category_selection);

                }
                Log.d(TAG,"gotten text: " + final_category_selection);
                String selectedChoices = pipesInspectionSelected.getText().toString();
                Intent pipesInspectionIntent = new Intent(PipesInspection.this, PostAJobActivity.class);
                pipesInspectionIntent.putExtra("pISelection", selectedChoices);
                startActivity(pipesInspectionIntent);
            }
        });
    }
//   pipesInspectionSelected.setText(final_category_selection);

    public void selectItemPI(View v){
    boolean checked = ((CheckBox) v).isChecked();
        switch (v.getId()){
            case R.id.pIPlumber:
                    if(checked) {
                        pipesInspectionSelection.add("Plumber");
                    }else{
                        pipesInspectionSelection.remove("Plumber");
                    }break;
            case R.id.pIDomesticWAter:
                if(checked) {
                    pipesInspectionSelection.add("Domestic Water");
                }else{
                    pipesInspectionSelection.remove("Domestic Water");
                }break;
            case R.id.pIIrrigation:
                if(checked) {
                    pipesInspectionSelection.add("Irrigation");
                }else{
                    pipesInspectionSelection.remove("Irrigation");
                }break;
            case R.id.pISwimmingPools:
                if(checked) {
                    pipesInspectionSelection.add("Swimming Pool");
                }else{
                    pipesInspectionSelection.remove("Swimming Pool");
                }break;
            case R.id.pIJacuzzi:
                if(checked) {
                    pipesInspectionSelection.add("Jacuzzi");
                }else{
                    pipesInspectionSelection.remove("Jacuzzi");
                }break;
            case R.id.pIKitchenPlumbing:
                if(checked) {
                    pipesInspectionSelection.add("Kitchen Plumbing");
                }else{
                    pipesInspectionSelection.remove("Kitchen Plumbing");
                }break;
            case R.id.pIBathroomPlumbing:
                if(checked) {
                    pipesInspectionSelection.add("Bathroom Plumbing");
                }else{
                    pipesInspectionSelection.remove("Bathroom Plumbing");
                }break;
            case R.id.pIRoof:
                if(checked) {
                    pipesInspectionSelection.add("Roof");
                }else{
                    pipesInspectionSelection.remove("Roof");
                }break;
            case R.id.pIAirCondition:
                if(checked) {
                    pipesInspectionSelection.add("Air Condition");
                }else{
                    pipesInspectionSelection.remove("Air Condition");
                }break;
            case R.id.piWaterTanks:
                if(checked) {
                    pipesInspectionSelection.add("Water Tanks");
                }else{
                    pipesInspectionSelection.remove("Water Tanks");
                }break;
            case R.id.pIOther:
                if(checked) {
                    pipesInspectionSelection.add("Other");
                }else{
                    pipesInspectionSelection.remove("Other");
                }break;

        }
    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up bottom nav view");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(mContext,PipesInspection.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
