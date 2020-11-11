package com.example.teddyv2.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.teddyv2.MainActivity;
import com.example.teddyv2.R;
import com.example.teddyv2.domain.user.User;

/**
 * Actividad del registro.
 * <br>
 * Esta actividad sirve como contenedor de los fragmentos que componen el proceso de registro y a
 * la vez de maquina de estados que permite navegar de un fragmento a otro. Todos los ragmentos,
 * por tanto tendran una referencia a esta actividad para poder navegar a la siguiente vista, y esta
 * actividad sera la encargada de crear el usuario (compartido entre todos los fragmentos) y
 * guardarlo en la base de datos una vez se haya creado con exito.
 */
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

            // Creacion de todos los Fragmentos
            termsFragment = TermsFragment.newInstance(this);
            userCreationFragment = UserCreationFragment.newInstance(this, user);
            nameFragment = NameFragment.newInstance(this, user);
            contactFragment = ContactFragment.newInstance(this, user);
            paymentFragment = PaymentFragment.newInstance(this, user);
            levelSelectionFragment = LevelSelectionFragment.newInstance(this, user);
            registrationCongratsFragment = RegistrationCongratsFragment.newInstance(this);

            // Inicializa en el fragmento de politica de privacidad
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(android.R.id.content, termsFragment, termsFragment.getClass().getSimpleName())
                    .commit();
        }
    }

    /**
     * Navega de la pantalla de la politica de privacidad a la pantalla de creacion del usuario
     */
    public void navigateFromTermsToUserCreation(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction()
                .add(android.R.id.content, userCreationFragment, userCreationFragment.getClass().getSimpleName());
        ft.hide(termsFragment);
        ft.commit();
    }

    /**
     * Navega de la pantalla de la creacion del usuario a la pantalla de nombre y apellidos
     */
    public void navigateFromUserCreationToName(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction()
                .add(android.R.id.content, nameFragment, nameFragment.getClass().getSimpleName());
        ft.hide(userCreationFragment);
        ft.commit();
    }

    /**
     * Navega de la pantalla de nombre y apellidos a la pantalla de datos de contacto
     */
    public void navigateFromNameToContact(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction()
                .add(android.R.id.content, contactFragment, contactFragment.getClass().getSimpleName());
        ft.hide(nameFragment);
        ft.commit();
    }

    /**
     * Navega de la pantalla de datos de contacto a la pantalla de detalles de pago
     */
    public void navigateFromContactToPayment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction()
                .add(android.R.id.content, paymentFragment, paymentFragment.getClass().getSimpleName());
        ft.hide(contactFragment);
        ft.commit();
    }

    /**
     * Navega de la pantalla de detalles de pago a la pantalla de seleccion de nivel
     */
    public void navigateFromPaymentToLevelSelection(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction()
                .add(android.R.id.content, levelSelectionFragment, levelSelectionFragment.getClass().getSimpleName());
        ft.hide(paymentFragment);
        ft.commit();
    }

    /**
     * Navega de la pantalla de seleccion de nivel a la pantalla de felicitacion
     */
    private void navigateFromLevelSelectionToCongrats(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction()
                .add(android.R.id.content, registrationCongratsFragment, registrationCongratsFragment.getClass().getSimpleName());
        ft.hide(levelSelectionFragment);
        ft.commit();
    }

    /**
     * Finaliza el registro. Esta funcion guarda el Usuario en la Base de Datos y navega a la
     * pantalla de felicitacion para indicar al usuario que su cuenta ha sido creada con exito.
     */
    public void finishRegistration(){
        // TODO: Include database transaction here to register the User
        navigateFromLevelSelectionToCongrats();
    }

    /**
     * Navega a la actividad principal.
     */
    public void navigateToMainActivity(){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

}