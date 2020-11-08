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
 * A simple {@link Fragment} subclass.
 * Use the {@link LevelSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LevelSelectionFragment extends Fragment {

    private RegisterActivity registerActivity;
    private User user;

    // Layout variables
    private TextView title;
    private Button continueBtn;
    private Spinner spinner;

    private void setRegisterActivity(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }

    private void setUser(User user){
        this.user = user;
    }

    public LevelSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RegisterCongratsFragment.
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

    private void assignLayoutVariables(View view) {
        title = (TextView) view.findViewById(R.id.single_spinner_layout_title);
        continueBtn = (Button) view.findViewById(R.id.single_spinner_continue_btn);
        spinner = (Spinner) view.findViewById(R.id.single_spinner_layout_spinner);
    }

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

    private void setUpListeners(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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