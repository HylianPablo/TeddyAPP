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
 * Fragmento inicial del registro que presenta al usuario con la politica de privacidad que debe
 * aceptar para poder continuar.
 */
public class TermsFragment extends Fragment {

    private RegisterActivity registerActivity;

    private CheckBox checkbox;
    private Button continueBtn;

    /**
     * Establece referencia con la Actividad de Registro.
     *
     * @param registerActivity actividad de registro asociada
     */
    private void setRegisterActivity(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }

    public TermsFragment() {
        // Required empty public constructor
    }

    /**
     * Metodo factoria a usar en lugar del constructor para una correcta instanciacion del
     * Fragmento.
     *
     * @return instancia del Fragmento
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
        checkbox = vista.findViewById(R.id.policy_checkbox);
        continueBtn = vista.findViewById(R.id.policy_continue_btn);

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