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

public class UndergroundLeaks extends AppCompatActivity{

    private static final String TAG = "UndergroundLeaks";
    private Context mContext = UndergroundLeaks.this;
    private  static final int ACTIVITY_NUM = 0;
    ArrayList<String> undergroundLeaksSelection = new ArrayList<String>();
    TextView undergroundLeaksSelected;
    Button nextButtonToPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commercial_underground_leaks_selection);
        Log.d(TAG, "onCreate: starting");

        initialiseWidgets();
        undergroundLeaksSelected = (TextView) findViewById(R.id.undergroundLeaksFinalResult);
        undergroundLeaksSelected.setVisibility(View.INVISIBLE);
        setupBottomNavigationView();
    }

    public void initialiseWidgets(){
        nextButtonToPost = (Button) findViewById(R.id.nextButton);
        nextButtonToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String final_category_selection = "";
                for (String Selections : undergroundLeaksSelection){
                    final_category_selection = final_category_selection + Selections + ", ";
                }
                undergroundLeaksSelected.setText(final_category_selection);
                Log.v(TAG,"gotten text: " + final_category_selection);
                String selectedChoices = undergroundLeaksSelected.getText().toString();
                Intent startChildActivityIntent = new Intent(UndergroundLeaks.this, PostAJobActivity.class);
                Log.v(TAG,"started intent : " + selectedChoices);

                startChildActivityIntent.hasExtra(selectedChoices);
                startActivity(startChildActivityIntent);
            }
        });
    }
    public void selectItemUL(View v){
    boolean checked = ((CheckBox) v).isChecked();
        switch (v.getId()){
            case R.id.uLPlumbing:
                    if(checked) {
                        undergroundLeaksSelection.add("Plumber");
                    }else{
                        undergroundLeaksSelection.remove("Plumber");
                    }break;
            case R.id.uLLandscaping:
                if(checked) {
                    undergroundLeaksSelection.add("Landscaping");
                }else{
                    undergroundLeaksSelection.remove("Landscaping");
                }break;
            case R.id.uLIrrigation:
                if(checked) {
                    undergroundLeaksSelection.add("Irrigation");
                }else{
                    undergroundLeaksSelection.remove("Irrigation");
                }break;
            case R.id.uLJacuzzi:
                if(checked) {
                    undergroundLeaksSelection.add("Jacuzzi");
                }else{
                    undergroundLeaksSelection.remove("Jacuzzi");
                }break;
            case R.id.uLFireLine:
                if(checked) {
                    undergroundLeaksSelection.add("Fire Line");
                }else{
                    undergroundLeaksSelection.remove("Fire Line");
                }break;
            case R.id.uLChilledWaterLines:
                if(checked) {
                    undergroundLeaksSelection.add("Chilled Water Lines");
                }else{
                    undergroundLeaksSelection.remove("Chilled Water Lines");
                }break;
            case R.id.uLDomesticWaterLines:
                if(checked) {
                    undergroundLeaksSelection.add("Domestic Water Lines");
                }else{
                    undergroundLeaksSelection.remove("Domestic Water Lines");
                }break;
            case R.id.uLCommercialKitchen:
                if(checked) {
                    undergroundLeaksSelection.add("Commercial Kitchen");
                }else{
                    undergroundLeaksSelection.remove("Commercial Kitchen");
                }break;
            case R.id.uLSwimmingPools:
                if(checked) {
                    undergroundLeaksSelection.add("Swimming Pools");
                }else{
                    undergroundLeaksSelection.remove("Swimming Pools");
                }break;
            case R.id.uLOther:
                if(checked) {
                    undergroundLeaksSelection.add("Other");
                }else{
                    undergroundLeaksSelection.remove("Other");
                }break;
            case R.id.uLAtrium:
                if(checked) {
                    undergroundLeaksSelection.add("Atrium");
                }else{
                    undergroundLeaksSelection.remove("Atrium");
                }break;
            case R.id.uLWaterFeature:
            if(checked) {
                undergroundLeaksSelection.add("Water Feature");
            }else{
                undergroundLeaksSelection.remove("Water Feature");
            }break;



        }
    }
//    public void finalSelection(View v){
//        String final_category_selection = "";
//        for (String Selections : undergroundLeaksSelection){
//            final_category_selection = final_category_selection + Selections + "\n";
//        }
//        undergroundLeaksSelected.setText(final_category_selection);
//        undergroundLeaksSelected.setVisibility(View.VISIBLE);
//    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up bottom nav view");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(mContext,UndergroundLeaks.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
