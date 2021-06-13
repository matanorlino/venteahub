package com.dehaja.venteahubmilktea.ui.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dehaja.venteahubmilktea.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SelectAddressActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private AutocompleteSupportFragment autocompleteFragment;
    private LatLng selectedPlace;
    private String selectedPlaceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_checkoutAddress);
        mapFragment.getMapAsync(SelectAddressActivity.this);
    }

    private void initializeMap() {
        LatLngBounds venteaBounds = new LatLngBounds(
                new LatLng(14.28575403726168, 121.1055055117034),
                new LatLng(14.288913085757631, 121.11029644386753)
        );
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(venteaBounds.getCenter(), 15));
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                selectedPlace = latLng;
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try{
                    List<Address> addressList = geocoder.getFromLocation(
                            latLng.latitude, latLng.longitude, 1);
                    selectedPlaceName = addressList.get(0).getAddressLine(0);
                    updateMapLocation();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeAutocomplete() {
        Places.initialize(getBaseContext(), "AIzaSyDNYt-tL60_OdNJwDPTl_7KSM-Kmyxslos");
        PlacesClient placesClient = Places.createClient(this);

        autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.place_autocomplete_search_input);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));

        // Set location restriction
        autocompleteFragment.setLocationRestriction(RectangularBounds.newInstance(
                new LatLng(14.235052234920598, 121.04939937761759),
                new LatLng(14.334508423369526, 121.16843657626411)));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull @NotNull Place place) {
                selectedPlace = place.getLatLng();
                selectedPlaceName = place.getName();
                updateMapLocation();
            }

            @Override
            public void onError(@NonNull @NotNull Status status) {
                selectedPlace = null;
                System.out.println(status.getStatusMessage());
            }
        });
    }

    private void updateMapLocation() {
        map.clear();
        map.addMarker(new MarkerOptions().position(selectedPlace).title(selectedPlaceName));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, 17));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        initializeMap();
        initializeAutocomplete();
    }

    public void closeOnClick(View view) {
        if (!selectedPlaceName.equals(null)) {
            Intent intent = new Intent();
            intent.putExtra("lng", selectedPlace.longitude);
            intent.putExtra("lat", selectedPlace.latitude);
            intent.putExtra("addressName", selectedPlaceName);
            setResult(Activity.RESULT_OK, intent);
        } else {
            setResult(Activity.RESULT_CANCELED);
        }
        finish();
    }
}