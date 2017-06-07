package com.herokuapp.meetnlunch.meetnlunch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.plus.Plus;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.BasicNameValuePair;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Search extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private boolean isReady = false;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationListener mLocationListener;
    private ArrayList<Integer> existingIds;
    private String[] foodArray = {"Korean", "Mexican", "Japanese", "Chinese", "French", "Vietnamese", "Italian", "Spanish", "Seafood"};
    private MyInfoWindowAdapter myWIndowAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_filters:
                    Intent iFilterView = new Intent(Search.this, Filter.class);
                    startActivity(iFilterView);
                    return true;
                case R.id.navigation_search:
                    return true;
                case R.id.navigation_profile:
                    Intent iProfileView = new Intent(Search.this, Profile.class);
                    startActivity(iProfileView);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        existingIds = new ArrayList<>();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        myWIndowAdapter = new MyInfoWindowAdapter();
        mMap.setInfoWindowAdapter(myWIndowAdapter);

        mLocationListener = new LocationListener() {

            private boolean isNetworkAvailable() {
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }

            @Override
            public void onLocationChanged(final Location location) {
                if (!isReady) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                            .zoom(16)                   // Sets the zoom
                            .build();                   // Creates a CameraPosition from the builder
//                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    isReady = true;
                }
                if (!isNetworkAvailable()) {

                    Toast.makeText(Search.this, "No internet connection", Toast.LENGTH_LONG).show();
                    return;
                }

                String url = "https://meetnlunchapp.herokuapp.com/app_dev.php/api/filter?";
                url += "food=" + Singleton.getInstance().getmUser().getFoodId();
                url += "&wanted_age=" + Singleton.getInstance().getmUser().getWantedAge();
                url += "&wanted_gender=" + Singleton.getInstance().getmUser().getWantedGender();
                url += "&range=" + Singleton.getInstance().getmUser().getRange();
                url += "&visible_age=" + Singleton.getInstance().getmUser().getVisibleAge();
                url += "&visible_gender=" + Singleton.getInstance().getmUser().getVisibleGender();
                url += "&customer_id=" + Singleton.getInstance().getmUser().getId();
                url += "&latitude=" + location.getLatitude() + "&longitude=" + location.getLongitude();

                Log.d("URL", url);
                String url2 = "https://meetnlunchapp.herokuapp.com/app_dev.php/api/users";
                BasicNameValuePair tokenPair = new BasicNameValuePair("Authorization", "Bearer " + Singleton.getInstance().getToken());

                Ion.with(getApplicationContext())
                        .load(url2)
                        .setHeader(tokenPair)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (e != null || result == null) {
                                    Log.d("ERROR RESPONSE", e.toString());
                                    return;
                                }
                                Log.d("API RESPONSE", result.toString());

                                JsonElement jsonCustomers = result.get("customers");
                                try {
                                    User[] users = new Gson().fromJson(jsonCustomers, User[].class);
                                    if (users == null)
                                        return;
                                    for (int i = 0; i < users.length; i++) {
                                        if (users[i].getLatitude() != 0) {
                                            if (!checkIfExist(users[i].getId())) {
                                                String name = "";
                                                if (users[i].getName() != null) {
                                                    name = users[i].getName();
                                                }

                                                int age = users[i].getAge();

                                                String gender = "";
                                                if (users[i].getGender() != null) {
                                                    gender = users[i].getGender();
                                                }

                                                myWIndowAdapter.ChangeAvatar(users[i].getAvatar());
                                                mMap.setInfoWindowAdapter(myWIndowAdapter);


                                                String title = name + ", " + age + " years old" + ", " + gender;

                                                mMap.addMarker(new MarkerOptions().position(new LatLng(users[i].getLatitude(),
                                                        users[i].getLongitude())).title(title).snippet("Looking for : " + foodArray[users[i].getFoodId()]
                                                        + " Restaurant\n" + "Description : " + users[i].getDescription() + "\nContact : " + users[i].getContact()));
                                            }
                                        }
                                    }
                                } catch (JsonSyntaxException err) {
                                }
                            }
                        });

            }
        };

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(10000);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mGoogleApiClient.connect();
        Criteria criteria = new Criteria();

    }

    private boolean checkIfExist(int id) {
        if (id == Singleton.getInstance().getmUser().getId()) {
            return true;
        }
        for (Integer existingId: existingIds) {
            if (id == existingId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    protected void startLocationUpdates() {
        Log.d("LOCATION UPDATE", "Loc changed started");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationListener);
//            LocationServices.FusedLocationApi.requestLocationUpdates(
//                mMap, mMap.getMyLocation(), this);
    }


    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        public void ChangeAvatar(int id) {
            if (id >= 0 && id <= 4)
                ((ImageView) myContentsView.findViewById(R.id.avatar_pic)).setImageResource(getResources().getIdentifier("profile" + id, "drawable", getPackageName()));
        }

        MyInfoWindowAdapter(){
            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
            tvSnippet.setText(marker.getSnippet());

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }

}
