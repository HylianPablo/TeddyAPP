package com.example.teddyv2.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.teddyv2.R;
import com.example.teddyv2.domain.user.User;
import com.example.teddyv2.domain.user.UserLevel;

/**
 * Fragmento encargado de recoger el nivel del Usuario.
 */
public class LevelSelectionFragment extends Fragment {

    private RegisterActivity registerActivity;
    private User user;

    // Layout variables
    private TextView title;
    private Button continueBtn;
    private Spinner spinner;

    /**
     * Establece referencia con la Actividad de Registro.
     *
     * @param registerActivity actividad de registro asociada
     */
    private void setRegisterActivity(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }

    /**
     * Establece referencia con el Usuario que se se esta creando.
     *
     * @param user usuario que se esta creando
     */
    private void setUser(User user){
        this.user = user;
    }

    public LevelSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Metodo factoria a usar en lugar del constructor para una correcta instanciacion del
     * Fragmento.
     *
     * @return instancia del Fragmento
     */
    public static LevelSelectionFragment newInstance(RegisterActivity registerActivity, User user) {
        LevelSelectionFragment fragment = new LevelSelectionFragment();
        fragment.setRegisterActivity(registerActivity);
        fragment.setUser(user);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.single_spinner_fragment, container, false);
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
    private void assignLayoutVariables(View view) {
        title = view.findViewById(R.id.single_spinner_layout_title);
        continueBtn = view.findViewById(R.id.single_spinner_continue_btn);
        spinner = view.findViewById(R.id.single_spinner_layout_spinner);
    }

    /**
     * Establece el aspecto que debe tener el layout correspondiente, ya que se crea a partir de una
     * plantilla.
     */
    private void setLayoutLook(){
        title.setText(R.string.level_selection_title);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.level_spinner,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Annade los controladores correspondientes a los elementos interactivos (botones, entradas de
     * texto, etc.).
     */
    private void setUpListeners(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // En este caso se setea aqui el dato del usuario porque es mas sencillo
                user.setLevel(UserLevel.getUserLevelByNumber(i));
                continueBtn.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                continueBtn.setEnabled(false);
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerActivity.finishRegistration();
            }
        });
    }

}