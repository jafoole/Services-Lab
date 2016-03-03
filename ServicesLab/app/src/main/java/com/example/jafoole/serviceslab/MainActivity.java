package com.example.jafoole.serviceslab;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    FloatingActionButton mFabPlay;
    FloatingActionButton mFabPause;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = (TextView)findViewById(R.id.textView);
        mFabPlay = (FloatingActionButton)findViewById(R.id.fabPlay);
        mFabPause = (FloatingActionButton)findViewById(R.id.fabPause);


    }

    @Override
    protected void onResume() {
        super.onResume();

        mFabPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent playIntent = new Intent(MainActivity.this, PlayService.class);
                startService(playIntent);



                //Notification
                NotificationCompat.Builder playBuilder = new NotificationCompat.Builder(MainActivity.this);
                playBuilder.setSmallIcon(android.R.drawable.ic_media_play);
                playBuilder.setContentTitle("Clair de Lune");
                playBuilder.setContentText("Claude Debussy");

                playBuilder.setAutoCancel(true);

                Intent intent = new Intent(MainActivity.this, MainActivity.class);

                int currentTime = (int) System.currentTimeMillis();

                PendingIntent pendingPlayIntent = PendingIntent.getActivity(MainActivity.this, (int) currentTime, intent, 0);
                playBuilder.setContentIntent(pendingPlayIntent);

                playBuilder.addAction(R.drawable.ic_stop_black_24dp, "Stop", pendingPlayIntent);
                playBuilder.addAction(android.R.drawable.ic_media_ff, "Fast Forward", pendingPlayIntent);

                Notification notification = playBuilder.build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(1, notification);
                //Notification end
            }
        });

        mFabPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent stopIntent = new Intent(MainActivity.this, PlayService.class);
                stopService(stopIntent);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

