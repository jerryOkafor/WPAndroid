package me.jerryhanks.parkgidi.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import me.jerryhanks.parkgidi.R;
import me.jerryhanks.parkgidi.auth.AuthActivity;
import me.jerryhanks.parkgidi.databinding.ActivityMainBinding;
import me.jerryhanks.parkgidi.map.MapsActivity;
import me.jerryhanks.parkgidi.util.ParkGidiUtil;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String FRAGTAG = "place_picker_frag";
    private static final String ACCOUNT_FRAG_TAG = "account_frg_tag";
    private static final String NAV_ITEM_POSITION = "com.jerryhanks.me.navitemposition";
    private static final String HOME_FRAG_TAG = "home_frag_tag";
    private FloatingActionButton requestParkingBtn;
    private ActionBarDrawerToggle drawerToggle;
    private ActivityMainBinding mainBinding;
    private int mNavItemPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mainBinding.toolbar);

        drawerToggle = new ActionBarDrawerToggle(
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

        ParkGidiUtil.showFragment(this, new FragmentHome(), HOME_FRAG_TAG);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mainBinding.drawer.closeDrawers();
        int id = item.getItemId();
        if (id == R.id.action_parking) {
            mNavItemPosition = 0;
            return true;
        } else if (id == R.id.action_history) {
            mNavItemPosition = 1;
        } else if (id == R.id.action_account) {
            mNavItemPosition = 2;
            ParkGidiUtil.showFragment(this, FragmentAccount.newInstance("", ""), ACCOUNT_FRAG_TAG);
            return true;
        } else if (id == R.id.action_help) {
            mNavItemPosition = 3;
            return true;
        } else if (id == R.id.action_setting) {
            mNavItemPosition = 3;
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
            return true;
        }


        return true;

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
            startActivity(MapsActivity.createIntent(this));
            toggleRequestButton(false);

        }
    }


    private void toggleRequestButton(boolean b) {
        requestParkingBtn.setEnabled(b);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt(NAV_ITEM_POSITION, mNavItemPosition);
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
