package me.jerryhanks.parkgidi.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import me.jerryhanks.parkgidi.R;


/**
 * Created by Jerry < @Po10cio >  on 07/04/2017 for WPAndroid.
 * Copyright (C) Pacent Technologies Ltd.
 */

public class ParkGidiUtil {

    public static void attachFragment(AppCompatActivity activity, Fragment fragment) {
        FragmentManager fm = activity.getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }


    public static DatabaseReference getGeoFireRef() {

        return getBaseRef().child("squatting/geofire");
    }

    private static DatabaseReference getBaseRef() {

        return FirebaseDatabase.getInstance().getReference();
    }
}
