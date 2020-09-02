package com.vendor.transportation_driver;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;


public class LocationActivity extends ActionBarActivity implements  LocationListener {
    String busId, driverId;
    GoogleMap googleMap;
    double lat, lng;
    LocationManager locationManager;
    SharedPreferences sharedPreferences;
    String response = null;
    String res, latitude, longitude;
    ProgressDialog progressDialog;
    private static String SIGNUP_URL = "http://trackmee.in/api/driver/update";
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_details_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.navigation);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sharedPreferences = getSharedPreferences("PREFNAME", MODE_APPEND);
        driverId = sharedPreferences.getString("driverId", null);
        savedInstanceState = getIntent().getExtras();
        busId = savedInstanceState.getString("busId");
        android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        googleMap = supportMapFragment.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ContextCompat.checkSelfPermission(LocationActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            googleMap.setMyLocationEnabled(true);
            locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria,true);
            Location location = locationManager.getLastKnownLocation(provider);


            if(location!=null)
            {
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(provider, 20, 1, this);
        } else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(LocationActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }

        //simpleHttpClient = new SimpleHttpClient();
        /*googleMap.setMyLocationEnabled(true);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);


        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(provider, 20, 1, this);*/


        CheckEnableGPS();

        //new AsyncCustomerhotspot().execute();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            googleMap.setMyLocationEnabled(true);
            locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria,true);
            Location location = locationManager.getLastKnownLocation(provider);


            if(location!=null)
            {
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(provider, 20, 1, this);
        } else {
            // Permission was denied. Display an error message.
        }
    }

    @Override
    public void onLocationChanged(Location location) {


        double lat = location.getLatitude();
        double lng = location.getLongitude();
        latitude = String.valueOf(lat);
        longitude = String.valueOf(lng);

        LatLng latLng = new LatLng(lat, lng);
       /* MarkerOptions markerOptions = new MarkerOptions();
        googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("Your here")).showInfoWindow();*/
        // Marker mark = googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("You are here!").snippet("Consider yourself located"));
        googleMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_primary));
        googleMap.addMarker(markerOptions);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        callapi();
    //    Toast.makeText(getApplicationContext(),latitude + longitude,Toast.LENGTH_LONG).show();
        //new JsonData().execute();

        ;
        //new AsyncCustomerhotspot().execute();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public class JsonData extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  progressDialog = new ProgressDialog(LocationActivity.this);
            progressDialog.setMessage("Please wait data is loading");
            progressDialog.setCancelable(false);
            progressDialog.show();*/
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("driverId", driverId));
            postParameters.add(new BasicNameValuePair("busId", busId));
            postParameters.add(new BasicNameValuePair("latitude", latitude));
            postParameters.add(new BasicNameValuePair("longitude", longitude));
            System.out.println("?????????" + postParameters);

            //arrayList.clear();
            try {
                response = SimpleHttpClient.executeHttpPost(SIGNUP_URL, postParameters);
                res = response.toString();
                System.out.println("Responce is :" + res);
                Toast.makeText(getApplicationContext(),"REsponce is :"+res,Toast.LENGTH_LONG).show();
                /*JSONArray parentArray = new JSONArray(res);
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    busId = finalObject.getString("busId");
                    busNo = finalObject.getString("busNumber");

                    System.out.println("bus id is :" + busId + "busNo is :" + busNo);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("busId", busId);
                   // map.put("busNo", busNo);
                   // arrayList.add(map);
                }*/
            } catch (Exception e) {
                e.printStackTrace();
                String errorMsg = e.getMessage();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            /*if (progressDialog.isShowing())
                progressDialog.dismiss();*/
            //adapter.updateResults(arrayList);
           /* googleMap.clear();

            LatLng latLng = new LatLng(lat, lng);

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng).title("You are here");
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
            googleMap.addMarker(markerOptions);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));*/

        }
    }
    private void callapi() {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

            if (netInfo == null || !netInfo.isConnected()
                    || !netInfo.isAvailable()) {
                // Toast.makeText(getApplicationContext(),"No Internet Found",Toast.LENGTH_LONG).show();
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setMessage("No Internet Found");
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
                    new JsonData().execute();
                } catch (Exception e) {
                    Log.d("Tag" + "Exception", e.toString());
                }
            }

        } catch (Exception e) {
            Log.d("Tag", e.toString());
        }
    }

    private void CheckEnableGPS() {
        String provider = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.equals("")) {
            //GPS Enabled
     /*Toast.makeText(getActivity(), "GPS Enabled: " + provider,
       Toast.LENGTH_LONG).show();*/
        } else {
     /*Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);*/

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("GPS not enabled, Please Enable your GPS");
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //this will navigate user to the device location settings screen
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            dialog.show();
        }
    /*@Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Logout alertdFragment = new Logout();
        alertdFragment.show(fragmentManager, "Exit");
        //super.onBackPressed();
    }*/
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
