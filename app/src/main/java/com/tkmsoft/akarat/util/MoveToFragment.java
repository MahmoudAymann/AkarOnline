package com.tkmsoft.akarat.util;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.tkmsoft.akarat.R;

/**
 * Created by MahmoudAyman on 10/27/2018.
 **/
public class MoveToFragment {

    private FragmentActivity fragmentActivity;

    public MoveToFragment(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    public void moveInMain(Fragment destinationFragment) {
        fragmentActivity.getFragmentManager().beginTransaction().replace(R.id.main_frame, destinationFragment).commit();
    }

    public void moveInLogin(Fragment fragment) {
        fragmentActivity.getFragmentManager().beginTransaction().replace(R.id.login_frame, fragment).commit();
    }
}
