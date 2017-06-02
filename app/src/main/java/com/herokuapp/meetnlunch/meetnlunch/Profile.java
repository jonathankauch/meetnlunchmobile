package com.herokuapp.meetnlunch.meetnlunch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

public class Profile extends AppCompatActivity {

    static boolean event = true;
    static boolean showName = true;
    static boolean showGender = true;
    static boolean showAge = true;
    static ImageView avatar = null;
    static String description = "";
    static String contact = "";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_filters:
                    Intent iFilterView =  new Intent(Profile.this, Filter.class);
                    startActivity(iFilterView);
                    return true;
                case R.id.navigation_search:
                    Intent iSearchView =  new Intent(Profile.this, Search.class);
                    startActivity(iSearchView);
                    return true;
                case R.id.navigation_profile:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ((Switch) findViewById(R.id.event_on)).setChecked(event);
        ((Switch) findViewById(R.id.show_age)).setChecked(showAge);
        ((Switch) findViewById(R.id.show_gender)).setChecked(showGender);
        ((EditText) findViewById(R.id.description_field)).setText(description);
        ((EditText) findViewById(R.id.contact_field)).setText(contact);


        Button save = (Button) findViewById(R.id.save_change_button);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View mLoginFormView) {
                getEventCreation();
                getShowGender();
                getShowAge();
                getAvatar();
                getDescription();
                getContact();
            }
        });
    }

    public void getEventCreation() {
        Switch bar = (Switch) findViewById(R.id.event_on);
        event = bar.isChecked();
    }

    public void getShowGender() {
        Switch bar = (Switch) findViewById(R.id.show_gender);
        showGender = bar.isChecked();
    }

    public void getShowAge() {
        Switch bar = (Switch) findViewById(R.id.show_age);
        showAge = bar.isChecked();
    }

    public void getAvatar() {
        avatar = (ImageView) findViewById(R.id.avatar_image);
    }

    public void getDescription() {
        description = ((EditText) findViewById(R.id.description_field)).getText().toString();
    }

    public void getContact() {
        contact = ((EditText) findViewById(R.id.contact_field)).getText().toString();
    }

}
