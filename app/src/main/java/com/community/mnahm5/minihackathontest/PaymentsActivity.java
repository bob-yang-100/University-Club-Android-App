package com.community.mnahm5.minihackathontest;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import android.Manifest;

public class PaymentsActivity extends AppCompatActivity {

    private String clientToken;
    private FragmentActivity mBraintreeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);



        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://ec2-54-252-158-169.ap-southeast-2.compute.amazonaws.com:3000/client_token", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String clientToken) {
                clientToken = clientToken;
                Toast.makeText(getApplicationContext(), clientToken, Toast.LENGTH_LONG).show();
                useFragment();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                super.onFailure(statusCode, headers, responseBytes, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
    }

    public void useFragment() {
        try {
            mBraintreeFragment = BraintreeFragment.newInstance(PaymentsActivity.this, clientToken);
            // mBraintreeFragment is ready to use!
        } catch (InvalidArgumentException e) {
            // There was an issue with your authorization string.
        }
    }
    public void Pay(View view) {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(clientToken);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }

    public PaymentsActivity() {
        super();
    }

    void postNonceToServer(String nonce) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("payment_method_nonce", nonce);
        client.post("http://ec2-54-252-158-169.ap-southeast-2.compute.amazonaws.com:3000/checkout", params,
                new AsyncHttpResponseHandler() {
                    // Your implementation here

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(getApplicationContext(), "Payment Sent", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }
}
