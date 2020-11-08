package com.example.teddyv2.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.text.InputType;
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
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
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

    private void setRegisterActivity(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }

    private void setUser(User user){
        this.user = user;
    }

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContactFragment.
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

    private void assignLayoutVariables(View view){
        title = (TextView) view.findViewById(R.id.two_input_layout_title);
        continueBtn = (Button) view.findViewById(R.id.two_input_continue_btn);
        phoneLayout = (TextInputLayout) view.findViewById(R.id.two_input_layout_first_inputLayout);
        phoneText = (EditText) view.findViewById(R.id.two_input_layout_first_editText);
        emailLayout = (TextInputLayout) view.findViewById(R.id.two_input_layout_second_inputLayout);
        emailText = (EditText) view.findViewById(R.id.two_input_layout_second_editText);
    }

    private void setLayoutLook(){
        title.setText(R.string.contact_registration_title);

        phoneLayout.setStartIconDrawable(R.drawable.phone_icon);
        phoneLayout.setHint(getString(R.string.phone_prompt));
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

    private void setUpListeners(){
        phoneText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isPhoneValid(phoneText.getText().toString())){
                    phoneLayout.setError(getString(R.string.invalid_phone));
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
                if(!isEmailValid(emailText.getText().toString())){
                    emailLayout.setError(getString(R.string.invalid_email));
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

    private void setUserData(){
        user.setPhone(phoneText.getText().toString());
        user.setEmail(emailText.getText().toString());
    }

    // TODO: Move the validation to a utils class
    private boolean isContinueOk(){
        return (isPhoneValid(phoneText.getText().toString()) && isEmailValid(emailText.getText().toString()));
    }

    private boolean isPhoneValid(String phone){
        return Patterns.PHONE.matcher(phone).matches();
    }

    private boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}