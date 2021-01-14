package com.example.teddyv2;

import android.content.Intent;
import android.os.Bundle;

import com.example.teddyv2.ui.settings.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.teddyv2.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton settingsButton = findViewById(R.id.settingsButton);




        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                /*
                setContentView(R.layout.settings_fragment);
                SettingsFragment fragment = new SettingsFragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                        .commit();

                 */

            }
        });

    }
}