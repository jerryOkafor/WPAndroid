package me.jerryhanks.parkgidi.map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import me.jerryhanks.parkgidi.R;
import me.jerryhanks.parkgidi.util.GeoFireSource;
import me.jerryhanks.parkgidi.util.ParkGidiUtil;
import timber.log.Timber;

public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GeoQueryEventListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    // The entry point to Google Play services, used by the Places API and Fused Location Provider.
    private GoogleApiClient mGoogleApiClient;

    // A default location (Lagos, Nigeria) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(6.465422, 3.406448);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private GoogleMap mMap;
    private GeoQuery mGeoQuery;
    private HashMap<String, Marker> mMarkers;
    private Circle mSearchCircle;
    private CameraPosition mCameraPosition;
    private GeoFire mGeoFire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        //Retrieve the view that holds the map
        setContentView(R.layout.maps_activity);

        // Build the Play services client for use by the Fused Location Provider and the Places API.
        // Use the addApi() method to request the Google Places API and the Fused Location Provider.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();

        //Build the GeoFire Connections here
        mGeoFire = GeoFireSource.getInstance();

        //init Geo Query so that Listener does not throw Error onStart()
        mGeoQuery = mGeoFire.queryAtLocation(
                new GeoLocation(mDefaultLocation.latitude,
                        mDefaultLocation.longitude), 1.6);

        // setup markers(List of all available parking Spaces)
        mMarkers = new HashMap<String, Marker>();


    }

    @Override
    protected void onStart() {
        super.onStart();
        //set the query Listener for GeoQuery
        mGeoQuery.addGeoQueryEventListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //remove all listeners here
        mGeoQuery.removeAllListeners();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        //set the map description
        mMap.setContentDescription("Parkgidi Map!");

        // Use a custom info window adapter to handle multiple lines of text in the
        // info window contents.
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {
                return null;
            }


            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the layouts for the info window, title and snippet.
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents,
                        (FrameLayout) findViewById(R.id.map), false);

                TextView title = ((TextView) infoWindow.findViewById(R.id.title));
                title.setText(marker.getTitle());

                TextView snippet = ((TextView) infoWindow.findViewById(R.id.snippet));
                snippet.setText(marker.getSnippet());

                return infoWindow;
            }
        });

        //Set an Info Click Listener for the InfoWindow
        //So that we can extract the details and process the
        //parking request
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                ShowYesOrNoDialog(marker);
            }
        });


        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();


        //add the Circle
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()))
                .radius(1000)
                .strokeWidth(10)
//                .strokeColor(Color.GREEN)
                .strokeColor(Color.argb(55, 0, 0, 0))
//                .fillColor(Color.argb(128, 255, 0, 0))
                .fillColor(Color.argb(40, 255, 0, 255))
                .clickable(false);
        mSearchCircle = mMap.addCircle(circleOptions);

        //Add a marker in somewhere in Lagos and move the camera.
        LatLng sydney = new LatLng(6.465422, 3.406448);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Name of The Parking Space"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        LatLng latLngCenter = new LatLng(mDefaultLocation.latitude, mDefaultLocation.longitude);


    }

    private void ShowYesOrNoDialog(Marker marker) {
        new MaterialDialog.Builder(this)
                .title(R.string.title_parking_request)
                .content(R.string.content_parking_request)
                .positiveText(R.string.yes)
                .negativeText(R.string.cancel)
                .neutralText(R.string.view_details)
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //Show the User the details of the parking Space
                        Timber.d("I will Show you the parking Space Details");
                        dialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Timber.d("Positive Button Clicked");
                        //Do something with the marker
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Timber.d("Negative Button Clicked");
                        //Simple dismiss the Dialog
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, MapsActivity.class);
    }

    @Override
    public void onKeyEntered(String key, GeoLocation location) {
        Timber.d(String.format("Key %s entered the search area at [%f,%f]",
                key, location.latitude, location.longitude));

        if (mMap != null) {
            MarkerOptions markerOption = new MarkerOptions()
                    .position(new LatLng(location.latitude, location.latitude));
            Marker marker = mMap.addMarker(markerOption);
            mMarkers.put(key, marker);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Save the Camera Position and the Last Known location
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }


    @Override
    public void onKeyExited(String key) {
        Timber.d(String.format("Key %s is no longer in the search area", key));
        //remove any old marker
        Marker marker = mMarkers.get(key);
        if (marker != null) {
            marker.remove();
            mMarkers.remove(key);
        }

    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {
        System.out.println(String.format("Key %s moved within the search area to [%f,%f]", key,
                location.latitude, location.longitude));

        //simply animate the Marker to the new point
        Marker marker = mMarkers.get(key);
        if (marker != null) {
            animateMarkerTo(marker, location.latitude, location.longitude);
        }

    }

    @Override
    public void onGeoQueryReady() {
        Timber.d("All initial data has been loaded and events have been fired!");
    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        System.err.println("There was an error with this query: " + error);
    }


    // Animation handler for old APIs without animation support
    private void animateMarkerTo(final Marker marker, final double lat, final double lng) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long DURATION_MS = 3000;
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final LatLng startPosition = marker.getPosition();
        handler.post(new Runnable() {
            @Override
            public void run() {
                float elapsed = SystemClock.uptimeMillis() - start;
                float t = elapsed / DURATION_MS;
                float v = interpolator.getInterpolation(t);

                double currentLat = (lat - startPosition.latitude) * v + startPosition.latitude;
                double currentLng = (lng - startPosition.longitude) * v + startPosition.longitude;
                marker.setPosition(new LatLng(currentLat, currentLng));

                // if animation is not finished yet, repeat
                if (t < 1) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Google Api Client has connected, prepare the map fragment here
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Timber.d("Play services connection suspended");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.d("Play services connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        if (mLocationPermissionGranted) {
            mLastKnownLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);

            // creates a new query around the users last know location with a radius of 1.6 kilometers
            mGeoQuery = mGeoFire.queryAtLocation(
                    new GeoLocation(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude()), 1.6);
        }

        // Set the map's camera position to the current location of the device.
        if (mCameraPosition != null) {
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
        } else if (mLastKnownLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
        } else {
            Timber.d("Current location is null. Using defaults.");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }


    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }
    }
}
