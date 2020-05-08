package com.example.api_rest_call.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.api_rest_call.R;
import com.example.api_rest_call.entities.Car;

import java.util.ArrayList;

class VehicleViewListAdapter extends ArrayAdapter<Car> {
    public VehicleViewListAdapter(Context context, ArrayList<Car> cars) {
        super(context, 0, cars);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Car car = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_vehicle, parent, false);
        }

        TextView brand = convertView.findViewById(R.id.brand);
        TextView model = convertView.findViewById(R.id.model);
        brand.setText(car.getMarca());
        model.setText(car.getModelo());

        return convertView;
    }
}
