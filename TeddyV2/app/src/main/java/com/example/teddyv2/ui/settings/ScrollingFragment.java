package com.example.teddyv2.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.teddyv2.R;
import com.example.teddyv2.data.LoginRepository;
import com.example.teddyv2.domain.matches.Match;

import java.util.ArrayList;

public class ScrollingFragment extends Fragment {

    ArrayList<Match> partidos;
    int pagina;

    public ScrollingFragment(){}

    public ScrollingFragment(ArrayList<Match> partidos){
        this.partidos = partidos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_scrolling, container, false);

        cargarPartidos(root);
        root.findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pagina>0){
                pagina--;
                cargarPartidos(getView().getRootView());
                }
            }
        });
        root.findViewById(R.id.buttonNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partidos.size()>(pagina*5)+5) {
                pagina++;
                cargarPartidos(getView().getRootView());
                }
            }
        });

        return root;
    }

    private void cargarPartidos(View root) {
        ArrayList<CardView> cardViews = new ArrayList<CardView>();
        cardViews.add((CardView)root.findViewById(R.id.primera_valoracion));
        cardViews.add((CardView)root.findViewById(R.id.segunda_valoracion));
        cardViews.add((CardView)root.findViewById(R.id.tercera_valoracion));
        cardViews.add((CardView)root.findViewById(R.id.cuarta_valoracion));
        String buscador = LoginRepository.getInstance().getLoggedInUser().getUserId();
        for (int i = 0 ; i < cardViews.size() ; i++){
            CardView c = cardViews.get(i);
            if(partidos.size()>((pagina*5)+i)){
                Match p = partidos.get((pagina*5)+i);
                c.setVisibility(View.VISIBLE);
                LinearLayout layout = (LinearLayout) c.getChildAt(0);
                ((TextView) layout.getChildAt(0)).setText(p.getModalidad()+" "+p.getNivel());
                ((TextView) layout.getChildAt(1)).setText(p.getDay()+", "+p.getHour());
                LinearLayout botonesUsuario = (LinearLayout)(((LinearLayout) layout.getChildAt(2)).getChildAt(1));
                for (int j = 0;j<3;j++){
                    Button boton = (Button) botonesUsuario.getChildAt(j);
                    if(j<p.getRestoParticipantes(buscador).size()){
                        boton.setVisibility(View.VISIBLE);
                        boton.setText(p.getRestoParticipantes(buscador).get(j));
                    }else{
                        boton.setVisibility(View.INVISIBLE);
                    }
                }


            }else{
                c.setVisibility(View.INVISIBLE);
            }
        }

        if(pagina>0) {
            root.findViewById(R.id.buttonBack).setVisibility(View.VISIBLE);
        }else{
            root.findViewById(R.id.buttonBack).setVisibility(View.INVISIBLE);
        }
        if(partidos.size()>(pagina*5)+5) {
            root.findViewById(R.id.buttonNext).setVisibility(View.VISIBLE);
        }else{
            root.findViewById(R.id.buttonNext).setVisibility(View.INVISIBLE);
        }


    }
}