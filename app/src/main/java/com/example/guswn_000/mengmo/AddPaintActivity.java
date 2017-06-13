package com.example.guswn_000.mengmo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPaintActivity extends AppCompatActivity
{
    EditText titleet;
    Mypainter mypainter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_paint);
        titleet = (EditText)findViewById(R.id.ettitle);
        mypainter = (Mypainter)findViewById(R.id.painter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.txtmenu, menu);
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
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    String filedate = dateFormat.format(date).toString();

                    String filename = titleet.getText().toString().trim() + ".png";
                    MyImage img = new MyImage(filename,filedate);
                    mypainter.Save(getExternalPath() + "Mengmo/img/" + filedate+ filename);
                    Intent intent = getIntent();
                    intent.putExtra("addimg",img);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onBackPressed()
    {
        checkDialog();
//        super.onBackPressed();
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

}
