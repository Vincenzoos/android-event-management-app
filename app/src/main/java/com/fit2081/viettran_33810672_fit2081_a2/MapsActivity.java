package com.fit2081.viettran_33810672_fit2081_a2;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.fit2081.viettran_33810672_fit2081_a1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.fit2081.viettran_33810672_fit2081_a1.databinding.ActivityMapsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    // variable for storing category location
    private String cateLocation;

    private String cateName;

    // Declare geocoder
    Geocoder geocoder;

    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // set class variable using bundle data
        cateLocation = getIntent().getExtras().getString("categoryLocation", "Australia");

        cateName = getIntent().getExtras().getString("categoryName", "Unknown category");

        // initialise Geocode to search location using String
        geocoder = new Geocoder(this, Locale.getDefault());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Move camera to the category location
        findLocationMoveCamera();

        // map onclick listener
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                String msg;
                List<Address> latLongToAddressList = new ArrayList<>();

                try {
                    latLongToAddressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude,1);
                } catch (IOException e){
                    throw new RuntimeException(e);
                }

                if (latLongToAddressList.isEmpty()){
                    msg = "Sorry, no location found";
                } else {
                    Address address = latLongToAddressList.get(0);
                    msg = "the address is " + address.getAddressLine(0);

//                    mMap.addMarker(new MarkerOptions().position(latLng));
                }
                Snackbar.make(mapFragment.getView(), msg, Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void findLocationMoveCamera(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            /**
             * cateLocation: String value, any string we want to search
             * maxResults: how many results to return if search was successful
             * successCallback method: if results are found, this method will be executed
             *                          runs in a background thread
             */
            geocoder.getFromLocationName(cateLocation, 1, addresses -> {
                // if there are results, this condition would return true
                if (!addresses.isEmpty()) {
                    // run on UI thread as the user interface will update once set map location
                    runOnUiThread(() -> {
                        // define new LatLng variable using the first address from list of addresses
                        LatLng newAddressLocation = new LatLng(
                                addresses.get(0).getLatitude(),
                                addresses.get(0).getLongitude()
                        );

                        // repositions the camera according to newAddressLocation
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(newAddressLocation));

                        // just for reference add a new Marker
                        mMap.addMarker(
                                new MarkerOptions()
                                        .position(newAddressLocation)
                                        .title(cateName)
                        );

                        // set zoom level to 8.5f or any number between range of 2.0 to 21.0
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
                    });
                } else {
                    // run on UI thread to display the error to user
                    runOnUiThread(() -> {
                        Toast.makeText(this, "address: " + cateLocation + " not found!", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }
    }
}