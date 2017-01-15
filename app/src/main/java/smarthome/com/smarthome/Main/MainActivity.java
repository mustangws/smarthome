package smarthome.com.smarthome.Main;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import smarthome.com.smarthome.Grafovi.Grafovi;
import smarthome.com.smarthome.Postavke.Postavke;
import smarthome.com.smarthome.R;
import smarthome.com.smarthome.Statistika.Measurements;
import smarthome.com.smarthome.Statistika.RequestInterface;
import smarthome.com.smarthome.Statistika.Senzor;
import smarthome.com.smarthome.Statistika.Statistika;
import smarthome.com.smarthome.Statistika.StatistikaAdapter;

public class MainActivity extends AppCompatActivity {

    public static String BASE_URL = " https://demo1677024.mockable.io/";

    private RecyclerView recyclerView;
    private ArrayList<Measurements> data;
    private StatistikaAdapter statistikaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" d-M-y \n h:m:s a");
        String strdate = simpleDateFormat.format(calendar.getTime());
        TextView textView = (TextView) findViewById(R.id.datum);
        textView.setText(strdate);
        show();
        initViews();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            initViews();
        }
        return super.onOptionsItemSelected(item);}

    public void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }
    public void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<Senzor> call = request.getJSON(MainActivity.BASE_URL+"/sensors");
        call.enqueue(new Callback<Senzor>() {
            @Override
            public void onResponse(Call<Senzor> call, Response<Senzor> response) {

                Senzor jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getMeasurements()));
                statistikaAdapter = new StatistikaAdapter(data);
                recyclerView.setAdapter(statistikaAdapter);
            }

            @Override
            public void onFailure(Call<Senzor> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    private void show(){

        final ImageButton statistika;
        final ImageButton grafovi;
        final ImageButton postavke;

        statistika=(ImageButton)findViewById(R.id.Statistika);
        grafovi=(ImageButton)findViewById(R.id.Grafovi);
        postavke=(ImageButton)findViewById(R.id.Postavke);
        statistika.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent
                        (MainActivity.this, Statistika.class);
                startActivity(intent);
            }
        });
        grafovi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent
                        (MainActivity.this, Grafovi.class);
                startActivity(intent);
            }
        });
        postavke.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent
                        (MainActivity.this, Postavke.class);
                startActivity(intent);
            }
        });
    }





}
