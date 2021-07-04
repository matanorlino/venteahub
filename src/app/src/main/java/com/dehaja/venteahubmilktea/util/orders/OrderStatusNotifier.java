package com.dehaja.venteahubmilktea.util.orders;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.Order;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;

public class OrderStatusNotifier extends Worker {

    public static final String ORDER_NUMBER = "order_number";
    public static final String TIMER_TASK = "timer_task";
    public static final String TIMER_TASK_1 = "timeTask1";
    private OrderStatus orderStatus;

    public OrderStatusNotifier(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        orderStatus = OrderStatus.getInstance();
        String status = orderStatus.getOrderStatus();
        String orderNumber = getInputData().getString(ORDER_NUMBER);

        try {
            if (status.equals(Properties.DELIVERING)) {
                displayNotification("VenteaHub Milktea", "Driver Found!");
                getStatus(orderNumber);
            } else if (status.equals(Properties.CANCELLED)) {
                displayNotification("VenteaHub Milktea", "Order was Cancelled!");
                WorkManager.getInstance(getApplicationContext()).cancelAllWork();
            } else if (status.equals(Properties.RECEIVED)) {
                displayNotification("VenteaHub Milktea", "Order Completed!");
                WorkManager.getInstance(getApplicationContext()).cancelAllWork();
            } else {
                getStatus(orderNumber);
            }
        } catch (Exception e) {
            getStatus(orderNumber);
        }

        return Result.success();
    }

    private void getStatus(String orderNumber) {
        String url = Properties.SERVER_URL + "api/App_Get_Order_Status.php?order_id=" + orderNumber;

        RequestQueue q = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET,
                url,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        if (Validator.isResponseSuccess(res.getString("response"))) {
                            JSONArray data = res.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject obj = data.getJSONObject(i);
                                orderStatus.setOrderStatus(obj.getString("state"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            error.printStackTrace();
        });
        q.add(jsonObjRequest);
    }

    private void displayNotification(String title, String message) {
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext()
                        .getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("ventea", "ventea", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "ventea")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());

    }
}
