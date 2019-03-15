package com.example.bob.health_helper.Base;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class BaseFragment extends Fragment {
    public void navigateTo(Class to) {
        Intent intent = new Intent(getActivity(), to);
        startActivity(intent);
    }
    public void showTips(String str){
        Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT).show();
    }
}
