package com.example.teddyv2.ui.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.teddyv2.data.LoginRepository;
import com.example.teddyv2.domain.matches.Match;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Query.Direction;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {


    String objetivo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            // Crear el fragment 
            SettingsFragment fragment = new SettingsFragment();

            // Crear una instacia del FragmentManager y realizar la transacción
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    public void onButtonClick(View v){
        final FragmentManager manager = getSupportFragmentManager();
        FirebaseFirestore.getInstance().collection("Partidos").whereArrayContains("participantes", LoginRepository.getInstance().getLoggedInUser().getUserId()).orderBy("fecha", Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<Match> partidos = new ArrayList<Match>();
                for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                    partidos.add(new Match(doc.getData(), doc.getId()));
                }
                if(partidos.size() == 0){
                    Toast.makeText(manager.findFragmentById(android.R.id.content).getContext(), "Todavia no hay partidos", Toast.LENGTH_LONG).show();

                }else{
                    ScrollingFragment fragment = new ScrollingFragment(partidos);

                    manager.beginTransaction()
                            .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                            .commit();
                }
            }
        });
    }

    public void rateUserClick(View v){
        Button button = (Button) v;
        String buttonText = button.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("USERNAME",buttonText);
        objetivo = buttonText;
        Intent intent = new Intent(SettingsActivity.this, ValoracionActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onButtonClickRates(View v){
        final FragmentManager manager = getSupportFragmentManager();
        String username = LoginRepository.getInstance().getLoggedInUser().getUserId();
        RatesFragment fragment = RatesFragment.newInstance(username);
        manager.beginTransaction()
                .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

}