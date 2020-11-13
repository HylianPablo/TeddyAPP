package com.example.teddyv2.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.teddyv2.R;
import com.example.teddyv2.domain.user.User;
import com.example.teddyv2.utils.ValidationUtils;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Fragmento encargado de recoger el nombre y apellido del Usuario.
 */
public class NameFragment extends Fragment {

    private RegisterActivity registerActivity;
    private User user;

    // Layout variables
    private TextView title;
    private Button continueBtn;

    private TextInputLayout nameLayout;
    private EditText nameText;

    private TextInputLayout surnameLayout;
    private EditText surnameText;

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

    public NameFragment() {
        // Required empty public constructor
    }

    /**
     * Metodo factoria a usar en lugar del constructor para una correcta instanciacion del
     * Fragmento.
     *
     * @return instancia del Fragmento
     */
    public static NameFragment newInstance(RegisterActivity registerActivity, User user) {
        NameFragment fragment = new NameFragment();
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
        View view = inflater.inflate(R.layout.two_text_input_layout, container, false);
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
        title = view.findViewById(R.id.two_input_layout_title);
        continueBtn = view.findViewById(R.id.two_input_continue_btn);
        nameLayout = view.findViewById(R.id.two_input_layout_first_inputLayout);
        nameText = view.findViewById(R.id.two_input_layout_first_editText);
        surnameLayout = view.findViewById(R.id.two_input_layout_second_inputLayout);
        surnameText = view.findViewById(R.id.two_input_layout_second_editText);
    }

    /**
     * Establece el aspecto que debe tener el layout correspondiente, ya que se crea a partir de una
     * plantilla.
     */
    private void setLayoutLook(){
        title.setText(R.string.name_registration_title);

        nameLayout.setStartIconDrawable(null);
        nameLayout.setHint(getString(R.string.prompt_name));
        nameText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        if(!user.getName().equals(""))
            nameText.setText(user.getName());

        surnameLayout.setStartIconDrawable(null);
        surnameLayout.setHint(getString(R.string.prompt_surname));
        surnameText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        if(!user.getSurname().equals(""))
            surnameText.setText(user.getSurname());

        continueBtn.setEnabled(isContinueOk());
    }

    /**
     * Annade los controladores correspondientes a los elementos interactivos (botones, entradas de
     * texto, etc.).
     */
    private void setUpListeners(){
        // Watcher puede ser el mismo en ambos
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                continueBtn.setEnabled(isContinueOk());
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        };

        nameText.addTextChangedListener(watcher);
        surnameText.addTextChangedListener(watcher);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUserData();
                registerActivity.navigateFromNameToContact();
            }
        });
    }

    /**
     * Determina si el puede continuarse al siguiente Fragmento.
     * @return {@code true} si se han rellenado correctamente todos los campos o {@code false} en
     * caso contrario
     */
    private boolean isContinueOk(){
        return (
                ValidationUtils.isNotNull(nameText.getText().toString())
                        && ValidationUtils.isNotNull(surnameText.getText().toString())
        );
    }

    /**
     * Guarda los datos del Usuario. Llamar a esta funcion previo a finalizar este Fragmento.
     */
    private void setUserData(){
        user.setName(nameText.getText().toString());
        user.setSurname(surnameText.getText().toString());
    }
}