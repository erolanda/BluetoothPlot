package com.eroland.bluetoothplot;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class DevicesFragment extends Fragment {
    private OnDevicesFragmentInteractionListener mListener;
    private DeviceAdapter deviceAdapter;

    public DevicesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_devices, container, false);

        deviceAdapter = new DeviceAdapter(getActivity(), new ArrayList<Device>());

        view.findViewById(R.id.button_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDevicesFragmentRequestScan();
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(deviceAdapter);

        return view;
    }

    public void onDevicesFragmentRequestScan() {
        if (mListener != null) {
            mListener.onDevicesFragmentRequestScan();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnDevicesFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void addDevice(Device device) {
        deviceAdapter.addDevice(device);
    }

    public interface OnDevicesFragmentInteractionListener {
        public void onDevicesFragmentRequestScan();
    }
}
