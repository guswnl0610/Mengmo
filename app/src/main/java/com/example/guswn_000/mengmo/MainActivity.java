package com.example.guswn_000.mengmo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity
{
    TabHost tabHost;
    private long pressedTime = 0;
    boolean isaddbtnvisible = false;
    ImageButton btnadd,btntext,btnimg,btnrec;
    ListView txtlistview,imglistview,reclistview;
    ArrayList<MyRecord> records = new ArrayList<>();
    RecordAdapter recordAdapter;
    ArrayList<MyText> texts = new ArrayList<>();
    TextAdapter textAdapter;
    ArrayList<MyImage> images = new ArrayList<>();
    ImageAdapter imageAdapter;

    final int NEW_RECORD = 20;
    final int NEW_TEXT = 21;
    final int NEW_IMAGE = 22;
    final int SHOW_TEXT = 23;
    final int SHOW_IMG = 24;

    private DbOpenHelper mDbOpenHelper;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("맹모장");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();

        makeDirectories();
        init();
        initlistviews();
    }

    public void makeDirectories()
    {
        String path = getExternalPath();
        File file = new File(path + "Mengmo");
        file.mkdir();
        File recdir = new File(path + "Mengmo/rec");
        recdir.mkdir();
        File imgdir = new File(path + "Mengmo/img");
        imgdir.mkdir();
        File txtdir = new File(path + "Mengmo/txt");
        txtdir.mkdir();

    }

    public void init()
    {
        btnadd = (ImageButton)findViewById(R.id.btnadd);
        btntext = (ImageButton)findViewById(R.id.btntextadd);
        btnimg = (ImageButton)findViewById(R.id.btnimgadd);
        btnrec = (ImageButton)findViewById(R.id.btnrecordadd);
        tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec ts1 = tabHost.newTabSpec("TXT").setContent(R.id.tab1).setIndicator("Text");
        tabHost.addTab(ts1); //Text탭생성
        TabHost.TabSpec ts2 = tabHost.newTabSpec("IMG").setContent(R.id.tab2).setIndicator("Image");
        tabHost.addTab(ts2);
        TabHost.TabSpec ts3 = tabHost.newTabSpec("REC").setContent(R.id.tab3).setIndicator("Record");
        tabHost.addTab(ts3);
    }

    public void initlistviews()
    {
        txtlistview = (ListView)findViewById(R.id.listviewtxt);
        imglistview = (ListView)findViewById(R.id.listviewimg);
        reclistview = (ListView)findViewById(R.id.listviewrec);
        recordAdapter = new RecordAdapter(this,records);
        reclistview.setAdapter(recordAdapter);
        reclistsetting();
//        recfilelist();
        doWhileCursorrecords();
        textAdapter = new TextAdapter(this,texts);
        txtlistview.setAdapter(textAdapter);
//        txtfilelist();
        txtlistSetting();
        doWhileCursortexts();
        imageAdapter = new ImageAdapter(this,images);
        imglistview.setAdapter(imageAdapter);
//        imgfilelist();
        imglistSetting();
        doWhileCursorimages();
    }

    public void doWhileCursorrecords() //음성녹음 파일의 경로를 받아와서 리스트뷰에 추가한다
    {
        records.clear();
        mCursor = null;
        mCursor = mDbOpenHelper.getAllrecpath();
        while (mCursor.moveToNext())
        {
            File f = new File(mCursor.getString(mCursor.getColumnIndex("path")));
            MyRecord rec = new MyRecord(f.getName(),f.getName().substring(0,14));
            records.add(rec);
            recordAdapter.notifyDataSetChanged();
        }
        recordAdapter.notifyDataSetChanged();
        mCursor.close();
    }

    public void doWhileCursortexts() //텍스트메모 파일의 경로를 받아와서 리스트뷰에 추가한다
    {
        texts.clear();
        mCursor = null;
        mCursor = mDbOpenHelper.getAlltxtpath();
        while (mCursor.moveToNext())
        {
            File f = new File(mCursor.getString(mCursor.getColumnIndex("path")));
            MyText txt = new MyText(f.getName().substring(14,f.getName().length()),f.getName().substring(0,14));
            texts.add(txt);
            textAdapter.notifyDataSetChanged();
        }
        textAdapter.notifyDataSetChanged();
        mCursor.close();
    }


    public void doWhileCursorimages() //텍스트메모 파일의 경로를 받아와서 리스트뷰에 추가한다
    {
        images.clear();
        mCursor = null;
        mCursor = mDbOpenHelper.getAllimgpath();
        while (mCursor.moveToNext())
        {
            File f = new File(mCursor.getString(mCursor.getColumnIndex("path")));
            MyImage img = new MyImage(f.getName().substring(14,f.getName().length()),f.getName().substring(0,14));
            images.add(img);
            imageAdapter.notifyDataSetChanged();
        }
        imageAdapter.notifyDataSetChanged();
        mCursor.close();
    }


    public void recSort() //음성녹음 리스트 이름순 정렬
    {
        recordAdapter.sortRecordAsc();
    }

    public void reclistsetting() //음성녹음 리스트뷰에 아이템클릭, 롱클릭 리스너 달기
    {
        reclistview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(MainActivity.this,ShowRecordActivity.class);
                intent.putExtra("Record",records.get(position).getTitle());
                startActivity(intent);
            }
        });
        reclistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
                dlg.setTitle("삭제 확인")
                        .setIcon(R.drawable.icon)
                        .setMessage("선택한 음성녹음을 정말 삭제하시겠습니까?")
                        .setNegativeButton("취소",null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                String path = getExternalPath() + "Mengmo/rec/" + records.get(position).getTitle();
                                mDbOpenHelper.deleterecpath(path);
//                                Toast.makeText(getApplicationContext(),path,Toast.LENGTH_SHORT).show();
                                remove(path);
                                doWhileCursorrecords();
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    public void txtlistSetting()
    {
        txtlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent showtxt = new Intent(MainActivity.this,ShowTextActivity.class);
                showtxt.putExtra("TEXT",texts.get(position));
                startActivityForResult(showtxt,SHOW_TEXT);
            }
        });

        txtlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id)
            {
                AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
                dlg.setTitle("삭제 확인")
                        .setIcon(R.drawable.icon)
                        .setMessage("선택한 메모를 삭제하시겠습니까?")
                        .setNegativeButton("취소",null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String path = getExternalPath() + "Mengmo/txt/" + texts.get(position).getDate() + texts.get(position).getTitle();
                                mDbOpenHelper.deletetxtpath(path);
//                                Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();
                                remove(path);
                                doWhileCursortexts();
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    public void imglistSetting()
    {
        imglistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(MainActivity.this,ShowPaintActivity.class);
                intent.putExtra("ShowImg",images.get(position));
                startActivityForResult(intent,SHOW_IMG);
            }
        });

        imglistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
                dlg.setTitle("삭제 확인")
                        .setIcon(R.drawable.icon)
                        .setMessage("선택한 이미지를 삭제하시겠습니까?")
                        .setNegativeButton("취소",null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String path = getExternalPath() + "Mengmo/img/" + images.get(position).getDate() + images.get(position).getTitle();
                                mDbOpenHelper.deleteimgpath(path); //선택한 이미지 path를 DB에서 지움
//                                Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();
                                remove(path); //선택한 이미지 파일을 삭제
                                doWhileCursorimages();
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.developer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.gotoblog)
        {
            Intent intent = new Intent(MainActivity.this,MyBlogActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
//                startActivity(intenttxt);
                startActivityForResult(intenttxt,NEW_TEXT);
                break;
            case R.id.btnimgadd:
                Intent intentimg = new Intent(MainActivity.this,AddPaintActivity.class);
//                startActivity(intentimg);
                startActivityForResult(intentimg,NEW_IMAGE);
                break;
            case R.id.btnrecordadd:
                Intent intentrec = new Intent(MainActivity.this,AddRecordActivity.class);
                startActivityForResult(intentrec,NEW_RECORD);

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_RECORD)
        {
            if(resultCode == RESULT_OK)
            {
                MyRecord myRecord = data.getParcelableExtra("newrec");
                mDbOpenHelper.INSERTrecpath(getExternalPath() +"Mengmo/rec/" + myRecord.getTitle());
//                Toast.makeText(this,getExternalPath()+"Mengmo/rec/"+myRecord.getTitle(),Toast.LENGTH_SHORT).show();

                doWhileCursorrecords();
                mCursor.close();
            }
        }
        else if (requestCode == NEW_TEXT)
        {
            if(resultCode == RESULT_OK)
            {
                MyText mytxt = data.getParcelableExtra("newtxt");
                mDbOpenHelper.INSERTtxtpath(getExternalPath() + "Mengmo/txt/" + mytxt.getDate() + mytxt.getTitle());
//                Toast.makeText(this,getExternalPath()+"Mengmo/txt/" + mytxt.getDate() + mytxt.getTitle(),Toast.LENGTH_LONG).show();

                doWhileCursortexts();
                mCursor.close();
            }
        }
        else if (requestCode == NEW_IMAGE)
        {
            if(resultCode == RESULT_OK)
            {
                MyImage newimg = data.getParcelableExtra("addimg");
                mDbOpenHelper.INSERTimgpath(getExternalPath() + "Mengmo/img/" + newimg.getDate() + newimg.getTitle());
//                Toast.makeText(this,getExternalPath() + "Mengmo/img/" + newimg.getDate() + newimg.getTitle(),Toast.LENGTH_LONG).show();
                doWhileCursorimages();
                mCursor.close();
//                imgfilelist();
            }
        }
        else if (requestCode == SHOW_TEXT)
        {
            if(resultCode == RESULT_OK)
            {
                MyText newtxt = data.getParcelableExtra("showtxt");
                String originpath = data.getStringExtra("originpath");
                mDbOpenHelper.deletetxtpath(originpath); //DB에서 기존파일경로를 삭제하고
                //새로운 파일의 경로를 추가한다
                mDbOpenHelper.INSERTtxtpath(getExternalPath() + "Mengmo/txt/" + newtxt.getDate() + newtxt.getTitle());
//                Toast.makeText(this,getExternalPath()+"Mengmo/txt/" + newtxt.getDate() + newtxt.getTitle(),Toast.LENGTH_LONG).show();

                doWhileCursortexts();
                mCursor.close();
//                txtfilelist();
            }
        }
        else if (requestCode == SHOW_IMG)
        {
            if(resultCode == RESULT_OK)
            {
                MyImage newimg = data.getParcelableExtra("shownewimg");
                String originimgpath = data.getStringExtra("Originimgpath");
                mDbOpenHelper.deleteimgpath(originimgpath);
                mDbOpenHelper.INSERTimgpath(getExternalPath() + "Mengmo/img/" + newimg.getDate() + newimg.getTitle());
//                imgfilelist();
//                Toast.makeText(this,getExternalPath() + "Mengmo/img/" + newimg.getDate() + newimg.getTitle(),Toast.LENGTH_LONG).show();
                doWhileCursorimages();
                mCursor.close();
            }
        }
    }

    public void remove(String path)
    {
        File file = new File(path);
        file.delete();
    }
}
