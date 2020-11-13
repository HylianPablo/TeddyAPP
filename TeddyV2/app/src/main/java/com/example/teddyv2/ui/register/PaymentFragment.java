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
 * Fragmento encargado de recoger los datos de pago del Usuario.
 * <br>
 * Se asume que el dato de pago es una cuenta PayPal.
 */
public class PaymentFragment extends Fragment {

    private RegisterActivity registerActivity;
    private User user;

    // Layout variables
    private TextView title;
    private Button continueBtn;

    private TextInputLayout paymentLayout;
    private EditText paymentText;

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

    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Metodo factoria a usar en lugar del constructor para una correcta instanciacion del
     * Fragmento.
     *
     * @return instancia del Fragmento
     */
    public static PaymentFragment newInstance(RegisterActivity registerActivity, User user) {
        PaymentFragment fragment = new PaymentFragment();
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
        View view = inflater.inflate(R.layout.single_text_input_layout, container, false);
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
        title = view.findViewById(R.id.single_input_layout_title);
        continueBtn = view.findViewById(R.id.single_input_continue_btn);
        paymentLayout =  view.findViewById(R.id.single_input_layout_inputLayout);
        paymentText = view.findViewById(R.id.single_input_layout_editText);
    }

    /**
     * Establece el aspecto que debe tener el layout correspondiente, ya que se crea a partir de una
     * plantilla.
     */
    private void setLayoutLook(){
        title.setText(R.string.reg_title_payment);

        paymentLayout.setStartIconDrawable(R.drawable.euro_icon);
        paymentLayout.setHint(getString(R.string.prompt_payment_account));
        paymentText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        if(!user.getPaymentAccount().equals(""))
            paymentText.setText(user.getPaymentAccount());

        continueBtn.setEnabled(isContinueOk());
    }

    /**
     * Annade los controladores correspondientes a los elementos interactivos (botones, entradas de
     * texto, etc.).
     */
    private void setUpListeners(){
        paymentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isContinueOk()){
                    paymentLayout.setError(getString(R.string.error_invalid_payment_account));
                }else{
                    paymentLayout.setError(null);
                    continueBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUserData();
                registerActivity.navigateFromPaymentToLevelSelection();
            }
        });
    }

    /**
     * Determina si el puede continuarse al siguiente Fragmento.
     * @return {@code true} si se han rellenado correctamente todos los campos o {@code false} en
     * caso contrario
     */
    private boolean isContinueOk(){
        return ValidationUtils.isValidEmail(paymentText.getText().toString());
    }

    /**
     * Guarda los datos del Usuario. Llamar a esta funcion previo a finalizar este Fragmento.
     */
    private void setUserData(){
        user.setPaymentAccount(paymentText.getText().toString());
    }
}