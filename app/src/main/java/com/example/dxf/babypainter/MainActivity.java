package com.example.dxf.babypainter;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.widget.ImageButton;
import android.view.View;

import static android.widget.ImageView.ScaleType.FIT_CENTER;


public class MainActivity extends AppCompatActivity {

    boolean state = false;
     Intent musicIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//展示activity_main活动
        getWindow().setBackgroundDrawableResource(R.drawable.backgrnd);//设置背景图片
        final ImageButton btnmusic = (ImageButton)findViewById(R.id.imageButton);//注册声音按钮
        ImageButton btnhuajia = (ImageButton)findViewById(R.id.imageButtonHuajia);//注册画架按钮
        ImageButton btntuya = (ImageButton)findViewById(R.id.imageButtonTuya);//注册涂鸦按钮

        final SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        final int sound_btn = sp.load(getApplicationContext(),R.raw.btn_tab,1);
        final float volume = 1f;

         musicIntent = new Intent(this,MusicService.class);
        if(state == false) {
            startService(musicIntent);//播放背景音乐
        }
        btnmusic.setMaxHeight(50);
        btnmusic.setMaxWidth(50);
        btnmusic.setBackgroundColor(Color.TRANSPARENT);
        btnhuajia.setBackgroundColor(Color.TRANSPARENT);
        btntuya.setBackgroundColor(Color.TRANSPARENT);


        btnmusic.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(state == false){//如果是有声音状态
                    sp.play(sound_btn,volume,volume,1,0,1);
                    state = true;//将标记设置为true
                    btnmusic.setImageResource(R.drawable.btnjingyin);//替换图片
                    stopService(musicIntent);
                }
                else{
                    sp.play(sound_btn,volume,volume,1,0,1);
                    state = false;//将标记设置为false
                    btnmusic.setImageResource(R.drawable.btnmusic);//替换图片
                    startService(musicIntent);
                }

                }
        });

        btntuya.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//当点击涂鸦按钮时
                sp.play(sound_btn,volume,volume,1,0,1);
                Intent intent=new Intent(MainActivity.this, TuyaActivity.class);//跳转到涂鸦界面
                startActivity(intent);
                 //Toast.makeText(MainActivity.this, "tuya", Toast.LENGTH_SHORT).show();
            }
        });

        btnhuajia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//当点击画架按钮时
                sp.play(sound_btn,volume,volume,1,0,1);
                Intent intent=new Intent(MainActivity.this, TianseActivity.class);//跳转到填色界面
                startActivity(intent);
                 //Toast.makeText(MainActivity.this, "tianse", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onUserLeaveHint() {
       // stopService(musicIntent);
        super.onUserLeaveHint();
    }

}
