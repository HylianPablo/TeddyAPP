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
 * Use the {@link NameFragment#newInstance} factory method to
 * create an instance of this fragment.
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

    private void setRegisterActivity(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }

    private void setUser(User user){
        this.user = user;
    }

    public NameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NameFragment.
     */
    // TODO: Rename and change types and number of parameters
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

    private void assignLayoutVariables(View view){
        title = (TextView) view.findViewById(R.id.two_input_layout_title);
        continueBtn = (Button) view.findViewById(R.id.two_input_continue_btn);
        nameLayout = (TextInputLayout) view.findViewById(R.id.two_input_layout_first_inputLayout);
        nameText = (EditText) view.findViewById(R.id.two_input_layout_first_editText);
        surnameLayout = (TextInputLayout) view.findViewById(R.id.two_input_layout_second_inputLayout);
        surnameText = (EditText) view.findViewById(R.id.two_input_layout_second_editText);
    }

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

    private void setUpListeners(){
        // Watcher can be equal for both
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

    private boolean isContinueOk(){
        return (!nameText.getText().toString().equals("") && !surnameText.getText().toString().equals(""));
    }

    private void setUserData(){
        user.setName(nameText.getText().toString());
        user.setSurname(surnameText.getText().toString());
    }
}