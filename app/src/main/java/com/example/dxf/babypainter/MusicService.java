package com.example.dxf.babypainter;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
// TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onStart(Intent intent,int startId){
        super.onStart(intent, startId);

        if(mediaPlayer==null){

// R.raw.abc是资源文件，MP3格式的
            mediaPlayer = MediaPlayer.create(this, R.raw.bgm);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

        }
    }

    @Override
    public void onDestroy() {
// TODO Auto-generated method stub
        super.onDestroy();
        mediaPlayer.stop();
    }
}
