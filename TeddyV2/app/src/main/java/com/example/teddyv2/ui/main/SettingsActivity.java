package com.example.teddyv2.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

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
}