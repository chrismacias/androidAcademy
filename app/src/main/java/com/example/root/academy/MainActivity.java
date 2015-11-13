package com.example.root.academy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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


public class MainActivity extends Activity {

    EditText query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        query = (EditText)findViewById(R.id.query);
        new HttpRequestTask(this).execute();
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

    public void loginUser(View view){

    }

    public void addWorkshop(View view){
        Intent i = new Intent(MainActivity.this, AddNewWorkshop.class);
        startActivity(i);
    }

    public void searchWorkshop(View view){
        final ArrayList<Workshop> workshops = null;
        String queryString = query.getText().toString();

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.57.1:8080")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        AcademyService apiService =
                retrofit.create(AcademyService.class);

        if(queryString.isEmpty()){
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
        }else {
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

    public interface AcademyService {
        @POST("/api/workshops")
        Call<Workshop> createWorkshop(@Body Workshop workshop);

        @GET("/api/workshops/search/{query}")
        Call<List<Workshop>> searchWorkshop(@Path("query") String query);

        @GET("/api/workshops/")
        Call<List<Workshop>> getAllWorkshops();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Workshop[]> {
        EditText emailET;
        Context context;
        protected Workshop[] doInBackground(Void... params) {
            emailET = (EditText)findViewById(R.id.loginEmail);
            try {


                final String url = "http://192.168.57.1:8080/api/workshops";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Workshop[] workshops = restTemplate.getForObject(url, Workshop[].class);

                //emailET.setText(greeting.getTitle());
                //return new ArrayList<Workshop>(Arrays.asList(workshops));
                return workshops;



            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        public HttpRequestTask(Context parentContext) {
            this.context = parentContext;
        }

        @Override
        protected void onPostExecute(Workshop[] workshops) {

            /*TextView greetingIdText = (TextView) findViewById(R.id.loginEmail);
            greetingIdText.setText(greeting.getTitle());*/
            //StableArrayAdapter adapter = new StableArrayAdapter(context,android.R.layout.simple_list_item_1,workshops);
            ArrayAdapter<Workshop> adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,workshops);
            ListView workshopLV = (ListView) findViewById(R.id.workshopList);
            workshopLV.setAdapter(adapter);

        }

    }

}
