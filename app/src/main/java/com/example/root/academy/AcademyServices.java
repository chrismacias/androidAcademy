package com.example.root.academy;

import org.springframework.http.ResponseEntity;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by root on 13/11/15.
 */
public interface AcademyServices {
    @POST("/api/workshops")
    Call<Workshop> createWorkshop(@Body Workshop workshop);

    @GET("/api/workshops/search/{query}")
    Call<List<Workshop>> searchWorkshop(@Path("query") String query);

    @GET("/api/workshops/{query}")
    Call<Workshop> getWorkshop(@Path("query") String query);

    @GET("/api/workshops/")
    Call<List<Workshop>> getAllWorkshops();

    @PUT("/api/workshops/")
    Call<Workshop> updateWorkshop(@Body Workshop workshop);

    @DELETE("/api/workshops/{id}")
    Call<ResponseEntity> deleteWorkshop(@Path("id") String id);
}