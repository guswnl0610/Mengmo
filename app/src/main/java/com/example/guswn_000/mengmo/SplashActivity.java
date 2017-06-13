package com.example.guswn_000.mengmo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SplashActivity extends Activity
{

//    private boolean permissionToRecordAccepted = false;
//    private boolean permissionToWriteAccepted = false;
//    private String [] permissions = {"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"};

    private int timeoutMillis = 3000;
    private long startTimeMillis = 0;
    private static final int PERMISSIONS_REQUEST = 1234;

    public int getTimeoutMillis()
    {
        return timeoutMillis;
    }

    public Class getNextActivityClass()
    {
        return MainActivity.class;
    }

    public String[] getRequiredPermissions()
    {
        String[] permissions = null;
        try
        {
            permissions = getPackageManager().getPackageInfo(getPackageName(),PackageManager.GET_PERMISSIONS).requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(permissions == null)
        {
            return new String[0];
        }
        else
        {
            return permissions.clone();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        startTimeMillis = System.currentTimeMillis();
        if(Build.VERSION.SDK_INT >= 23)
        {
            checkPermissions();
        }
        else
        {
            startNextActivity();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSIONS_REQUEST)
        {
            checkPermissions();
        }
    }

    private void startNextActivity()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
        long delayMillis = getTimeoutMillis() - (System.currentTimeMillis() - startTimeMillis);
        if(delayMillis < 0)
        {
            delayMillis = 0;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, getNextActivityClass()));
                finish();
            }
        },delayMillis);
    }

    private void checkPermissions()
    {
        String[] ungrantedPermissions = requiredPermissionsStillNeeded();
        if(ungrantedPermissions.length == 0)
        {
            startNextActivity();
        }
        else
        {
            if (Build.VERSION.SDK_INT >= 23) {
                requestPermissions(ungrantedPermissions, PERMISSIONS_REQUEST);
            }
        }
    }

    private String[] requiredPermissionsStillNeeded()
    {
        Set<String> permissions = new HashSet<String>();
        for(String permission : getRequiredPermissions())
        {
            permissions.add(permission);
        }
        for(Iterator<String> i = permissions.iterator(); i.hasNext();)
        {
            String permission = i.next();
            if (Build.VERSION.SDK_INT >= 23) {
                if(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
                {
                    i.remove();
                }
                else
                {}
            }
        }
        return permissions.toArray(new String[permissions.size()]);
    }



}
