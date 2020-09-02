package com.vendor.transportation_driver;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Praveen on 3/31/2016.
 */
public class BusListActivity extends AppCompatActivity {
    String driverId;
    SharedPreferences sharedPreferences;
    String response = null;
    String res, busId,busNo;
    private  CustomAdapter2 adapter;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<HashMap<String, String>>();
    ProgressDialog progressDialog;
    private static String SIGNUP_URL = "http://trackmee.in/api/driver/buses";
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* toolbar.setNavigationIcon(R.drawable.navigation);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
        sharedPreferences = getSharedPreferences("PREFNAME",MODE_APPEND);
        driverId = sharedPreferences.getString("driverId", null);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String busId = arrayList.get(position).get("busId");
                Intent bus = new Intent(getApplicationContext(),BusLocation.class);
                bus.putExtra("busId",busId);
                startActivity(bus);
            }
        });

        adapter = new CustomAdapter2(getApplicationContext(),arrayList);
        lvItems.setAdapter(adapter);
      //  new  JsonData().execute();
        try {
            ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

            if (netInfo == null || !netInfo.isConnected()
                    || !netInfo.isAvailable()) {
                // Toast.makeText(getApplicationContext(),"No Internet Found",Toast.LENGTH_LONG).show();
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setMessage("No Internet Connection Found");
                adb.setCancelable(false);
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                adb.show();


            } else {
                try {
                    new  JsonData().execute();
                } catch (Exception e) {
                    Log.d("Tag" + "Exception", e.toString());
                }
            }

        } catch (Exception e) {
            Log.d("Tag", e.toString());
        }

    }
    public class JsonData extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(BusListActivity.this);
            progressDialog.setMessage("Please wait data is loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("driverId", driverId));
            System.out.println("?????????" + postParameters);

            arrayList.clear();
            try {
                response = SimpleHttpClient.executeHttpPost(SIGNUP_URL, postParameters);
                res = response.toString();
                System.out.println("Responce is :" + res);
                JSONArray parentArray = new JSONArray(res);
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    busId = finalObject.getString("busId");
                    busNo = finalObject.getString("busNumber");

                    System.out.println("bus id is :" + busId + "busNo is :" + busNo);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("busId", busId);
                    map.put("busNo", busNo);
                    arrayList.add(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
                String errorMsg = e.getMessage();
            }
            return res;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            adapter.updateResults(arrayList);

        }
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.logout) {
            AlertDialog.Builder builder = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                builder = new AlertDialog.Builder(BusListActivity.this, R.style.AppCompatAlertDialogStyle);
            }

            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to logout ?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                   /* editor.clear();
                    editor.commit();*/
                    Intent i = new Intent(BusListActivity.this, MainActivity.class);
                    i.addFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
