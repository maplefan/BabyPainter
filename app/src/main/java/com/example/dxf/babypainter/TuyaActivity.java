package com.example.dxf.babypainter;


import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Image;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class TuyaActivity extends AppCompatActivity implements DialogColorPicker.ColorSelectListener {


    public int oldColor;
    SimpleDoodleView simpleDoodleView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuya);

        final SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        final int sound_btn = sp.load(getApplicationContext(),R.raw.btn_tab,1);
        final float volume = 1f;

        ImageButton btnback = (ImageButton)findViewById(R.id.imageButtonTuyaBack);//注册退出按钮
        ImageButton btnChooseColor = (ImageButton)findViewById(R.id.imageButtonChooseColor);//注册选择颜色按钮
        ImageButton btnNext = (ImageButton)findViewById(R.id.imageButtonNext);
        ImageButton btnPrevious = (ImageButton)findViewById(R.id.imageButtonPrevious);
        ImageButton btnDelete = (ImageButton)findViewById(R.id.imageButtonDelete);

        btnback.setBackgroundColor(Color.TRANSPARENT);//退出按钮背景设置为透明
        btnChooseColor.setBackgroundColor(Color.TRANSPARENT);//选择颜色按钮背景设置为透明
        btnNext.setBackgroundColor(Color.TRANSPARENT);
        btnPrevious.setBackgroundColor(Color.TRANSPARENT);
        btnDelete.setBackgroundColor(Color.TRANSPARENT);

        simpleDoodleView = new SimpleDoodleView(this);
        RelativeLayout li = (RelativeLayout)findViewById(R.id.backgrndTuya);
        li.addView(simpleDoodleView);

        simpleDoodleView.setColor(Color.BLACK);//画笔默认颜色为黑色

        btnback.bringToFront();
        btnChooseColor.bringToFront();
        btnNext.bringToFront();
        btnPrevious.bringToFront();
        btnDelete.bringToFront();

        oldColor = simpleDoodleView.getColor();
        final Context context = this;





        btnChooseColor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp.play(sound_btn,volume,volume,1,0,1);
                DialogColorPicker.showDialogColorPicker(TuyaActivity.this, 1);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp.play(sound_btn,volume,volume,1,0,1);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp.play(sound_btn,volume,volume,1,0,1);
                 simpleDoodleView.delete();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp.play(sound_btn,volume,volume,1,0,1);
             simpleDoodleView.reundo();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp.play(sound_btn,volume,volume,1,0,1);
                 simpleDoodleView.undo();
            }
        });
    }

    @Override
    public void onColorSelected(DialogFragment dialog, int color)
    {
        simpleDoodleView.setColor(color);
    }
}
