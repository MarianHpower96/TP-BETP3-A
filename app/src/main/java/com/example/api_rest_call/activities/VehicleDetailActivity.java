package com.example.api_rest_call.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api_rest_call.services.CarService;

import com.example.api_rest_call.R;
import com.example.api_rest_call.entities.Car;

public class VehicleDetailActivity extends AppCompatActivity {

    private Car car;
    private CarService repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detalle");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        repository = new CarService();
        String vehicle_id = getIntent().getStringExtra("id");
        fetchVehicle(vehicle_id);
    }

    private void fetchVehicle(String id) {
        repository.getById( id, this::populateView,
                () -> displayError("Error")
        );
    }

    private void populateView(Car car) {
        this.car = car;

        setContentView(R.layout.activity_vehicle_detail);

        TextView brand = findViewById(R.id.brand);
        TextView model = findViewById(R.id.model);
        brand.setText(car.getMarca());
        model.setText(car.getModelo());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, VehicleListActivity.class);
        startActivity(intent);

        return true;
    }

    public void onEditButtonClick(View view) {
        Intent intent = new Intent(this, EditVehicleActivity.class);

        intent.putExtra("id", car.getId());
        startActivity(intent);
    }

    public void onDeleteButtonClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar auto")
                .setMessage("Vas a borrar el auto el auto: " + car.getMarca() +" - "+ car.getModelo())
                .setPositiveButton("Eliminar", (dialog, whichButton) -> {
                    deleteVehicle();
                    dialog.dismiss();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void deleteVehicle() {
        repository.delete(
                car.getId(), (Void function) -> redirectToVehiclesList(),
                () -> displayError("Error")
        );
    }

    private void redirectToVehiclesList() {
        Intent intent = new Intent(this, VehicleListActivity.class);
        startActivity(intent);
    }

    private void displayError(String message) {
        Toast.makeText(VehicleDetailActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
