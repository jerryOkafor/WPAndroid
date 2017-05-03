package me.jerryhanks.parkgidi.account;

import android.os.Bundle;

import me.jerryhanks.parkgidi.SingleFragmentActivity;
import me.jerryhanks.parkgidi.util.ParkGidiUtil;

public class AccountActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParkGidiUtil.attachFragment(this, AccountFragment.newInstance("", ""));
    }
}
