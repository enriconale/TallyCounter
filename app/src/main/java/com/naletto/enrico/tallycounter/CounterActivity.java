package com.naletto.enrico.tallycounter;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;


public class CounterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new CounterFragment())
                    .commit();
        }
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    CounterFragment x = (CounterFragment)getFragmentManager().findFragmentById(R.id
                            .container);
                    x.incrementCounter();
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    CounterFragment x = (CounterFragment)getFragmentManager().findFragmentById(R.id
                            .container);
                    x.decrementCounter();
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
}
