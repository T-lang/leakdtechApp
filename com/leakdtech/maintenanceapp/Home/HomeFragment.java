package com.leakdtech.maintenanceapp.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.leakdtech.maintenanceapp.Home.Categories.Commercial.CommercialActivity;
import com.leakdtech.maintenanceapp.Home.Categories.Industrial.IndustrialActivity;
import com.leakdtech.maintenanceapp.Menu.MenuActivity;
import com.leakdtech.maintenanceapp.Home.Categories.PetroChemical.PetroChemicalActivity;
import com.leakdtech.maintenanceapp.R;
import com.leakdtech.maintenanceapp.Home.Categories.Residential.ResidentialActivity;

/**
 * Created by LYB OJO on 8/28/2017.
 */

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private TextView mPetrochemicalServices, mIndustrialServices, mCommercialServices,
            mResidentialServices, mToolsHomeNavigation;
    private Button mHelpAndContactUs, mScheduleACallback;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mPetrochemicalServices = (TextView) view.findViewById(R.id.petrochemicalTab);
        mIndustrialServices = (TextView) view.findViewById(R.id.industrialTab);
        mCommercialServices = (TextView) view.findViewById(R.id.commercialTab);
        mResidentialServices = (TextView) view.findViewById(R.id.residentialTab);
        mHelpAndContactUs =(Button) view.findViewById(R.id.helpAndContactUsBtn);
        mScheduleACallback =(Button) view.findViewById(R.id.scheduleACallBackBtn);
        mToolsHomeNavigation = (TextView) view.findViewById(R.id.toolsHomeNavigation);

        setHomeActivityIntent();
        return view;
    }

    private void setHomeActivityIntent(){
        mPetrochemicalServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to petroChemicalActivity");
                Intent intent = new Intent(getActivity(), PetroChemicalActivity.class);
                getActivity().startActivity(intent);


            }
        });
        mCommercialServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to CommercialServices");
                Intent intent = new Intent(getActivity(), CommercialActivity.class);
                getActivity().startActivity(intent);


            }
        });
        mIndustrialServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to IndustrialServices");
                Intent intent = new Intent(getActivity(), IndustrialActivity.class);
                getActivity().startActivity(intent);


            }
        });
        mResidentialServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to petroChemicalActivity");
                Intent intent = new Intent(getActivity(), ResidentialActivity.class);
                getActivity().startActivity(intent);


            }
        });
        mHelpAndContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactUs.class);
                startActivity(intent);
            }
        });
        mScheduleACallback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScheduleACallBack.class);
                startActivity(intent);
            }
        });
        mToolsHomeNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
