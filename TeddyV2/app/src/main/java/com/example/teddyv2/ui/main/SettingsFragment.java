package com.example.teddyv2.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teddyv2.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public SettingsFragment() {
        // Required empty public constructor
    }

    private static final String TAG = "SettingsFragment";
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        final EditTextPreference paypal = findPreference("user_paypal");
        paypal.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                paypal.setSummary(newValue.toString());
                return true;
            }
        });

        final EditTextPreference password = findPreference("user_password");
        password.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String encripted = "";
                for(int i=0;i<newValue.toString().length();i++){
                    encripted+="*";
                }
                paypal.setSummary(encripted);
                return true;
            }
        });

        final EditTextPreference phone = findPreference("user_phone");
        phone.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                phone.setSummary(newValue.toString());
                return true;
            }
        });

        final ListPreference user_level = findPreference("user_level");
        user_level.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                user_level.setTitle("Nivel de jugador: "+newValue.toString());
                return true;
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingsFragment.
     */

}