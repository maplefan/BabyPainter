package com.example.dxf.babypainter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class TianseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tianse);
        getWindow().setBackgroundDrawableResource(R.drawable.backgrnd_tianse);//设置背景图片

        final SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        final int sound_btn = sp.load(getApplicationContext(),R.raw.btn_tab,1);
        final float volume = 1f;

        final ImageButton btnpangxie = (ImageButton)findViewById(R.id.imageButtonPangxie);//注册螃蟹按钮
        final ImageButton btnhaixing = (ImageButton)findViewById(R.id.imageButtonHaixing);//注册螃蟹按钮
        final ImageButton btnzhangyua = (ImageButton)findViewById(R.id.imageButtonZhangyua);//注册螃蟹按钮
        final ImageButton btnzhangyub = (ImageButton)findViewById(R.id.imageButtonZhangyub);//注册螃蟹按钮
        final ImageButton btnhaitun = (ImageButton)findViewById(R.id.imageButtonHaitun);//注册螃蟹按钮
        final ImageButton btnjingyu = (ImageButton)findViewById(R.id.imageButtonJingyu);//注册螃蟹按钮

        final ImageButton btnback = (ImageButton)findViewById(R.id.imageButtonBack);//注册退出按钮
        btnpangxie.setBackgroundColor(Color.TRANSPARENT);//螃蟹按钮背景设置为透明
        btnhaixing.setBackgroundColor(Color.TRANSPARENT);//螃蟹按钮背景设置为透明
        btnzhangyua.setBackgroundColor(Color.TRANSPARENT);//螃蟹按钮背景设置为透明
        btnzhangyub.setBackgroundColor(Color.TRANSPARENT);//螃蟹按钮背景设置为透明
        btnhaitun.setBackgroundColor(Color.TRANSPARENT);//螃蟹按钮背景设置为透明
        btnjingyu.setBackgroundColor(Color.TRANSPARENT);//螃蟹按钮背景设置为透明
        btnback.setBackgroundColor(Color.TRANSPARENT);//退出按钮背景设置为透明

        btnback.setOnClickListener(new View.OnClickListener() {//按下退出按钮时
            public void onClick(View v) {
                sp.play(sound_btn,volume,volume,1,0,1);
                finish();//结束当前活动，回到上一界面
            }
        });

        btnpangxie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//按下螃蟹按钮时
                sp.play(sound_btn,volume,volume,1,0,1);
                Intent intent=new Intent(TianseActivity.this, TiansePangxie.class);//跳转到给螃蟹上色到界面
                startActivity(intent);
            }
        });

        btnhaixing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//按下海星按钮时
                sp.play(sound_btn,volume,volume,1,0,1);
                Intent intent=new Intent(TianseActivity.this, TianseHaixing.class);//跳转到给海星上色到界面
                startActivity(intent);
            }
        });

        btnzhangyua.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//按下章鱼a按钮时
                sp.play(sound_btn,volume,volume,1,0,1);
                Intent intent=new Intent(TianseActivity.this, TianseZhangyua.class);//跳转到给章鱼a上色到界面
                startActivity(intent);
            }
        });
    }
}
