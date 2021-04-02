package com.dehaja.venteahubmilktea.ui.customer;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dehaja.venteahubmilktea.models.Product;
import com.dehaja.venteahubmilktea.models.ProductCategory;

import java.util.ArrayList;

public class ProductPagerAdapter extends FragmentStateAdapter {

    private ArrayList<ProductCategory> categories;
    private ArrayList<Product> products;
    public ProductPagerAdapter( @NonNull FragmentActivity fragmentActivity, ArrayList<Product> products, ArrayList<ProductCategory> categories) {
        super(fragmentActivity);
        this.products = products;
        this.categories = categories;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ProductListFragment.newInstance(position, products, categories);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
