package me.jerryhanks.wpandroid;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import me.jerryhanks.wpandroid.databinding.ActivityMainBinding;
import me.jerryhanks.wpandroid.util.WPAndroidDemoUtil;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

        //attach the posts frg
        WPAndroidDemoUtil.attachFragment(this, new PostsFragment());


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_posts) {

        } else if (id == R.id.action_pages) {

        } else if (id == R.id.action_users) {

        }
        return false;
    }
}
