package com.herokuapp.meetnlunch.meetnlunch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



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

        Button mLostPass = (Button) findViewById(R.id.send_mail_button);
        mLostPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View mLoginFormView) {
            }
        });
    }
}

