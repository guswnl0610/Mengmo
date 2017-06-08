package com.example.guswn_000.mengmo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddTextActivity extends AppCompatActivity
{
    EditText title, content;

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
                else if (!content.getText().toString().equals(""))
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
                    finish();
                    /*


                    이 부분에 저장하는 부분 만들기


                     */
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
