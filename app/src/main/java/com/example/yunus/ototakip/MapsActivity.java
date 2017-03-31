package com.example.yunus.ototakip;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,GoogleMap.OnMarkerClickListener,
        LocationListener,DirectionCallback {

    GoogleApiClient mGoogleApiClient;
    private String serverKey = "AIzaSyDDKmbLMisjUnZT_CmYcQmLauwvOh-wpKA";
    Location sonKonum;
    CoordinatorLayout rootLayout;
    Marker suAnKonumMarker,bakim,muayene,sigorta;
    LocationRequest yerIstek;
    private GoogleMap mMap;
    public LatLng suanKonumumuz;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference ref = database.child("bakim_yerleri");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        rootLayout = (CoordinatorLayout) findViewById(R.id.mapCoordinatorLayout);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else
        {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        if (Yakinimdakiler.bakim.isChecked())
        {


            ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s)
                {
                    Snackbar.make(rootLayout, "Bakım istasyonları haritaya yerleştiriliyor...", Snackbar.LENGTH_LONG).show();
                    mMap.addMarker(new MarkerOptions().
                            position(new LatLng(Double.parseDouble(dataSnapshot.child("enlem").getValue().toString()),
                                    Double.parseDouble(dataSnapshot.child("boylam").getValue().toString())))
                             .title(dataSnapshot.child("ad").getValue().toString())
                             .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker)));
                    Log.v("Firebase Data Alma", "başarılı");


                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

        else if(Yakinimdakiler.muayene.isChecked())
        {

        }

        else if(Yakinimdakiler.sigorta.isChecked())
        {


        }
        mMap.setOnMarkerClickListener(this);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        yerIstek = new LocationRequest();
        yerIstek.setInterval(1000);
        yerIstek.setFastestInterval(1000);
        yerIstek.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, yerIstek, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }



    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // izin verilmiş. Yapman gerekeni yap
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else
                {

                    // İzin verilmedi
                    Toast.makeText(this, "İzin verilmedi.", Toast.LENGTH_LONG).show();
                }
                return;

            }

            //Ek case yapıları eklenip, diğer izinler için kullanılabilir.

        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        sonKonum = location;
        if (suAnKonumMarker != null)
        {
            suAnKonumMarker.remove();
        }

        //O anki konumu markerla ve yerleştir
        suanKonumumuz = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(suanKonumumuz);
        markerOptions.title("Şu anki konumunuz");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker));
        suAnKonumMarker = mMap.addMarker(markerOptions);
        /*mMap.moveCamera(CameraUpdateFactory.newLatLng(suanKonumumuz));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));*/

        //stop location updates
        if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }



    }




    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {

    }

    @Override
    public void onDirectionFailure(Throwable t)
    {
        CoordinatorLayout rootLayout = (CoordinatorLayout) findViewById(R.id.mapCoordinatorLayout);
        Snackbar.make(rootLayout, "Rota oluşturulamadı."+t.getMessage(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onMarkerClick(final Marker marker)
    {
       /* if (marker.equals(bakim)) {
            Snackbar.make(rootLayout, "Rota oluşturuluyor...", Snackbar.LENGTH_LONG).show();
            GoogleDirection.withServerKey(serverKey)
                    .from(suanKonumumuz)
                    .to(bakim.getPosition())
                    .transportMode(TransportMode.DRIVING)
                    .execute(this);

        }*/
        return false;
    }




}
