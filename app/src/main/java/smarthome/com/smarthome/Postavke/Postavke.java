package smarthome.com.smarthome.Postavke;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.StringTokenizer;

import retrofit2.Retrofit;
import smarthome.com.smarthome.Main.MainActivity;
import smarthome.com.smarthome.R;

public class Postavke extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    EditText URLedit;
    TextView pokaziURL, pokaziInterval;
    Integer odabraniInterval = 5, timeConvert;
    StringTokenizer tokens;
    String time, time2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.postavke);
        URLedit = (EditText) findViewById(R.id.url);
        pokaziURL = (TextView) findViewById(R.id.pokaziURL);
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.intervali, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String label = spinner.getSelectedItem().toString();
                tokens = new StringTokenizer(label, " ");
                time = tokens.nextToken().trim();
                timeConvert = Integer.parseInt(time);
                if(timeConvert==1 || timeConvert==2 || timeConvert==3 || timeConvert==6 || timeConvert==12) timeConvert=timeConvert*60;
                time2=String.valueOf(timeConvert);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void saveURL(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("URL", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String url = URLedit.getText().toString();
        editor.putString("noviURL", url);
        editor.apply();
        MainActivity.BASE_URL = url;
        pokaziURL.setText("URL promijenjen u " + url);
    }

    public void intervalPost(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .build();
        PostInterface service = retrofit.create(PostInterface.class);
        //service.postTime(time2);
        pokaziURL.setText("Interval promijenjen u " + time2 + " min");
    }
}
