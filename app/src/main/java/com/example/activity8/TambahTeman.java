package com.example.activity8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahTeman extends AppCompatActivity {
    private EditText editnama,edittelpon;
    private Button simpanbtn;
    String nm,tlp;
    int success;
    private static String url_insert = "https://praktikumtiumy.com/tambahtm.php";
    private static final String TAG = TambahTeman.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_teman);

        editnama = findViewById(R.id.ednama);
        edittelpon = findViewById(R.id.edtelpon);
        simpanbtn = findViewById(R.id.btnsimpan);

        simpanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpanData();
            }
        });

    }
    public void SimpanData() {
        if (editnama.getText().toString().equals("") || edittelpon.getText().toString().equals("")) {
            Toast.makeText(TambahTeman.this, "semua harus diisi data", Toast.LENGTH_SHORT).show();
        }
        else{
        nm = editnama.getText().toString();
        tlp = edittelpon.getText().toString();

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest strReq = new StringRequest(Request.Method.POST, url_insert, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Response :" + response.toString());
                    try {
                        JSONObject jObj = new JSONObject(response);
                        success = jObj.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Toast.makeText(TambahTeman.this, "Sukses simpan data", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TambahTeman.this, "gagal", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG,"Error :"+error.getMessage());
                    Toast.makeText(TambahTeman.this,"Gagal simpan data",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String,String> getParams(){
            Map<String,String> params = new HashMap<>();
            params.put("nama",nm);
            params.put("telpon",tlp);
            return params;
            }
            };
            requestQueue.add(strReq);

        }
    }
}