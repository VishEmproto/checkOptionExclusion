package com.vishu.test.repo;

import com.vishu.test.models.Property;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET("iranjith4/ad-assignment/db")
    Call<Property> getPropertyDetails();
}
