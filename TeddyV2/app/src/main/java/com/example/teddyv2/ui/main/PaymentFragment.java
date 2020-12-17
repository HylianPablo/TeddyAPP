package com.example.teddyv2.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.teddyv2.MainActivity;
import com.example.teddyv2.R;
import com.example.teddyv2.ui.login.LoginActivity;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Button acceptPaymentButton;
    private TextView infoReserva;
    private TextView cantidadAbonar;
    // TODO: Rename and change types of parameters

    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentFragment newInstance() {
        PaymentFragment fragment = new PaymentFragment();
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
        View root = inflater.inflate(R.layout.fragment_payment, container, false);

        acceptPaymentButton = root.findViewById(R.id.acceptPaymentButton);
        infoReserva = root.findViewById(R.id.paymentInfoMatch);
        infoReserva.setText("Ha reservado usted la pista XX, de XX:XX a XX:XX."); //TODO pillar los datos
        cantidadAbonar = root.findViewById(R.id.paymentInfoPrice);
        cantidadAbonar.setText("Cantidad a abonar: 10 euros.");
        final Fragment f = this;
        acceptPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().getSupportFragmentManager().beginTransaction().remove(f).commit();
                getPayment();
            }
        });
        Intent intent = new Intent(this.getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);
        Log.d("CreateView","------------FIN---------------");
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
        Log.d("Get Payment","------------FIN---------------");
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

                Log.d("CONFIRM", String.valueOf(confirm));
                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.d("paymentExample", paymentDetails);
                        Log.i("paymentExample", paymentDetails);
                        Log.d("Pay Confirm : ", String.valueOf(confirm.getPayment().toJSONObject()));

                        Intent intent = new Intent(this.getActivity(), MainActivity.class);
                        startActivity(intent);
                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred : ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.d("onDestroy","------------FIN---------------");
        getActivity().stopService(new Intent(this.getActivity(), PayPalService.class));
        super.onDestroy();
    }
}