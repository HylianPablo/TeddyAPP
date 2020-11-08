package com.example.teddyv2.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.teddyv2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationCongratsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationCongratsFragment extends Fragment {

    private RegisterActivity registerActivity;

    // Layout variables
    private TextView subTitle;
    private Button continueBtn;

    public RegistrationCongratsFragment() {
        // Required empty public constructor
    }

    private void setRegisterActivity(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment RegistrationCongratsFragment.
     */
    public static RegistrationCongratsFragment newInstance(RegisterActivity registerActivity) {
        RegistrationCongratsFragment fragment = new RegistrationCongratsFragment();
        fragment.setRegisterActivity(registerActivity);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.congrats_fragment, container, false);
        assignLayoutVariables(view);
        setLayoutLook();
        setUpListeners();
        return view;
    }

    private void assignLayoutVariables(View view){
        subTitle = (TextView) view.findViewById(R.id.congrats_subtitle);
        continueBtn = (Button) view.findViewById(R.id.congrats_continue_btn);
    }

    private void setLayoutLook(){
        subTitle.setText(R.string.registration_congrats_subtitle);
    }

    private void setUpListeners(){
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerActivity.navigateToMainActivity();
            }
        });
    }
}