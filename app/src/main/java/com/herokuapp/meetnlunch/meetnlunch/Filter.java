package com.herokuapp.meetnlunch.meetnlunch;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.BasicNameValuePair;
import com.koushikdutta.ion.Ion;

public class Filter extends AppCompatActivity {

    static int filteredAge = 18;
    static int filteredDistance = 1;
    static int filteredFoodType = 0;
    static int filteredGender = 0;
    static int visibilityAge = 18;
    static int visibilityGender = 0;
    static int genderID = 0;
    static int genderVisibilityID = 0;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_filters:
                    return true;
                case R.id.navigation_search:
                    Intent iSearchView =  new Intent(Filter.this, Search.class);
                    startActivity(iSearchView);
                    return true;
                case R.id.navigation_profile:
                    Intent iProfileView =  new Intent(Filter.this, Profile.class);
                    startActivity(iProfileView);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        filteredAge = Singleton.getInstance().getmUser().getWantedAge();
        filteredDistance = Singleton.getInstance().getmUser().getRange();
        filteredFoodType = Singleton.getInstance().getmUser().getFoodId() - 1;
        visibilityAge = Singleton.getInstance().getmUser().getVisibleAge();

        switch (Singleton.getInstance().getmUser().getVisibleGenderId()) {
            case 0:
                ((RadioButton) findViewById(R.id.male_gender_filter)).setChecked(true);
                break;
            case 1:
                ((RadioButton) findViewById(R.id.female_gender_filter)).setChecked(true);
                break;
            default:
                ((RadioButton) findViewById(R.id.both_gender_filter)).setChecked(true);
                break;
        }

        switch (Singleton.getInstance().getmUser().getVisibleGenderId()) {
            case 0:
                ((RadioButton) findViewById(R.id.male_gender_visibility)).setChecked(true);
                break;
            case 1:
                ((RadioButton) findViewById(R.id.female_gender_visibility)).setChecked(true);
                break;
            default:
                ((RadioButton) findViewById(R.id.both_gender_visibility)).setChecked(true);
                break;
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SeekBar mAgeFilter = (SeekBar) findViewById(R.id.filter_age);
        final TextView mAgeValue = (TextView) findViewById(R.id.age_value);

        mAgeFilter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                mAgeValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar mDistanceFilter = (SeekBar) findViewById(R.id.filter_range);
        final TextView mDistanceValue = (TextView) findViewById(R.id.range_value);

        mDistanceFilter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                mDistanceValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar mAgeVisibility = (SeekBar) findViewById(R.id.filter_age_visibility);
        final TextView mAgeVisibilityValue = (TextView) findViewById(R.id.age_visibility);

        mAgeVisibility.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                mAgeVisibilityValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ((SeekBar) findViewById(R.id.filter_age)).setProgress(filteredAge);
        ((SeekBar) findViewById(R.id.filter_range)).setProgress(filteredDistance);
        ((SeekBar) findViewById(R.id.filter_age_visibility)).setProgress(visibilityAge);
        ((Spinner) findViewById(R.id.filter_food)).setSelection(filteredFoodType);

        Button save = (Button) findViewById(R.id.save_change_button);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View mLoginFormView) {


                getFilteredAge();
                getFilteredDistance();
                getFilteredFoodType();
                getAgeVisibility();
                getFilteredGender();
                getVisibilityGender();

                final JsonObject json = new JsonObject();
                json.addProperty("food", filteredFoodType);
                json.addProperty("wantedAge", filteredAge);
                json.addProperty("wantedGender", getGender(filteredGender));
                json.addProperty("visibilityRange", filteredDistance);
                json.addProperty("visibleAge", visibilityAge);
                json.addProperty("visibleGender", getGender(visibilityGender));

                Log.d("JSON OBJECT", json.toString());

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
}

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }


    public SeekBar getFilteredAge() {
        SeekBar ageBar = (SeekBar) findViewById(R.id.filter_age);
        filteredAge = ageBar.getProgress();
        return ageBar;
    }

    public SeekBar getFilteredDistance() {
        SeekBar distanceBar = (SeekBar) findViewById(R.id.filter_range);
        filteredDistance = distanceBar.getProgress();
        return distanceBar;
    }

    public Spinner getFilteredFoodType() {
        Spinner foodBar = (Spinner) findViewById(R.id.filter_food);
        filteredFoodType = foodBar.getSelectedItemPosition() + 1;
        return foodBar;
    }

    public SeekBar getAgeVisibility() {
        SeekBar ageBar = (SeekBar) findViewById(R.id.filter_age_visibility);
        visibilityAge = ageBar.getProgress();
        return ageBar;
    }

    public void getFilteredGender() {
        RadioGroup genderBar = (RadioGroup) findViewById(R.id.gender_filter);
        genderID = genderBar.getCheckedRadioButtonId();
        View radioButton = genderBar.findViewById(genderID);
        filteredGender = genderBar.indexOfChild(radioButton);
    }

    public void getVisibilityGender() {
        RadioGroup genderBar = (RadioGroup) findViewById(R.id.gender_visibility);
        genderVisibilityID = genderBar.getCheckedRadioButtonId();
        View radioButton = genderBar.findViewById(genderVisibilityID);
        visibilityGender = genderBar.indexOfChild(radioButton);
    }

    public String getGender(int gender) {
        switch (gender) {
            case 0:
                return "M";
            case 1:
                return "F";
            default:
                return "both";
        }
    }
}
