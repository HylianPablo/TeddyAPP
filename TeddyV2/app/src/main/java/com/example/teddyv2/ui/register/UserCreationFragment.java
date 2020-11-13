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
 * Fragmento encargado de recoger el nombre de usuario y contrasenna. El nombre de usuario deberia
 * validarse contra la base de datos para comprobar si existe. Lo mas correcto referido a UX seria
 * comprobarlo segun lo va escribiendo, pero puede ser mas eficiente hacerlo al pulsar el boton,
 * ya que la escalabilidad en caso contrario se veria muy afectada.
 */
public class UserCreationFragment extends Fragment {

    private RegisterActivity registerActivity;
    private User user;

    // Layout variables
    private TextView title;
    private Button continueBtn;

    private TextInputLayout userLayout;
    private EditText userText;

    private TextInputLayout passwordLayout;
    private EditText passwordText;

    private TextInputLayout passwordRepeatLayout;
    private EditText passwordRepeatText;


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

    public UserCreationFragment() {
        // Required empty public constructor
    }

    /**
     * Metodo factoria a usar en lugar del constructor para una correcta instanciacion del
     * Fragmento.
     *
     * @return instancia del Fragmento
     */
    public static UserCreationFragment newInstance(RegisterActivity registerActivity, User user) {
        UserCreationFragment fragment = new UserCreationFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.three_text_input_layout, container, false);
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
        title = view.findViewById(R.id.three_input_layout_title);
        continueBtn = view.findViewById(R.id.three_input_continue_btn);
        userLayout = view.findViewById(R.id.three_input_layout_first_inputLayout);
        userText = view.findViewById(R.id.three_input_layout_first_editText);
        passwordLayout = view.findViewById(R.id.three_input_layout_second_inputLayout);
        passwordText = view.findViewById(R.id.three_input_layout_second_editText);
        passwordRepeatLayout = view.findViewById(R.id.three_input_layout_third_inputLayout);
        passwordRepeatText = view.findViewById(R.id.three_input_layout_third_editText);
    }

    /**
     * Establece el aspecto que debe tener el layout correspondiente, ya que se crea a partir de una
     * plantilla.
     */
    private void setLayoutLook(){
        title.setText(R.string.user_creation_title);

        userLayout.setStartIconDrawable(R.drawable.person_icon);
        userLayout.setHint(getString(R.string.prompt_username));
        userText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        if(!user.getUsername().equals(""))
            userText.setText(user.getUsername());

        passwordLayout.setStartIconDrawable(R.drawable.lock_icon);
        passwordLayout.setHint(getString(R.string.prompt_password));
        passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        if(!user.getPassword().equals(""))
            passwordText.setText(user.getPassword());

        passwordRepeatLayout.setStartIconDrawable(R.drawable.lock_icon);
        passwordRepeatLayout.setHint(getString(R.string.prompt_password_repetition));
        passwordRepeatText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        continueBtn.setEnabled(isContinueOk());
    }

    /**
     * Annade los controladores correspondientes a los elementos interactivos (botones, entradas de
     * texto, etc.).
     */
    private void setUpListeners(){
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    setUserData();
                    registerActivity.navigateFromUserCreationToName();
            }
        });

        userText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!ValidationUtils.isNotNull(userText.getText().toString())){
                    userLayout.setError(getString(R.string.invalid_username));
                }else{
                    userLayout.setError(null);
                    continueBtn.setEnabled(isContinueOk());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!ValidationUtils.isValidPassword(passwordText.getText().toString())){
                    passwordLayout.setError(getString(R.string.invalid_password));
                }else{
                    passwordLayout.setError(null);
                    continueBtn.setEnabled(isContinueOk());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        passwordRepeatText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!passwordsMatch()){
                    passwordRepeatLayout.setError(getString(R.string.passwords_dont_match));
                }else{
                    passwordRepeatLayout.setError(null);
                    continueBtn.setEnabled(isContinueOk());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    /**
     * Comprueba que las dos contrasennas introducidas coincidan.
     *
     * @return {@code true} si coinciden o {@code false} en caso contrario
     */
    private boolean passwordsMatch(){
        return passwordText.getText().toString().equals(passwordRepeatText.getText().toString());
    }

    /**
     * Determina si el boton puede estar habilitado o no.
     *
     * @return {@code true} si se han rellenado correctamente todos los campos o {@code false} en
     * caso contrario
     */
    private boolean isContinueOk(){
        return (
                ValidationUtils.isNotNull(userText.getText().toString())
                        && ValidationUtils.isValidPassword(passwordText.getText().toString())
                        && passwordsMatch()
        );
    }

    /**
     * Guarda los datos del Usuario. Llamar a esta funcion previo a finalizar este Fragmento.
     */
    private void setUserData(){
        user.setUsername(userText.getText().toString());
        user.setPassword(passwordText.getText().toString());
    }

    /**
     * Muestra mensaje de error en el campo "username" con el mensaje "El nombre de usuario no está disponible".
     *
     */
    public void mostrarErrorUsuario(){
        userLayout.setError(getString(R.string.username_taken_error));
    }

}