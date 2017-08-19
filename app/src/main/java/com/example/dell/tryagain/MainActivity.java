package com.example.dell.tryagain;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String urls="http://192.168.1.3:8081/myApp/public/api/student/all";

    private RecyclerView recyclerview;
    private  RecyclerView.Adapter adapter;
    private Switch switchh;

    private List<ListItem> itemlists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview= (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        switchh= (Switch) findViewById(R.id.switc);
        switchh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        itemlists=new ArrayList<>();
        loadRecylerViewData();
    }

    private void loadRecylerViewData() {
        final ProgressDialog progresDialog= new ProgressDialog(this);
        progresDialog.setMessage("Loading");
        StringRequest stringrequest = new StringRequest(Request.Method.GET, urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progresDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray();
                    for(int i=0; i<array.length();i++)
                    {
                        JSONObject o=array.getJSONObject(i);
                        ListItem item= new ListItem(o.getString("firstname"),
                                                    o.getString("lastname"));
itemlists.add(item);
                    }
                    adapter=new MyAdapter(itemlists, getApplicationContext());
                    recyclerview.setAdapter(adapter);

                }

                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progresDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(stringrequest);
    }
}
