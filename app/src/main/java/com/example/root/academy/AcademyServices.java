package com.example.root.academy;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by root on 13/11/15.
 */
public interface AcademyServices {
    @POST("/api/workshops")
    Call<Workshop> createWorkshop(@Body Workshop workshop);

    @GET("/api/workshops/search/{query}")
    Call<List<Workshop>> searchWorkshop(@Path("query") String query);

    @GET("/api/workshops/")
    Call<List<Workshop>> getAllWorkshops();
}