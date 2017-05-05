package me.jerryhanks.parkgidi.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    public static String generateSQN() {
        return "SQ-12345677";
    }

    public static void showFragment(AppCompatActivity activity, Fragment initialFrag, String tag) {
        //attach the posts frg
        FragmentManager fm = activity.getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(tag);

        if (fragment == null) {
            FragmentTransaction transaction = fm.beginTransaction();
            fragment = initialFrag;
            transaction.add(R.id.fragment_container, fragment, tag);
            transaction.commit();
        }
    }

    public static DatabaseReference getParkingSpaceRef() {
        return getBaseRef().child("parkingSpaces");
    }

    public static String getUserId() {
        return getFirebaseUser().getUid();
    }

    public static FirebaseUser getFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
