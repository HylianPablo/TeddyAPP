package com.example.teddyv2.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teddyv2.R;
import com.example.teddyv2.data.LoginRepository;
import com.example.teddyv2.domain.matches.Match;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchesFoundFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchesFoundFragment extends Fragment {

    ArrayList<Match> partidos;
    int pagina;

    public MatchesFoundFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment matchesFoundFragment.
     */
    public static MatchesFoundFragment newInstance(ArrayList<Match>partidos) {
        MatchesFoundFragment fragment = new MatchesFoundFragment();
        fragment.partidos = partidos;
        fragment.pagina = 0;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_matches_found, container, false);
        cargarPartidos(root);
        root.findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pagina>0) {
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

    private void cargarPartidos(View root){

        ArrayList<CardView> cardViews = new ArrayList<CardView>();
        cardViews.add((CardView)root.findViewById(R.id.cardView1));
        cardViews.add((CardView)root.findViewById(R.id.cardView2));
        cardViews.add((CardView)root.findViewById(R.id.cardView3));
        cardViews.add((CardView)root.findViewById(R.id.cardView4));
        cardViews.add((CardView)root.findViewById(R.id.cardView5));
        final String idJugador = LoginRepository.getInstance().getLoggedInUser().getUserId();
        for (int i = 0 ; i < cardViews.size() ; i++) {
            CardView c = cardViews.get(i);
            if (partidos.size() > ((pagina * 5) + i)) {
                final Match p = partidos.get((pagina*5)+i);
                c.setVisibility(View.VISIBLE);
                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(p.addParticipante(idJugador)){
                            FirebaseFirestore.getInstance().collection("Partidos").document(p.getId()).update(p.toHashMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    redirigirPago(p);
                                }
                            });
                        }else{
                            Toast.makeText(getContext(), "No te puedes apuntar a ese partido", Toast.LENGTH_LONG).show();
                        }



                    }
                });
                LinearLayout layout1 = (LinearLayout) c.getChildAt(0);
                ((TextView) layout1.getChildAt(0)).setText(p.getModalidad().toString());
                ((TextView) layout1.getChildAt(1)).setText(p.getNivel().toString());
                LinearLayout layout2 = (LinearLayout) layout1.getChildAt(2);
                ((TextView) layout2.getChildAt(0)).setText(p.getDay());
                ((TextView) layout2.getChildAt(1)).setText(p.getHour());
                ((TextView) layout2.getChildAt(2)).setText(p.getIdOrganizador());
            } else {
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

    public void redirigirPago(Match partido){
        PaymentFragment paymentFragment = PaymentFragment.newInstance(partido);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, paymentFragment, paymentFragment.getClass().getSimpleName())
                .commit();
    }
}