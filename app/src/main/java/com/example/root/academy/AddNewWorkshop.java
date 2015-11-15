package com.example.root.academy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class AddNewWorkshop extends Activity {
    EditText titleET;
    EditText minET;
    EditText maxET;
    DatePicker startDateDP;

    Gson gson;
    Retrofit retrofit;
    AcademyServices apiService;
    final String BASE_URL = "http://192.168.57.1:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_workshop);

        titleET  = (EditText)findViewById(R.id.title);
        minET = (EditText)findViewById(R.id.min);
        maxET = (EditText)findViewById(R.id.max);
        startDateDP = (DatePicker)findViewById(R.id.startDate);

        gson = new GsonBuilder()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService =
                retrofit.create(AcademyServices.class);
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
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String title = titleET.getText().toString();
        int min = Integer.parseInt( minET.getText().toString());
        int max = Integer.parseInt(maxET.getText().toString());
        String startDate = df.format(new Date(startDateDP.getYear(), startDateDP.getMonth(), startDateDP.getDayOfMonth()));
        Workshop workshop = new Workshop(title,min,max,startDate);
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
        Intent i = new Intent(AddNewWorkshop.this,MainActivity.class);
        startActivity(i);

    }
}
