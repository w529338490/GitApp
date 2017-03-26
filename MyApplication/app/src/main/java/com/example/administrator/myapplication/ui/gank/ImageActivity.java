package com.example.administrator.myapplication.ui.gank;

import android.app.Instrumentation;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.GlideRoundTransform;
import com.example.administrator.myapplication.eventbus.BeseEvent;
import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle.components.RxActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.R.attr.bitmap;

public class ImageActivity extends RxActivity implements View.OnClickListener
{

    ImageView imgs;
    String imgPath;
    Button back;
    Toolbar toolbar;
    Bitmap saveImageBitmap;
    File cachepath;
    Bitmap bitmap;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iamge);
        EventBus.getDefault().registerSticky(this);  //在onCreate，注入EventBus    使用registerSticky,防止Activity未启动,接收不到广播
        initView();
        initData();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initData()
    {

        Glide.with(ImageActivity.this)
                .load(imgPath)
                .transform(new GlideRoundTransform(this,20))
                .centerCrop()
                .placeholder(R.mipmap.ic_mr)
                .into(imgs);


        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Observable<String> ob=Observable.create(new Observable.OnSubscribe<String>()
                {
                    @Override
                    public void call(Subscriber<? super String> subscriber)
                    {

                        Instrumentation inst = new Instrumentation();
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);

                    }
                });
                ob.subscribeOn(Schedulers.io())//指定获取数据在io子线程
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>()
                    {
                        @Override
                        public void call(String s)
                        {

                        }
                    });
            }
        });
        toolbar.findViewById(R.id.img_share).setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            observerImg();

            //由文件得到uri
            Uri uri=Uri.fromFile(cachepath);
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/jpeg");
            ImageActivity.this.startActivity(Intent.createChooser(shareIntent, "一张美图"));

        }
    });


        toolbar.findViewById(R.id.img_save).setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            observerImg();
        }
    });

    }

    private void initView()
    {
        imgs= (ImageView) findViewById(R.id.imgs);
        toolbar=(Toolbar) findViewById(R.id.toolbar);

        imgs.setOnClickListener(this);
      //  back.setOnClickListener(this);
        initData();
    }

    //5、接收消息
   // 接收消息时，我们使用EventBus中最常用的onEventMainThread（）函数来接收消息，
    // 具体为什么用这个，我们下篇再讲，

    public void onEventMainThread(BeseEvent event)
    {

        imgPath = event.getUrl();

    }



    public void observerImg()
    {

        Observable<Bitmap>observable=Observable.create(new Observable.OnSubscribe<Bitmap>()
        {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber)
            {
                try
                {
                    saveImageBitmap = Picasso.with(ImageActivity.this).load(imgPath).get();
                    saveCroppedImage(saveImageBitmap);
                    subscriber.onNext(saveImageBitmap);

                } catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
        });

        observable  .subscribeOn(Schedulers.io())//指定获取数据在io子线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>()
                {
                    @Override
                    public void call(Bitmap bitmap)
                    {
                        Log.e("String","==============="+bitmap);

                        Toast.makeText(ImageActivity.this,"保存成功",Toast.LENGTH_SHORT).show();

                    }
                });


    }
    private void saveCroppedImage(Bitmap bmp)
    {





        File appDir = new File(Environment.getExternalStorageDirectory(), "GitApp");
        if (appDir.exists()&&appDir.isFile())
        {
            appDir.delete();

        }
        File ff=new File(appDir,"hahha"+SystemClock.elapsedRealtime()+".jpg");
        if(!ff.exists()||!ff.isFile())
        {
            ff.getParentFile().mkdir();
            try
            {
                ff.createNewFile();

            } catch (IOException e)
            {
                Log.e("cap","===============");
                e.printStackTrace();
            }

        }
        FileOutputStream outputStream = null;
        try
        {
            outputStream = new FileOutputStream(ff);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            cachepath=ff;

        }
         catch (IOException e)
        {
            Log.e("cap","");
            e.printStackTrace();
        }

        Uri uri = Uri.fromFile(ff);
        // 通知图库更新
        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        ImageActivity.this.sendBroadcast(scannerIntent);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
       // onDestroy的时候 销毁 EvenBus
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Override
    public void onClick(View view)
    {

    }
}
