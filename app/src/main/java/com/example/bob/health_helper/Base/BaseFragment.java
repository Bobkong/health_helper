package com.example.bob.health_helper.Base;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
    public void navigateTo(Class to) {
        Intent intent = new Intent(getActivity(), to);
        startActivity(intent);
    }
}
