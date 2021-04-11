package com.dehaja.venteahubmilktea.ui.driver;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.Order;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Order> orders;
    View view;
    public OrderAdapter(Context context, List<Order> order) {
        this.inflater = LayoutInflater.from(context);
        this.orders = order;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.view = inflater.inflate(R.layout.fragment_order_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            System.out.println("Raw Date: " + orders.get(position).getDate());
            String datePattern = "MM/dd/yyyy hh:mm a";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDate = sdf.parse(orders.get(position).getDate());
            String formattedDate = new SimpleDateFormat(datePattern).format(parsedDate);
            System.out.println("Formatted Date: " + formattedDate);
            holder.cardAddress.setText(orders.get(position).getAddress());
            holder.cardContactNo.setText(orders.get(position).getContact_no());
            holder.cardDate.setText(formattedDate.toUpperCase());
            holder.cardTotal.setText(String.format("%s %.2f", Properties.PESO_SIGN, orders.get(position).getTotal()));
            holder.viewOrder.setContentDescription(String.valueOf(orders.get(position).getOrder_id()));
            holder.acceptOrder.setContentDescription(String.valueOf(orders.get(position).getOrder_id()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void clear() {
        orders.clear();
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardAddress;
//        TextView cardUsername;
        TextView cardContactNo;
        TextView cardDate;
        TextView cardTotal;
        Button viewOrder;
        Button acceptOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardAddress = itemView.findViewById(R.id.txtCardAddress);
            cardContactNo = itemView.findViewById(R.id.txtCardContact);
            cardDate = itemView.findViewById(R.id.txtCardDateTime);
            cardTotal = itemView.findViewById(R.id.txtCardTotal);
            viewOrder = itemView.findViewById(R.id.btnCardViewOrder);
            acceptOrder = itemView.findViewById(R.id.btnCardAccept);
        }

    }
}
