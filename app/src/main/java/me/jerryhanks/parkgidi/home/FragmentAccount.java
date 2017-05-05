package me.jerryhanks.parkgidi.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import me.jerryhanks.parkgidi.R;
import me.jerryhanks.parkgidi.data.ParkingSpace;
import me.jerryhanks.parkgidi.data.model.Location;
import me.jerryhanks.parkgidi.data.source.ParkGidiRepository;
import me.jerryhanks.parkgidi.databinding.FragmentAccountBinding;
import me.jerryhanks.parkgidi.util.ParkGidiUtil;
import me.jerryhanks.parkgidi.util.schedulers.SchedulerProvider;
import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;


public class FragmentAccount extends Fragment implements View.OnClickListener, HomeContract.AccoutnView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_PLACE_PICKER = 101;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentAccountBinding mBinding;
    private TextInputLayout modelWrapper, numberPlateWrapper, apartmentWrapper, sqNumberWrapper;
    private TextInputEditText modelEd, numberPlateEd, apartmentEd, sqNumberEd;
    private CheckBox newCartOfsCheckBox, newSpacetOfsCheckBox;
    private boolean isLocationChosen = false;
    private Place mSelectedPlace;
    private HomeContract.Presenter mPresenter;
    private CardView selectedPlaceCard;
    private TextView placedetailTv;
    private TextView placeCoordTv;

    public FragmentAccount() {
        // Required empty public constructor
    }

    public static FragmentAccount newInstance(String param1, String param2) {
        FragmentAccount fragment = new FragmentAccount();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new HomePresenter(ParkGidiRepository.getInstance(), this, SchedulerProvider.getInstance());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);

        //set the onclick Listeners
        mBinding.topUpBtn.setOnClickListener(this);
        mBinding.paymentMethodLayout.setOnClickListener(this);
        mBinding.addACarLayout.setOnClickListener(this);
        mBinding.addASpaceLayout.setOnClickListener(this);
        mBinding.deleteAccountLayout.setOnClickListener(this);

        return mBinding.getRoot();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.top_up_btn) {
            //top up
        } else if (id == R.id.payment_method_layout) {
            //open payment method activity
        } else if (id == R.id.add_a_car_layout) {
            //add a new car
            showAddCarDialog();
        } else if (id == R.id.add_a_space_layout) {
            //open add new space Dialog
            showAddSpaceDialog();
        } else if (id == R.id.delete_account_layout) {
            //delete the users account
        } else if (id == R.id.loc_choice_btn || id == R.id.sel_place_inst_tv) {
            //show the Location Picker
            launchPlacePicker();

        }

    }

    private void showAddCarDialog() {

        MaterialDialog newCarDialog = new MaterialDialog.Builder(getContext())
                .limitIconToDefaultSize()
                .title(R.string.add_car_title)
                .customView(R.layout.new_car_custom_layout, false)
                .autoDismiss(false)
                .cancelable(false)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        String carModel = modelEd.getText().toString().trim();
                        String numberPlate = numberPlateEd.getText().toString().trim();
                        if (TextUtils.isEmpty(carModel)) {
                            modelWrapper.setError("Car model required!");
                            return;
                        }
                        if (TextUtils.isEmpty(numberPlate)) {
                            numberPlateWrapper.setError("Car number plate is required!");
                            return;
                        }

                        if (!newCartOfsCheckBox.isChecked()) {
                            Toast.makeText(getContext(), "Please check the box to continue", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //If ou are here it means that the fields are not empty
                        addANewCar(carModel, numberPlate);
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();

        modelWrapper = (TextInputLayout) newCarDialog.findViewById(R.id.car_model_wrapper);
        numberPlateWrapper = (TextInputLayout) newCarDialog.findViewById(R.id.car_number_wrapper);

        modelEd = (TextInputEditText) newCarDialog.findViewById(R.id.car_model_ed);
        numberPlateEd = (TextInputEditText) newCarDialog.findViewById(R.id.car_number_plate_ed);

        newCartOfsCheckBox = (CheckBox) newCarDialog.findViewById(R.id.new_car_tofs_check_box);

        //Use Rx to watch and Shoe Errror
        RxTextView.textChanges(modelEd)
                .map(new Function<CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.length() == 0;
                    }
                })
                .distinctUntilChanged()
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Timber.d("Unsubscribed");
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        if (aBoolean)
                            modelWrapper.setError("Car model is required!");
                        modelWrapper.setErrorEnabled(aBoolean);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.d(e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("Complete");

                    }
                });

        //Use Rx to watch and Shoe Error
        RxTextView.textChanges(numberPlateEd)
                .map(new Function<CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.length() == 0;
                    }
                })
                .distinctUntilChanged()
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Timber.d("Unsubscribed");
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        if (aBoolean)
                            numberPlateWrapper.setError("Car number plate is required!");
                        numberPlateWrapper.setErrorEnabled(aBoolean);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.d(e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("Complete");

                    }
                });


        newCarDialog.show();
    }

    private void showAddSpaceDialog() {

        MaterialDialog newSpaceDialog = new MaterialDialog.Builder(getContext())
                .limitIconToDefaultSize()
                .title(R.string.add_space_title)
                .customView(R.layout.new_space_custom_layout, true)
                .autoDismiss(false)
                .cancelable(false)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        String apartment = apartmentEd.getText().toString().trim();
                        String sqn = sqNumberEd.getText().toString().trim();

                        //get the location and other details

                        if (TextUtils.isEmpty(apartment)) {
                            apartmentWrapper.setError("Apartment Name is required!");
                            return;
                        }


                        if (!isLocationChosen) {
                            Toast.makeText(getContext(), "Please check the box to continue", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (!newSpacetOfsCheckBox.isChecked()) {
                            Toast.makeText(getContext(), "Check the Checkbox to continue", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!isLocationChosen) {
                            Toast.makeText(getContext(), "You must chose a location to continue", Toast.LENGTH_LONG).show();
                            return;
                        }

                        addANewSpace(mSelectedPlace, apartment, sqn);
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();

        apartmentWrapper = (TextInputLayout) newSpaceDialog.findViewById(R.id.apartment_wrapper);
        sqNumberWrapper = (TextInputLayout) newSpaceDialog.findViewById(R.id.sqn_number_wrapper);

        selectedPlaceCard = (CardView) newSpaceDialog.findViewById(R.id.selected_space_card);
        placedetailTv = (TextView) newSpaceDialog.findViewById(R.id.place_detail_tv);
        placeCoordTv = (TextView) newSpaceDialog.findViewById(R.id.place_coord_tv);

        apartmentEd = (TextInputEditText) newSpaceDialog.findViewById(R.id.apartment_ed);
        sqNumberEd = (TextInputEditText) newSpaceDialog.findViewById(R.id.sqn_number_ed);
        sqNumberEd.setText(ParkGidiUtil.generateSQN());

        newSpacetOfsCheckBox = (CheckBox) newSpaceDialog.findViewById(R.id.new_space_tofs_check_box);

        ImageButton locChoiceBtn = (ImageButton) newSpaceDialog.findViewById(R.id.loc_choice_btn);
        TextView locChoiceTv = (TextView) newSpaceDialog.findViewById(R.id.sel_place_inst_tv);
        locChoiceBtn.setOnClickListener(this);
        locChoiceTv.setOnClickListener(this);

        //Use Rx to watch and Shoe Errror
        RxTextView.textChanges(apartmentEd)
                .map(new Function<CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.length() == 0;
                    }
                })
                .distinctUntilChanged()
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Timber.d("Unsubscribed");
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        if (aBoolean)
                            apartmentWrapper.setError("Apartment name is required!");
                        apartmentWrapper.setErrorEnabled(aBoolean);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.d(e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("Complete");

                    }
                });

        newSpaceDialog.show();
    }

    private void launchPlacePicker() {
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(getActivity());
            // Start the Intent by requesting a result, identified by a request code.
            startActivityForResult(intent, REQUEST_PLACE_PICKER);

        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil
                    .showErrorDialogFragment(e.getConnectionStatusCode(),
                            getActivity(), this, REQUEST_PLACE_PICKER, null);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(getContext(), "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void addANewSpace(Place place, String apartmentName, String sQNumber) {
        ParkingSpace space = ParkingSpace.newBuilder()
                .withLocation(Location.newBuilder().fromPlace(place))
                .setApartmrntName(apartmentName)
                .setSQNumber(sQNumber)
                .setOwner(ParkGidiUtil.getUserId())
                .build();
        mPresenter.saveNewParkingSpace(space);

    }

    private void addANewCar(String carModel, String numberPlate) {
        Toast.makeText(getContext(), "I am here, i am adding a new Car to your Accounts!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter, "presenter can not be null");
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PLACE_PICKER) {
            if (resultCode == Activity.RESULT_OK) {
                mSelectedPlace = PlacePicker.getPlace(getContext(), data);
                placeCoordTv.setText(mSelectedPlace.getName().toString());
                placedetailTv.setText(String.format(
                        getString(R.string.format_visible_place),
                        mSelectedPlace.getId(),
                        mSelectedPlace.getAddress(),
                        mSelectedPlace.getPhoneNumber()));

                //make the place card Visible
                selectedPlaceCard.setVisibility(View.VISIBLE);

                //set the boolean flag as true
                isLocationChosen = true;
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
