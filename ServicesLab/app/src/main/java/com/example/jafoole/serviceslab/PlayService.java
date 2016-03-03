package com.example.jafoole.serviceslab;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by sbabba on 3/2/16.
 */
public class PlayService extends Service {
    MediaPlayer mediaPlayer = new MediaPlayer();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        onPlay();

    }

    public void onPlay(){
        try {
            String url = "http://download.lisztonian.com/music/download/Clair+de+Lune-113.mp3";
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
        } catch (Throwable thr){
            thr.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer.isPlaying()){
            Toast.makeText(PlayService.this, "Paused", Toast.LENGTH_SHORT).show();
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
            Toast.makeText(PlayService.this, "Playing", Toast.LENGTH_SHORT).show();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();
    }
}
