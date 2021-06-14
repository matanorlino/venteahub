package com.dehaja.venteahubmilktea.ui.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    private final LatLngBounds selectableArea = new LatLngBounds(
            new LatLng(14.235052234920598, 121.04939937761759),
            new LatLng(14.334508423369526, 121.16843657626411));
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
        setVenteaMarker();
        // move camera to ventea area
        LatLngBounds venteaBounds = Properties.getVenteaBounds();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(venteaBounds.getCenter(), 15));
        map.setLatLngBoundsForCameraTarget(selectableArea);
        map.setMinZoomPreference(6.0f);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                selectedPlace = latLng;
                try{
                    selectedPlaceName = Properties.getAddressNameByLatLng(getApplicationContext(), selectedPlace);
                    updateMapLocation();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeAutocomplete() {
        // LEAVE THE API_KEY AS STRING LITERAL
        Places.initialize(getBaseContext(), "AIzaSyDNYt-tL60_OdNJwDPTl_7KSM-Kmyxslos");
        PlacesClient placesClient = Places.createClient(this);

        autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.place_autocomplete_search_input);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));

        // Set location restriction
        autocompleteFragment.setLocationRestriction(RectangularBounds.newInstance(selectableArea));

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

    private void setVenteaMarker() {
        // add marker to ventea
        map.addMarker(new MarkerOptions()
                .position(new LatLng(14.28715398053406, 121.10748793798585))
                .title("Ventea Hub Milktea")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .draggable(false));
    }
    private void updateMapLocation() {
        map.clear();
        setVenteaMarker();
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