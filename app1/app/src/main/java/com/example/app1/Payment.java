package com.example.app1;

import static com.example.app1.CartActivity.Cart.cartItems;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Payment extends AppCompatActivity {
    private static final int PAYPAL_REQUEST_CODE = 123;
    private static final PayPalConfiguration CONFIGURATION = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("Ae7JqTQ4vY2JJPtZ92nXw6Qz3q4-RGEMT9Wp8CLsbrEZqw-d6I6tCOdAKiut7CI2hV_CXWAauZs-lsoN");
    ImageButton goback;
    Button payPal, payCash;
    TextView amount;
    String _name2, _city2, _add2, _phn2;
    private static final int REQUEST_DELIVERY = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        goback = findViewById(R.id.gobackpay);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = getIntent();
                _name2 = intent.getStringExtra("nametxt");
                _city2 = intent.getStringExtra("city");
                _add2 = intent.getStringExtra("Address");
                _phn2 = intent.getStringExtra("phoneNumber");

                 */

                Intent intent1 = new Intent(Payment.this, CartActivity.class);
                /*
                intent1.putExtra("nametxt",_name2);
                intent1.putExtra("city",_city2);
                intent1.putExtra("Address",_add2);
                intent1.putExtra("phoneNumber",_phn2);

                 */
                startActivity(intent1);
                finish();
            }
        });

        payPal = findViewById(R.id.buttonPayPal);
        payCash = findViewById(R.id.paycash);
        amount = findViewById(R.id.amount);

        double productPrice = 0;
        for (Data product : cartItems) {
            try {
                productPrice = Double.parseDouble(product.getDataprice());

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        amount.setText("Total Amount: OMR " + productPrice);

        payPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPayment();
            }
        });


        double finalProductPrice = productPrice;
        payCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Payment.this, CartActivity.class);
                startActivity(intent1);
                finish();
                handlePayCashButtonClick();
            }
            private void handlePayCashButtonClick() {
                Toast.makeText(Payment.this, "Sent to delivery "+ finalProductPrice +" OMR", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void getPayment() {

        double productPrice = 0;
        for (Data product : cartItems) {
            try {
                productPrice = Double.parseDouble(product.getDataprice());

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        PayPalPayment payment = new PayPalPayment(
                new BigDecimal(String.valueOf(productPrice)),
                "USD",
                "Product Payment",
                PayPalPayment.PAYMENT_INTENT_SALE
        );
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, CONFIGURATION);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (paymentConfirmation != null) {
                    try {
                        String paymentDetails = paymentConfirmation.toJSONObject().toString();
                        JSONObject object = new JSONObject(paymentDetails);
                        Intent intent1 = new Intent(Payment.this, CartActivity.class);
                        startActivity(intent1);
                        finish();
                        CartActivity.Cart.clearCart();
                        Toast.makeText(this, "Payment Successful & Sent to delivery", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Payment canceled", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(this, "Invalid payment", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_DELIVERY && resultCode == RESULT_OK) {
            Intent deliveryIntent = new Intent(this, Delivery.class);
            startActivityForResult(deliveryIntent, REQUEST_DELIVERY);
        }
    }
    private void handleSuccessfulPayment(ArrayList<Data> selectedProducts) {

    }
}

