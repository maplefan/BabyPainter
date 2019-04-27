package com.example.dxf.babypainter;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.AudioManager;
import android.media.Image;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TiansePangxie extends AppCompatActivity {

    boolean first = false;
    boolean second = false;
    int a_r = 255;
    int a_g = 255;
    int a_b = 255;
    int b_r = 255;
    int b_g = 255;
    int b_b = 255;
    int c_r = 255;
    int c_g = 255;
    int c_b = 255;
    int color[] = {
            Color.rgb(255,0,255),
            Color.rgb(129,0,127),
            Color.rgb(138,42,227),
            Color.rgb(65,105,226),
            Color.rgb(63,224,208),
            Color.rgb(0,255,1),
            Color.rgb(128,255,0),
            Color.rgb(255,255,1),
            Color.rgb(254,165,0),
            Color.rgb(254,0,0),
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tianse_pangxie);
        getWindow().setBackgroundDrawableResource(R.drawable.backgrnd_color);

        final SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        final int sound_btn = sp.load(getApplicationContext(),R.raw.btn_tab,1);
        final float volume = 1f;

        ImageButton btnback = (ImageButton)findViewById(R.id.imageButtonColorBack);
        ImageButton btnNext = (ImageButton)findViewById(R.id.imageButtonNext1);
        ImageButton btnPrevious = (ImageButton)findViewById(R.id.imageButtonPrevious1);
        ImageButton btnDelete = (ImageButton)findViewById(R.id.imageButtonDelete);
        ImageButton btnAdd = (ImageButton)findViewById(R.id.imageButtonAdd);
        final ColourImageBaseLayerView col = (ColourImageBaseLayerView)findViewById(R.id.col);

        final ImageButton colorA = (ImageButton)findViewById(R.id.imageButtonColorA);
        final ImageButton colorB = (ImageButton)findViewById(R.id.imageButtonColorB);
        final ImageButton colorC = (ImageButton)findViewById(R.id.imageButtonColorC);

        btnback.setBackgroundColor(Color.TRANSPARENT);
        btnNext.setBackgroundColor(Color.TRANSPARENT);
        btnPrevious.setBackgroundColor(Color.TRANSPARENT);
        btnDelete.setBackgroundColor(Color.TRANSPARENT);
        colorA.setBackgroundColor(Color.TRANSPARENT);
        colorB.setBackgroundColor(Color.TRANSPARENT);
        colorC.setBackgroundColor(Color.TRANSPARENT);
        btnAdd.setBackgroundColor(Color.TRANSPARENT);




        final ChooseButton cbtn = new ChooseButton(this,300,300,250,80,10);
        LinearLayout li = (LinearLayout)findViewById(R.id.tianse);
        li.addView(cbtn);


        col.bringToFront();

        colorA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp.play(sound_btn,volume,volume,1,0,1);
                colorA.setColorFilter(null);
                first = false;
            }
        });

        colorB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp.play(sound_btn,volume,volume,1,0,1);
                colorB.setColorFilter(null);
            }
        });

        colorC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp.play(sound_btn,volume,volume,1,0,1);
                colorC.setColorFilter(null);
            }
        });

        cbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(first == false){
                    sp.play(sound_btn,volume,volume,1,0,1);
                    first = true;
                    if(cbtn.state > 0) {
                        colorA.setColorFilter(color[cbtn.state - 1]);
                        a_r = Color.red(color[cbtn.state - 1]);
                        a_g = Color.green(color[cbtn.state - 1]);
                        a_b = Color.blue(color[cbtn.state - 1]);
                    }
                    colorB.setColorFilter(null);
                }
                else{
                    sp.play(sound_btn,volume,volume,1,0,1);
                    if(cbtn.state > 0){
                        colorB.setColorFilter(color[cbtn.state-1]);
                        b_r = Color.red(color[cbtn.state - 1]);
                        b_g = Color.green(color[cbtn.state - 1]);
                        b_b = Color.blue(color[cbtn.state - 1]);
                    }
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp.play(sound_btn,volume,volume,1,0,1);
                c_r = (a_r + b_r)/2;
                c_g = (a_g + b_g)/2;
                c_b = (a_b + b_b)/2;
                colorC.setColorFilter(Color.rgb(c_r,c_g,c_b));
                col.setColor(Color.rgb(c_r,c_g,c_b));
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp.play(sound_btn,volume,volume,1,0,1);
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp.play(sound_btn,volume,volume,1,0,1);
                col.reundo();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp.play(sound_btn,volume,volume,1,0,1);
                col.undo();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp.play(sound_btn,volume,volume,1,0,1);
                col.delete();
            }
        });

    }
}
