package com.herokuapp.meetnlunch.meetnlunch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class Filter extends AppCompatActivity {

    static int filteredAge = 0;
    static int filteredDistance = 0;
    static int filteredFoodType = 0;
    static int filteredGender = 0;
    static int visibilityAge = 0;
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
        ((RadioGroup) findViewById(R.id.gender_filter)).check(genderID);
        ((RadioGroup) findViewById(R.id.gender_visibility)).check(genderVisibilityID);

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
            }
        });
    }

    public void getFilteredAge() {
        SeekBar ageBar = (SeekBar) findViewById(R.id.filter_age);
        filteredAge = ageBar.getProgress();
    }

    public void getFilteredDistance() {
        SeekBar distanceBar = (SeekBar) findViewById(R.id.filter_range);
        filteredDistance = distanceBar.getProgress();
    }

    public void getFilteredFoodType() {
        Spinner foodBar = (Spinner) findViewById(R.id.filter_food);
        filteredFoodType = foodBar.getSelectedItemPosition();
    }

    public void getAgeVisibility() {
        SeekBar ageBar = (SeekBar) findViewById(R.id.filter_age_visibility);
        visibilityAge = ageBar.getProgress();
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
}
