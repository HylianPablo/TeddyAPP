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
 * Fragmento puramente estetico para dar al usuario un mensaje indicando que la creacion de su
 * cuenta se ha llevado acabo de forma exitosa.
 */
public class RegistrationCongratsFragment extends Fragment {

    private RegisterActivity registerActivity;

    // Layout variables
    private TextView subTitle;
    private Button continueBtn;

    public RegistrationCongratsFragment() {
        // Required empty public constructor
    }

    /**
     * Establece referencia con la Actividad de Registro.
     *
     * @param registerActivity actividad de registro asociada
     */
    private void setRegisterActivity(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }

    /**
     * Metodo factoria a usar en lugar del constructor para una correcta instanciacion del
     * Fragmento.
     *
     * @return instancia del Fragmento
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

    /**
     * Asigna las referencias a los elementos del Layout con las variables.
     *
     * @param view vista del layout
     */
    private void assignLayoutVariables(View view){
        subTitle = view.findViewById(R.id.congrats_subtitle);
        continueBtn = view.findViewById(R.id.congrats_continue_btn);
    }

    /**
     * Establece el aspecto que debe tener el layout correspondiente, ya que se crea a partir de una
     * plantilla.
     */
    private void setLayoutLook(){
        subTitle.setText(R.string.reg_congrats);
    }

    /**
     * Annade los controladores correspondientes a los elementos interactivos (botones, entradas de
     * texto, etc.).
     */
    private void setUpListeners(){
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerActivity.navigateToMainActivity();
            }
        });
    }
}