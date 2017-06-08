package com.example.guswn_000.mengmo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity
{
    boolean isaddbtnvisible = false;
    ImageButton btnadd,btntext,btnimg,btnrec;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnadd = (ImageButton)findViewById(R.id.btnadd);
        btntext = (ImageButton)findViewById(R.id.btntextadd);
        btnimg = (ImageButton)findViewById(R.id.btnimgadd);
        btnrec = (ImageButton)findViewById(R.id.btnrecordadd);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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

                break;
        }
    }



}
