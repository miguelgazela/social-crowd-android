package pt.up.fe.socialcrowd.activities;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.API.Request;
import pt.up.fe.socialcrowd.API.RequestException;
import pt.up.fe.socialcrowd.logic.BaseEvent;
import pt.up.fe.socialcrowd.logic.GPSCoords;
import pt.up.fe.socialcrowd.managers.DataHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MarkerOptions;

public class MarkerActivity extends FragmentActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener{
	/**
	 * Note that this may be null if the Google Play services APK is not available.
	 */
	private GoogleMap mMap;
	private Geocoder geo;
	private  LatLng loc;
	private LocationClient mLocationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_location);
		geo = new Geocoder(getApplicationContext());
		if(!Geocoder.isPresent()){
			Log.d("GEO", "NOT PRESENT!");
		}
		 final Builder bounds = new LatLngBounds.Builder();
		try {
			ArrayList<BaseEvent> events = Request.getEvents();
			for(int i = 0; i < events.size(); i++){
				GPSCoords gps = events.get(i).getLocation().getGps();
				if(gps != null){
					mMap.addMarker(new MarkerOptions()
	                .position(new LatLng(gps.getLatitude(),gps.getLongitude()))
	                .title(events.get(i).getLocation().getText()));
					  bounds.include(new LatLng(gps.getLatitude(),gps.getLongitude()));
				}
			}

			final View mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();
			if (mapView.getViewTreeObserver().isAlive()) {
	            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
	                @SuppressWarnings("deprecation") // We use the new method when supported
	                @SuppressLint("NewApi") // We check which build version we are using.
	                @Override
	                public void onGlobalLayout() {
	                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
	                      mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	                    } else {
	                      mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
	                    }
	                    LatLngBounds bound = bounds.build();
	                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bound, 50));
	                }
	            });
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
		setUpLocationClientIfNeeded();
		mLocationClient.connect();
	}

	/**
	 * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
	 * installed) and the map has not already been instantiated.. This will ensure that we only ever
	 * call {@link #setUpMap()} once when {@link #mMap} is not null.
	 * <p>
	 * If it isn't installed {@link SupportMapFragment} (and
	 * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
	 * install/update the Google Play services APK on their device.
	 * <p>
	 * A user can return to this FragmentActivity after following the prompt and correctly
	 * installing/updating/enabling the Google Play services. Since the FragmentActivity may not have been
	 * completely destroyed during this process (it is likely that it would only be stopped or
	 * paused), {@link #onCreate(Bundle)} may not be called again so we should call this method in
	 * {@link #onResume()} to guarantee that it will be called.
	 */
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
					.getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	/**
	 * This is where we can add markers or lines, add listeners or move the camera. In this case, we
	 * just add a marker near Africa.
	 * <p>
	 * This should only be called once and when we are sure that {@link #mMap} is not null.
	 */
	private void setUpMap() {
		setUpLocationClientIfNeeded();
		// Pan to see all markers in view.
		// Cannot zoom to bounds until the map has a size.
		mMap.setMyLocationEnabled(true);
		//mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,12));
	}

	private void setUpLocationClientIfNeeded() {
		if (mLocationClient == null) {
			mLocationClient = new LocationClient(getApplicationContext(), this, this);
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		LatLng myLoc = new LatLng(mLocationClient.getLastLocation().getLatitude(),
				mLocationClient.getLastLocation().getLongitude());
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLoc,11));
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onLocationChanged(Location arg0) {


	}
}
