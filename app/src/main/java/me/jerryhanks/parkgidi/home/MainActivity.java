package me.jerryhanks.parkgidi.home;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import me.jerryhanks.parkgidi.R;
import me.jerryhanks.parkgidi.auth.AuthActivity;
import me.jerryhanks.parkgidi.databinding.ActivityMainBinding;
import me.jerryhanks.parkgidi.map.MapsActivity;
import me.jerryhanks.parkgidi.util.ParkGidiUtil;
import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final int REQUEST_PLACE_PICKER = 123;
    private FloatingActionButton requestParkingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mainBinding.toolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                mainBinding.drawer,
                mainBinding.toolbar,
                R.string.action_drawe_open,
                R.string.action_drawe_close);

        mainBinding.drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        mainBinding.navigationView.setNavigationItemSelectedListener(this);

        requestParkingBtn = mainBinding.btnRequestParking;
        requestParkingBtn.setOnClickListener(this);

        //attach the posts frg
        ParkGidiUtil.attachFragment(this, new HomeFragment());


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_parking) {

        } else if (id == R.id.action_pages) {

        } else if (id == R.id.action_users) {

        } else if (id == R.id.action_logout) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            startActivity(new Intent(MainActivity.this, AuthActivity.class));
                            finish();
                        }
                    });
        }


        return false;
    }

    public static Intent createIntent(AuthActivity activity) {
        return new Intent(activity, MainActivity.class);
    }

    public static Intent createIntent(AuthActivity activity, IdpResponse response) {
        return createIntent(activity);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_request_parking) {
            startActivity(MapsActivity.creatIntent(this));

        }
    }

    private void launchPlacePicker() {
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            // Start the Intent by requesting a result, identified by a request code.
            startActivityForResult(intent, REQUEST_PLACE_PICKER);

            //disable fabb btn
            toggleRequestButton(false);


        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), this, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void toggleRequestButton(boolean b) {
        requestParkingBtn.setEnabled(b);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PLACE_PICKER) {
            // This result is from the PlacePicker dialog.

            // Enable the Fab request button
            toggleRequestButton(true);

            if (resultCode == Activity.RESULT_OK) {
                /* User has picked a place, extract data.
                   Data is extracted from the returned intent by retrieving a Place object from
                   the PlacePicker.
                 */
                final Place place = PlacePicker.getPlace(this, data);

                /* A Place object contains details about that place, such as its name, address
                and phone number. Extract the name, address, phone number, place ID and place types.
                 */
                final CharSequence name = place.getName();
                final CharSequence address = place.getAddress();
                final CharSequence phone = place.getPhoneNumber();
                final String placeId = place.getId();
                String attribution = PlacePicker.getAttributions(data);

                if (attribution == null) {
                    attribution = "";
                }

                // Print data to debug log
                Timber.d("Place selected: " + placeId + " (" + name.toString() + ")");


            } else {
                // User has not selected a place, hide the card.

            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
