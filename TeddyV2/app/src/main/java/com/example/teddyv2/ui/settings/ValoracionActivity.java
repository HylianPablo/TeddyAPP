package com.example.teddyv2.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.teddyv2.R;
import com.example.teddyv2.data.LoginRepository;
import com.example.teddyv2.data.model.LoggedInUser;
import com.example.teddyv2.domain.user.Valoracion;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Actividad de la valoracion.
 * <br>
 * Esta actividad permite annadir una valoracion a un usuario seleccionado en el historial de
 * partidos. Una vez finalizado el proceso de valoracion, se almacena en la base de datos y se
 * navega de vuelta a la actividad anterior.
 */
public class ValoracionActivity extends AppCompatActivity {

    // TODO: Esto es correcto?
    private static final String BUNDLE_KEY = "usuario-valorado";

    // Atributos de datos
    Valoracion valoracion;

    // Atributos del layout
    TextView nombreUsuario;
    RatingBar ratingBar;
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

    /**
     * Inicializa el atributo de Valoracion donde se almacenara toda la informacion lo relativa
     * a la valoracion.
     *
     * @throws AssertionError si Bundle es null
     */
    private void initAttributes(){
        String usuarioValorado;
        LoggedInUser usuarioValorador;
        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;

        usuarioValorado = (String) bundle.get("USERNAME");
        usuarioValorador = LoginRepository.getInstance().getLoggedInUser();
        valoracion = new Valoracion(usuarioValorado, usuarioValorador);
    }

    /**
     * Inicializa los atributos del layout, annadiendo el texto y las referencias para administrar
     * el comportamiento del layout desde el codigo.
     */
    private void initLayoutAttributes(){
        nombreUsuario = findViewById(R.id.reviewed_user);
        nombreUsuario.setText(valoracion.getNameOfUsuarioValorado());
        ratingBar = findViewById(R.id.rating_bar);
        review = findViewById(R.id.review_input_text);
        acceptButton = findViewById(R.id.review_button);
    }

    /**
     * Setea los listeners de los diferentes elementos del layout.
     */
    private void setUpListeners(){
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(v != 0.0f){
                    acceptButton.setEnabled(true);
                }
                valoracion.setPuntuacion((int) v);
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                almacenarValoracion();
                finish();
            }
        });

        review.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                valoracion.setComentario(editable.toString());
            }
        });
    }

    /**
     * Almacena la valoracion en la base de datos.
     */
    private void almacenarValoracion(){
        FirebaseFirestore.getInstance()
                .collection("Valoraciones").add(valoracion.toHashMap());
    }
}