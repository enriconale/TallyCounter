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

    private TallyCounter mCounter;
    private TextView mCounterView;
    private ImageButton mAddToggle;
    private ImageButton mSubtractToggle;
    private SharedPreferences mSharedPref;

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
        int step = Integer.parseInt(mSharedPref.getString("pref_step", "1"));
        mCounter = new TallyCounter(loadTally(), step);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_counter, container, false);

        mCounterView = (TextView)v.findViewById(R.id.textView);
        mCounterView.setText(mCounter.toString());

        mAddToggle = (ImageButton)v.findViewById(R.id.button1);
        //addToggle.getBackground().setColorFilter(0x61616161, PorterDuff.Mode.MULTIPLY);
        mAddToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounter.increment();
                mCounterView.setText(mCounter.toString());
                mSubtractToggle.setEnabled(mCounter.canDecrease());
            }
        });

        mSubtractToggle = (ImageButton)v.findViewById(R.id.button2);
        //subtractToggle.getBackground().setColorFilter(0x61616161, PorterDuff.Mode.MULTIPLY);
        mSubtractToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounter.decrement();
                mCounterView.setText(mCounter.toString());
                mSubtractToggle.setEnabled(mCounter.canDecrease());
            }
        });

        return v;
    }

    public void resetView() {
        mCounter.reset();
        mCounterView.setText(mCounter.toString());
        mSubtractToggle.setEnabled(mCounter.canDecrease());
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int step = Integer.parseInt(mSharedPref.getString("pref_step", "1"));
        mCounter.setStep(step);
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            saveTally();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Unable to save the counter.", Toast.LENGTH_SHORT);
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

    public void incrementCounter() {
        mCounter.increment();
        mCounterView.setText(mCounter.toString());
        mSubtractToggle.setEnabled(mCounter.canDecrease());
    }

    public void decrementCounter() {
        mCounter.decrement();
        mCounterView.setText(mCounter.toString());
        mSubtractToggle.setEnabled(mCounter.canDecrease());
    }
}
