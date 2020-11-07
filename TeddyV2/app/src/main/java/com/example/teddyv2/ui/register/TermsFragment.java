package com.example.teddyv2.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.teddyv2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TermsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TermsFragment extends Fragment {

    private RegisterActivity registerActivity;

    private CheckBox checkbox;
    private Button continueBtn;

    private void setRegisterActivity(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }

    public TermsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param registerActivity RegisterActivity linked to teh fragment for callback
     * @return A new instance of fragment TermsFragment.
     */
    public static TermsFragment newInstance(RegisterActivity registerActivity) {
        TermsFragment fragment = new TermsFragment();
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
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_terms, container, false);
        checkbox = (CheckBox) vista.findViewById(R.id.policy_checkbox);
        continueBtn = (Button) vista.findViewById(R.id.policy_continue_btn);

        continueBtn.setEnabled(checkbox.isChecked());
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerActivity.navigateFromTermsToUserCreation();
            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continueBtn.setEnabled(checkbox.isChecked());
            }
        });

        return vista;
    }
}