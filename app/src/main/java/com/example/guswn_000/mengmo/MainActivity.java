package com.example.guswn_000.mengmo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity
{
    TabHost tabHost;
    private long pressedTime = 0;
    boolean isaddbtnvisible = false;
    ImageButton btnadd,btntext,btnimg,btnrec;
    ListView txtlistview,imglistview,reclistview;

    private boolean permissionToRecordAccepted = false;
    private boolean permissionToWriteAccepted = false;
    private String [] permissions = {"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int requestCode = 200;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            requestPermissions(permissions,requestCode);
        }
//        checkpermission();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnadd = (ImageButton)findViewById(R.id.btnadd);
        btntext = (ImageButton)findViewById(R.id.btntextadd);
        btnimg = (ImageButton)findViewById(R.id.btnimgadd);
        btnrec = (ImageButton)findViewById(R.id.btnrecordadd);

        String path = getExternalPath();
        File file = new File(path + "Mengmo");
        file.mkdir();

        tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec ts1 = tabHost.newTabSpec("TXT").setContent(R.id.tab1).setIndicator("Text");
        tabHost.addTab(ts1); //Text탭생성
        TabHost.TabSpec ts2 = tabHost.newTabSpec("IMG").setContent(R.id.tab2).setIndicator("Image");
        tabHost.addTab(ts2);
        TabHost.TabSpec ts3 = tabHost.newTabSpec("REC").setContent(R.id.tab3).setIndicator("Record");
        tabHost.addTab(ts3);

        txtlistview = (ListView)findViewById(R.id.listviewtxt);
        imglistview = (ListView)findViewById(R.id.listviewimg);
        reclistview = (ListView)findViewById(R.id.listviewrec);

    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnadd:
                if(isaddbtnvisible == false)
                {
                    btntext.setVisibility(View.VISIBLE);
                    btnimg.setVisibility(View.VISIBLE);
                    btnrec.setVisibility(View.VISIBLE);
                    isaddbtnvisible = true;
                }
                else
                {
                    btntext.setVisibility(View.GONE);
                    btnimg.setVisibility(View.GONE);
                    btnrec.setVisibility(View.GONE);
                    isaddbtnvisible = false;
                }
                break;
            case R.id.btntextadd:
                Intent intenttxt = new Intent(MainActivity.this,AddTextActivity.class);
                startActivity(intenttxt);
                break;
            case R.id.btnimgadd:
                Intent intentimg = new Intent(MainActivity.this,AddPaintActivity.class);
                startActivity(intentimg);
                break;
            case R.id.btnrecordadd:
                Intent intentrec = new Intent(MainActivity.this,AddRecordActivity.class);
                startActivity(intentrec);

                break;
        }
    }

    @Override
    public void onBackPressed()
    {
//        super.onBackPressed();
        if(pressedTime == 0)
        {
            Toast.makeText(this,"한 번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
            pressedTime = System.currentTimeMillis();
        }
        else
        {
            int seconds = (int)(System.currentTimeMillis() - pressedTime);
            if(seconds > 2000)
            {
                Toast.makeText(this,"한 번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
                pressedTime = 0;
            }
            else
            {
                finish();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 200:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                permissionToWriteAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(!permissionToRecordAccepted) MainActivity.super.finish();
        if(!permissionToWriteAccepted) MainActivity.super.finish();

    }


    public String getExternalPath()
    {
        String sdPath = "";
        String ext = Environment.getExternalStorageState();
        if(ext.equals(Environment.MEDIA_MOUNTED))
        {
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
            //sdPath = "mnt/sdcard/";
        }
        else
        {
            sdPath = getFilesDir() + "";
        }
//        Toast.makeText(getApplicationContext(),sdPath,Toast.LENGTH_SHORT).show();
        return sdPath;
    }



}
