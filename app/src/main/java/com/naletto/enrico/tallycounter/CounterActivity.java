package com.naletto.enrico.tallycounter;

import android.app.Activity;
import android.os.Bundle;



public class CounterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new CounterFragment())
                    .commit();
        }
    }
}
