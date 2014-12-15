package com.naletto.enrico.tallycounter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class CounterFragment extends Fragment {

    private static final String FILENAME = "tally.json";
    private final int MAX_VALUE = 10000000;

    private TallyCounter mCounter;
    private TextView mCounterView;
    private ImageButton mAddToggle;
    private ImageButton mSubtractToggle;
    private SharedPreferences mSharedPref;
    private boolean mScreenAlwaysOn;
    private boolean mVolumeButtonsActivated;
    private boolean mSwButtonsActivated;
    private int mStep;

    public CounterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        try {
            mStep = Integer.parseInt(mSharedPref.getString("pref_step", "1"));
            if (mStep > MAX_VALUE) {
                mStep = 1;
                Toast.makeText(getActivity().getApplicationContext(), R.string.step_over_limit,
                        Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = mSharedPref.edit();
                editor.putString("pref_step", "1");
                editor.apply();
            }
        } catch (NumberFormatException e) {
            mStep = 1;
            Toast.makeText(getActivity().getApplicationContext(), R.string.step_overflow,
                    Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = mSharedPref.edit();
            editor.putString("pref_step", "1");
            editor.apply();
        }
        mScreenAlwaysOn = mSharedPref.getBoolean("pref_screen_dim", false);
        mVolumeButtonsActivated = mSharedPref.getBoolean("pref_volume_buttons", false);
        mCounter = new TallyCounter(loadTally(), mStep);
        mSwButtonsActivated = true;
        if (mScreenAlwaysOn) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_counter, container, false);

        mCounterView = (TextView)v.findViewById(R.id.textView);
        mCounterView.setText(mCounter.toString());

        mAddToggle = (ImageButton)v.findViewById(R.id.button1);
        mAddToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddToggle.setEnabled(mCounter.increment());
                mSubtractToggle.setEnabled(true);
                mCounterView.setText(mCounter.toString());
            }
        });

        mSubtractToggle = (ImageButton)v.findViewById(R.id.button2);
        mSubtractToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubtractToggle.setEnabled(mCounter.decrement());
                mAddToggle.setEnabled(true);
                mCounterView.setText(mCounter.toString());
            }
        });

        return v;
    }

    public void resetView() {
        mCounter.reset();
        mCounterView.setText(mCounter.toString());
        mSubtractToggle.setEnabled(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.counter, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_reset:
                resetView();
                return true;
            case R.id.action_settings:
                Intent i = new Intent(getActivity(), SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.action_lock_buttons:
                if (mSwButtonsActivated) {
                    Toast.makeText(getActivity(), R.string.sw_buttons_disabled,
                            Toast.LENGTH_SHORT).show();
                    item.setIcon(R.drawable.ic_action_action_lock_outline);
                    item.setTitle(R.string.action_unlock_buttons);
                    mSwButtonsActivated = !mSwButtonsActivated;
                    mAddToggle.setVisibility(View.GONE);
                    mSubtractToggle.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getActivity(), R.string.sw_buttons_enabled,
                            Toast.LENGTH_SHORT).show();
                    item.setIcon(R.drawable.ic_action_action_lock_open);
                    item.setTitle(R.string.action_lock_buttons);
                    mSwButtonsActivated = !mSwButtonsActivated;
                    mAddToggle.setVisibility(View.VISIBLE);
                    mSubtractToggle.setVisibility(View.VISIBLE);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        try {
            mStep = Integer.parseInt(mSharedPref.getString("pref_step", "1"));
            if (mStep > MAX_VALUE) {
                mStep = 1;
                Toast.makeText(getActivity().getApplicationContext(), R.string.step_over_limit,
                        Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = mSharedPref.edit();
                editor.putString("pref_step", "1");
                editor.apply();
            }
        } catch (NumberFormatException e) {
            mStep = 1;
            Toast.makeText(getActivity().getApplicationContext(), R.string.step_overflow,
                    Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = mSharedPref.edit();
            editor.putString("pref_step", "1");
            editor.apply();
        }
        mScreenAlwaysOn = mSharedPref.getBoolean("pref_screen_dim", false);
        mVolumeButtonsActivated = mSharedPref.getBoolean
                ("pref_volume_buttons", false);
        if (mScreenAlwaysOn) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        mCounter.setStep(mStep);
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            saveTally();
        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.save_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveTally() throws JSONException, IOException {
        Writer writer = null;
        try {
            JSONObject json = mCounter.toJSON();
            OutputStream out = getActivity().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(json.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private int loadTally() {
        int tmpCount = 0;
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(getActivity()
                    .openFileInput(FILENAME)));
            jsonReader.beginObject();
            if (jsonReader.nextName().equals("count")) {
                tmpCount = jsonReader.nextInt();
            }
        } catch (Exception e) {
            return 0;
        }
        return tmpCount;
    }

    //Methods needed to let the volume buttons control the counter.
    public void incrementCounter() {
        if (mVolumeButtonsActivated) {
            mAddToggle.setEnabled(mCounter.increment());
            mSubtractToggle.setEnabled(true);
            mCounterView.setText(mCounter.toString());
        }
    }

    public void decrementCounter() {
        if (mVolumeButtonsActivated) {
            mSubtractToggle.setEnabled(mCounter.decrement());
            mAddToggle.setEnabled(true);
            mCounterView.setText(mCounter.toString());
        }
    }
}
