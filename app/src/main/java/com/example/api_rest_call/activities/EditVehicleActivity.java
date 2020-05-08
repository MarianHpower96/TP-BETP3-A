package com.example.api_rest_call.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api_rest_call.R;
import com.example.api_rest_call.services.CarService;
import com.example.api_rest_call.entities.Car;
import com.google.android.material.textfield.TextInputEditText;

public class EditVehicleActivity extends AppCompatActivity {

    private Car car;
    private CarService carService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Edición");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        carService = new CarService();
        String vehicle_id = getIntent().getStringExtra("id");
        fetchVehicle(vehicle_id);
    }

    private void fetchVehicle(String id) {
        carService.getById( id, this::populateView,
                () -> displayError("Error")
        );
    }

    private void populateView(Car car) {
        this.car = car;

        setContentView(R.layout.activity_vehicle_edition);

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

    public void onCancelButtonClick(View view) {
        finish();
    }

    public void onSaveButtonClick(View view) {
        String marca = ((TextInputEditText) findViewById(R.id.brand)).getText().toString();
        String modelo = ((TextInputEditText) findViewById(R.id.model)).getText().toString();

        try {
            car.setMarca(marca);
            car.setModelo(modelo);
        } catch (IllegalArgumentException e) {
            displayError("Ingresar todos los campos");
            return;
        }

        carService.update( car, (Void v) -> redirectToDetailsView(),
                () -> displayError("Error")
        );
    }

    private void redirectToDetailsView() {
        Intent intent = new Intent(this, VehicleDetailActivity.class);
        intent.putExtra("id", car.getId());
        startActivity(intent);
    }

    private void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
