package com.dehaja.venteahubmilktea.util.constants;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Properties {
    public static String TITLE = "Ventea Hub Milktea";
    /*
     * Configuration
     */
    public static String SERVER_URL = "http://192.168.1.28/commission/";
    public static final String ERROR = "0";
    public static final String SUCCESS = "1";
    public static final String DUPLICATE = "2";
    /*
     * Access Level
     */
    // Administrator user
    public static final String ADMIN = "admin";
    // Customer user
    public static final String CUSTOMER = "customer";
    // Delivery personnel
    public static final String DRIVER = "driver";

    /*
     * Order State
     */
    // Admin has not yet see the order made by the customer
    public static final String UNEXAMINED = "unexamined";
    // Order was placed but cancelled by the user
    public static final String CANCELLED = "cancelled";
    // Order was placed but the driver has not yet accepted to deliver
    public static final String WAITING_DRIVER = "wait_deliver";
    // Order is being delivered by the driver
    public static final String DELIVERING = "delivering";
    // Order was delivered and paid
    public static final String RECEIVED = "received";
    // Other driver tries to update status
    public static final String CANNOT_UPDATE_BY_DRIVER = "Cannot update delivery status of other personnel";

    /*
     * Product View
     */
    public static final String ADD_TO_CART = "Add to Cart - ";
    public static final String UPDATE_CART = "Update Cart - ";
    public static final String PESO_SIGN = "\u20B1 ";
    public static final String FROM_PRODUCT_LIST = "product_list";
    public static final String FROM_CART = "cart";
    public static final String X = "x";

    /*
     * Map Key
     */
    public static String MAP_API_KEY = "AIzaSyDNYt-tL60_OdNJwDPTl_7KSM-Kmyxslos";
    private static final LatLng VENTEA_LOC = new LatLng(14.28715398053406, 121.10748793798585);
    private static final LatLngBounds VENTEA_BOUNDS = new LatLngBounds(
            new LatLng(14.28575403726168, 121.1055055117034),
            new LatLng(14.288913085757631, 121.11029644386753)
    );

    public void setServerUrl(String url) {
        Properties.SERVER_URL = url;
    }

    public static LatLng getVenteaLocation() {
        return Properties.VENTEA_LOC;
    }

    public static LatLngBounds getVenteaBounds() {
        return Properties.VENTEA_BOUNDS;
    }

    public static String getAddressNameByLatLng(Context context, LatLng latLng) throws IOException{
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressList = geocoder.getFromLocation(
                latLng.latitude, latLng.longitude, 1);
        return addressList.get(0).getAddressLine(0);
    }

    public static String getAddressName(String [] address) {
        if (address != null && address.length > 0) {
            String add = Arrays.toString(Arrays.copyOfRange(address, 0, address.length - 2));
            return "";
        }
        return null;
    }

}
