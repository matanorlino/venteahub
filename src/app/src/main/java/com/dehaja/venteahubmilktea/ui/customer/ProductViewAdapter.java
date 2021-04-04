package com.dehaja.venteahubmilktea.ui.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.Product;
import com.dehaja.venteahubmilktea.util.constants.Properties;

import java.util.ArrayList;
import java.util.Locale;

public class ProductViewAdapter extends ArrayAdapter<Product> {

    public ProductViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> products) {
        super(context, resource, products);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
        }

        TextView textProductName = (TextView) convertView.findViewById(R.id.textProductName);
        TextView textProductDescription = (TextView) convertView.findViewById(R.id.textProductDescription);
        TextView textProductPrice = (TextView) convertView.findViewById(R.id.textProductPrice);
        ImageView imageProductImage = (ImageView) convertView.findViewById(R.id.imageProductImage);

        textProductName.setText(product.getProduct_name());
        textProductDescription.setText(product.getProduct_description());
        textProductPrice.setText("P ".concat(String.format(Locale.US,"%.2f", product.getSell_price())));

        String imgUrl = Properties.SERVER_URL + "/assets/product_img/" + product.getProduct_img();
        Glide.with(getContext())
                .load(imgUrl)
                .placeholder(R.mipmap.app_logo)
                .error(R.mipmap.app_logo)
                .override(100, 100)
                .centerCrop()
                .into(imageProductImage);

        return convertView;
    }
}
