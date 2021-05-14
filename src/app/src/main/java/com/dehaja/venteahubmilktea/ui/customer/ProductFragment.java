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
                                        Product product = new Product();
                                        product.setProduct_id(obj.getInt("product_id"));
                                        product.setCategory_id(obj.getInt("product_category_id"));
                                        product.setMarket_price(Float.parseFloat(obj.getString("market_price")));
                                        product.setSell_price(Float.parseFloat(obj.getString("sell_price")));
                                        product.setProduct_code(obj.getString("product_code"));
                                        product.setProduct_img(obj.getString("product_img"));
                                        product.setModel(obj.getString("model"));
                                        product.setPurchase_description(obj.getString("purchase_description"));
                                        product.setProduct_description(obj.getString("product_description"));
                                        product.setStatus(obj.getString("status"));
                                        product.setProduct_name(obj.getString("product_name"));
                                        products.add(product);
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
        new TabLayoutMediator(tabCategories, pagerProducts,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(categories.get(position).getCategory_name());
                    }
                }).attach();
    }
}