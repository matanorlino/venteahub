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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.Order;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Order> orders;
    private View view;
    private VenteaUser user;
    public OrderAdapter(Context context, List<Order> order, VenteaUser user) {
        this.inflater = LayoutInflater.from(context);
        this.orders = order;
        this.user = user;
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
            if (user.getAccesslevel().equals(Properties.CUSTOMER)) {
                String _date = orders.get(position).getDate();
                String state = _date.substring(_date.indexOf('('), _date.length());
                formattedDate += " " + state;
            }

            System.out.println("Formatted Date: " + formattedDate);
            holder.cardAddress.setText(orders.get(position).getAddress());
            holder.cardContactNo.setText(orders.get(position).getContact_no());
            holder.cardDate.setText(formattedDate.toUpperCase());
            holder.cardTotal.setText(String.format("%s %.2f", Properties.PESO_SIGN, orders.get(position).getTotal()));
            holder.viewOrder.setContentDescription(String.valueOf(orders.get(position).getOrder_id()));
            holder.acceptOrder.setContentDescription(String.valueOf(orders.get(position).getOrder_id()));
            holder.feedBack.setContentDescription(String.valueOf(orders.get(position).getOrder_id()));

            if (user.getAccesslevel().equals(Properties.DRIVER)) {
                holder.feedBack.setVisibility(View.INVISIBLE);
                holder.acceptOrder.setVisibility(View.VISIBLE);
            } else if (user.getAccesslevel().equals(Properties.CUSTOMER)) {
                holder.feedBack.setVisibility(View.VISIBLE);
                holder.acceptOrder.setVisibility(View.INVISIBLE);
            }
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
//      TextView cardUsername;
        TextView cardContactNo;
        TextView cardDate;
        TextView cardTotal;
        Button viewOrder;
        Button acceptOrder;
        Button feedBack;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardAddress = itemView.findViewById(R.id.txtCardAddress);
            cardContactNo = itemView.findViewById(R.id.txtCardContact);
            cardDate = itemView.findViewById(R.id.txtCardDateTime);
            cardTotal = itemView.findViewById(R.id.txtCardTotal);
            viewOrder = itemView.findViewById(R.id.btnCardViewOrder);
            acceptOrder = itemView.findViewById(R.id.btnCardAccept);
            feedBack = itemView.findViewById(R.id.btnCardFeedback);
        }
    }
}
