package com.example.administrator.myapplication.ui.comm;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.administrator.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.example.administrator.myapplication.R.raw.welcome_video;

public class LoginActivity extends AppCompatActivity
{
    public static final String VIDEO_NAME = "welcome_video.mp4";

    private VideoView mVideoView;
    private TextView textView;
    TextView go;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_login);
        mVideoView= (VideoView) findViewById(R.id.videoView);
        textView= (TextView) findViewById(R.id.mytv);
        go= (TextView) findViewById(R.id.go);
        //获取资源文件路径
        File videoFile = getFileStreamPath(VIDEO_NAME);
        if(!videoFile.exists())
        {

            try {
                videoFile=getVideoFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       Start(videoFile.getPath());
    }

    private void Start( String videoFile)
    {

        go.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                if( mVideoView.isPlaying())
            {
                mVideoView.stopPlayback();
            }

                finish();

            }
        });
       // mVideoView.setVideoPath(videoFile);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                ObjectAnimator ani=ObjectAnimator.ofFloat(textView,"alpha",0,1);
                ani.setDuration(2000);
                ani.setRepeatCount(-1);
                ani.setRepeatMode(ObjectAnimator.REVERSE);
                ani.start();

            }
        });




    }

    public File getVideoFile() throws IOException {
        File videoFile=null;
        FileOutputStream fos=openFileOutput(VIDEO_NAME, MODE_PRIVATE);

        InputStream in = getResources().openRawResource(welcome_video);
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = in.read(buff)) != -1)
        {
            fos.write(buff, 0, len);

        }
        videoFile = getFileStreamPath(VIDEO_NAME);

        return videoFile;
    }
}
