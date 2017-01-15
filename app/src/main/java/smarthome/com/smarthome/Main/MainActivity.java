package smarthome.com.smarthome.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smarthome.com.smarthome.Grafovi.Grafovi;
import smarthome.com.smarthome.Postavke.Postavke;
import smarthome.com.smarthome.R;
import smarthome.com.smarthome.Statistika.Statistika;

public class MainActivity extends AppCompatActivity {

    public static String BASE_URL = "http://http://demo1677024.mockable.io/";
    private RecyclerView mRecyclerView;
    private RestManager mManager;
    private SenzoriAdapter mSenzoriAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" d-M-y \n h:m:s a");
        String strdate = simpleDateFormat.format(calendar.getTime());
        TextView textView = (TextView) findViewById(R.id.datum);
        textView.setText(strdate);
        show();
        configViews();
        mManager = new RestManager();
        Call<List<Senzori>> listCall = mManager.getSenzoriService().getAllSenzori();
        listCall.enqueue(new Callback<List<Senzori>>() {
            @Override
            public void onResponse(Call<List<Senzori>> call, Response<List<Senzori>> response) {
                if(response.isSuccess()){
                    List<Senzori> senzoriList = response.body();
                    for(int i=0;i<senzoriList.size();i++){
                        Senzori senzori = senzoriList.get(i);
                        mSenzoriAdapter.addSenzori(senzori);
                    }
                }
                else{
                    int sc = response.code();
                    switch (sc){

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Senzori>> call, Throwable t) {

            }
        });

    }

    private void configViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mSenzoriAdapter = new SenzoriAdapter();
        mRecyclerView.setAdapter(mSenzoriAdapter);
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
