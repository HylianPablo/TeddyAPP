package com.example.teddyv2.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.teddyv2.R;

import static com.example.teddyv2.utils.ValidationUtils.isValidEmail;

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
                if(isValidEmail(newValue.toString())) {
                    paypal.setSummary(newValue.toString());
                }else{
                    Toast.makeText(getContext(), "La dirección de correo electrónico es inválida.", Toast.LENGTH_LONG).show();
                }
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
                password.setSummary(encripted);
                return true;
            }
        });

        final EditTextPreference phone = findPreference("user_phone");
        phone.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(!newValue.toString().matches("[0-9\\+ ]+")){
                    Toast.makeText(getContext(), "El número de teléfono sólo debe incluir números.", Toast.LENGTH_LONG).show();
                }else {
                    phone.setSummary(newValue.toString());
                }
                return true;
            }
        });

        final ListPreference user_level = findPreference("user_level");
        user_level.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                user_level.setTitle("Nivel de jugador: " + newValue.toString());
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