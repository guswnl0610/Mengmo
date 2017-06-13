package com.example.guswn_000.mengmo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class ShowRecordActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener
{
    SeekBar seekBar;
    ImageButton playbtn, stopbtn, pausebtn;
    TextView textView;
    private MediaPlayer player = null;
    private final int PLAY_STOP = 0;
    private final int PLAYING = 1;
    private final int PLAY_PAUSE = 2;
    private int playerstate = PLAY_STOP;
    private String filename,filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_record);
        setTitle("맹모장");
        playbtn = (ImageButton)findViewById(R.id.showrecplay);
        stopbtn = (ImageButton)findViewById(R.id.showrecstop);
        pausebtn = (ImageButton)findViewById(R.id.showrecpause);
        seekBar = (SeekBar)findViewById(R.id.showrecseekbar);
        textView = (TextView)findViewById(R.id.showrectv);
        Intent intent = getIntent();
        filename = intent.getStringExtra("Record");
        textView.setText(filename);
        filepath = getExternalPath() + "Mengmo/rec/" + filename;



    }

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
//            super.handleMessage(msg);
            if (player == null) return;
            try
            {
                if(player.isPlaying())
                {
                    seekBar.setProgress(player.getCurrentPosition());
                    handler.sendEmptyMessageDelayed(0,100);
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
        if(v.getId() == R.id.showrecplay)
        {
            startbutton();
        }
        else if(v.getId() == R.id.showrecstop)
        {
            stopbutton();
        }
        else if (v.getId() == R.id.showrecpause)
        {
            pausebutton();
        }
    }

    public void startbutton()
    {
        if(playerstate == PLAY_STOP)
        {
            playerstate = PLAYING;
            initMediaPlayer();
            startPlay();
            updateUI();
        }
        else if (playerstate == PLAY_PAUSE)
        {
            playerstate = PLAYING;
            startPlay();
            updateUI();
        }
    }

    public void pausebutton()
    {
        if(playerstate == PLAYING)
        {
            playerstate = PLAY_PAUSE;
            pausePlay();
            updateUI();
        }
    }

    public void stopbutton()
    {
        if(playerstate == PLAYING || playerstate == PLAY_PAUSE)
        {
            playerstate = PLAY_STOP;
            stopPlay();
            releasePlayer();
            updateUI();
        }
    }

    public void initMediaPlayer()
    {
        if(player == null)
        {
            player = new MediaPlayer();
        }
        else
        {
            player.reset();
        }
        player.setOnCompletionListener(this);
        try
        {
            player.setDataSource(filepath);
            player.prepare();
            int point = player.getDuration();
            seekBar.setMax(point);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void startPlay()
    {
        try
        {
            player.start();
            handler.sendEmptyMessageDelayed(0,100);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void pausePlay()
    {
        player.pause();
        handler.sendEmptyMessageDelayed(0,0);
    }

    public void stopPlay()
    {
        player.stop();
        handler.sendEmptyMessageDelayed(0,0);
    }

    public void releasePlayer()
    {
        player.release();
        player = null;
        seekBar.setProgress(0);
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
    public void onCompletion(MediaPlayer mp)
    {
        playerstate = PLAY_STOP;
        handler.sendEmptyMessageDelayed(0,0);


    }

    public void updateUI()
    {
        if(playerstate == PLAY_STOP)
        {
            seekBar.setProgress(0);
        }
        else if (playerstate == PLAYING)
        {

        }
        else if (playerstate == PLAY_PAUSE)
        {

        }
    }
}
