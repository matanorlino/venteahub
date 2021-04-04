package com.dehaja.venteahubmilktea.ui.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.CartItem;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends ArrayAdapter<CartItem> {

    private Button btnCartQty;
    private CartItem cartItem;
    private TextView txtCartProductName;
    private TextView txtCartPrice;

    public CartAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CartItem> cartItems) {
        super(context, resource, cartItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.cartItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_cart, parent, false);
        }

        this.btnCartQty = (Button) convertView.findViewById(R.id.btnCartQty);
        this.txtCartProductName = (TextView) convertView.findViewById(R.id.txtCartProductName);
        this.txtCartPrice = (TextView) convertView.findViewById(R.id.txtCartPrice);

        this.btnCartQty.setText(String.format(Locale.US,"%d %s", cartItem.getQuantity(), Properties.X));
        this.txtCartProductName.setText(this.cartItem.getProductName());
        this.txtCartPrice.setText(String.format(Locale.US,"%s %.2f",
                Properties.PESO_SIGN, cartItem.getQuantity() * cartItem.getSellPrice()));

        return convertView;
    }
}