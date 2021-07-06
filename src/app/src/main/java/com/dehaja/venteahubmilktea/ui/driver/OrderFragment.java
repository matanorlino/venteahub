package com.dehaja.venteahubmilktea.ui.driver;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.Order;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Order> orders;
    private OrderAdapter orderAdapter;
    private VenteaUser user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        user = (VenteaUser) getActivity().getIntent().getSerializableExtra("VenteaUser");
        recyclerView = root.findViewById(R.id.recOrderList);

        loadInit(null);

        SwipeRefreshLayout refreshLayout = root.findViewById(R.id.fragment_order_layout);
        refreshLayout.setOnRefreshListener(() -> loadInit(refreshLayout));

        return root;
    }

    private void loadInit(SwipeRefreshLayout refreshLayout) {
        // Get order list from API
        orders = new ArrayList<>();
        getOrderList(getContext(), refreshLayout);
    }

    private void getOrderList(Context context, SwipeRefreshLayout refreshLayout) {
        String url = "";
        if (user.getAccesslevel().equals(Properties.DRIVER)) {
            url = Properties.SERVER_URL + "api/App_Get_Wait_Driver.php";
        } else {
            url = Properties.SERVER_URL + "api/App_Get_Order_History.php?buyer_id="+ user.getId();
        }

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
                                orders.clear();
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject obj = data.getJSONObject(i);
                                    String addr[] = obj.getString("address").split(",");
                                    LatLng latLng = new LatLng(Double.parseDouble(addr[0]), Double.parseDouble(addr[1]));
                                    String addressName = obj.getString("address");
                                    try {
                                        addressName = Properties.getAddressNameByLatLng(getContext(), latLng);
                                    } catch (Exception e) {
                                        addressName = obj.getString("address");
                                        e.printStackTrace();
                                    }

                                    orders.add(new Order(
                                            obj.getInt("order_id"),
                                            obj.getInt("user_id"),
                                            addressName,
                                            obj.getString("username"),
                                            obj.getString("contact_no"),
                                            obj.getString("date"),
                                            (float)obj.getDouble("total")
                                    ));
                                }

                                // Set adapter to recycler view
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(layoutManager);
                                orderAdapter = new OrderAdapter(getContext(), orders, user);
                                recyclerView.setAdapter(orderAdapter);

                                if(refreshLayout != null) {
                                    refreshLayout.setRefreshing(false);
                                }
                            } else {
                                Toast.makeText(getContext(), "Failed to load Order list", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Failed to load Order list", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Failed to load Order list", Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
        });
        q.add(jsonObjRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.loadInit(null);
    }

    public void closeOnClick(View view) {
        this.onResume();
    }
}