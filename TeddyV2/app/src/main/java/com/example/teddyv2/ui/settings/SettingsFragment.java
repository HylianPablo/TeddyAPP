package com.example.teddyv2.ui.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teddyv2.R;
import com.example.teddyv2.data.LoginRepository;
import com.example.teddyv2.domain.user.User;
import com.example.teddyv2.domain.user.UserLevel;
import com.example.teddyv2.utils.EncriptationUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import static com.example.teddyv2.utils.ValidationUtils.isValidEmail;

public class SettingsFragment extends PreferenceFragmentCompat {


    User usuario;

    public SettingsFragment() {
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
                    usuario.setPaymentAccount(newValue.toString());
                    actualizarBD();
                    Toast.makeText(getContext(), R.string.paypal_changed_ok, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), R.string.error_invalid_payment_account, Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

        final EditTextPreference password = findPreference("user_password");
        password.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(newValue.toString().length()<=5){
                    Toast.makeText(getContext(), R.string.error_invalid_password, Toast.LENGTH_LONG).show();
                }else {
                    usuario.setPassword(EncriptationUtils.sha1(newValue.toString()));
                    actualizarBD();
                    Toast.makeText(getContext(), R.string.password_changed_ok, Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
        password.setOnBindEditTextListener(
                new EditTextPreference.OnBindEditTextListener() {
                    @Override
                    public void onBindEditText(@NonNull EditText editText) {
                        editText.setText("");
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                });

        final EditTextPreference phone = findPreference("user_phone");
        phone.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(!newValue.toString().matches("[0-9\\+ ]+")){
                    Toast.makeText(getContext(), R.string.error_invalid_phone, Toast.LENGTH_LONG).show();
                }else {
                    phone.setSummary(newValue.toString());
                    usuario.setPhone(newValue.toString());
                    actualizarBD();
                    Toast.makeText(getContext(), R.string.phone_changed_ok, Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

        final ListPreference user_level = findPreference("user_level");
        user_level.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                user_level.setSummary(newValue.toString());
                usuario.setLevel(UserLevel.getUserLevelByNumber(user_level.findIndexOfValue(newValue.toString())));
                actualizarBD();
                return true;
            }
        });

        cargarDatos();
        cargarValoraciones();


    }

    @Override
    public void onStart() {
        super.onStart();
    }


    private void cargarDatos() {
        FirebaseFirestore.getInstance().collection("Usuarios").document(LoginRepository.getInstance().getLoggedInUser().getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        usuario = new User(document.getData());
                        usuario.setUsername(LoginRepository.getInstance().getLoggedInUser().getUserId());
                        mostrarDatos(usuario);
                    }
                }
            }
        });
    }

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
        final EditTextPreference password = findPreference("user_password");
        password.setSummary(R.string.password_encypted);
    }

    private  void cargarValoraciones(){
        FirebaseFirestore.getInstance().collection("Valoraciones").whereEqualTo("idUsuarioValorado",LoginRepository.getInstance().getLoggedInUser().getUserId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                double suma = 0;
                for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                    suma+=doc.getDouble("puntuacion");
                }
                final EditTextPreference valoracion = findPreference("user_rating");
                if(queryDocumentSnapshots.getDocuments().size() == 0){
                    valoracion.setTitle(R.string.no_ratings);
                }else{
                    valoracion.setTitle(getContext().getResources().getString(R.string.medium_user_rate)+" " + (suma/queryDocumentSnapshots.getDocuments().size())+"/5.0");
                }
            }
        });
    }

    private void actualizarBD(){
        FirebaseFirestore.getInstance().collection("Usuarios").document(usuario.getUsername()).update(usuario.toHashMapNoEncripted()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                cargarDatos();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), R.string.update_error, Toast.LENGTH_LONG).show();
                cargarDatos();
            }
        });

    }
}