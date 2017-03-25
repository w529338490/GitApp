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
    String cachepath;
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
        Picasso.with(this).load(imgPath).into(imgs);


        Glide.with(ImageActivity.this)
                .load(imgPath)
                .asBitmap() //必须
                .centerCrop()
                .placeholder(R.mipmap.ic_mr)
                .into(new SimpleTarget<Bitmap>()
                {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation)
                    {
                       // imgs.setImageBitmap(resource);
                        saveImageBitmap=resource;
                    }
                });


        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        //模拟手机返回键功能
                        Instrumentation inst = new Instrumentation();
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        super.run();
                    }
                }.start();
            }
        });
        toolbar.findViewById(R.id.img_share).setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Toast.makeText(ImageActivity.this,"aaa",Toast.LENGTH_SHORT).show();
            //由文件得到uri


        }
    });


        toolbar.findViewById(R.id.img_save).setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {

                Observable<Bitmap>observable=Observable.create(new Observable.OnSubscribe<Bitmap>()
                {
                    @Override
                    public void call(Subscriber<? super Bitmap> subscriber)
                    {
                        try
                        {
                            saveImageBitmap = Picasso.with(ImageActivity.this).load(imgPath).get();
                            subscriber.onNext(saveImageBitmap);
                            saveCroppedImage(saveImageBitmap);

                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                    }
                });

            observable .map(new Func1<Bitmap, String>()
            {
                @Override
                public String call(Bitmap bitmap)
                {


                    String s="保存成功";
                    Log.e("String","==============="+s);
                     return s;
                }
            })
                        .subscribeOn(Schedulers.io())//指定获取数据在io子线程
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>()
                        {
                            @Override
                            public void onCompleted()
                            {

                            }

                            @Override
                            public void onError(Throwable e)
                            {

                            }

                            @Override
                            public void onNext(String s)
                            {
                                Log.e("String","==============="+s);
                                Toast.makeText(ImageActivity.this,s,Toast.LENGTH_SHORT).show();


                            }
                        });


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
