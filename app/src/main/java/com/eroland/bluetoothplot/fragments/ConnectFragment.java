package com.eroland.bluetoothplot.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eroland.bluetoothplot.R;

/**
 * Created by Zerachin on 10/10/2015.
 */


public class ConnectFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_connect, container, false);
        return v;
    }


}
