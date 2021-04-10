package com.dehaja.venteahubmilktea.ui.driver;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFragment extends Fragment {
    RecyclerView recyclerView;
    List<Order> orders;
    OrderAdapter orderAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        recyclerView = root.findViewById(R.id.recOrderList);
        loadInit(null);

        SwipeRefreshLayout refreshLayout = root.findViewById(R.id.swipeLayout);
        refreshLayout.setOnRefreshListener(() -> {
            loadInit(refreshLayout);
        });

        return root;
    }
    private void loadInit(SwipeRefreshLayout refreshLayout) {
        // Get order list from API
        orders = new ArrayList<>();
        getOrderList(getContext(), refreshLayout);
    }
    private void getOrderList(Context context, SwipeRefreshLayout refreshLayout) {
        String url = Properties.SERVER_URL + "api/App_Get_Wait_Driver.php";
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
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject obj = data.getJSONObject(i);
                                    orders.add(new Order(
                                            obj.getInt("order_id"),
                                            obj.getInt("user_id"),
                                            obj.getString("address"),
                                            obj.getString("username"),
                                            obj.getString("contact_no"),
                                            obj.getString("date"),
                                            (float)obj.getDouble("total")
                                    ));
                                }

                                // Set adapter to recycler view
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(layoutManager);
                                orderAdapter = new OrderAdapter(getContext(), orders);
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
}