package com.example.teddyv2.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.teddyv2.R;
import com.example.teddyv2.data.LoginRepository;
import com.example.teddyv2.domain.user.User;
import com.example.teddyv2.domain.user.UserLevel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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

        FirebaseFirestore.getInstance().collection("Usuarios").document(LoginRepository.getInstance().getLoggedInUser().getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        User usuario = new User(document.getData());
                        mostrarDatos(usuario);
                    }
                }
            }
        });

    }

    @Override
    public void onStart() {super.onStart();}



    private void mostrarDatos(User usuario){
        final EditTextPreference paypal = findPreference("user_paypal");
        paypal.setSummary(usuario.getPaymentAccount());
        paypal.setText(usuario.getPaymentAccount());
        final EditTextPreference phone = findPreference("user_phone");
        phone.setText(usuario.getPhone());
        phone.setSummary(usuario.getPhone());
        final ListPreference user_level = findPreference("user_level");
        user_level.setValueIndex(UserLevel.getNumberByLevel(usuario.getLevel()));
        user_level.setSummary(user_level.getValue());



    }



}