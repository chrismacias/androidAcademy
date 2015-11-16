package com.example.root.academy;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class showWorkshops extends Activity {

    EditText titleET;
    EditText minET;
    EditText maxET;
    EditText startDateDP;

    Gson gson;
    Retrofit retrofit;
    AcademyServices apiService;
    final String BASE_URL = "http://192.168.57.1:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_workshops);

        gson = new GsonBuilder()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService =
                retrofit.create(AcademyServices.class);

        titleET  = (EditText)findViewById(R.id.title);
        minET = (EditText)findViewById(R.id.minGroup);
        maxET = (EditText)findViewById(R.id.maxGroup);
        startDateDP = (EditText)findViewById(R.id.startDate);

        String workshopID = getIntent().getExtras().getString("workshopID");
        getWorkshop(workshopID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_workshops, menu);
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

    public void getWorkshop(String id){
        Call<Workshop> call = apiService.getWorkshop(id);
        call.enqueue(new Callback<Workshop>() {
            @Override
            public void onResponse(Response<Workshop> response,
                                   Retrofit retrofit) {
                int statusCode = response.code();
                if (response.body() != null) {
                    Workshop workshop = response.body();

                    if (workshop != null) {
                        populateEditText(workshop);
                    }
                }

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    private void populateEditText(Workshop workshop) {
        titleET.setText(workshop.getTitle());
        minET.setText(Integer.toString(workshop.getMin()));
        maxET.setText(Integer.toString(workshop.getMax()));
        startDateDP.setText(workshop.getStartDate());
    }

    public void editWorkshop(View view){

    }
}
