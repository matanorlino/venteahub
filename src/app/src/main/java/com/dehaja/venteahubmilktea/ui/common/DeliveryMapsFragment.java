package com.dehaja.venteahubmilktea.ui.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.Order;
import com.dehaja.venteahubmilktea.models.OrderItem;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.ui.driver.OrderAdapter;
import com.dehaja.venteahubmilktea.ui.driver.OrderItemsAdapter;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
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
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.sapereaude.maskedEditText.MaskedEditText;

import static androidx.core.content.ContextCompat.getSystemService;

public class DeliveryMapsFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, RoutingListener {
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

    private VenteaUser user;
    private Order order;
    private Button btnMapCancel;
    private Button btnMapDelivered;
    private TextView txtMapNoRecord;
    private SupportMapFragment mapFragment;

    private boolean hasNoRecord = false;

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
        public void onMapReady(GoogleMap map) {
            LatLng venteaLoc = new LatLng(14.28715398053406, 121.10748793798585);
            map.addMarker(new MarkerOptions().position(venteaLoc).title(Properties.TITLE)).showInfoWindow();
//            map.setTrafficEnabled(true);
            // change to customer's address
            LatLngBounds venteeaBounds = new LatLngBounds(
                    new LatLng(14.28575403726168, 121.1055055117034),
                    new LatLng(14.288913085757631, 121.11029644386753)
            );
//            map.moveCamera(CameraUpdateFactory.newLatLng(venteaLoc));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(venteeaBounds.getCenter(), 15));
            DeliveryMapsFragment.this.map = map;
            if (hasNoRecord) {
                DeliveryMapsFragment.this.map.clear();
            } else {
                getMyLocation();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_delivery_maps, container, false);
        requestPermission();
        user = (VenteaUser)(getActivity().getIntent().getSerializableExtra("VenteaUser"));
        btnMapCancel = root.findViewById(R.id.btnMapCancel);
        btnMapDelivered = root.findViewById(R.id.btnMapDelivered);
        txtMapNoRecord = root.findViewById(R.id.txtMapNoRecord);

        getToDeliverOrder(getContext());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            if (hasNoRecord) {
                mapFragment.getView().setVisibility(View.INVISIBLE);
            } else {
                mapFragment.getView().setVisibility(View.VISIBLE);
                txtMapNoRecord.setVisibility(View.INVISIBLE);
                btnMapCancel.setVisibility(View.VISIBLE);
                btnMapDelivered.setVisibility(View.VISIBLE);
                mapFragment.getMapAsync(callback);
            }
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);
        } else {
            locationPermission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //if permission granted.
                    locationPermission = true;
                    getMyLocation();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
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
                LatLng ltlng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        ltlng, 16f);
                map.moveCamera(cameraUpdate);
                findRoutes(ltlng, venteaLoc);
            }
        });

        //get destination location when user click on map
