package me.jerryhanks.parkgidi.home;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import me.jerryhanks.parkgidi.data.ParkingSpace;
import me.jerryhanks.parkgidi.data.source.ParkGidiRepository;
import me.jerryhanks.parkgidi.util.schedulers.BaseSchedulerProvider;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Potencio on 5/5/2017. @ 2:17 PM
 * For ParkGidi
 */

public class HomePresenter implements HomeContract.Presenter {

    @NonNull
    private final ParkGidiRepository mDataRepository;

    @NonNull
    private final HomeContract.View mView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeDisposable mCompositeDisposables;

    public HomePresenter(
            @NonNull ParkGidiRepository mDataRepository,
            @NonNull HomeContract.View mView,
            @NonNull BaseSchedulerProvider mSchedulerProvider) {
        this.mDataRepository = checkNotNull(mDataRepository, "data repository can not be null");
        this.mView = checkNotNull(mView, "home view can not be null");
        this.mSchedulerProvider = checkNotNull(mSchedulerProvider, "scheduler provider can not be null");
        this.mCompositeDisposables = new CompositeDisposable();

        //set the presenter for the view
        mView.setPresenter(this);

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposables.clear();
    }

    @Override
    public void saveNewParkingSpace(@NonNull ParkingSpace space) {
        mDataRepository.saveParkingSpace(space);
    }
}
