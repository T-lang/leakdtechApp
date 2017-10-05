package com.leakdtech.maintenanceapp.Utils;

import android.os.Environment;

/**
 * Created by LYB OJO on 9/26/2017.
 */

public class FilePaths {
    public String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();

    public String PICTURES = ROOT_DIR + "/Pictures";
    public String CAMERA = ROOT_DIR + "/DCIM/camera";

    public String FIREBASE_IMAGE_STORAGE = "photos/users";
}
