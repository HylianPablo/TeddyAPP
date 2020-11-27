package com.example.teddyv2.ui.main;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teddyv2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchesFoundFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchesFoundFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters

    public MatchesFoundFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment matchesFoundFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchesFoundFragment newInstance(String param1, String param2) {
        MatchesFoundFragment fragment = new MatchesFoundFragment();
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

        CardView cardView1 = root.findViewById(R.id.cardView1);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFragment paymentFragment = new PaymentFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content, paymentFragment, paymentFragment.getClass().getSimpleName())
                        .commit();
            }
        });

        CardView cardView2 = root.findViewById(R.id.cardView2);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFragment paymentFragment = new PaymentFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content, paymentFragment, paymentFragment.getClass().getSimpleName())
                        .commit();
            }
        });

        CardView cardView3 = root.findViewById(R.id.cardView3);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFragment paymentFragment = new PaymentFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content, paymentFragment, paymentFragment.getClass().getSimpleName())
                        .commit();
            }
        });

        CardView cardView4 = root.findViewById(R.id.cardView4);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFragment paymentFragment = new PaymentFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content, paymentFragment, paymentFragment.getClass().getSimpleName())
                        .commit();
            }
        });

        CardView cardView5 = root.findViewById(R.id.cardView5);
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFragment paymentFragment = new PaymentFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content, paymentFragment, paymentFragment.getClass().getSimpleName())
                        .commit();
            }
        });

        return root;
    }
}