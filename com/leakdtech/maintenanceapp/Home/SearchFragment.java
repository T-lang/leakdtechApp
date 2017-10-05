package com.leakdtech.maintenanceapp.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.leakdtech.maintenanceapp.Login.LoginOrSignupMainActivity;
import com.leakdtech.maintenanceapp.R;

/**
 * Created by LYB OJO on 8/28/2017.
 */

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";

    private Button mButtonSearch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mButtonSearch= (Button) view.findViewById(R.id.btn_search);

        return view;

    }
}
