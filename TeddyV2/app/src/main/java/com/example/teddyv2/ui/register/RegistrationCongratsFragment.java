package com.example.teddyv2.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teddyv2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationCongratsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationCongratsFragment extends Fragment {

    public RegistrationCongratsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment RegistrationCongratsFragment.
     */
    public static RegistrationCongratsFragment newInstance() {
        return new RegistrationCongratsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.congrats_fragment, container, false);
    }
}