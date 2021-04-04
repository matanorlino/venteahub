package com.dehaja.venteahubmilktea.ui.customer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.Product;
import com.dehaja.venteahubmilktea.models.ProductCategory;
import com.dehaja.venteahubmilktea.models.VenteaUser;

import java.util.ArrayList;

public class ProductListFragment extends Fragment {

    private static final String ARG_COUNTER = "counter";
    private static final String ARG_PRODUCT = "products";
    private static final String ARG_CATEGORY = "categories";
    private Integer counter;
    private ArrayList<Product> products;
    private ArrayList<ProductCategory> categories;

    public ProductListFragment() {}

    public static ProductListFragment newInstance(Integer counter, ArrayList<Product> products, ArrayList<ProductCategory> categories) {
        ProductListFragment plFragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COUNTER, counter);
        args.putParcelableArrayList(ARG_PRODUCT, (ArrayList<? extends Parcelable>) products);
        for (Product p : products) {
        }
        args.putParcelableArrayList(ARG_CATEGORY, (ArrayList<? extends Parcelable>) categories);
        plFragment.setArguments(args);
        return plFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            counter = getArguments().getInt(ARG_COUNTER);
            products = getArguments().getParcelableArrayList(ARG_PRODUCT);
            categories = getArguments().getParcelableArrayList(ARG_CATEGORY);
            for (Product p : products) {
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Product> categoryProducts = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategory_id() == categories.get(counter).getCategory_id()) {
                categoryProducts.add(p);
            }
        }
        VenteaUser user = (VenteaUser) getActivity().getIntent().getSerializableExtra("VenteaUser");
        ProductViewAdapter adapter = new ProductViewAdapter(getContext(), 0, categoryProducts);
        ListView listProducts = (ListView) view.findViewById(R.id.listProducts);
        listProducts.setAdapter(adapter);
        listProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ProductViewActivity.class);
                intent.putExtra("product", (Product) adapterView.getItemAtPosition(i));
                intent.putExtra("VenteaUser", user);
                startActivity(intent);
            }
        });
    }
}
