package com.example.teddyv2.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.teddyv2.R;
import com.example.teddyv2.domain.user.User;

public class RegisterActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);

        if (savedInstanceState == null){
            user = new User();
            TermsFragment fragment = TermsFragment.newInstance(this);

            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    public void navigateFromTermsToUserCreation(){
        UserCreationFragment fragment = UserCreationFragment.newInstance(this, user);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    public void navigateFromUserCreationToName(){
        NameFragment fragment = NameFragment.newInstance(this, user);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    public void navigateFromNameToContact(){
        ContactFragment fragment = ContactFragment.newInstance(this, user);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    public void navigateFromContactToPayment(){
        PaymentFragment fragment = PaymentFragment.newInstance(this, user);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    private void navigateFromPaymentToCongrats(){
        RegisterCongratsFragment fragment = RegisterCongratsFragment.newInstance();

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    public void finishRegistration(){
        // TODO: Include database transaction here to register the User
        navigateFromPaymentToCongrats();
    }

}