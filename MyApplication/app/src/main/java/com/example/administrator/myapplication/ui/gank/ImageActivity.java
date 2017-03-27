package com.example.administrator.myapplication.ui.gank;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.GlideRoundTransform;
import com.example.administrator.myapplication.Utill.ToastUtil;
import com.example.administrator.myapplication.eventbus.BeseEvent;
import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle.components.RxActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
        ViewCompat.setTransitionName(imgs, "shareimg");

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

            //由文件得到uri

            if(cachepath!=null)
            {
                Uri uri=Uri.fromFile(cachepath);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/jpeg");
                ImageActivity.this.startActivity(Intent.createChooser(shareIntent, "一张美图"));

            }else
            {
                ToastUtil.show(getApplicationContext(),"请先保存图片");
            }

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
                .compose(this.<Bitmap>bindToLifecycle())
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
