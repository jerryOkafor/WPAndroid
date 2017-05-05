package me.jerryhanks.parkgidi.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;

import me.jerryhanks.parkgidi.data.ParkingSpace;
import me.jerryhanks.parkgidi.util.GeoFireSource;
import me.jerryhanks.parkgidi.util.ParkGidiUtil;

/**
 * Created by Potencio on 5/4/2017. @ 8:18 PM
 * For ParkGidi
 */

public class ParkGidiRepository implements ParkGidiDataSource {
    @Nullable
    private static ParkGidiRepository INSTANCE;

    @NonNull
    private final GeoFire mGeoFire;

    public ParkGidiRepository() {
        mGeoFire = GeoFireSource.getInstance();
    }

    @Override
    public void saveParkingSpace(@NonNull ParkingSpace space) {
        DatabaseReference reference = ParkGidiUtil.getParkingSpaceRef().push();
        String key = reference.getKey();
        space.setKey(key);
        reference.setValue(space);
        LatLng latLng = space.getLocation().getLatLng();
        mGeoFire.setLocation(key, new GeoLocation(latLng.latitude, latLng.longitude));
    }

    /**
     * Returns the single instance of this class, creating if it is neccessary
     */
    public static ParkGidiRepository getInstance() {

        if (INSTANCE == null) {

            INSTANCE = new ParkGidiRepository();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
