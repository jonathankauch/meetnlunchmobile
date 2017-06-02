package com.herokuapp.meetnlunch.meetnlunch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

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
                String API_URL="https://meetnlunchapp.herokuapp.com/api/register?email=" + getEmailText() +"&password=" + getPasswordText() + "&name=" + getNameText();
            }
        });
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

