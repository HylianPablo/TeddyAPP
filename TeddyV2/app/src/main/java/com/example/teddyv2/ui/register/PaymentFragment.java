package com.example.teddyv2.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.teddyv2.R;
import com.example.teddyv2.domain.user.User;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment {

    private RegisterActivity registerActivity;
    private User user;

    // Layout variables
    private TextView title;
    private Button continueBtn;

    private TextInputLayout paymentLayout;
    private EditText paymentText;

    private void setRegisterActivity(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }

    private void setUser(User user){
        this.user = user;
    }

    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PaymentFragment.
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

    private void assignLayoutVariables(View view){
        title = (TextView) view.findViewById(R.id.single_input_layout_title);
        continueBtn = (Button) view.findViewById(R.id.single_input_continue_btn);
        paymentLayout = (TextInputLayout) view.findViewById(R.id.single_input_layout_inputLayout);
        paymentText = (EditText) view.findViewById(R.id.single_input_layout_editText);
    }

    private void setLayoutLook(){
        title.setText(R.string.payment_title);

        paymentLayout.setStartIconDrawable(R.drawable.euro_icon);
        paymentLayout.setHint(getString(R.string.prompt_payment_account));
        paymentText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        if(!user.getPaymentAccount().equals(""))
            paymentText.setText(user.getPaymentAccount());

        continueBtn.setEnabled(isContinueOk());
    }

    private void setUpListeners(){
        paymentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isContinueOk()){
                    paymentLayout.setError(getString(R.string.invalid_payment));
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

    private boolean isContinueOk(){
        return Patterns.EMAIL_ADDRESS.matcher(paymentText.getText().toString()).matches();
    }

    private void setUserData(){
        user.setPaymentAccount(paymentText.getText().toString());
    }
}