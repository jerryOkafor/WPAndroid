package me.jerryhanks.parkgidi.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import me.jerryhanks.parkgidi.R;
import me.jerryhanks.parkgidi.databinding.FragmentAccountBinding;


public class FragmentAccount extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentAccountBinding mBinding;

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
        } else if (id == R.id.delete_account_layout) {
            //delete the users account
        }

    }

    private void showAddCarDialog() {
        new MaterialDialog.Builder(getContext())
                .limitIconToDefaultSize()
                .title(R.string.add_car_title)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }
                })
                .checkBoxPromptRes(R.string.agree_to_terms_of_service, false, null)
                .show();
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
