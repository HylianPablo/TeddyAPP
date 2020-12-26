package com.example.teddyv2.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.teddyv2.R;
import com.example.teddyv2.domain.matches.Match;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment {

    //Paypal  request code
    private static final int PAYPAL_REQUEST_CODE = 123;

    private static final String PAYPAL_CLIENT_ID = "AYEDZGZuJVLq-TU_NNzslpjCUYiLQnfRzIGIQlUz400QMTbEApiX2YyribRGP1vYFwIdHTYCFTjvE_h4";
    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration() // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(PAYPAL_CLIENT_ID)
            //.rememberUser(true)
            .merchantName("Teddy")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.paypal.com/webapps/mpp/ua/privacy-full"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.paypal.com/webapps/mpp/ua/useragreement-full"))
            ;  // or live (ENVIRONMENT_PRODUCTION)


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Button acceptPaymentButton;
    private TextView infoReserva;
    private TextView cantidadAbonar;
    private Match partido;


    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PaymentFragment.
     */
    public static PaymentFragment newInstance(Match partido) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.partido = partido;
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
        View root = inflater.inflate(R.layout.fragment_payment, container, false);


        acceptPaymentButton = root.findViewById(R.id.acceptPaymentButton);
        infoReserva = root.findViewById(R.id.paymentInfoMatch);
        infoReserva.setText("Ha reservado usted la pista "+partido.getIdPista()+", el día "+partido.getDay()+", a las "+partido.getHour()+".");
        cantidadAbonar = root.findViewById(R.id.paymentInfoPrice);
        cantidadAbonar.setText("Cantidad a abonar: 10 euros.");
        final Fragment f = this;
        acceptPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPayment();
            }
        });
        Intent intent = new Intent(this.getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);
        return root;
    }

    private void getPayment() {
        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new
                BigDecimal(10),"EUR","Reserva Partido",PayPalPayment.PAYMENT_INTENT_SALE);
        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this.getActivity(), PaymentActivity.class);
        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        //invoice number
        payment.invoiceNumber("1");

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);

                        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                    } catch (JSONException e) {
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            }
        }
    }

    @Override
    public void onDestroy() {
        getActivity().stopService(new Intent(this.getActivity(), PayPalService.class));
        super.onDestroy();
    }
}