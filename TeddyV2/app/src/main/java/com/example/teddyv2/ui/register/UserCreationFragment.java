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
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserCreationFragment#newInstance} factory method to
 * create an instance of this fragment.
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


    private void setRegisterActivity(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }

    private void setUser(User user){
        this.user = user;
    }

    public UserCreationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserCreationFragment.
     */
    // TODO: Rename and change types and number of parameters
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

    private void assignLayoutVariables(View view){
        title = (TextView) view.findViewById(R.id.three_input_layout_title);
        continueBtn = (Button) view.findViewById(R.id.three_input_continue_btn);
        userLayout = (TextInputLayout) view.findViewById(R.id.three_input_layout_first_inputLayout);
        userText = (EditText) view.findViewById(R.id.three_input_layout_first_editText);
        passwordLayout = (TextInputLayout) view.findViewById(R.id.three_input_layout_second_inputLayout);
        passwordText = (EditText) view.findViewById(R.id.three_input_layout_second_editText);
        passwordRepeatLayout = (TextInputLayout) view.findViewById(R.id.three_input_layout_third_inputLayout);
        passwordRepeatText = (EditText) view.findViewById(R.id.three_input_layout_third_editText);
    }

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
                if(!isValidUserName()){
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
                if(!isPasswordValid()){
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

    private boolean isValidUserName(){
        // TODO: Comprobar que este nombre de usuario ne existe con la base de datos
        // TODO: Cambiar este stub
        return !userText.getText().toString().equals("");
    }

    // TODO: mover a una clase utils para reutilizacion
    private boolean isPasswordValid() {
        return (!passwordText.getText().toString().equals("") && passwordText.getText().toString().trim().length() > 5);
    }

    private boolean passwordsMatch(){
        return passwordText.getText().toString().equals(passwordRepeatText.getText().toString());
    }

    private boolean isContinueOk(){
        return (isValidUserName() && isPasswordValid() && passwordsMatch());
    }

    private void setUserData(){
        user.setUsername(userText.getText().toString());
        user.setPassword(passwordText.getText().toString());
    }
}