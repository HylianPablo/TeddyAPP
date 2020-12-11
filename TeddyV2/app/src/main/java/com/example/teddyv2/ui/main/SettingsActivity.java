package com.example.teddyv2.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.teddyv2.ui.aftermatch.ValoracionActivity;

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

    public void onButtonClick(View v){
        ScrollingFragment fragment = new ScrollingFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    public void rateUserClick(View v){
        Button button = (Button) v;
        String buttonText = button.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("USERNAME",buttonText);
        Intent intent = new Intent(SettingsActivity.this, ValoracionActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        System.out.println("Usuario: "+button);
    }
}