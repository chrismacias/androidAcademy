package com.example.root.academy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class MainActivity extends Activity {

    EditText query;
    Gson gson;
    Retrofit retrofit;
    AcademyServices apiService;
    final String BASE_URL = "http://192.168.57.1:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        query = (EditText)findViewById(R.id.query);

        gson = new GsonBuilder()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService =
                retrofit.create(AcademyServices.class);
        getAllWorkshops();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        Intent i = new Intent(MainActivity.this, AddNewWorkshop.class);
        startActivity(i);
    }

    public void getAllWorkshops(){
        Call<List<Workshop>> call = apiService.getAllWorkshops();
        call.enqueue(new Callback<List<Workshop>>() {
            @Override
            public void onResponse(Response<List<Workshop>> response,
                                   Retrofit retrofit) {
                int statusCode = response.code();
                if (response.body() != null) {
                    List<Workshop> workshopsArray = response.body();

                    if (workshopsArray != null) {
                        populateListView((ArrayList<Workshop>) workshopsArray);
                    }
                }

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void searchWorkshop(View view){
        final ArrayList<Workshop> workshops = null;
        String queryString = query.getText().toString();

        if(queryString.isEmpty()){
            getAllWorkshops();
        }
        else {
            try {

                Call<List<Workshop>> call = apiService.searchWorkshop(queryString);
                call.enqueue(new Callback<List<Workshop>>() {
                    @Override
                    public void onResponse(Response<List<Workshop>> response,
                                           Retrofit retrofit) {
                        int statusCode = response.code();
                        if (response.body() != null) {
                            List<Workshop> workshopsArray = response.body();

                            if (workshopsArray != null) {
                                populateListView((ArrayList<Workshop>) workshopsArray);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            } catch (Exception e) {

            }
        }
    }

    public void populateListView(ArrayList<Workshop> workshops){
        ArrayAdapter<Workshop> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,workshops);
        ListView workshopLV = (ListView) findViewById(R.id.workshopList);
        workshopLV.setAdapter(adapter);
    }

}
