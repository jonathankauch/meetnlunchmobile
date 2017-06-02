package com.herokuapp.meetnlunch.meetnlunch;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.net.HttpURLConnection;
import java.net.URL;


public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button mBack = (Button) findViewById(R.id.back_button);
        mBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View mLoginFormView) {
                Intent iLogin = new Intent(Register.this, Login.class);
                startActivity(iLogin);
            }
        });

        Button mRegister = (Button) findViewById(R.id.register_button);
        mRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View mLoginFormView) {
                final JsonObject json = new JsonObject();
                json.addProperty("email", getEmailText());
                json.addProperty("password", getPasswordText());
                json.addProperty("username", getNameText());

                if (!isNetworkAvailable()) {

                    Toast.makeText(Register.this, "No internet connection", Toast.LENGTH_LONG).show();
                    return;
                }

                Ion.with(getApplicationContext())
                        .load(getString(R.string.api_url) + "register")
                        .setJsonObjectBody(json)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (e != null || result == null) {
                                    Log.d("ERROR RESPONSE", e.getMessage());
                                    return;
                                }
                                Log.d("API RESPONSE", result.toString());
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


    public View getProgressBar() {
        return findViewById(R.id.register_progress);
    }

    public String getEmailText() {
        return ((AutoCompleteTextView) findViewById(R.id.email)).getText().toString();
    }
    public String getPasswordText() {
        return ((AutoCompleteTextView) findViewById(R.id.password)).getText().toString();
    }
    public String getNameText() {
        return ((AutoCompleteTextView) findViewById(R.id.name)).getText().toString();
    }

    public TextView getResponse() {
        return (TextView) findViewById(R.id.RESPONSE);
    }

}

