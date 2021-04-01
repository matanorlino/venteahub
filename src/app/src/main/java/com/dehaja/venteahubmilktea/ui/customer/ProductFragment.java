package com.dehaja.venteahubmilktea.ui.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.Product;
import com.dehaja.venteahubmilktea.models.ProductCategory;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductFragment extends Fragment {

    private ArrayList<ProductCategory> categories;
    private ArrayList<Product> products;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product, container, false);
        categories = new ArrayList<>();
        products = new ArrayList<>();

        System.out.println("CATEGORY SIZE INIT: " + categories.size());
        getCategories();
        return root;
    }

    public void getCategories() {
        String url = Properties.SERVER_URL + "api/App_Categories.php";
        RequestQueue q = Volley.newRequestQueue(getContext());

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
                                    categories.add(new ProductCategory(
                                            obj.getInt("category_id"),
                                            obj.getString("category_name"),
                                            obj.getString("category_note")
                                    ));
                                }
                                System.out.println("CATEGORY SIZE INNER: " + categories.size());
                                getProducts();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        q.add(jsonObjRequest);
    }

    private void getProducts() {
        String url = Properties.SERVER_URL + "api/App_Products.php";
        RequestQueue q = Volley.newRequestQueue(getContext());

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
                                    if (obj != null) {
                                        System.out.println("getProducts");
                                        products.add(new Product(
                                                obj.getInt("product_id"),
                                                obj.getInt("product_category_id"),
                                                Float.parseFloat(obj.getString("market_price")),
                                                Float.parseFloat(obj.getString("sell_price")),
                                                obj.getString("product_code"),
                                                obj.getString("product_img"),
                                                obj.getString("model"),
                                                obj.getString("purchase_description"),
                                                obj.getString("product_description"),
                                                obj.getString("status"),
                                                obj.getString("product_name")
                                        ));
                                    }
                                }
                                setCategoryTab();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        q.add(jsonObjRequest);
    }

    private void setCategoryTab() {
        TabLayout tabCategories = (TabLayout) getActivity().findViewById(R.id.tabCategories);
        tabCategories.removeAllTabs();
        tabCategories.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabCategories.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPager2 pagerProducts = (ViewPager2) getActivity().findViewById(R.id.pagerProducts);
        pagerProducts.setAdapter(new ProductPagerAdapter(getActivity(), products, categories));
        System.out.println("setCategoryTab" + products.size());
        new TabLayoutMediator(tabCategories, pagerProducts,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(categories.get(position).getCategory_name());
                    }
                }).attach();
    }
}