package com.example.administrator.myapplication.ui.user;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.administrator.myapplication.DB.DbBean.VideoBean;
import com.example.administrator.myapplication.DB.DbDao.VideoDao;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.ToastUtil;
import com.example.administrator.myapplication.adapter.MyVideoViewAdapter;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MyVideoActivity extends AppCompatActivity
{
    RecyclerView recyview;
    List<VideoBean> myVideos;
    MyVideoViewAdapter adpter;
    LinearLayoutManager manger;
    ItemTouchHelper helper;   //实现recyview拖拽动画
    VideoDao Dao=new VideoDao(MyVideoActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video);
        manger=new LinearLayoutManager(this);
        myVideos= (List<VideoBean>) this.getIntent().getSerializableExtra("myvideo");
        init();
    }

    private void init()
    {
        recyview= (RecyclerView) findViewById(R.id.recyview);
        adpter=new MyVideoViewAdapter(myVideos,this);
        recyview.setAdapter(adpter);
        recyview.setLayoutManager(manger);
        adpter.SetDownlodLiner(new MyVideoViewAdapter.DownlodLiner()
        {
            @Override
            public void down(String path, String tittle)
            {
                DownLodVieo(path,tittle);
            }

            @Override
            public void delet(VideoBean currentBean,int position)
            {
                Dao.deletData(currentBean);
                myVideos.remove(position);
                adpter.notifyDataSetChanged();

            }
        });


    }
    public void DownLodVieo(String path,String tittle)
    {
        DownTask downTask=new DownTask();
        downTask.execute(path,tittle);

    }


    class DownTask extends AsyncTask<String, Integer, byte[]>
    {
        private ProgressDialog progressDialog;//进度条
        private byte[] current = new byte[2 * 1024];//每次读到的字节数组
        private byte[] total;//下载图片后汇总的字节数组
        private boolean flag; //是否被取消
        private String tittle;

      //下载之前 干么事

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MyVideoActivity.this);
            progressDialog.setMessage("正在操作中，请稍候……");
            progressDialog.setMax(100);//进度条最大值
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//水平样式
            progressDialog.setIndeterminate(false);//进度条的动画效果（有动画则无进度值）

            flag = true;
            //退出对话框事件
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    flag = false;
                }
            });
            progressDialog.show();
        }
    //后台执行任务
        @Override
        protected byte[] doInBackground(String... params)
        {
            tittle=params[1];
            try {
                //创建URL对象，用于访问网络资源
                URL url = new URL(params[0]);
                //获得HttpUrlConnection对象
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                //设置超时时间
                http.setConnectTimeout(5000);
                Logger.e( http.getResponseMessage());
                //获得总长度
                int length = http.getContentLength();
                Logger.e(length/1024+"");
                Logger.e("=======", length + "");
                //为total分配空间
                total = new byte[length];

                //开始读取数据
                int pointer = 0;//已用掉的索引
                InputStream is = http.getInputStream();

                int real = is.read(current); //读取当前批次的字节并保存到数组中
                while(flag && real > 0){
                    //如果读取到了字节，则保存到total中
                    for(int i = 0; i < real; i ++){
                        total[pointer + i] = current[i];
                    }
                    //指针向后移
                    pointer += real;
                    //计算进度
                    int progress = (int)((double)pointer / length * 100);//先计算出百分比在转换成整型
                    //更新进度
                    publishProgress(progress, pointer, length);
                    //继续读取下一批
                    real = is.read(current);
                }
                //关闭流对象
                is.close();
                //将获得的所有字节全部返回
                return total;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(byte[] bytes)
        {
            super.onPostExecute(bytes);

            progressDialog.dismiss();
            if(bytes!=null)
            {
                saveCroppedImage(bytes,tittle);
            }else
            {
                ToastUtil.show("下载失败!");
            }


        }

        //更新进度条回调
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
//            progressDialog.setMessage(
//                    String.format("已读%d M, 共%d M",//字节为单位
//                            values[1] / 1024 / 1024, values[2] / 1024 / 1024));//将values[1]赋给第一个%d,第二个同理

            progressDialog.setProgress(values[0]);//进度动态提示

        }
    }

    //把下载的 字节数组,写入指定文件
    private void saveCroppedImage(byte[] bytes,String tittle)
    {
        String path = null;
        //System.currentTimeMillis()
        String imageName = tittle + ".mp4";
        String local_file =
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/GitApp/";
        File f = new File(local_file);
        if (!f.exists())
        {
            f.mkdirs();  //mkdirs 创建多级目录,防止上级目录不存在
        }
        path = f.getAbsolutePath() + "/" + imageName;
        File saveIamge = new File(path);

        FileOutputStream outputStream = null;
        try
        {
            outputStream = new FileOutputStream(saveIamge);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            ToastUtil.showLong("下载完成,文件在："+saveIamge.getAbsolutePath());
        } catch (IOException e)
        {
            ToastUtil.showLong("下载失败,文件在");
            e.printStackTrace();
        } finally
        {
            try
            {
                if (outputStream != null)
                {
                    outputStream.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
}
