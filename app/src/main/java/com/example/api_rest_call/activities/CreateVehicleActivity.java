package com.example.api_rest_call.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.api_rest_call.R;
import com.example.api_rest_call.services.CarService;
import com.example.api_rest_call.entities.Car;
import com.google.android.material.textfield.TextInputEditText;

public class CreateVehicleActivity extends AppCompatActivity {

    private CarService carService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Crea tu vehiculo");

        carService = new CarService();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_create_vehicle);
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
        String brand = ((TextInputEditText) findViewById(R.id.brand)).getText().toString();
        String model = ((TextInputEditText) findViewById(R.id.model)).getText().toString();

        Car car = new Car();
        try {
            car.setMarca(brand);
            car.setModelo(model);
        } catch (IllegalArgumentException e) {
            displayError("Todos los campos son obligatorios");
            return;
        }

        carService.create( car, (Void function) -> redirectToListView(),
                () -> displayError("Hubo un error guardando los datos")
        );
    }

    private void redirectToListView() {
        Intent intent = new Intent(this, VehicleListActivity.class);
        startActivity(intent);
    }

    private void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
