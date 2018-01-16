package com.loading.loadinganimationprogress;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView progressBar;

    private int mProgressStatus=0;
    private Handler mHandler = new Handler();
    private AnimationDrawable animationDrawable;
    private ProgressBar mProgressBar;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ImageView) findViewById(R.id.progress);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView2);
        textView.setTextColor(Color.WHITE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mProgressStatus < 100) {
                    mProgressStatus++;
                    android.os.SystemClock.sleep(100);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            //progressBar.setProgress(mProgressStatus);
                            mProgressBar.setProgress(mProgressStatus);
                            textView.setText(Integer.toString(mProgressStatus)+"%");
                            progressBar.setBackgroundResource(R.drawable.loading_animation);
                            animationDrawable = (AnimationDrawable) progressBar.getBackground();
                            progressBar.setVisibility(View.VISIBLE);
                            animationDrawable.start();

                        }
                    });
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"Loading Complete", 5000).show();
                        progressBar.setVisibility(View.GONE);
                        animationDrawable.stop();
                        Intent intent = new Intent(MainActivity.this, PostActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }).start();

    }
}
