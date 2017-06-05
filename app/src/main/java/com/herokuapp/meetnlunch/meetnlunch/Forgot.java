package com.herokuapp.meetnlunch.meetnlunch;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class Forgot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        Button mLogin = (Button) findViewById(R.id.back_button);
        mLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View mLoginFormView) {
                Intent iLogin = new Intent(Forgot.this, Login.class);
                startActivity(iLogin);
            }
        });

        Button mSendMail = (Button) findViewById(R.id.send_mail_button);
        mSendMail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View mLoginFormView) {
                final JsonObject json = new JsonObject();
                json.addProperty("username", getUserText());
                Toast.makeText(Forgot.this, getUserText(), Toast.LENGTH_LONG).show();

                if (!isNetworkAvailable()) {

                    Toast.makeText(Forgot.this, "No internet connection", Toast.LENGTH_LONG).show();
                    return;
                }

                Ion.with(getApplicationContext())
                        .load(getString(R.string.api_url) + "forgot")
                        .setJsonObjectBody(json)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (e != null || result == null) {
                                    ((TextView) findViewById(R.id.RESPONSE)).setText(R.string.username_NOEXIST);
                                    Log.d("ERROR RESPONSE", e.getMessage());
                                    return;
                                }
                                Log.d("API RESPONSE", result.toString());
                                ((TextView) findViewById(R.id.RESPONSE)).setText(R.string.username_EXIT);
                                ((AutoCompleteTextView) findViewById(R.id.user_text)).setVisibility(View.GONE);
                                ((Button) findViewById(R.id.send_mail_button)).setVisibility(View.GONE);
                                ((TextInputLayout) findViewById(R.id.user_text_layout)).setVisibility(View.GONE);
                                ((AutoCompleteTextView) findViewById(R.id.code_field)).setVisibility(View.VISIBLE);
                                ((Button) findViewById(R.id.check_code_button)).setVisibility(View.VISIBLE);
                            }
                        });
            }
        });

        Button mCode = (Button) findViewById(R.id.check_code_button);
        mCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View mLoginFormView) {
                final JsonObject json = new JsonObject();
                json.addProperty("token", getTokenText());

                if (!isNetworkAvailable()) {

                    Toast.makeText(Forgot.this, "No internet connection", Toast.LENGTH_LONG).show();
                    return;
                }

                Ion.with(getApplicationContext())
                        .load(getString(R.string.api_url) + "forgot/check")
                        .setJsonObjectBody(json)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (e != null || result == null) {
                                    ((TextView) findViewById(R.id.RESPONSE)).setText(R.string.code_INVALID);
                                    Log.d("ERROR RESPONSE", e.getMessage());
                                    return;
                                }
                                Log.d("API RESPONSE", result.toString());
                                ((TextView) findViewById(R.id.RESPONSE)).setText(R.string.code_VALID);
                                ((AutoCompleteTextView) findViewById(R.id.code_field)).setVisibility(View.GONE);
                                ((Button) findViewById(R.id.check_code_button)).setVisibility(View.GONE);
                                ((TextInputLayout) findViewById(R.id.token_text_layout)).setVisibility(View.GONE);
                                ((AutoCompleteTextView) findViewById(R.id.new_pass)).setVisibility(View.VISIBLE);
                                ((Button) findViewById(R.id.set_new_pass)).setVisibility(View.VISIBLE);
                            }
                        });
            }
        });

        Button mReset = (Button) findViewById(R.id.set_new_pass);
        mReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View mLoginFormView) {
                final JsonObject json = new JsonObject();
                json.addProperty("password", getPasswordText());
                json.addProperty("username", getUserText());

                if (!isNetworkAvailable()) {

                    Toast.makeText(Forgot.this, "No internet connection", Toast.LENGTH_LONG).show();
                    return;
                }

                Ion.with(getApplicationContext())
                        .load(getString(R.string.api_url) + "forgot/reset")
                        .setJsonObjectBody(json)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (e != null || result == null) {
                                    ((TextView) findViewById(R.id.RESPONSE)).setText(R.string.password_FAIL);
                                    Log.d("ERROR RESPONSE", e.getMessage());
                                    return;
                                }
                                Log.d("API RESPONSE", result.toString());
                                ((TextView) findViewById(R.id.RESPONSE)).setText(R.string.password_RESET);
                                ((AutoCompleteTextView) findViewById(R.id.new_pass)).setVisibility(View.GONE);
                                ((Button) findViewById(R.id.set_new_pass)).setVisibility(View.GONE);
                                ((TextInputLayout) findViewById(R.id.pass_text_layout)).setVisibility(View.GONE);
                            }
                        });
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public String getUserText() {
        return ((AutoCompleteTextView) findViewById(R.id.user_text)).getText().toString();
    }

    public String getTokenText() {
        return ((AutoCompleteTextView) findViewById(R.id.code_field)).getText().toString();
    }

    public String getPasswordText() {
        return ((AutoCompleteTextView) findViewById(R.id.new_pass)).getText().toString();
    }
}

