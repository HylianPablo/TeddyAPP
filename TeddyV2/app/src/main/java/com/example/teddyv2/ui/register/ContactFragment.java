package com.example.teddyv2.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.InputType;
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
 * Fragmento encargado de recoger los datos de contacto del Usuario.
 */
public class ContactFragment extends Fragment {

    private RegisterActivity registerActivity;
    private User user;

    // Layout variables
    private TextView title;
    private Button continueBtn;

    private TextInputLayout phoneLayout;
    private EditText phoneText;

    private TextInputLayout emailLayout;
    private EditText emailText;

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

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Metodo factoria a usar en lugar del constructor para una correcta instanciacion del
     * Fragmento.
     *
     * @return instancia del Fragmento
     */
    public static ContactFragment newInstance(RegisterActivity registerActivity, User user) {
        ContactFragment fragment = new ContactFragment();
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
        phoneLayout = view.findViewById(R.id.two_input_layout_first_inputLayout);
        phoneText = view.findViewById(R.id.two_input_layout_first_editText);
        emailLayout = view.findViewById(R.id.two_input_layout_second_inputLayout);
        emailText = view.findViewById(R.id.two_input_layout_second_editText);
    }

    /**
     * Establece el aspecto que debe tener el layout correspondiente, ya que se crea a partir de una
     * plantilla.
     */
    private void setLayoutLook(){
        title.setText(R.string.reg_title_contact_info);

        phoneLayout.setStartIconDrawable(R.drawable.phone_icon);
        phoneLayout.setHint(getString(R.string.prompt_phone));
        phoneText.setInputType(InputType.TYPE_CLASS_PHONE);
        if(!user.getPhone().equals(""))
            phoneText.setText(user.getPhone());

        emailLayout.setStartIconDrawable(R.drawable.email_icon);
        emailLayout.setHint(getString(R.string.prompt_email));
        emailText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        if(!user.getEmail().equals(""))
            emailText.setText(user.getEmail());

        continueBtn.setEnabled(isContinueOk());
    }

    /**
     * Annade los controladores correspondientes a los elementos interactivos (botones, entradas de
     * texto, etc.).
     */
    private void setUpListeners(){
        phoneText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!ValidationUtils.isValidPhone(phoneText.getText().toString())){
                    phoneLayout.setError(getString(R.string.error_invalid_phone));
                }else{
                    phoneLayout.setError(null);
                    continueBtn.setEnabled(isContinueOk());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!ValidationUtils.isValidEmail(emailText.getText().toString())){
                    emailLayout.setError(getString(R.string.error_invalid_email));
                }else{
                    emailLayout.setError(null);
                    continueBtn.setEnabled(isContinueOk());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUserData();
                registerActivity.navigateFromContactToPayment();
            }
        });
    }

    /**
     * Guarda los datos del Usuario. Llamar a esta funcion previo a finalizar este Fragmento.
     */
    private void setUserData(){
        user.setPhone(phoneText.getText().toString());
        user.setEmail(emailText.getText().toString());
    }

    /**
     * Determina si el puede continuarse al siguiente Fragmento.
     * @return {@code true} si se han rellenado correctamente todos los campos o {@code false} en
     * caso contrario
     */
    private boolean isContinueOk(){
        return (
                ValidationUtils.isValidPhone(phoneText.getText().toString())
                        && ValidationUtils.isValidEmail(emailText.getText().toString())
        );
    }

}