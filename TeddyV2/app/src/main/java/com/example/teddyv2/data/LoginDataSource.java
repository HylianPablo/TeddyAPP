package com.example.teddyv2.data;

import com.example.teddyv2.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        if(username.equals("admin") && password.equals("password")) {
            try {
                // TODO: handle loggedInUser authentication
                LoggedInUser sampleUser =
                        new LoggedInUser(
                                "userId",
                                "Usuario de ejemplo");
                return new Result.Success<>(sampleUser);
            } catch (Exception e) {
                return new Result.Error(new IOException("Error logging in", e));
            }
        }
        return new Result.Error(new Exception("Usuario o contraseña incorrectos."));
    }

    public void logout() {
        // TODO: revoke authentication
    }
}