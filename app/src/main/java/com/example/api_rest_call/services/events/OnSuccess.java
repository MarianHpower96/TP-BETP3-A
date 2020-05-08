package com.example.api_rest_call.services.events;

public interface OnSuccess<T> {
    void execute(T response);
}
