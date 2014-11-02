package com.example.mapapp;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;


public class MainActivity extends Activity implements LocationListener {
	// Google Map
    private GoogleMap googleMap;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	/* StrictMode is an android developer tool which is used to catch accidental disk and 
    	 * network accesses on the application's main thread, where UI operations and animations take place. */
    	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		        .detectDiskReads()
		        .detectDiskWrites()
		        .detectNetwork()   
		        .penaltyLog()
		        .build());
    	StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		        .detectLeakedSqlLiteObjects()
		        .detectLeakedClosableObjects()
		        .penaltyLog()
		        .penaltyDeath()
		        .build());
    	/* http://developer.android.com/reference/android/os/StrictMode.html */
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        LocationManager locationManager;
    	String provider;
    	// Initialize the map.
    	googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    	
        // Get the Location System Service
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        // Get the best network provider
        provider = locationManager.getBestProvider(new Criteria(), false); 
        // Check whether a network provider is available for the app
        if(provider!=null && !provider.equals("")){        	 
            // get the last known location available with the network provider
            Location location = locationManager.getLastKnownLocation(provider);
            // request location updates to be sent to LocationListener
            locationManager.requestLocationUpdates(provider, 20000, 1, this);
            // Check whether an updated location is returned by system before updating the text
            if(location!=null) {            	
            	// Once the map is initialized, call the onLocationChanged method of the LocationListener and pass the retrieved location to it.
            	onLocationChanged(location);
            } else {
            	// Display error if location is not available
                Toast.makeText(getBaseContext(), "If you are using emulator, switch to DDMS and send co-ordinates", Toast.LENGTH_SHORT).show(); 
            }
        } else {
            Toast.makeText(getBaseContext(), "Please enable GPS", Toast.LENGTH_SHORT).show();
        }        
 
    }
 
    @Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
    	if (googleMap == null) {
			// If initialization of the Map failed, display an error
            Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
        } else {
        	// Create a new pair of Latitude and Longitude and construct them as a degree
        	LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
    		// Create a new marker to point the current location on the map.
            Marker myLocation = googleMap.addMarker(new MarkerOptions().position(current).title("Current Location"));
            // Zoom the camera to the current location.
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
        }
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
 
}
