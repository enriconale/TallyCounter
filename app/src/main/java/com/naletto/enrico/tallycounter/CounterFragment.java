package com.naletto.enrico.tallycounter;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;



public class CounterFragment extends Fragment {

    private TallyCounter counter = new TallyCounter();
    private TextView counterView;
    private ImageButton addToggle;
    private ImageButton subtractToggle;

    public CounterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_counter, container, false);

        counterView = (TextView)v.findViewById(R.id.textView);
        counterView.setText(counter.toString());

        addToggle = (ImageButton)v.findViewById(R.id.button1);
        //addToggle.getBackground().setColorFilter(0x61616161, PorterDuff.Mode.MULTIPLY);
        addToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.increment();
                counterView.setText(counter.toString());
                subtractToggle.setEnabled(counter.getCount() != 0);
            }
        });

        subtractToggle = (ImageButton)v.findViewById(R.id.button2);
        //subtractToggle.getBackground().setColorFilter(0x61616161, PorterDuff.Mode.MULTIPLY);
        subtractToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.decrement();
                counterView.setText(counter.toString());
                subtractToggle.setEnabled(counter.getCount() != 0);
            }
        });

        return v;
    }

    public void resetView() {
        counter.reset();
        counterView.setText(counter.toString());
        subtractToggle.setEnabled(counter.getCount() != 0);
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

}
