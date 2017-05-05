package me.jerryhanks.parkgidi.data.source;

import android.support.annotation.NonNull;

import me.jerryhanks.parkgidi.data.ParkingSpace;

/**
 * Created by Potencio on 5/4/2017. @ 8:18 PM
 * For ParkGidi
 */

public interface ParkGidiDataSource {

    void saveParkingSpace(@NonNull ParkingSpace space);
}
