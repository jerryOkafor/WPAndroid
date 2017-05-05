package me.jerryhanks.parkgidi.home;

import android.support.annotation.NonNull;

import me.jerryhanks.parkgidi.BasePresenter;
import me.jerryhanks.parkgidi.BaseView;
import me.jerryhanks.parkgidi.data.ParkingSpace;

/**
 * Created by Potencio on 5/5/2017. @ 2:16 PM
 * For ParkGidi
 */

public class HomeContract {
    interface View extends BaseView<Presenter> {
    }

    interface AccoutnView extends View {

    }

    interface Presenter extends BasePresenter {

        void saveNewParkingSpace(@NonNull ParkingSpace space);
    }
}
