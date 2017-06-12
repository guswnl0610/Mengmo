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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowTextActivity extends AppCompatActivity
{
    EditText titleet,contentet;
    String origincontent,originpath;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text);
        titleet = (EditText) findViewById(R.id.showtxttitle);
        contentet = (EditText)findViewById(R.id.showtxtcontent);
        Intent intent = getIntent();
        MyText origintxt = intent.getParcelableExtra("TEXT");
        String origintitle = origintxt.getTitle();
        String origindate = origintxt.getDate();


        originpath = getExternalPath() + "Mengmo/txt/" + origindate + origintitle;
        int point = origintitle.length();
        titleet.setText(origintitle.substring(0,point-4));
        read(originpath);

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
                if(!titleet.getText().toString().equals(""))
                {
                    checkDialog();
                }
                else if (!contentet.getText().toString().equals(""))
                {
                    checkDialog();
                }
                else
                {
                    Toast.makeText(this,"비었음",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.save:

                if(titleet.getText().toString().trim().equals(""))
                {
                    Toast.makeText(this,"제목을 입력해주세요",Toast.LENGTH_SHORT).show();
                    titleet.requestFocus();
                }
                else if (contentet.getText().toString().equals(""))
                {
                    Toast.makeText(this,"내용을 입력해주세요",Toast.LENGTH_SHORT).show();
                    contentet.requestFocus();
                }
                else
                {
                    String newtitle = titleet.getText().toString().trim();
                    String newcontent = contentet.getText().toString();
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newdate = dateFormat.format(date).toString();
                    MyText myText = new MyText(newtitle,newcontent,newdate);
                    String newpath = getExternalPath() + "Mengmo/txt/" +newdate + newtitle + ".txt";
                    String newfilename = newtitle + ".txt";

                    remove(originpath);
                    write(newpath,newcontent);
                    //저장
                    Intent intent2 = getIntent();
                    intent2.putExtra("showtxt",myText);
                    intent2.putExtra("originpath",originpath);
                    setResult(RESULT_OK,intent2);

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

    @Override
    public void onBackPressed()
    {
        checkDialog();
    }

    public void read(String path)
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String readStr = "";
            String str = null;
            while ((str = br.readLine()) != null)
            {
                readStr += str + "\n";
            }
            br.close();
            contentet.setText(readStr);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void write(String path,String content)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path,true));
            bw.write(content);
            bw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void remove(String path)
    {
        File file = new File(path);
        file.delete();
    }




    public String getExternalPath()
    {
        String sdPath = "";
        String ext = Environment.getExternalStorageState();
        if(ext.equals(Environment.MEDIA_MOUNTED))
        {
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        }
        else
        {
            sdPath = getFilesDir() + "";
        }
        return sdPath;
    }

}
