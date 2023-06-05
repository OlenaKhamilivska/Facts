package com.example.numbersapi;

import com.example.numbersapi.pojo.NumbersPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
public interface GetDataService {
    @Headers("Content-Type: application/json")
    @GET ("/{number}")
    Call <NumbersPojo> getFactEnteredNumber(@Path("number") String fact);

    @Headers("Content-Type: application/json")
    @GET ("/random/math")
    Call <NumbersPojo> getFactRandomNumber();
}
