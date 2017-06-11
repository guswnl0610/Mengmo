package com.example.guswn_000.mengmo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ShowPaintActivity extends AppCompatActivity
{
    EditText titleet;
    Mypainter mypainter;
    ImageView imageView;

    String originfilename,originfilepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_paint);
        titleet = (EditText)findViewById(R.id.ettitle);
        mypainter = (Mypainter)findViewById(R.id.mapainter);
        imageView = (ImageView)findViewById(R.id.Openimgview);
        Intent intent = getIntent();
        originfilename = intent.getStringExtra("ShowImg");
        originfilepath = getExternalPath() + "Mengmo/" + originfilename;
        Toast.makeText(this,originfilepath,Toast.LENGTH_SHORT).show();
        titleet.setText(originfilename.substring(0,originfilename.length()-4));

//        Bitmap temp = BitmapFactory.decodeFile(originfilepath);
//        mypainter.mcanvas.drawBitmap(temp,0,0,null);

//        mypainter.Open(originfilepath);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.txtmenu, menu);
        mypainter.Open(originfilepath);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.cancel:
                checkDialog();
                break;
            case R.id.save:
                if(titleet.getText().toString().trim().equals(""))
                {
                    Toast.makeText(this,"제목을 입력해주세요",Toast.LENGTH_SHORT).show();
                    titleet.requestFocus();
                }
                else
                {
                    String filename = titleet.getText().toString().trim() + ".png";
                    MyImage img = new MyImage(filename);
                    mypainter.Remove(originfilepath);
                    mypainter.Save(getExternalPath() + "Mengmo/" + filename);
                    Intent intent = getIntent();
                    intent.putExtra("shownewimg",img);
                    setResult(RESULT_OK,intent);
                    finish();
                    /*

                    저장하는부분 여기에

                     */
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        checkDialog();
    }


    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.width5:
                mypainter.changewidth(5);
                break;
            case R.id.width10:
                mypainter.changewidth(10);
                break;
            case R.id.width20:
                mypainter.changewidth(20);
                break;
            case R.id.width30:
                mypainter.changewidth(30);
                break;
            case R.id.red:
                mypainter.changecolor("RED");
                break;
            case R.id.orange:
                mypainter.changecolor("ORANGE");
                break;
            case R.id.yellow:
                mypainter.changecolor("YELLOW");
                break;
            case R.id.green:
                mypainter.changecolor("GREEN");
                break;
            case R.id.blue:
                mypainter.changecolor("BLUE");
                break;
            case R.id.navy:
                mypainter.changecolor("NAVY");
                break;
            case R.id.purple:
                mypainter.changecolor("PURPLE");
                break;
            case R.id.pink:
                mypainter.changecolor("PINK");
                break;
            case R.id.black:
                mypainter.changecolor("BLACK");
                break;
            case R.id.white:
                mypainter.changecolor("WHITE");
                break;
            case R.id.eraser:
                mypainter.eraseAll();
                break;

        }
    }

    public void checkDialog()
    {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("확인")
                .setMessage("변경한 내용을 저장하지 않고 " +
                        "취소하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                })
                .setNegativeButton("취소",null)
                .show();
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
