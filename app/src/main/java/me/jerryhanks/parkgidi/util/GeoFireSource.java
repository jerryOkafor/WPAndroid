package me.jerryhanks.parkgidi.util;

import android.support.annotation.Nullable;

import com.firebase.geofire.GeoFire;

/**
 * Created by Potencio on 5/5/2017. @ 3:31 PM
 * For ParkGidi
 */

public class GeoFireSource {
    @Nullable
    private static GeoFire INSTANCE;

    /**
     * Returns GeoFire Instance or creates one if necessary
     */
    public static GeoFire getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GeoFire(ParkGidiUtil.getGeoFireRef());
        }

        return INSTANCE;
    }

    /**
     * Destroys the GeoFirInstance
     */
    public static void destroyInstnace() {
        INSTANCE = null;
    }
}
