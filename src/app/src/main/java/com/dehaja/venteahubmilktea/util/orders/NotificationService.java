package com.dehaja.venteahubmilktea.util.orders;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.work.WorkManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.util.constants.NotificationTool;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationService extends Service {

    public static final String ORDER_ID = "order_id";
    private boolean firstFetch = true;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int orderId = intent.getIntExtra(ORDER_ID, 0);
        getStatus(orderId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void getStatus(int orderId) {
        String url = Properties.SERVER_URL + "api/App_Get_Order_Status.php?order_id=" + orderId;

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
                                String status = obj.getString("state");

                                try {
                                    if (status.equals(Properties.DELIVERING) && firstFetch) {
                                        displayNotification("VenteaHub Milktea", "Driver Found!");
                                        getStatus(orderId);
                                        firstFetch = false;
                                    } else if (status.equals(Properties.CANCELLED)) {
                                        displayNotification("VenteaHub Milktea", "Order was Cancelled!");
                                        stopService(new Intent(this, NotificationService.class));
                                    } else if (status.equals(Properties.RECEIVED)) {
                                        displayNotification("VenteaHub Milktea", "Order Completed!");
                                        stopService(new Intent(this, NotificationService.class));
                                    } else {
                                        getStatus(orderId);
                                    }
                                } catch (Exception e) {
                                    getStatus(orderId);
                                }
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
