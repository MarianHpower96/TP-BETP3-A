package com.example.api_rest_call.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.api_rest_call.R;
import com.example.api_rest_call.services.CarService;
import com.example.api_rest_call.entities.Car;

public class VehicleListActivity extends AppCompatActivity {

    private ListView listView;
    private ListAdapter viewListAdapter;
    private ArrayList<Car> cars = new ArrayList<>();
    private CarService repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        setTitle("Listado de vehículos");

        repository = new CarService();
        this.fetchVehiclesList();
    }

    private void fetchVehiclesList() {
        repository.getAll(
                this::populateList,
                () -> displayError("Hubo un error leyendo los datos")
        );
    }

    private void populateList(List<Car> carList) {
        if (listView == null) {
            setListView();
        }

        cars.clear();
        cars.addAll(carList);

        ((BaseAdapter) viewListAdapter).notifyDataSetChanged();
    }

    private void setListView() {
        setContentView(R.layout.activity_vehicles_list);

        viewListAdapter = new VehicleViewListAdapter(this, cars);
        listView = findViewById(R.id.vehicles_list);
        listView.setAdapter(viewListAdapter);
        listView.setOnItemClickListener(onItemClick());
    }

    private void displayError(String message) {
        Toast.makeText(VehicleListActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private AdapterView.OnItemClickListener onItemClick() {
        return (adapterView, view, i, l) -> {
            Car car = (Car) adapterView.getItemAtPosition(i);
            redirectToDetailsView(car);
        };
    }

    private void redirectToDetailsView(Car car) {
        Intent intent = new Intent(VehicleListActivity.this, VehicleDetailActivity.class);
        intent.putExtra("id", car.getId());
        startActivity(intent);
    }

    public void onCreateButtonClicked(View view) {
        redirectToCreateView();
    }

    private void redirectToCreateView() {
        Intent intent = new Intent(this, CreateVehicleActivity.class);
        startActivity(intent);
    }
}
