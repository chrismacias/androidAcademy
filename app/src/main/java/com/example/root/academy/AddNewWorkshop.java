package com.example.root.academy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;


public class AddNewWorkshop extends Activity {
    EditText title;
    EditText min;
    EditText max;
    DatePicker startDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_workshop);

        title  = (EditText)findViewById(R.id.title);
        min = (EditText)findViewById(R.id.min);
        max = (EditText)findViewById(R.id.max);
        startDate = (DatePicker)findViewById(R.id.startDate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_workshop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addWorkshop(View view){
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.57.1:8080")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        AcademyService apiService =
                retrofit.create(AcademyService.class);

        try {

            /*Workshop workshop = new Workshop("tadsf", 1, 2, "2015-02-02");
            Call<Workshop> call = apiService.createWorkshop(workshop);
            call.enqueue(new Callback<Workshop>() {
                @Override
                public void onResponse(Response<Workshop> response,
                                       Retrofit retrofit) {
                    int statusCode = response.code();
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });*/
            //String workshop = "java";
            Workshop workshop = new Workshop("workshop",1,2,"2015-02-02");
            //Call<List<Workshop>> call = apiService.createWorkshop(workshop);
            Call call = apiService.createWorkshop(workshop);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Response response,
                                       Retrofit retrofit) {
                    int statusCode = response.code();
                    if (response.body() != null) {
                        Workshop workshop = (Workshop) response.body();
                    }
                    Log.e("wokrsho","whor");

                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
        catch(Exception e){
            Log.e("MainActivity", e.getMessage(), e);
        }

    }
    public interface AcademyService {
        @POST("/api/workshops")
        Call<Workshop> createWorkshop(@Body Workshop workshop);

        @GET("/api/workshops/search/{query}")
        Call<List<Workshop>> searchWorkshop(@Path("query") String query);

        @GET("/api/workshops/")
        Call<List<Workshop>> getAllWorkshops();
    }
}
