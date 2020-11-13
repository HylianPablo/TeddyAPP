package com.example.teddyv2.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.teddyv2.data.LoginRepository;
import com.example.teddyv2.data.Result;
import com.example.teddyv2.data.model.LoggedInUser;
import com.example.teddyv2.R;
import com.example.teddyv2.utils.ValidationUtils;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        loginRepository.login(username, password,this);

    }
        public void loginCallback(Result<LoggedInUser> result){
        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.error_login));
        }
    }

        public void loginDataChanged(String username, String password) {
        if (!ValidationUtils.isNotNull(username)) {
            loginFormState.setValue(new LoginFormState(R.string.error_invalid_username, null));
        } else if (!ValidationUtils.isValidPassword(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.error_invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }
}