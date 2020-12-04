package com.example.teddyv2.ui.main;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.teddyv2.R;
import com.example.teddyv2.domain.matches.Match;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchesFoundFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchesFoundFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    ArrayList<Match> partidos;
    int pagina;

    // TODO: Rename and change types of parameters

    public MatchesFoundFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment matchesFoundFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        if (getArguments() != null) {
        }

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
                pagina--;
                cargarPartidos(getView().getRootView());
            }
        });
        root.findViewById(R.id.buttonNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagina++;
                cargarPartidos(getView().getRootView());
            }
        });


        return root;
    }

    private void cargarPartidos( View root){

        CardView cardView1 = root.findViewById(R.id.cardView1);
        CardView cardView2 = root.findViewById(R.id.cardView2);
        CardView cardView3 = root.findViewById(R.id.cardView3);
        CardView cardView4 = root.findViewById(R.id.cardView4);
        CardView cardView5 = root.findViewById(R.id.cardView5);

        if(partidos.size()<(pagina*5)+1){
            cardView1.setVisibility(View.INVISIBLE);
        }else {
            cardView1.setVisibility(View.VISIBLE);
            cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PaymentFragment paymentFragment = new PaymentFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(android.R.id.content, paymentFragment, paymentFragment.getClass().getSimpleName())
                            .commit();
                }
            });
            ((TextView) root.findViewById(R.id.textCardView1Mod)).setText(partidos.get(0).getModalidad().toString());
            ((TextView) root.findViewById(R.id.textCardView1Diff)).setText(partidos.get(0).getNivel().toString());
            ((TextView) root.findViewById(R.id.textCardView1Mod)).setText(partidos.get(0).getModalidad().toString());
            ((TextView) root.findViewById(R.id.textCardView1Date)).setText(partidos.get(0).getDay());
            ((TextView) root.findViewById(R.id.textCardView1Hour)).setText(partidos.get(0).getHour());
            ((TextView) root.findViewById(R.id.textCardView1Author)).setText(partidos.get(0).getIdOrganizador());
        }
        if(partidos.size()<(pagina*5)+2){
            cardView2.setVisibility(View.INVISIBLE);
        }else {
            cardView2.setVisibility(View.VISIBLE);
            cardView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PaymentFragment paymentFragment = new PaymentFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(android.R.id.content, paymentFragment, paymentFragment.getClass().getSimpleName())
                            .commit();
                }
            });
            ((TextView) root.findViewById(R.id.textCardView2Mod)).setText(partidos.get(1).getModalidad().toString());
            ((TextView) root.findViewById(R.id.textCardView2Diff)).setText(partidos.get(1).getNivel().toString());
            ((TextView) root.findViewById(R.id.textCardView2Mod)).setText(partidos.get(1).getModalidad().toString());
            ((TextView) root.findViewById(R.id.textCardView2Date)).setText(partidos.get(1).getDay());
            ((TextView) root.findViewById(R.id.textCardView2Hour)).setText(partidos.get(1).getHour());
            ((TextView) root.findViewById(R.id.textCardView2Author)).setText(partidos.get(1).getIdOrganizador());
        }

        if(partidos.size()<(pagina*5)+3){
            cardView3.setVisibility(View.INVISIBLE);
        }else {
            cardView3.setVisibility(View.VISIBLE);
            cardView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PaymentFragment paymentFragment = new PaymentFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(android.R.id.content, paymentFragment, paymentFragment.getClass().getSimpleName())
                            .commit();
                }
            });
            ((TextView) root.findViewById(R.id.textCardView3Mod)).setText(partidos.get(2).getModalidad().toString());
            ((TextView) root.findViewById(R.id.textCardView3Diff)).setText(partidos.get(2).getNivel().toString());
            ((TextView) root.findViewById(R.id.textCardView3Mod)).setText(partidos.get(2).getModalidad().toString());
            ((TextView) root.findViewById(R.id.textCardView3Date)).setText(partidos.get(2).getDay());
            ((TextView) root.findViewById(R.id.textCardView3Hour)).setText(partidos.get(2).getHour());
            ((TextView) root.findViewById(R.id.textCardView3Author)).setText(partidos.get(2).getIdOrganizador());
        }
        if(partidos.size()<(pagina*5)+4){
            cardView4.setVisibility(View.INVISIBLE);
        }else {
            cardView4.setVisibility(View.VISIBLE);
            cardView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PaymentFragment paymentFragment = new PaymentFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(android.R.id.content, paymentFragment, paymentFragment.getClass().getSimpleName())
                            .commit();
                }
            });
            ((TextView) root.findViewById(R.id.textCardView4Mod)).setText(partidos.get(3).getModalidad().toString());
            ((TextView) root.findViewById(R.id.textCardView4Diff)).setText(partidos.get(3).getNivel().toString());
            ((TextView) root.findViewById(R.id.textCardView4Mod)).setText(partidos.get(3).getModalidad().toString());
            ((TextView) root.findViewById(R.id.textCardView4Date)).setText(partidos.get(3).getDay());
            ((TextView) root.findViewById(R.id.textCardView4Hour)).setText(partidos.get(3).getHour());
            ((TextView) root.findViewById(R.id.textCardView4Author)).setText(partidos.get(3).getIdOrganizador());
        }

        if(partidos.size()<(pagina*5)+5){
            cardView5.setVisibility(View.INVISIBLE);
        }else {
            cardView5.setVisibility(View.VISIBLE);
            cardView5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PaymentFragment paymentFragment = new PaymentFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(android.R.id.content, paymentFragment, paymentFragment.getClass().getSimpleName())
                            .commit();
                }
            });
            ((TextView) root.findViewById(R.id.textCardView5Mod)).setText(partidos.get(4).getModalidad().toString());
            ((TextView) root.findViewById(R.id.textCardView5Diff)).setText(partidos.get(4).getNivel().toString());
            ((TextView) root.findViewById(R.id.textCardView5Mod)).setText(partidos.get(4).getModalidad().toString());
            ((TextView) root.findViewById(R.id.textCardView5Date)).setText(partidos.get(4).getDay());
            ((TextView) root.findViewById(R.id.textCardView5Hour)).setText(partidos.get(4).getHour());
            ((TextView) root.findViewById(R.id.textCardView5Author)).setText(partidos.get(4).getIdOrganizador());
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