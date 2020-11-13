package com.example.teddyv2.data;

import androidx.annotation.NonNull;

import com.example.teddyv2.data.model.LoggedInUser;
import com.example.teddyv2.ui.login.LoginViewModel;
import com.example.teddyv2.utils.EncriptationUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    public static LoginRepository getInstance(){
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
    // handle login
    public void login(final String username, final String password, final LoginViewModel modeloVista) {
            final String digestPass = EncriptationUtils.sha1(password);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Usuarios").document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists() && digestPass.equals(document.get("password").toString())) {
                            LoggedInUser sampleUser = new LoggedInUser(username, document.get("nombre").toString());
                            Result<LoggedInUser> result = new Result.Success<>(sampleUser);
                            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
                            modeloVista.loginCallback(result);
                        } else {
                            Result<LoggedInUser> result = new Result.Error(new Exception("Usuario o contraseña incorrectos."));
                            modeloVista.loginCallback(result);
                        }
                    } else {
                        Result<LoggedInUser> result = new Result.Error(new Exception("Fallo en la base de datos."));
                        modeloVista.loginCallback(result);
                    }
                }
            });

    }

}