package com.dehaja.venteahubmilktea.ui.customer;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.Product;
import com.dehaja.venteahubmilktea.models.ProductCategory;

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
        System.out.println("newInstance");
        ProductListFragment plFragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COUNTER, counter);
        args.putParcelableArrayList(ARG_PRODUCT, (ArrayList<? extends Parcelable>) products);
        for (Product p : products) {
            System.out.println("PRODUCT LIST FRAGMENT newInstance: " + p.getProduct_name());
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
                System.out.println("PRODUCT LIST FRAGMENT onCreate: " + p.getProduct_name());
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
        TextView textView = view.findViewById(R.id.testText);
        StringBuilder sb = new StringBuilder();
        for (Product p : products) {
            sb.append(p.getProduct_name());
            System.out.println("PRODUCT LIST FRAGMENT FOR LOOP: " + p.getProduct_name());
            System.out.println("PRODUCT LIST FRAGMENT FOR LOOPsb: " + sb.toString());
        }
        System.out.println("PRODUCT LIST FRAGMENT: " + sb.toString());
        textView.setText(sb.toString());
        //content ng tab view
    }
}
