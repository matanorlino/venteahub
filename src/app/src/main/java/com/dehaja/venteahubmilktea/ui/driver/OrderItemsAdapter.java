package com.dehaja.venteahubmilktea.ui.driver;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.OrderItem;
import com.dehaja.venteahubmilktea.util.constants.Properties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<OrderItem> orderItems;

    public OrderItemsAdapter(Context context, List<OrderItem> orderItems) {
        this.inflater = LayoutInflater.from(context);
        this.orderItems = orderItems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_order_view_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            // set date
            String datePattern = "MM/dd/yyyy hh:mm a";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDate = sdf.parse(orderItems.get(position).getDate());
            String formattedDate = new SimpleDateFormat(datePattern).format(parsedDate);

            // set image
            String imgUrl = Properties.SERVER_URL + "assets/product_img/" + orderItems.get(position).getProduct_img();
            Glide.with(inflater.getContext())
                    .load(imgUrl)
                    .placeholder(R.mipmap.app_logo)
                    .error(R.mipmap.app_logo)
                    .override(100,100)
                    .centerCrop()
                    .into(holder.imgOrderViewProduct);

            // set text
            holder.txtOrderViewProductName.setText(orderItems.get(position).getProduct_name());
            holder.txtOrderViewPrice.setText(String.format("%s %.2f", Properties.PESO_SIGN, orderItems.get(position).getSell_price()));
            holder.txtOrderViewQty.setText(String.valueOf(orderItems.get(position).getQty()));
            holder.txtOrderViewSubTotal.setText(String.format("%s %.2f", Properties.PESO_SIGN,
                    (orderItems.get(position).getQty() * orderItems.get(position).getSell_price())));
            holder.txtOrderViewRequest.setText(orderItems.get(position).getRequest());
            holder.txtOrderViewRequest.setInputType(InputType.TYPE_NULL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtOrderViewProductName;
        TextView txtOrderViewPrice;
        TextView txtOrderViewQty;
        TextView txtOrderViewSubTotal;
        EditText txtOrderViewRequest;
        ImageView imgOrderViewProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderViewProductName = itemView.findViewById(R.id.txtOrderViewProductName);
            txtOrderViewPrice = itemView.findViewById(R.id.txtOrderViewPrice);
            txtOrderViewQty = itemView.findViewById(R.id.txtOrderViewQty);
            txtOrderViewSubTotal = itemView.findViewById(R.id.txtOrderViewSubTotal);
            txtOrderViewRequest = itemView.findViewById(R.id.txtOrderViewRequest);
            imgOrderViewProduct = itemView.findViewById(R.id.imgOrderViewProduct);
        }

    }
}
