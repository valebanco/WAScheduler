package it.bancon.wascheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import it.bancon.wascheduler.model.ContactLoader;

public class MainActivity extends AppCompatActivity {

    PermissionChecker permissionChecker;
    //ContactLoader contactLoader = new ContactLoader(MainActivity.this,MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission(MainActivity.this,MainActivity.this,Manifest.permission.READ_CONTACTS,PermissionChecker.MESSAGE_RATIONALE_READ_CONTACTS,PermissionChecker.REQUEST_CODE_READ_CONTACTS);
        checkPermission(MainActivity.this, MainActivity.this, Manifest.permission.CAMERA,PermissionChecker.MESSAGE_RATIONALE_CAMERA,PermissionChecker.REQUEST_CODE_CAMERA);
    }

    private void checkPermission(Activity activity, Context context, String permission,String messageRationale,int requestCode) {
        permissionChecker = new PermissionChecker(activity,context,permission);

       if(permissionChecker.checkPermission(messageRationale,requestCode)) {

           switch (requestCode){
               case PermissionChecker.REQUEST_CODE_READ_CONTACTS:
                   //contactLoader.getContactList();
                   break;
               case  PermissionChecker.REQUEST_CODE_CAMERA:
                   Toast.makeText(context,"Camera permission enabled!",Toast.LENGTH_SHORT);
                   break;
           };

       }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionChecker.REQUEST_CODE_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"Permission CAMERA granted!",Toast.LENGTH_SHORT);
        }

        if (requestCode == PermissionChecker.REQUEST_CODE_READ_CONTACTS && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //contactLoader.getContactList();
        }

    }
}