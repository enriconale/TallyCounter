package com.naletto.enrico.tallycounter;

import android.app.Activity;
import android.os.Bundle;


public class SettingsActivity extends Activity {
    public static final String PREF_STEP = "pref_step";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }


}
