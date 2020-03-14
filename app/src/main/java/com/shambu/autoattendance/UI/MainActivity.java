package com.shambu.autoattendance.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pathsense.android.sdk.location.PathsenseLocationProviderApi;
import com.shambu.autoattendance.Interfaces.GeofencingServiceTogglerInterface;
import com.shambu.autoattendance.PathsenseGeofenceReceiver;
import com.shambu.autoattendance.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements GeofencingServiceTogglerInterface {

    private String TAG = MainActivity.class.getSimpleName();
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private Boolean mLocationAccessGranted = false;

    private LatLng latLng;

    private SharedPreferences pref;

    @BindView(R.id.btm_nav_view)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        NavController navController = Navigation.findNavController(this, R.id.navhost_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        iSServicesOK();

        getLocationPermissions();

        pref = getApplicationContext().getSharedPreferences("AutoAtt", 0);
        latLng = new LatLng(Double.valueOf(pref.getString("ClassRoomLat", "12.121212")),
                Double.valueOf(pref.getString("ClassRoomLng", "12.121212"))) ;

        if(latLng.latitude!=12.121212 && pref.getString("GeofencingService", "ON").equals("ON")){
            Log.d(TAG, "Creating GeoFence...");
            PathsenseLocationProviderApi api = PathsenseLocationProviderApi.getInstance(this);
            api.addGeofence("Classroom", latLng.latitude, latLng.longitude, 100, PathsenseGeofenceReceiver.class);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        latLng = new LatLng(Double.valueOf(pref.getString("ClassRoomLat", "12.121212")),
                Double.valueOf(pref.getString("ClassRoomLng", "12.121212"))) ;

        if(latLng.latitude!=12.121212 && pref.getString("GeofencingService", "ON").equals("ON")){
            Log.d(TAG, "Creating GeoFence...");
            PathsenseLocationProviderApi api = PathsenseLocationProviderApi.getInstance(this);
            api.addGeofence("Classroom", latLng.latitude, latLng.longitude, 100, PathsenseGeofenceReceiver.class);
        }
    }

    private void getLocationPermissions() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION)!=
        PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED ){
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationAccessGranted = false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE : {
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                            mLocationAccessGranted = true;
                            return;
                        }
                    }
                    mLocationAccessGranted = false;
                }
            }
        }
    }

    public boolean iSServicesOK(){
        Log.d(TAG, "Checking google services");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available== ConnectionResult.SUCCESS){
            Log.d(TAG, "Google play services is working");

            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "Resolvable error in google play services");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,
                    available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Log.d(TAG, "Map requests cannot be made");
        }
        return false;
    }


    @Override
    public void startGService() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("GeofencingService", "ON");
        editor.commit();
        PathsenseLocationProviderApi api = PathsenseLocationProviderApi.getInstance(this);
        api.addGeofence("Classroom", latLng.latitude, latLng.longitude, 100, PathsenseGeofenceReceiver.class);
    }

    @Override
    public void stopGService() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("GeofencingService", "OFF");
        editor.commit();
        PathsenseLocationProviderApi api = PathsenseLocationProviderApi.getInstance(this);
        api.removeGeofence("Classroom");
        api.destroy();
    }
}
