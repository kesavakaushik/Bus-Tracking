package com.vendor.transportation_driver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String response = null;
    String res,result;
    EditText editText;
    Button login;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
  //  private CustomAdapter2 adapter;
  //  ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
    ProgressDialog progressDialog;
    private static String SIGNUP_URL = "http://trackmee.in/api/driver";
    //ListView lvItems;
    String mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences("PREFNAME",MODE_APPEND);
        editor = sharedPreferences.edit();
        editText = (EditText) findViewById(R.id.mobile);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
       // lvItems = (ListView) findViewById(R.id.lvItems);

       // adapter = new CustomAdapter2(getApplicationContext(), arrayList);
       // lvItems.setAdapter(adapter);


    }


    @Override
    public void onClick(View v) {
         mobile = editText.getText().toString();
        new JsonData().execute();
        /*Intent i = new Intent(getApplicationContext(),BusListActivity.class);
        startActivity(i);*/
    }

    public class JsonData extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait data is loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("mobile", mobile));
            System.out.println("?????????" + postParameters);

           // arrayList.clear();
            try {
                response = SimpleHttpClient.executeHttpPost(SIGNUP_URL, postParameters);
                res = response.toString();
                System.out.println("Responce is : "+res);
                JSONArray parentArray = new JSONArray(res);
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                  result = finalObject.getString("response");
                    if (Integer.parseInt(result) == 1){
                         String driverId = finalObject.getString("driverId");
                        editor.putString("driverId",driverId);
                        editor.commit();

                    }



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
           // System.out.println("@@@@@@@@@" + res);
           // adapter.updateResults(arrayList);
            if (Integer.parseInt(result) == 1){

                    Intent i = new Intent(getApplicationContext(),BusListActivity.class);
                startActivity(i);
            }
            else{
                Toast.makeText(getApplicationContext(),"Please Enter a valid mobile Number",Toast.LENGTH_LONG).show();
            }
        }

    }
}
