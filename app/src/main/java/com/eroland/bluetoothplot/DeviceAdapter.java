package com.eroland.bluetoothplot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DeviceAdapter extends ArrayAdapter<Device> {
    private List<Device> mData;

    public DeviceAdapter(Context context, List<Device> devices) {
        super(context, 0, devices);
        mData = devices;
    }

    public void addDevice(Device device) {
        mData.add(device);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final Device device = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.delegate_device, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.text_view_name);
            holder.address = (TextView) convertView.findViewById(R.id.text_view_address);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(device.getName());
        holder.address.setText(device.getAddress());

        return convertView;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    static class ViewHolder {
        public TextView name;
        public TextView address;
    }
}
