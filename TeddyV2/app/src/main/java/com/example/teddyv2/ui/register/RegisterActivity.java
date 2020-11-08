package com.example.teddyv2.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.teddyv2.MainActivity;
import com.example.teddyv2.R;
import com.example.teddyv2.domain.user.User;

public class RegisterActivity extends AppCompatActivity {

    private User user;

    private TermsFragment termsFragment;
    private UserCreationFragment userCreationFragment;
    private NameFragment nameFragment;
    private ContactFragment contactFragment;
    private PaymentFragment paymentFragment;
    private LevelSelectionFragment levelSelectionFragment;
    private RegistrationCongratsFragment registrationCongratsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);

        if (savedInstanceState == null){
            user = new User();

            termsFragment = TermsFragment.newInstance(this);
            userCreationFragment = UserCreationFragment.newInstance(this, user);
            nameFragment = NameFragment.newInstance(this, user);
            contactFragment = ContactFragment.newInstance(this, user);
            paymentFragment = PaymentFragment.newInstance(this, user);
            levelSelectionFragment = LevelSelectionFragment.newInstance(this, user);
            registrationCongratsFragment = RegistrationCongratsFragment.newInstance(this);

            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(android.R.id.content, termsFragment, termsFragment.getClass().getSimpleName())
                    .commit();
        }
    }

    public void navigateFromTermsToUserCreation(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction()
                .add(android.R.id.content, userCreationFragment, userCreationFragment.getClass().getSimpleName());
        ft.hide(termsFragment);
        ft.commit();
    }

    public void navigateFromUserCreationToName(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction()
                .add(android.R.id.content, nameFragment, nameFragment.getClass().getSimpleName());
        ft.hide(userCreationFragment);
        ft.commit();
    }

    public void navigateFromNameToContact(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction()
                .add(android.R.id.content, contactFragment, contactFragment.getClass().getSimpleName());
        ft.hide(nameFragment);
        ft.commit();
    }

    public void navigateFromContactToPayment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction()
                .add(android.R.id.content, paymentFragment, paymentFragment.getClass().getSimpleName());
        ft.hide(contactFragment);
        ft.commit();
    }

    public void navigateFromPaymentToLevelSelection(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction()
                .add(android.R.id.content, levelSelectionFragment, levelSelectionFragment.getClass().getSimpleName());
        ft.hide(paymentFragment);
        ft.commit();
    }

    private void navigateFromLevelSelectionToCongrats(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction()
                .add(android.R.id.content, registrationCongratsFragment, registrationCongratsFragment.getClass().getSimpleName());
        ft.hide(levelSelectionFragment);
        ft.commit();
    }

    public void finishRegistration(){
        // TODO: Include database transaction here to register the User
        navigateFromLevelSelectionToCongrats();
    }

    public void navigateToMainActivity(){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

}