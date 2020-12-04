package com.example.teddyv2.ui.aftermatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.teddyv2.MainActivity;
import com.example.teddyv2.R;
import com.example.teddyv2.data.model.LoggedInUser;
import com.example.teddyv2.domain.user.User;

public class ValoracionActivity extends AppCompatActivity {

    // TODO: Esto es correcto?
    private static final String BUNDLE_KEY = "usuario-valorado";

    // Atributos de datos
    private LoggedInUser usuarioValorador;
    private User usuarioValorado;

    // Atributos del layout
    TextView nombreUsuario;
    RatingBar valoracion;
    EditText review;
    Button acceptButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valoracion);

        initAttributes();
        initLayoutAttributes();
        setUpListeners();

    }

    private void initAttributes(){
        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;

        usuarioValorado = (User) bundle.get(BUNDLE_KEY);
        // TODO: set usuario valorador una vez este implementado el getter de LoginRepository
        // usuarioValorador = LoginRepository.getInstance().getLoggedInUser();
    }

    private void initLayoutAttributes(){
        nombreUsuario = findViewById(R.id.reviewed_user);
        nombreUsuario.setText(usuarioValorado.getName());
        valoracion = findViewById(R.id.rating_bar);
        review = findViewById(R.id.review_input_text);
        acceptButton = findViewById(R.id.review_button);
    }

    private void setUpListeners(){
        valoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(ratingBar.getRating() != 0){
                    acceptButton.setEnabled(true);
                }
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                almacenarValoracion();
                startActivity(new Intent(ValoracionActivity.this, MainActivity.class));
            }
        });
    }

    private void almacenarValoracion(){
        // Enviar a la base de datos
    }
}