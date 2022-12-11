package it.bancon.wascheduler;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

class PermissionChecker {
    public static final String MESSAGE_RATIONALE_READ_CONTACTS = "Read Contact permission is necessary. If you do not enable this permission you will unable to use the app! Please,type yes or enable permission in settings section.";
    public static final String MESSAGE_RATIONALE_CAMERA = "Camera permission is necessary. If you do not enable this permission you will unable to use the app! Please,type yes or enable permission in settings section. ";
    public static final int REQUEST_CODE_READ_CONTACTS = 100;
    public static final int REQUEST_CODE_CAMERA =200;

    private  Context context;
    private  Activity activity;
    private String permission;

    public PermissionChecker(Activity activity, Context context, String permission) {
        this.activity = activity;
        this.context = context;
        this.permission = permission;

    }

    public boolean checkPermission(String message,int requestCode){
        if(!hasPermission()) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)) {
                rationalePermissionRequest(message,requestCode);
            } else {
                requestPermission(requestCode);
            }

            return false;
        } else {
            return true;
        }
    }
    public boolean hasPermission(Context context,String permission){
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }
    private boolean hasPermission(){
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }
    public void requestPermission(int requestCode) {
        ActivityCompat.requestPermissions(activity,new String[]{permission},requestCode);
    }

    public void rationalePermissionRequest (String message,int requestCode) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(message);

        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                requestPermission(requestCode);
            }
        });

        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}
