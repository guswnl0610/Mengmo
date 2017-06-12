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

    MyManageDB manageDB;

    final int NEW_RECORD = 20;
    final int NEW_TEXT = 21;
    final int NEW_IMAGE = 22;
    final int SHOW_TEXT = 23;
    final int SHOW_IMG = 24;

    private boolean permissionToRecordAccepted = false;
    private boolean permissionToWriteAccepted = false;
    private String [] permissions = {"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("맹모장");
        int requestCode = 200;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            requestPermissions(permissions,requestCode);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String path = getExternalPath();
        File file = new File(path + "Mengmo");
        file.mkdir();
        File recdir = new File(path + "Mengmo/rec");
        recdir.mkdir();
        File imgdir = new File(path + "Mengmo/img");
        imgdir.mkdir();
        File txtdir = new File(path + "Mengmo/txt");
        txtdir.mkdir();

        manageDB = MyManageDB.getmInstance(this);
        init();
        initlistviews();
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
        recfilelist();
        textAdapter = new TextAdapter(this,texts);
        txtlistview.setAdapter(textAdapter);
        txtfilelist();
        txtlistSetting();
        imageAdapter = new ImageAdapter(this,images);
        imglistview.setAdapter(imageAdapter);
        imgfilelist();
        imglistSetting();
    }

    public void recfilelist() //음성녹음 파일을 리스트에 넣는다
    {
//        File[] recfiles = new File(getExternalPath() + "Mengmo/rec").listFiles();
//        records.clear();
//        if(recfiles != null)
//        {
//            for (File f : recfiles)
//            {
//                if(f.getName().contains("Rec.mp4"))
//                {
//                    records.add(new MyRecord(f.getName()));
//                }
//            }
//        }
//        recSort();
//        recordAdapter.notifyDataSetChanged();
        //여기까지 정상적으로 되던거


//        records.clear();
//        if(recfiles != null)
//        {
////            records.clear();
//            String sql = "Select * from records order by date desc;";
//            Cursor cursor = manageDB.execSELECTquery(sql);
//            cursor.moveToFirst();
//            do
//            {
//                String str = "";
//                str += cursor.getInt(0)+"";
//                records.add(new MyRecord(cursor.getString(1),cursor.getString(2)));
//                recordAdapter.notifyDataSetChanged();
//            }
//            while (cursor.moveToNext());
//            cursor.close();
//        }

        records.clear();
        String sql = "Select path from recordpath order by path desc;";
        Cursor cursor = manageDB.execSELECTquery(sql);
        cursor.moveToFirst();
        while (cursor.moveToNext())
        {
            String path = cursor.getString(cursor.getColumnIndex("path"));
            File f = new File(path);
            records.add(new MyRecord(f.getName(),f.getName().substring(0,14)));
        }
        cursor.close();
        recSort();
        recordAdapter.notifyDataSetChanged();

    }

    public void txtfilelist()
    {
        File[] txtfiles = new File(getExternalPath() + "Mengmo/txt").listFiles();
        texts.clear();
        if(txtfiles != null)
        {
            for (File f : txtfiles)
            {
                if(f.getName().contains(".txt"))
                {
                    texts.add(new MyText(f.getName().substring(14,f.getName().length()), f.getName().substring(0,14)));
                    //이전에 만들어둔 텍스트들 읽어올 때에 내용, 날짜는 db를 통해서 가져와야할듯
                }
            }
        }
        textAdapter.sortTextDsc();
        textAdapter.notifyDataSetChanged();
    }

    public void imgfilelist()
    {
        File[] imgfiles = new File(getExternalPath() + "Mengmo/img").listFiles();
        images.clear();
        if(imgfiles != null)
        {
            for (File f : imgfiles)
            {
                if(f.getName().contains(".png"))
                {
//                    images.add(new MyImage(f.getName()));
                    images.add(new MyImage(f.getName().substring(14,f.getName().length()) , f.getName().substring(0,14)));

                }
            }
        }
        imageAdapter.sortImgDsc();
        imageAdapter.notifyDataSetChanged();
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
                        .setMessage("선택한 음성녹음을 정말 삭제하시겠습니까?")
                        .setNegativeButton("취소",null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                remove(getExternalPath() + "Mengmo/rec/" + records.get(position).getTitle());
                                recfilelist();
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
                        .setMessage("선택한 메모를 삭제하시겠습니까?")
                        .setNegativeButton("취소",null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                remove(getExternalPath() + "Mengmo/txt/" + texts.get(position).getTitle());
                                txtfilelist();
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
                        .setMessage("선택한 이미지를 삭제하시겠습니까?")
                        .setNegativeButton("취소",null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                remove(getExternalPath() + "Mengmo/img/" + images.get(position).getTitle());
                                imgfilelist();
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
                manageDB.INSERTrecpath(getExternalPath() + "Mengmo/rec/" + myRecord.getTitle());
//                manageDB.INSERTrecords(myRecord.getTitle(),myRecord.getDate());

                recfilelist();
            }
        }
        else if (requestCode == NEW_TEXT)
        {
            if(resultCode == RESULT_OK)
            {
                MyText mytxt = data.getParcelableExtra("newtxt");
//                texts.add(mytxt);
//                textAdapter.sortTextDsc();
                txtfilelist();
            }
        }
        else if (requestCode == NEW_IMAGE)
        {
            if(resultCode == RESULT_OK)
            {
                MyImage newimg = data.getParcelableExtra("addimg");
                imgfilelist();
            }
        }
        else if (requestCode == SHOW_TEXT)
        {
            if(resultCode == RESULT_OK)
            {
                MyText newtxt = data.getParcelableExtra("showtxt");
                txtfilelist();
            }
        }
        else if (requestCode == SHOW_IMG)
        {
            if(resultCode == RESULT_OK)
            {
                MyImage newimg = data.getParcelableExtra("shownewimg");
                imgfilelist();
            }
        }
    }


    public void remove(String path)
    {
        File file = new File(path);
        file.delete();
    }
}
