package com.dehaja.venteahubmilktea.ui.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.ui.common.DeliveryMapsFragment;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.List;

public class CheckoutAddress extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap map;
    private final static int LOCATION_REQUEST_CODE = 23;
    boolean locationPermission = false;
    //polyline object
    private List<Polyline> polylines = null;
    private Location myLocation = null;
    private Location destinationLocation = null;
    private LatLng venteaLoc = new LatLng(14.28715398053406, 121.10748793798585);
    protected LatLng start = null;
    protected LatLng end = null;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng venteaLoc = new LatLng(14.28715398053406, 121.10748793798585);
            map.addMarker(new MarkerOptions().position(venteaLoc).title(Properties.TITLE)).showInfoWindow();
            map.setTrafficEnabled(true);
            LatLngBounds venteeaBounds = new LatLngBounds(
                    new LatLng(14.28575403726168, 121.1055055117034),
                    new LatLng(14.288913085757631, 121.11029644386753)
            );
//            map.moveCamera(CameraUpdateFactory.newLatLng(venteaLoc));
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(venteeaBounds.getCenter(), 15));
            CheckoutAddress.this.map = map;
            getMyLocation();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checkout_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    //to get user location
    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(),"Unable to get location. Please check your location permission.",Toast.LENGTH_LONG).show();
            return;
        }
        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                myLocation = location;
                LatLng ltlng = new LatLng(location.getLatitude(),location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        ltlng, 16f);
                map.animateCamera(cameraUpdate);
            }
        });

        //get destination location when user click on map
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                end = venteaLoc;
                map.clear();

                start = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        getMyLocation();
    }
}