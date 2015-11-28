package com.eroland.bluetoothplot;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

public class DevicesFragment extends Fragment {
    private static final String TAG = DevicesFragment.class.getSimpleName();
    private OnDevicesFragmentInteractionListener mListener;
    private BluetoothDeviceAdapter bluetoothDeviceAdapter;
    private ConnectThread mConnectThread = null;

    public DevicesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_devices, container, false);

        TextView textView = new TextView(getContext());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.ListViewHeaderTextSize));
        textView.setText("Paired devices");
        textView.setGravity(Gravity.CENTER);

        bluetoothDeviceAdapter = new BluetoothDeviceAdapter(getActivity(), new ArrayList<BluetoothDevice>());

        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.addHeaderView(textView, null, false);

        View footerView = inflater.inflate(R.layout.listview_footer, null);
        listView.addFooterView(footerView, null, false);

        footerView.findViewById(R.id.button_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDevicesFragmentRequestScan();
            }
        });

        listView.setAdapter(bluetoothDeviceAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "clicked on item " + i + " " + bluetoothDeviceAdapter.getItem(i - 1).getName());
                pairDevice(bluetoothDeviceAdapter.getItem(i - 1));
            }
        });


        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Toast.makeText(getContext(), "Turning ON Bluetooth", Toast.LENGTH_LONG).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }else{
                pairedDevices();
            }
        }else{
            // Device does not support Bluetooth
            Toast.makeText(getContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                pairedDevices();
            }
        }
    }


    public void pairedDevices(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                addDevice(device);
            }
        }
    }

    public void pairDevice(BluetoothDevice device){
        if (device instanceof BluetoothDevice)
            try{
                Log.d("pairDevice()", "Start Pairing...");
                Method m = device.getClass().getMethod("createBond", (Class[]) null);
                m.invoke(device, (Object[]) null);
                Log.d("pairDevice()", "Pairing finished.");
                mConnectThread = new ConnectThread(device);
                mConnectThread.start();
            } catch (Exception e){
                Log.e("pairDevice()", e.getMessage());
            }

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

    public void addDevice(BluetoothDevice device) {
        bluetoothDeviceAdapter.addDevice(device);
    }

    public interface OnDevicesFragmentInteractionListener {
        public void onDevicesFragmentRequestScan();
    }
}