//        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                end = venteaLoc;
//                map.clear();
//
//                start = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
//                //start route finding
//                findRoutes(start,end);
//            }
//        });
    }

    // function to find Routes.
    public void findRoutes(LatLng start, LatLng end)
    {
        if(start == null || end == null) {
            Toast.makeText(getContext(),"Unable to get location",Toast.LENGTH_LONG).show();
        }
        else
        {
            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(start, end)
                    .key(Properties.MAP_API_KEY)  //also define your api key here.
                    .build();
            routing.execute();
        }
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        e.printStackTrace();
        Toast.makeText(getContext(),"Unable to get location",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRoutingStart() {

    }

    // SPF ALGO
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if(polylines!= null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng = null;
        LatLng polylineEndLatLng = null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i < route.size(); i++) {
            if(i == shortestRouteIndex)
            {
                polyOptions.color(getResources().getColor(R.color.purple_200));
                polyOptions.width(10);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = map.addPolyline(polyOptions);
                polylineStartLatLng=polyline.getPoints().get(0);
                int k = polyline.getPoints().size();
                polylineEndLatLng=polyline.getPoints().get(k-1);
                polylines.add(polyline);
            }

        }

        //Add Marker on route starting position
        MarkerOptions startMarker = new MarkerOptions();
        startMarker.position(polylineStartLatLng);
        startMarker.title(user.getUsername());
        map.addMarker(startMarker);

        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("Destination");
        map.addMarker(endMarker);
    }

    @Override
    public void onRoutingCancelled() {
        findRoutes(start,end);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        findRoutes(start,end);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        getMyLocation();
    }

    private void getToDeliverOrder(Context context) {
        String url = Properties.SERVER_URL + "api/App_Get_Delivering.php?delivered_by=" + user.getId();
        RequestQueue q = Volley.newRequestQueue(context);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            if (Validator.isResponseSuccess(res.getString("response"))) {
                                JSONArray data = res.getJSONArray("data");
                                System.out.println("[data]: " + data);
                                if (data.length() < 1) {
                                    txtMapNoRecord.setVisibility(View.VISIBLE);
                                    hasNoRecord = true;
                                }
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject obj = data.getJSONObject(i);
                                    order = new Order(
                                            obj.getInt("order_id"),
                                            obj.getInt("user_id"),
                                            obj.getString("address"),
                                            obj.getString("username"),
                                            obj.getString("contact_no"),
                                            obj.getString("date"),
                                            (float)obj.getDouble("total"),
                                            obj.getInt("delivered_by")
                                     );
                                }
                                setOnBtnClickListener();
                            } else {
                                Toast.makeText(getContext(), "Failed to load Order", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Failed to load Order", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Failed to load Order", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        q.add(jsonObjRequest);
    }

    public void updateOrderStatus(View view, String state, int order_id) {
        if (state.equals(Properties.CANNOT_UPDATE_BY_DRIVER)) {
            Toast.makeText(getContext(), state, Toast.LENGTH_LONG).show();
            return;
        }
        String url = Properties.SERVER_URL + "api/App_Update_Order_Status.php";
        RequestQueue q = Volley.newRequestQueue(getContext());
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("response: " + response);
                            JSONObject res = new JSONObject(response);

                            if (Validator.isResponseSuccess(res.getString("response"))) {
                                String msg = String.format("Order status has been updated to %s", state);
                                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();

                                getToDeliverOrder(getContext());
                            } else {
                                Toast.makeText(getContext(), "Error accepting order", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error accepting order", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Invalid Login", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", String.valueOf(user.getId()));
                params.put("state", state);
                params.put("order_id", String.valueOf(order_id));
                return params;
            }
        };
        q.add(jsonObjRequest);
    }

    public void setOnBtnClickListener() {
        if (!hasNoRecord && order != null) {
            txtMapNoRecord.setVisibility(View.VISIBLE);
            btnMapCancel.setVisibility(View.VISIBLE);
            btnMapDelivered.setVisibility(View.VISIBLE);

            if (user.getId() == order.getDelivered_by()) {
                btnMapCancel.setOnClickListener(view -> {
                    updateOrderStatus(view,  Properties.CANCELLED, order.getOrder_id());
                });

                btnMapDelivered.setOnClickListener(view -> {
                    updateOrderStatus(view,  Properties.RECEIVED, order.getOrder_id());
                });
            } else {
                btnMapCancel.setOnClickListener(view -> {
                    updateOrderStatus(view,  Properties.CANNOT_UPDATE_BY_DRIVER, order.getOrder_id());
                });

                btnMapDelivered.setOnClickListener(view -> {
                    updateOrderStatus(view,  Properties.CANNOT_UPDATE_BY_DRIVER, order.getOrder_id());
                });
            }
        } else {
            txtMapNoRecord.setVisibility(View.VISIBLE);
            btnMapCancel.setVisibility(View.INVISIBLE);
            btnMapDelivered.setVisibility(View.INVISIBLE);
            hasNoRecord = true;
            if (DeliveryMapsFragment.this.map != null) {
                DeliveryMapsFragment.this.map.clear();
            }
            if (mapFragment != null) {
                mapFragment.getView().setVisibility(View.INVISIBLE);
            }
        }
    }
}