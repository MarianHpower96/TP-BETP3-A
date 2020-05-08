package com.example.api_rest_call.services;

import android.util.Log;

import com.example.api_rest_call.controller.CarController;
import com.example.api_rest_call.entities.Car;
import com.example.api_rest_call.services.events.OnError;
import com.example.api_rest_call.services.events.OnSuccess;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarService {
    private final CarController carController;

    public CarService() {
        carController = ServiceBuilder.buildService(CarController.class);
    }

    public void getAll(OnSuccess<List<Car>> onSuccess, OnError onError) {
        Call<List<Car>> http_call = carController.getVehicles();

        http_call.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if (response.body() == null)
                    return;

                onSuccess.execute(response.body());
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                Log.i("HTTP ERROR", t.getMessage());
                onError.execute();
            }
        });
    }

    public void getById(String id, OnSuccess<Car> onSuccess, OnError onError) {
        Call<Car> http_call = carController.getVehicle(id);

        http_call.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                Car car = response.body();
                onSuccess.execute(car);
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                Log.i("API ERROR", t.getMessage());
                onError.execute();
            }
        });
    }

    public void create(Car car, OnSuccess<Void> onSuccess, OnError onError) {

        Call<Void> http_call = carController.createVehicle(car);

        http_call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                onSuccess.execute(null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("API ERROR", t.getMessage());
                onError.execute();
            }
        });
    }

    public void update(Car car, OnSuccess<Void> onSuccess, OnError onError) {
        Call<Void> http_call = carController.updateVehicle(
                car.getId(),
                car.getMarca(),
                car.getModelo()
        );

        http_call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                onSuccess.execute(null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("API ERROR", t.getMessage());
                onError.execute();
            }
        });
    }

    public void delete(String id, OnSuccess<Void> onSuccess, OnError onError) {
        Call<Void> http_call = carController.deleteVehicle(id);

        http_call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                onSuccess.execute(null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("HTTP ERROR", t.getMessage());
                onError.execute();
            }
        });
    }
}
