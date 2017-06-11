package com.example.guswn_000.mengmo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRecordActivity extends AppCompatActivity
{
    String filepath,filename;
    MediaRecorder recorder = null;
    MediaPlayer player = null;
    ImageButton start,stop,play;
    SeekBar seekBar;
    boolean isplaying = false;
    int point = 0;
    MyRecord myRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        start = (ImageButton)findViewById(R.id.recstart);
        stop = (ImageButton)findViewById(R.id.recstop);
        play = (ImageButton)findViewById(R.id.recplay);
        seekBar = (SeekBar)findViewById(R.id.seekBar);



        start.setEnabled(true);
        stop.setEnabled(false);
        play.setEnabled(false);
    }


    Handler mprogressHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(player == null)
            {
                return;
            }

            try
            {
                if(player.isPlaying())
                {
                    seekBar.setProgress(player.getCurrentPosition());
                    mprogressHandler.sendEmptyMessageDelayed(0,100);
                }
                else
                {
                    player.release();
                    player = null;
//                    if(isplaying == false)
//                    {
//                        seekBar.setProgress(0);
//                    }
                }
            }
            catch (IllegalStateException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    };


    public void onClick(View v)
    {
        if(v.getId() == R.id.recstart)
        {

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            filepath = getExternalPath() + "Mengmo/"+dateFormat.format(date).toString()+"Rec.mp4";
            filename = dateFormat.format(date).toString()+"Rec.mp4";
            //녹음버튼 눌렀을 때
            start.setEnabled(false);
            stop.setEnabled(true);
            play.setEnabled(false);
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            recorder.setOutputFile(filepath);
            try
            {
                Toast.makeText(this,"녹음이 시작되었습니다.",Toast.LENGTH_SHORT).show();
                recorder.prepare();
                recorder.start();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else if (v.getId() == R.id.recstop)
        {
            //중지버튼 눌렀을 때
            if(recorder == null)
            {
                return;
            }
            recorder.stop();
            recorder.release();
            recorder = null;
            start.setEnabled(false);
            stop.setEnabled(false);
            play.setEnabled(true);
            Toast.makeText(this,"녹음이 저장되었습니다.",Toast.LENGTH_SHORT).show();

            myRecord = new MyRecord(filename);


        }
        else
        {

            isplaying = true;
            //재생 버튼 눌렀을 떄
            startplay();
        }
    }

    @Override
    public void onBackPressed()
    {
//        super.onBackPressed();
          if(myRecord == null) //음성녹음 버튼을 누르지 않았을 경우
          {
              finish();
          }
          else
          {
              Intent intent = getIntent();
              intent.putExtra("newrec",myRecord);
              setResult(RESULT_OK,intent);
              finish();
          }
    }

    public void startplay()
    {
        if(player != null)
        {
            player.stop();
            player.release();
            player = null;
        }

        Toast.makeText(this,"녹음된 파일을 재생합니다.",Toast.LENGTH_SHORT).show();

        try
        {
            player = new MediaPlayer();
            player.setDataSource(filepath);
            player.prepare();
            point = player.getDuration();
            seekBar.setMax(point);
//            player.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if(isplaying == true)
        {
            seekBar.setProgress(0);
            try
            {
                mprogressHandler.sendEmptyMessageDelayed(0,100);
                player.start();
            }
            catch (Exception e)
            {
                Toast.makeText(this,"Error : "+ e.getMessage(),Toast.LENGTH_SHORT).show();
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
