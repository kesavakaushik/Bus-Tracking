package com.vendor.transportation_driver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by dell on 1/27/2016.
 */
public class SplashScreen extends Activity {
    TextView tvText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_xml);
        tvText = (TextView) findViewById(R.id.text);
/*
        String fontPath = "fonts/roboto-condensed.light.ttf";
// Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

// Applying font
        tvText.setTypeface(tf);
*/

        Thread runTimer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            }
        };

        runTimer.start();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}


