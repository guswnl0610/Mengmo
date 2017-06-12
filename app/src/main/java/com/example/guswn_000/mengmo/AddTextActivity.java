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
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTextActivity extends AppCompatActivity
{
    EditText title, content;
    MyText myText;
    String txttitle;
    String txtcontent;
    String txtdate;
    String filepath,filename;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_text);
        title = (EditText)findViewById(R.id.titleET);
        content = (EditText)findViewById(R.id.contentET);

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
                if(!title.getText().toString().equals(""))
                {
                    checkDialog();
                }
                else if (!content.getText().toString().equals(""))
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
                if(title.getText().toString().trim().equals(""))
                {
                    Toast.makeText(this,"제목을 입력해주세요",Toast.LENGTH_SHORT).show();
                    title.requestFocus();
                }
                else if (content.getText().toString().equals(""))
                {
                    Toast.makeText(this,"내용을 입력해주세요",Toast.LENGTH_SHORT).show();
                    content.requestFocus();
                }
                else
                {
                    txttitle = title.getText().toString().trim();
                    txtcontent = content.getText().toString();
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    txtdate = dateFormat.format(date).toString();
                    myText = new MyText(txttitle,txtcontent,txtdate);
                    filepath = getExternalPath() + "Mengmo/txt/" +txtdate + txttitle + ".txt";
                    filename = txttitle+".txt";
                    write(filepath,txtcontent);

                    Intent intent = getIntent();
                    intent.putExtra("newtxt",myText);
                    setResult(RESULT_OK,intent);

                    finish();
                    /*


                    이 부분에 저장하는 부분 만들기


                     */


                }

                break;
        }

        return super.onOptionsItemSelected(item);
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


    @Override
    public void onBackPressed()
    {
        if(!title.getText().toString().equals(""))
        {
            checkDialog();
        }
        else if (!content.getText().toString().equals(""))
        {
            checkDialog();
        }
        else
        {
            Toast.makeText(this,"비었음",Toast.LENGTH_SHORT).show();
            finish();
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
        }
        else
        {
            sdPath = getFilesDir() + "";
        }
        return sdPath;
    }

}
