package com.example.teddyv2.ui.main;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.teddyv2.R;

import java.util.Calendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchMatchFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;


    public static SearchMatchFragment newInstance(int index) {
        SearchMatchFragment fragment = new SearchMatchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        //Lanza las vistas desplizables
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_match, container, false);

        final Spinner matchType = root.findViewById(R.id.matchTypeSearch);
        ArrayAdapter<String> adapterMatchType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.matchTypes));
        matchType.setAdapter(adapterMatchType);

        final Spinner difficultyType = root.findViewById(R.id.difficultyTypeSearch);
        ArrayAdapter<String> adapterDifficultyType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.level_spinner));
        difficultyType.setAdapter(adapterDifficultyType);

        final EditText startHour = root.findViewById(R.id.startHourSearch);
        startHour.setInputType(InputType.TYPE_NULL);
        startHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                TimePickerDialog picker = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                startHour.setText(sHour + ":" + String.format("%02d",sMinute));
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        final EditText matchDate = root.findViewById(R.id.matchDateSearch);
        matchDate.setInputType(InputType.TYPE_NULL);
        matchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                matchDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        final Button searchMatchButton = root.findViewById(R.id.searchMatchButton);
        searchMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PaymentFragment paymentFragment = new PaymentFragment();
                MatchesFoundFragment matchesFoundFragment = new MatchesFoundFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content, matchesFoundFragment, matchesFoundFragment.getClass().getSimpleName())
                        .commit();
            }
        });

        searchMatchButton.setEnabled(false);

        startHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(startHour.getText().toString().length()==0||matchDate.getText().toString().length()==0){
                    searchMatchButton.setEnabled(false);
                }else{
                    searchMatchButton.setEnabled(true);
                }
            }
        });


        /*pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
         */
        //searchMatchButton.setEnabled(false);


        return root;
    }

}