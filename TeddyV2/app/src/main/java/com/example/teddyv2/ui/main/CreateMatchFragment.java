package com.example.teddyv2.ui.main;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import android.widget.Toast;

import com.example.teddyv2.R;
import com.example.teddyv2.data.LoginRepository;
import com.example.teddyv2.domain.matches.Match;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateMatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateMatchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public CreateMatchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateMatchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateMatchFragment newInstance() {
        CreateMatchFragment fragment = new CreateMatchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View root = inflater.inflate(R.layout.fragment_create_match, container, false);

        final Spinner matchType = root.findViewById(R.id.matchTypeCreate);
        ArrayAdapter<String> adapterMatchType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.matchTypes));
        matchType.setAdapter(adapterMatchType);

        final Spinner difficultyType = root.findViewById(R.id.difficultyTypeCreate);
        ArrayAdapter<String> adapterDifficultyType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.level_spinner));
        difficultyType.setAdapter(adapterDifficultyType);

        final EditText startHour = root.findViewById(R.id.startHourCreate);
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
                                if(sMinute>30){
                                    sHour++;
                                }
                                startHour.setText(sHour + ":00" );
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });


        final EditText matchDate = root.findViewById(R.id.matchDateCreate);
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
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();
            }
        });

        final Button createMatchButton = root.findViewById(R.id.createMatchButton);
        createMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                crearPartido(matchDate.getText().toString(), startHour.getText().toString(), matchType.getSelectedItemPosition(), difficultyType.getSelectedItemPosition());
            }
        });

        createMatchButton.setEnabled(false);

        startHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(startHour.getText().toString().length()==0|| matchDate.getText().toString().length()==0){
                    createMatchButton.setEnabled(false);
                }else{
                    createMatchButton.setEnabled(true);
                }
            }
        });

        return root;
    }

    private void crearPartido(final String fecha, final String hora, final int tipoPartido, final int dificultad){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            formato.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            final Timestamp fechaInicio = new Timestamp(formato.parse(fecha+ " " + hora));
            Date fechaAhora = new Date(System.currentTimeMillis());

            if(fechaInicio.toDate().before(fechaAhora)){
                Toast.makeText(getContext(), "La hora debe de ser posterior a la actual", Toast.LENGTH_LONG).show();
            }else {
                final ArrayList<String> pistasDisponibles = new ArrayList<String>();
                Task secondTask = FirebaseFirestore.getInstance().collection("Pistas").get();
                secondTask.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                            DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(i);
                            final String idPista = doc.getId();
                            final boolean ultimo = (i == (queryDocumentSnapshots.getDocuments().size() - 1));
                            Task firstTask = FirebaseFirestore.getInstance().collection("Partidos").whereEqualTo("fecha", fechaInicio).whereEqualTo("idPista", doc.getId()).get();
                            firstTask.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (queryDocumentSnapshots.getDocuments().size() == 0) {
                                        if (pistasDisponibles.size() == 0) {
                                            pistasDisponibles.add(idPista);
                                            crearPartidoCallback(fechaInicio, hora, idPista, tipoPartido, dificultad);
                                        }
                                    } else {
                                        if (pistasDisponibles.size() == 0 && ultimo) {
                                            mostrarError();
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }catch (Exception e){
            mostrarError();
        }
    }

    private void crearPartidoCallback(Timestamp fechaInicio, String hora, String idPista, int tipoPartido, int dificultad){
            Match nuevo = new Match(fechaInicio, LoginRepository.getInstance().getLoggedInUser().getUserId(),idPista,tipoPartido, dificultad);
            FirebaseFirestore.getInstance().collection("Partidos").add(nuevo.toHashMap()).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mostrarError();
                }
            }).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    PaymentFragment paymentFragment = new PaymentFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, paymentFragment, paymentFragment.getClass().getSimpleName())
                    .commit();
                }
            });
    }

    public void mostrarError(){
        Toast.makeText(getContext(), "No se ha podido crear el partido", Toast.LENGTH_LONG).show();
    }



}