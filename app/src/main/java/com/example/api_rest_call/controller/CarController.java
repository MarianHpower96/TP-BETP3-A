package com.example.api_rest_call.controller;

import com.example.api_rest_call.entities.Car;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CarController {

    String BASE_ENDPOINT = "/app/api";

    @GET(BASE_ENDPOINT + "/read")
    Call<List<Car>> getVehicles();

    @GET(BASE_ENDPOINT + "/read/{id}")
    Call<Car> getVehicle(@Path("id") String id);

    @POST(BASE_ENDPOINT + "/create")
    Call<Void> createVehicle(@Body Car car);

    @FormUrlEncoded
    @PUT(BASE_ENDPOINT + "/update/{id}")
    Call<Void> updateVehicle(
            @Path("id") String id,
            @Field("marca") String marca,
            @Field("modelo") String modelo
    );

    @DELETE(BASE_ENDPOINT + "/delete/{id}")
    Call<Void> deleteVehicle(@Path("id") String id);
}
