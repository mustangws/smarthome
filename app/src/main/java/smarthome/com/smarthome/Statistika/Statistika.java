package smarthome.com.smarthome.Statistika;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import smarthome.com.smarthome.Main.MainActivity;
import smarthome.com.smarthome.R;

public class Statistika extends AppCompatActivity{

    private TextView pocetniDatum, zavrsniDatum, aktivniDatumPrikaz;
    ImageButton kalendarDo;
    ImageButton kalendarOd;
    private Calendar pocetakDatum;
    private Calendar krajDatum;
    private Calendar aktivniDatum;
    static final int DATE_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID1 = 0;

    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private ArrayList<Measurements> data, data2, data3;
    private StatistikaAdapter statistikaAdapter, statistikaAdapter2, statistikaAdapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.statistika);
        pocetniDatum = (TextView) findViewById(R.id.pocetni);
        kalendarOd = (ImageButton) findViewById(R.id.kalendarOd);
        pocetakDatum = Calendar.getInstance();
        kalendarOd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(pocetniDatum, pocetakDatum);
            }
        });
        zavrsniDatum = (TextView) findViewById(R.id.zavrsni);
        kalendarDo = (ImageButton) findViewById(R.id.kalendarDo);
        krajDatum = Calendar.getInstance();
        kalendarDo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(zavrsniDatum, krajDatum);
            }
        });
        updateDisplay(pocetniDatum, pocetakDatum);
        updateDisplay(zavrsniDatum, krajDatum);
        initViewsToplina();
        initViewsVlaga();
        initViewsSvjetlost();
    }

    private void updateDisplay(TextView dateDisplay, Calendar date) {
        dateDisplay.setText(
                new StringBuilder()
                        .append(date.get(Calendar.DAY_OF_MONTH)).append("-")
                        .append(date.get(Calendar.MONTH) + 1).append("-")
                        .append(date.get(Calendar.YEAR)));

    }

    public void showDateDialog(TextView dateDisplay, Calendar date) {
        aktivniDatumPrikaz = dateDisplay;
        aktivniDatum = date;
        showDialog(DATE_DIALOG_ID);
    }

    private OnDateSetListener dateSetListener = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            aktivniDatum.set(Calendar.YEAR, year);
            aktivniDatum.set(Calendar.MONTH, monthOfYear);
            aktivniDatum.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDisplay(aktivniDatumPrikaz, aktivniDatum);
            unregisterDateDisplay();
        }
    };

    private void unregisterDateDisplay() {
        aktivniDatumPrikaz = null;
        aktivniDatum = null;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, dateSetListener, aktivniDatum.get(Calendar.YEAR), aktivniDatum.get(Calendar.MONTH), aktivniDatum.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(aktivniDatum.get(Calendar.YEAR), aktivniDatum.get(Calendar.MONTH), aktivniDatum.get(Calendar.DAY_OF_MONTH));
                break;
        }
    }

    public void initViewsToplina(){
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSONToplina();
    }
    public void loadJSONToplina(){
        Date datePoc = pocetakDatum.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = format1.format(datePoc);
        Date dateKr = krajDatum.getTime();
        String date2 = format1.format(dateKr);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<Senzor> call = request.getJSON(MainActivity.BASE_URL+"sensors/1/"+date1+"/"+date2);
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
    public void initViewsVlaga(){
        recyclerView2 = (RecyclerView)findViewById(R.id.recycler2);
        recyclerView2.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager);
        loadJSONVlaga();
    }
    public void loadJSONVlaga(){
        Date datePoc = pocetakDatum.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = format1.format(datePoc);
        Date dateKr = krajDatum.getTime();
        String date2 = format1.format(dateKr);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<Senzor> call = request.getJSON(MainActivity.BASE_URL+"sensors/2/"+date1+"/"+date2);
        call.enqueue(new Callback<Senzor>() {
            @Override
            public void onResponse(Call<Senzor> call, Response<Senzor> response) {

                Senzor jsonResponse = response.body();
                data2 = new ArrayList<>(Arrays.asList(jsonResponse.getMeasurements()));
                statistikaAdapter2 = new StatistikaAdapter(data2);
                recyclerView2.setAdapter(statistikaAdapter2);
            }

            @Override
            public void onFailure(Call<Senzor> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
    public void initViewsSvjetlost(){
        recyclerView3 = (RecyclerView)findViewById(R.id.recycler3);
        recyclerView3.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView3.setLayoutManager(layoutManager);
        loadJSONSvjetlost();
    }
    public void loadJSONSvjetlost(){
        Date datePoc = pocetakDatum.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = format1.format(datePoc);
        Date dateKr = krajDatum.getTime();
        String date2 = format1.format(dateKr);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<Senzor> call = request.getJSON(MainActivity.BASE_URL+"/sensors/3/"+date1+"/"+date2);
        call.enqueue(new Callback<Senzor>() {
            @Override
            public void onResponse(Call<Senzor> call, Response<Senzor> response) {

                Senzor jsonResponse = response.body();
                data3 = new ArrayList<>(Arrays.asList(jsonResponse.getMeasurements()));
                statistikaAdapter3 = new StatistikaAdapter(data3);
                recyclerView3.setAdapter(statistikaAdapter3);
            }

            @Override
            public void onFailure(Call<Senzor> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
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
            initViewsToplina();
            initViewsVlaga();
            initViewsSvjetlost();
    }
        return super.onOptionsItemSelected(item);}
}


