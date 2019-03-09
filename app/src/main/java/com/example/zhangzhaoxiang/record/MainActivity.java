package com.example.zhangzhaoxiang.record;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {
    private Button mBtn1,mBtn2,mBtn3;
    private String path;
    private String mVoiceName;             //保存的录音的名字
    private MediaRecorder recod;//Android中的媒体录制类
    private void createRecod(){
        recod=new MediaRecorder();
//给recod添加媒体源
        recod.setAudioSource(MediaRecorder.AudioSource.MIC);
//给recod设置输出格式
        recod.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        recod.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mVoiceName = getMyTime() + ".amr";
        recod.setOutputFile(path);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
        mBtn1=(Button) findViewById(R.id.mBtn1);
        mBtn2=(Button) findViewById(R.id.mBtn2);
        mBtn3=(Button) findViewById(R.id.mBtn3);


        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
    }
    private String getMyTime() {
        //存储格式化后的时间
        String time;
        //存储上午下午
        String ampTime = "";
        //判断上午下午，am上午，值为 0 ； pm下午，值为 1
        int apm = Calendar.getInstance().get(Calendar.AM_PM);
        if (apm == 0) {
            ampTime = "上午";
        } else {
            ampTime = "下午";
        }
        //设置格式化格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd E " + ampTime + " kk:mm:ss");
        time = format.format(new Date());

        return time;
    }

    public void startRecod(String path){
        this.path=path;
        if(recod!=null){
            return;
        }
        createRecod();

//准备---》启动
        try {
            recod.prepare();
            recod.start();
        } catch (IllegalStateException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void stopRecod(){
        if(recod==null){
            return;
        }
        recod.stop();
        recod.release();
        recod=null;
        System.gc();
    }


    @Override
    public void onClick(View v) {
        int ID=v.getId();
        switch (ID) {
            case R.id.mBtn1:
//开始录制
                startRecod(Environment.getExternalStorageDirectory().getAbsolutePath()+"/abc.amr");
                break;

            case R.id.mBtn2:
                stopRecod();
                break;
            case R.id.mBtn3:
                MediaPlayer player=new MediaPlayer();
                try {
                    player.setDataSource(this, Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+"/abc.amr"));
                    player.prepare();
                    player.start();
                } catch (IllegalArgumentException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SecurityException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalStateException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }


                break;
        }
    }

}
