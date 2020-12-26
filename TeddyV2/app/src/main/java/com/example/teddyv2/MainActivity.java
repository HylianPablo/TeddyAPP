package com.example.teddyv2;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.teddyv2.ui.settings.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.teddyv2.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.view_pager);
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
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Spannable wordtoSpan = new SpannableString(String.valueOf(tab.getText()));
                wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tab.setText(wordtoSpan);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Spannable wordtoSpan = new SpannableString(String.valueOf(tab.getText()));
                wordtoSpan.setSpan(new StyleSpan(Typeface.NORMAL), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tab.setText(wordtoSpan);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        tabs.selectTab(tabs.getTabAt(1));

    }
}