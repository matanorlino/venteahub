package com.dehaja.venteahubmilktea.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class AccountFragment extends Fragment {
    VenteaUser user;
    FragmentActivity activity;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        user = (VenteaUser)(activity.getIntent().getSerializableExtra("VenteaUser"));
        Button btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(v -> {
            updateOnClick(view);
        });

        loadUser(view);
    }

    private void loadUser(View view) {
        EditText txtUsername = (EditText) view.findViewById(R.id.acc_txtUsername);
        EditText txtEmail = (EditText) view.findViewById(R.id.acc_txtEmail);
        EditText txtPassword = (EditText) view.findViewById(R.id.acc_txtPassword);
        EditText txtContactNo = (EditText) view.findViewById(R.id.acc_txtContactNo);

        txtUsername.setText(user.getUsername());
        txtEmail.setText(user.getEmail());
        txtContactNo.setText(user.getContact_no());
    }

    public void updateOnClick(View view) {
        String url = Properties.SERVER_URL + "api/App_Update_User.php";
        RequestQueue q = Volley.newRequestQueue(activity);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("response: " + response);
                            JSONObject res = new JSONObject(response);
                            if (Validator.isResponseSuccess(res.getString("response"))) {
                                updateVenteaUser(view);
                                Toast.makeText(activity.getApplicationContext(), "Successfully updated!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(activity.getApplicationContext(), "Invalid Login", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(activity.getApplicationContext(), "Invalid Login", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity.getApplicationContext(), "Invalid Login", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String txtEmail = ((EditText) view.findViewById(R.id.acc_txtEmail)).getText().toString();
                String txtPassword = ((EditText) view.findViewById(R.id.acc_txtPassword)).getText().toString();
                String txtContactNo = ((MaskedEditText) view.findViewById(R.id.acc_txtContactNo)).getText().toString();
                System.out.println("txtContactNo: " + txtContactNo);
                Integer id = user.getId();

                if (!txtPassword.trim().isEmpty()) {
                    params.put("password", txtPassword);
                }
                params.put("id", id.toString());
                params.put("email", txtEmail);
                params.put("contact_no", txtContactNo);

                return params;
            }
        };
        q.add(jsonObjRequest);
    }

    private void updateVenteaUser(View view) {
        String txtEmail = ((EditText) view.findViewById(R.id.acc_txtEmail)).getText().toString();
        String txtPassword = ((EditText) view.findViewById(R.id.acc_txtPassword)).getText().toString();
        String txtContactNo = ((MaskedEditText) view.findViewById(R.id.acc_txtContactNo)).getText().toString();

        user.setEmail(txtEmail);
        user.setContact_no(txtContactNo);
        if (!txtPassword.isEmpty()) {
            user.setPassword(txtPassword.toString());
        }
        activity.getIntent().removeExtra("VenteaUser");
        activity.getIntent().putExtra("VenteaUser", user);
    }
}