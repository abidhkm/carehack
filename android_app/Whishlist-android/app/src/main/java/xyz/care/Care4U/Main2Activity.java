package xyz.care.Care4U;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FloatingActionButton fab;
   static GoogleApiClient googleApiClient = null;
    final static String TAG = "Omnalista";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Default MyWish view




        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1234);
        } else {
            // Pre-Marshmallow
        }

        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {

                        Log.d(TAG, "Google play services Connection successful");
                        Toast.makeText(getApplicationContext(), "Google play services Connection successful", Toast.LENGTH_SHORT).show();

                        startMonitoring();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.d(TAG, "Google play services Connection suspended");
                        Toast.makeText(getApplicationContext(), "Google play services Connection suspended", Toast.LENGTH_SHORT).show();

                    }
                }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {

                Log.d(TAG, "Google play services Connection failed" + connectionResult.getErrorMessage());
                Toast.makeText(getApplicationContext(), "Google play services Connection failed", Toast.LENGTH_SHORT).show();
            }
        }).build();

        googleApiClient.connect();


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //add a fragment
        myWishFragment myFragment = new myWishFragment();
        fragmentTransaction.add(R.id.main2, myFragment);


        fragmentTransaction.commit();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(view.GONE);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                //add a fragment
                AddNew myFragment = new AddNew();
                fragmentTransaction.add(R.id.main2, myFragment);
                fragmentTransaction.addToBackStack("fragment");
                fragmentTransaction.commit();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        TextView text = (TextView) view.findViewById(R.id.email);
        SharedPreferences prefs = getSharedPreferences("whishlist", MODE_PRIVATE);
        text.setText(prefs.getString("email", "No name defined"));
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences prefs = getSharedPreferences("whishlist", MODE_PRIVATE);
            prefs.edit().clear().commit();
            startActivity(new Intent(Main2Activity.this, LoginActivity.class));
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_view_wishes) {
            //
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            View view1 = findViewById(R.id.main2);
            fab.setVisibility(view1.VISIBLE);
            //add a fragment
            myWishFragment myFragment = new myWishFragment();
            fragmentTransaction.replace(R.id.main2, myFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_browsewish) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            View view1 = findViewById(R.id.main2);
            fab.setVisibility(view1.GONE);
            //add a fragment
            browseWishFragment myFragment = new browseWishFragment();
            fragmentTransaction.replace(R.id.main2, myFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_add_wish) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            View view1 = findViewById(R.id.main2);
            fab.setVisibility(view1.GONE);
            //add a fragment
            AddNew myFragment = new AddNew();
            fragmentTransaction.replace(R.id.main2, myFragment);
            fragmentTransaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        int response = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (response != ConnectionResult.SUCCESS) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, response, 1).show();
            Log.d(TAG, "Google Play Services not available - show dialog to user ");
            Toast.makeText(getApplicationContext(), "Google Play Services not available - show dialog to user ", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "Google Play Services available. No action needed");
            Toast.makeText(getApplicationContext(), "Google Play Services available. No action needed", Toast.LENGTH_SHORT).show();
        }
    }

  static  void startMonitoring() {

        try {
            Log.d(TAG, "Start Monitoring");
           // Toast.makeText(AppController.getAppContext(), "Start Monitoring", Toast.LENGTH_SHORT).show();

            LocationRequest locationRequest = LocationRequest.create().
                    setInterval(10000).
                    setFastestInterval(5000).
                    setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            if (ActivityCompat.checkSelfPermission(AppController.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AppController.getAppContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {


                    Log.d(TAG, "Location Update Lat/Lng" + location.getLatitude() + "/" + location.getLongitude());
                   // Toast.makeText(AppController.getAppContext(), "Location Update Lat/Lng" + location.getLatitude() + "/" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Start Monitoring Exception" + e.toString());
            Toast.makeText(AppController.getAppContext(), "Start Monitoring Exception" + e.toString(), Toast.LENGTH_SHORT).show();
        }



    }

   static void addGeofence(double lat, double lng, final String id) {

    //   Toast.makeText(AppController.getAppContext(),id+lat+lng,Toast.LENGTH_LONG).show();
       Log.d(TAG,id+" lat "+lat+"Long "+lng);
        Geofence geofence = new Geofence.Builder().
                setRequestId(id).
                setCircularRegion(lat, lng, 1000).
                setExpirationDuration(Geofence.NEVER_EXPIRE).
                setNotificationResponsiveness(1000).
                setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER).
                build();

        GeofencingRequest geofencingRequest = new GeofencingRequest.Builder().
                setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER).
                addGeofence(geofence).build();

        Intent intent = new Intent(AppController.getAppContext(), GeofenceService.class);
        PendingIntent pendingIntent = PendingIntent.getService(AppController.getAppContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (!googleApiClient.isConnected()) {

        } else {
            if (ActivityCompat.checkSelfPermission(AppController.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.GeofencingApi.addGeofences(googleApiClient, geofencingRequest, pendingIntent).
                    setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {

                           //     Toast.makeText(AppController.getAppContext(), "Geofence added "+id, Toast.LENGTH_LONG).show();

                                Log.d(TAG,"Geofence added successfully");

                            } else {

                                Toast.makeText(AppController.getAppContext(), "Geofence error in adding "+id, Toast.LENGTH_LONG).show();

                                Log.d(TAG,"Geofence error in adding ");
                            }
                        }
                    });
        }
    }


}
