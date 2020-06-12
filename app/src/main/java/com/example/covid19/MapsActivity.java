package com.example.covid19;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.facebook.login.LoginManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public String latitude;
    public String longitude;
    private GoogleMap mMap;
    Button dashboard;
    LoginManager loginManager;
    private DatabaseReference databaseReference;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    TextView tvc;
    private EditText edittextlatitude;
    private EditText edittextlongitude;
    Button stasticsbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        dashboard = findViewById(R.id.button5);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intologin = new Intent(MapsActivity.this, Navegation.class);
                startActivity(intologin);
            }
        });

        edittextlatitude = findViewById(R.id.etLatitude);
        edittextlatitude.setEnabled(false);
        edittextlongitude = findViewById(R.id.etLongitude);
        edittextlongitude.setEnabled(false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Location");

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        tvc = findViewById(R.id.textView4);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String datatbaseLatitude = Objects.requireNonNull(dataSnapshot.child("Latitude").getValue()).toString().substring(1, Objects.requireNonNull(dataSnapshot.child("Latitude").getValue()).toString().length() - 1);
                    String datatbaseLonditude = Objects.requireNonNull(dataSnapshot.child("Longitude").getValue()).toString().substring(1, Objects.requireNonNull(dataSnapshot.child("Longitude").getValue()).toString().length() - 1);

                    String[] stringLat = datatbaseLatitude.split(", ");
                    Arrays.sort(stringLat);
                    latitude = stringLat[stringLat.length - 1].split("=")[1];

                    String[] stringLong = datatbaseLonditude.split(",");
                    Arrays.sort(stringLong);
                    longitude = stringLong[stringLong.length - 1].split("=")[1];
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                databaseReference.child("Latitude").setValue(edittextlatitude.getText().toString());
//                databaseReference.child("Longitude").setValue(edittextlongitude.getText().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        stasticsbtn = findViewById(R.id.button6);
        stasticsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intostatics = new Intent(MapsActivity.this, statistics.class);
                startActivity(intostatics);
            }
        });

//        insertbtn=findViewById(R.id.button6);
//        insertbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                FirebaseDatabase database = FirebaseDatabase.getInstance();
////                DatabaseReference myRef = database.getReference("location");
////                myRef.child("latitude").setValue(edittextlatitude.getText().toString());
////                myRef.child("longitude").setValue(edittextlongitude.getText().toString());
//            }
//        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.style_json)));

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        locationListener = new LocationListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onLocationChanged(Location location) {
                try {
                    edittextlatitude.setText(Double.toString(location.getLatitude()));
                    edittextlongitude.setText(Double.toString(location.getLongitude()));
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                    Marker m = mMap.addMarker(new MarkerOptions().position(latLng).title(location.getLatitude() + "," + location.getLongitude()));
//                  mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10.0f));
//ahmedabad
                    final Circle circle1 = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(23.0225, 72.5714))
                            .radius(10000)
                            .strokeColor(Color.RED)
                            .fillColor(Color.DKGRAY));
//patan
                    final Circle circle2 = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(23.8509506, 72.1195838))
                            .radius(10000)
                            .strokeColor(Color.RED)
                            .fillColor(Color.DKGRAY));
//near mehsana
                    final Circle circle3 = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(23.4017148, 71.8076555))
                            .radius(10000)
                            .strokeColor(Color.RED)
                            .fillColor(Color.DKGRAY));
//mumbai
                    final Circle circle4 = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(19.0760, 72.8777))
                            .radius(60000)
                            .strokeColor(Color.RED)
                            .fillColor(Color.DKGRAY));
//surat
                    final Circle circle5 = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(21.1702, 72.8311))
                            .radius(1000)
                            .strokeColor(Color.RED)
                            .fillColor(Color.DKGRAY));
//hydrabad
                    final Circle circle6 = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(17.3850, 78.4867))
                            .radius(20000)
                            .strokeColor(Color.RED)
                            .fillColor(Color.DKGRAY));
//delhi
                    final Circle circle7 = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(28.7041, 77.1025))
                            .radius(10000)
                            .strokeColor(Color.RED)
                            .fillColor(Color.DKGRAY));
//jodhpur
                    final Circle circle8 = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(26.2389, 73.0243))
                            .radius(10000)
                            .strokeColor(Color.RED)
                            .fillColor(Color.DKGRAY));
//jaipur
                    final Circle circle9 = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(26.9124, 75.7873))
                            .radius(20000)
                            .strokeColor(Color.RED)
                            .fillColor(Color.DKGRAY));
//udaipur
                    final Circle circle10 = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(26.7125, 75.5954))
                            .radius(10000)
                            .strokeColor(Color.RED)
                            .fillColor(Color.DKGRAY));


                    float[] distance1 = new float[2];
                    Location.distanceBetween(m.getPosition().latitude, m.getPosition().longitude,
                            circle1.getCenter().latitude, circle1.getCenter().longitude, distance1);

                    float[] distance2 = new float[2];
                    Location.distanceBetween(m.getPosition().latitude, m.getPosition().longitude,
                            circle2.getCenter().latitude, circle2.getCenter().longitude, distance2);

                    float[] distance3 = new float[2];
                    Location.distanceBetween(m.getPosition().latitude, m.getPosition().longitude,
                            circle3.getCenter().latitude, circle3.getCenter().longitude, distance3);

                    float[] distance4 = new float[2];
                    Location.distanceBetween(m.getPosition().latitude, m.getPosition().longitude,
                            circle4.getCenter().latitude, circle4.getCenter().longitude, distance4);

                    float[] distance5 = new float[2];
                    Location.distanceBetween(m.getPosition().latitude, m.getPosition().longitude,
                            circle5.getCenter().latitude, circle5.getCenter().longitude, distance5);

                    float[] distance6 = new float[2];
                    Location.distanceBetween(m.getPosition().latitude, m.getPosition().longitude,
                            circle6.getCenter().latitude, circle6.getCenter().longitude, distance6);

                    float[] distance7 = new float[2];
                    Location.distanceBetween(m.getPosition().latitude, m.getPosition().longitude,
                            circle7.getCenter().latitude, circle7.getCenter().longitude, distance7);

                    float[] distance8 = new float[2];
                    Location.distanceBetween(m.getPosition().latitude, m.getPosition().longitude,
                            circle8.getCenter().latitude, circle8.getCenter().longitude, distance8);

                    float[] distance9 = new float[2];
                    Location.distanceBetween(m.getPosition().latitude, m.getPosition().longitude,
                            circle9.getCenter().latitude, circle9.getCenter().longitude, distance9);

                    float[] distance10 = new float[2];
                    Location.distanceBetween(m.getPosition().latitude, m.getPosition().longitude,
                            circle10.getCenter().latitude, circle10.getCenter().longitude, distance10);

                    if (!(distance10[0] < circle10.getRadius() || distance9[0] < circle9.getRadius() || distance8[0] < circle8.getRadius() || distance7[0] < circle7.getRadius() || distance6[0] < circle6.getRadius() || distance5[0] < circle5.getRadius() || distance4[0] < circle4.getRadius() || distance1[0] < circle1.getRadius() || distance2[0] < circle2.getRadius() || distance3[0] < circle3.getRadius())) {
                        tvc.setText("Not Inside Danger Zone");
                        m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    } else {
                        tvc.setText("Inside Danger Zone");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            LoginManager.getInstance().logOut();
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to log out ", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void onStop() {
        LoginManager.getInstance().logOut();
        super.onStop();
    }
}
