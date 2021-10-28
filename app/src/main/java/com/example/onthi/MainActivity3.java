package com.example.onthi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.transition.Fade;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.onthi.MyService.MyBinder;



public class MainActivity3 extends AppCompatActivity {
    //share element

    ImageView imgPlay;
    //service
    private MyService myService;
    private  boolean isBound = false;
    private ServiceConnection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //animation
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        //share element
        imgPlay = findViewById(R.id.imageS3);

        Animation animation =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.anirotate);

        //service
         final Button btnPlay = (Button) findViewById(R.id.buttonPlayS3);
        final Button btnStop = (Button) findViewById(R.id.btnStopS3);
        final Button btnTua = (Button) findViewById(R.id.btnTuaS3);
        connection = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {

                isBound = false;
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyBinder binder = (MyBinder) service;
                myService = binder.getService();
                isBound = true;
            }
        };


        final Intent intent =
                new Intent(MainActivity3.this,
                        MyService.class);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bindService(intent, connection, Context.BIND_AUTO_CREATE);
                imgPlay.startAnimation(animation);

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(isBound){
                    unbindService(connection);
                    isBound = false;
                }
            }
        });

        btnTua.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(isBound){

                    myService.fastForward();
                }else{
                    Toast.makeText(MainActivity3.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBound){
                    // tua bài hát
                    myService.fastStart();
                }else{
                    Toast.makeText(MainActivity3.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });






    }
}