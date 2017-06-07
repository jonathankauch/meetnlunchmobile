package com.herokuapp.meetnlunch.meetnlunch;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.BasicNameValuePair;
import com.koushikdutta.ion.Ion;

public class Profile extends AppCompatActivity {

    static boolean event = true;
    static boolean showName = true;
    static boolean showGender = true;
    static boolean showAge = true;
    static ImageView avatar = null;
    static int ProfilePicID = 0;
    String pic = "profile0";
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

        ((TextView) findViewById(R.id.name_user)).setText(Singleton.getInstance().getmUser().getName());
        ((TextView) findViewById(R.id.gender_user)).setText(Singleton.getInstance().getmUser().getGender() + " - " + Singleton.getInstance().getmUser().getAge());
        ((Switch) findViewById(R.id.event_on)).setChecked(event);
        ((Switch) findViewById(R.id.show_age)).setChecked(showAge);
        ((EditText) findViewById(R.id.description_field)).setText(description);
        ((EditText) findViewById(R.id.contact_field)).setText(contact);
        ((ImageView) findViewById(R.id.avatar)).setImageResource(getResources().getIdentifier(pic, "drawable", getPackageName()));


        Button save = (Button) findViewById(R.id.save_change_button);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View mLoginFormView) {
                getEventCreation();
                getShowAge();
                getAvatar();
                getDescription();
                getContact();
                final JsonObject json = new JsonObject();
                json.addProperty("isVisible", event);
                json.addProperty("showAge", showAge);
                json.addProperty("description", description);
                json.addProperty("contact", contact);
                json.addProperty("avatar", ProfilePicID);

                if (!isNetworkAvailable()) {

                    Toast.makeText(Profile.this, "No internet connection", Toast.LENGTH_LONG).show();
                    return;
                }

                BasicNameValuePair tokenPair = new BasicNameValuePair("Authorization", "Bearer " + Singleton.getInstance().getToken());


                Ion.with(getApplicationContext())
                        .load("PUT", getString(R.string.api_url) + "users/" + Singleton.getInstance().getmUser().getId())
                        .setHeader(tokenPair)
                        .setJsonObjectBody(json)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (e != null || result == null) {
                                    Log.d("ERROR RESPONSE", e.toString());
                                    return;
                                }

                                Log.d("API RESPONSE", result.toString());

                                JsonElement jsonUser = result.get("user");
                                User user = new Gson().fromJson(jsonUser, User.class);

                                if (user != null) {

                                    Singleton.getInstance().setmUser(user);
                                }

                            }
                        });
            }
        });

        Button switchRight = (Button) findViewById(R.id.switch_right);
        switchRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ProfilePicID < 4) {
                    ++ProfilePicID;
                    pic = "profile"+ProfilePicID;
                    (avatar = (ImageView) findViewById(R.id.avatar)).setImageResource(getResources().getIdentifier(pic, "drawable", getPackageName()));
                }
            }
        });

        Button switchLeft = (Button) findViewById(R.id.switch_left);
        switchLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ProfilePicID > 0) {
                    --ProfilePicID;
                    pic = "profile"+ProfilePicID;
                    (avatar = (ImageView) findViewById(R.id.avatar)).setImageResource(getResources().getIdentifier(pic, "drawable", getPackageName()));
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

    public void getEventCreation() {
        Switch bar = (Switch) findViewById(R.id.event_on);
        event = bar.isChecked();
    }

    public void getShowAge() {
        Switch bar = (Switch) findViewById(R.id.show_age);
        showAge = bar.isChecked();
    }

    public void getAvatar() {
//        avatar = (ImageView) findViewById(R.id.avatar_image);
    }

    public void getDescription() {
        description = ((EditText) findViewById(R.id.description_field)).getText().toString();
    }

    public void getContact() {
        contact = ((EditText) findViewById(R.id.contact_field)).getText().toString();
    }

}
