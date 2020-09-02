/*
package com.vendor.transportation_driver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

*/
/**
 * Created by dell on 3/17/2016.
 *//*

public class BusNames extends AppCompatActivity {
    String schid;
    String response = null;
    String res;
    String busId;
    String busNo;
    private  CustomAdapter2 adapter;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<HashMap<String, String>>();
    ProgressDialog progressDialog;
    private static String SIGNUP_URL = "http://www.abhinavinfotech.com/bustrack/busweb.php";

   */
/* int images[] = {R.drawable.right_arrow1,
            R.drawable.right_arrow1,
            R.drawable.right_arrow1,
            R.drawable.right_arrow1,
            R.drawable.right_arrow1,
            R.drawable.right_arrow1,R.drawable.right_arrow1};
    String schoolNames[] = {"Bus No : 1", "Bus No : 2","Bus No :3",
            "Bus No :4", "Bus No : 5","Bus No : 6","Bus No : 7"};*//*


    ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                */
/*Intent i = new Intent(getApplicationContext(), SchoolsName.class);
                startActivity(i);*//*

            }
        });
        savedInstanceState = getIntent().getExtras();
        schid = savedInstanceState.getString("schid");
        lvItems = (ListView) findViewById(R.id.lvItems);

         adapter = new CustomAdapter2(getApplicationContext(),arrayList);
        lvItems.setAdapter(adapter);
        new  JsonData().execute();
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent bus = new Intent(getApplicationContext(), BusDetailsActivity.class);
                startActivity(bus);
            }
        });
    }
    public class JsonData extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(BusNames.this);
            progressDialog.setMessage("Please wait data is loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("schoolId", schid));
            System.out.println("?????????" + postParameters);

            arrayList.clear();
            try {
                response = SimpleHttpClient.executeHttpPost(SIGNUP_URL, postParameters);
                res = response.toString();
                System.out.println("Responce is :" +res);
                JSONArray parentArray = new JSONArray(res);
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    busId = finalObject.getString("busId");
                    busNo = finalObject.getString("busNumber");

                    System.out.println("bus id is :" + busId + "busNo is :" + busNo);
                    HashMap<String,String> map = new HashMap<String,String>();
                    map.put("busId",busId);
                    map.put("busNo", busNo);
                    arrayList.add(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
                String errorMsg = e.getMessage();
            }
            return res;
        }


          */
/*  try {

                    response = SimpleHttpClient.executeHttpPost(SIGNUP_URL, postParameters);
                    res = response.toString();
                    System.out.println("Responce is :" +res);
                URL url = new URL(params[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = bufferedReader.readLine()) != null)
                {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONArray parentArray = new JSONArray(finalJson);
                for (int i=0; i<parentArray.length(); i++)
                {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    String school_id = finalObject.getString("schoolId");
                    String school_name = finalObject.getString("schoolName");
                    // String item_name = finalObject.getString("item_name");
                    String school_location = finalObject.getString("schoolLocation");
                    HashMap<String,String> map = new HashMap<String,String>();
                    map.put("school_name",school_name);
                    map.put("school_id",school_id);
                    map.put("school_location",school_location);
                    arrayList.add(map);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                {
                    connection.disconnect();
                }
                if (bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;*//*



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            adapter.updateResults(arrayList);

        }
    }
    */
/*class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return schoolNames.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflator = getLayoutInflater();
            convertView = layoutInflator.inflate(R.layout.bus_row_list, null);

            ImageView ivImage = (ImageView) convertView.findViewById(R.id.imageView);
            TextView tvText1 = (TextView) convertView.findViewById(R.id.tvSchoolName);

            ivImage.setImageResource(images[position]);
            tvText1.setText(schoolNames[position]);






            return convertView;
        }*//*

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
    }

*/
