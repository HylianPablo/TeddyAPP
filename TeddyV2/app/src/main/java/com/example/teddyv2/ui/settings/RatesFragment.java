package com.example.teddyv2.ui.settings;

import android.os.Bundle;

import androidx.core.view.NestedScrollingChild;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.teddyv2.R;
import com.example.teddyv2.domain.matches.Match;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatesFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USERNAME = "param1";

    private String username;

    public RatesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     * @return A new instance of fragment RatesFragment.
     */
    public static RatesFragment newInstance(String user) {
        Log.d("user"," "+user);
        RatesFragment fragment = new RatesFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(USERNAME);
            Log.d("user"," "+username);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_rates, container, false);
        final Fragment f = this;
        Button backButton = root.findViewById(R.id.salirButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(f).commit();
            }
        });

        FirebaseFirestore.getInstance().collection("Valoraciones").whereEqualTo("idUsuarioValorado",username).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<String> valoraciones = new ArrayList<String>();
                for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                    String comentario = (String) doc.get("comentario");
                    if(comentario != null && !comentario.equals("")) valoraciones.add(comentario);
                }
                mostrarValoraciones(valoraciones);
            }
        });



                return root;
    }

    private void mostrarValoraciones(ArrayList<String> valoraciones){
        String resultado  = "";
        for (String s : valoraciones) resultado +=s+"\n\n";
        ((MaterialTextView)((NestedScrollView) this.getActivity().findViewById(R.id.textView2)).getChildAt(0)).setText(resultado);
    }

}