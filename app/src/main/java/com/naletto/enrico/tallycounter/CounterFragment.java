package com.naletto.enrico.tallycounter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;



public class CounterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TallyCounter counter = new TallyCounter();
    private TextView counterView;
    private ImageButton addToggle;
    private ImageButton subtractToggle;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CounterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CounterFragment newInstance(String param1, String param2) {
        CounterFragment fragment = new CounterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public CounterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

}
