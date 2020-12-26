package com.example.teddyv2.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.teddyv2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USERNAME = "param1";

    // TODO: Rename and change types of parameters
    private String username;

    public RatesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     * @return A new instance of fragment RatesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RatesFragment newInstance(String user) {
        RatesFragment fragment = new RatesFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(USERNAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_rates, container, false);
        final Fragment f = this;
        Button backButton = root.findViewById(R.id.salirButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(f).commit();
            }
        });
        return root;
    }
}