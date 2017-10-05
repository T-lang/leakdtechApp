package com.leakdtech.maintenanceapp.PostAJob;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.leakdtech.maintenanceapp.Menu.NavigationDrawr.EditProfileFragment;
import com.leakdtech.maintenanceapp.R;
import com.leakdtech.maintenanceapp.Utils.FilePaths;
import com.leakdtech.maintenanceapp.Utils.FileSearch;
import com.leakdtech.maintenanceapp.Utils.ImageAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import static com.leakdtech.maintenanceapp.R.id.dummyJobDate;
import static com.leakdtech.maintenanceapp.R.id.dummyJobDescription;
import static com.leakdtech.maintenanceapp.R.id.dummyJobLocation;


/**
 * Created by LYB OJO on 9/25/2017.
 */

public class GalleryFrag extends Fragment {

    private static final String TAG = "GalleryFrag";

    private static final int NUM_GRID_COLUMNS = 3;

    private GridView gridView;
    private ImageView galleryImage;
    private ProgressBar mProgressBar;
    private Spinner directorySpinner;

    private ArrayList<String> directories;
    private String mAppend = "file:/";
    private String mSelectedImage, mJobTitle, mJobLocation, mJobDescription, mJobDate;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        galleryImage = (ImageView) view.findViewById(R.id.galleryImageView);
        gridView = (GridView) view.findViewById(R.id.gridView);
        directorySpinner = (Spinner) view.findViewById(R.id.spinnerDirectory);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        directories = new ArrayList<>();
        Log.d(TAG,"onCreateView: starting");
        ImageView shareClose = (ImageView) view.findViewById(R.id.ivCloseShare);
        shareClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing the gallery fragment.");
                getActivity().finish();
            }
        });



        TextView nextScreen = (TextView) view.findViewById(R.id.tvNext);
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExtras();
                Log.d(TAG, "gotten Extras" +mJobDate+","+mJobDescription+","+mJobTitle+" "+mJobLocation);
                Log.d(TAG, "onClick: navigating to the final share screen.");

                    Intent intent = new Intent(getActivity(), JobSummary.class);
                    intent.putExtra(getString(R.string.selected_image), mSelectedImage);
                    intent.putExtra(getString(R.string.job_date), mJobDate);
                    intent.putExtra(getString(R.string.job_title), mJobTitle);
                    intent.putExtra(getString(R.string.job_location), mJobLocation);
                    intent.putExtra(getString(R.string.job_description), mJobDescription);
                    startActivity(intent);


            }
        });
        init();
        return view;
    }


    private boolean isRootTask(){
        if(((CameraAndPhotoActivity)getActivity()).getTask() == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void getExtras(){
        mJobDate = getActivity().getIntent().getExtras().getString("jobDate");
        mJobTitle = getActivity().getIntent().getExtras().getString("jobTitle");
        mJobLocation = getActivity().getIntent().getExtras().getString("jobLocation");
        mJobDescription = getActivity().getIntent().getExtras().getString("jobDescription");



    }

    private void init() {
        FilePaths filePaths = new FilePaths();
        if(FileSearch.getDirectoryPaths(filePaths.PICTURES) != null){
            directories = FileSearch.getDirectoryPaths(filePaths.PICTURES);
        }

        ArrayList<String> directoryNames = new ArrayList<>();
        for(int i = 0; i < directories.size(); i++){

            int index = directories.get(i).lastIndexOf("/");
            String string = directories.get(i).substring(index).replace("/", "");
            directoryNames.add(string);
        }
        directories.add(filePaths.CAMERA);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, directoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        directorySpinner.setAdapter(adapter);
        directorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: selected: " + directories.get(position));
                setupGridView(directories.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupGridView(String selectedDirectory){
        Log.d(TAG, "setupGridView: directory chosen: " + selectedDirectory);
        final ArrayList<String> imgURLs = FileSearch.getFilePaths(selectedDirectory);

        //set the grid column width
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imageWidth);

        //use the grid adapter to adapter the images to gridview
        ImageAdapter adapter = new ImageAdapter(getActivity(), R.layout.layout_job_imageview, mAppend, imgURLs);
        gridView.setAdapter(adapter);

        //set the first image to be displayed when the activity fragment view is inflated
        setImage(imgURLs.get(0), galleryImage, mAppend);
        mSelectedImage = imgURLs.get(0);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: selected an image: " + imgURLs.get(position));

                setImage(imgURLs.get(position), galleryImage, mAppend);
                mSelectedImage = imgURLs.get(position);
            }
        });

    }


    private void setImage(String imgURL, ImageView image, String append){
        Log.d(TAG, "setImage: setting image");

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(append + imgURL, image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });    }

}