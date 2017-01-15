package smarthome.com.smarthome.Grafovi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import smarthome.com.smarthome.Main.MainActivity;
import smarthome.com.smarthome.R;

public class Grafovi extends AppCompatActivity {

    static final int DATE_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID1 = 0;
    public String TAG = "Grafovi";
    ImageButton kalendarDo;
    ImageButton kalendarOd;
    ArrayList<Entry> xT,xV,xS;
    ArrayList<String> yT,yV,yS;
    private TextView pocetniDatum, zavrsniDatum, aktivniDatumPrikaz;
    private Calendar pocetakDatum;
    private Calendar krajDatum;
    private Calendar aktivniDatum;
    private LineChart mChart, mChart1, mChart2;
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            aktivniDatum.set(Calendar.YEAR, year);
            aktivniDatum.set(Calendar.MONTH, monthOfYear);
            aktivniDatum.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDisplay(aktivniDatumPrikaz, aktivniDatum);
            unregisterDateDisplay();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.grafovi);
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
        xT = new ArrayList<Entry>();
        yT = new ArrayList<String>();
        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setDrawGridBackground(false);
        mChart.setDescription("");
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);
        XAxis xl = mChart.getXAxis();
        xl.setAvoidFirstLastClipping(true);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setInverted(true);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        xV = new ArrayList<Entry>();
        yV = new ArrayList<String>();
        mChart1 = (LineChart) findViewById(R.id.chart2);
        mChart1.setDrawGridBackground(false);
        mChart1.setDescription("");
        mChart1.setTouchEnabled(true);
        mChart1.setDragEnabled(true);
        mChart1.setScaleEnabled(true);
        mChart1.setPinchZoom(true);
        XAxis x2 = mChart1.getXAxis();
        x2.setAvoidFirstLastClipping(true);
        YAxis leftAxis1 = mChart1.getAxisLeft();
        leftAxis1.setInverted(true);
        YAxis rightAxis1 = mChart1.getAxisRight();
        rightAxis1.setEnabled(false);
        Legend l1 = mChart1.getLegend();
        l1.setForm(Legend.LegendForm.LINE);
        xS = new ArrayList<Entry>();
        yS = new ArrayList<String>();
        mChart2 = (LineChart) findViewById(R.id.chart3);
        mChart2.setDrawGridBackground(false);
        mChart2.setDescription("");
        mChart2.setTouchEnabled(true);
        mChart2.setDragEnabled(true);
        mChart2.setScaleEnabled(true);
        mChart2.setPinchZoom(true);
        XAxis x3 = mChart2.getXAxis();
        x3.setAvoidFirstLastClipping(true);
        YAxis leftAxis2 = mChart2.getAxisLeft();
        leftAxis2.setInverted(true);
        YAxis rightAxis2 = mChart2.getAxisRight();
        rightAxis2.setEnabled(false);
        Legend l2 = mChart2.getLegend();
        l2.setForm(Legend.LegendForm.LINE);
        drawChart();
        drawChart1();
        drawChart2();
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

    public void drawChart() {

        Date datePoc = pocetakDatum.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = format1.format(datePoc);
        Date dateKr = krajDatum.getTime();
        String date2 = format1.format(dateKr);

        Log.e(TAG, "URL : " + (MainActivity.BASE_URL + "/sensors/1/"+date1+"/"+date2));

        String tag_string_req = "req_chart";

        StringRequest strReq = new StringRequest(Request.Method.GET, MainActivity.BASE_URL + "/sensors/1/"+date1+"/"+date2,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "Response: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String id = jsonObject.getString("id");
                            String type = jsonObject.getString("type");

                            JSONArray jsonArray = jsonObject.getJSONArray("measurements");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                double value = object.getDouble("value");
                                float value2 = (float) value;
                                String date = object.getString("time");
                                xT.add(new Entry(value2, i));
                                yT.add(date);
                            }

                            LineDataSet set1 = new LineDataSet(xT, "Data Value");
                            set1.setLineWidth(1.5f);
                            set1.setCircleRadius(4f);
                            LineData data = new LineData(yT, set1);
                            mChart.setData(data);
                            mChart.invalidate();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
        strReq.setRetryPolicy(new RetryPolicy() {

            @Override
            public void retry(VolleyError arg0) throws VolleyError {
            }

            @Override
            public int getCurrentTimeout() {
                return 0;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;
            }
        });
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void drawChart1() {

        Date datePoc = pocetakDatum.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = format1.format(datePoc);
        Date dateKr = krajDatum.getTime();
        String date2 = format1.format(dateKr);

        Log.e(TAG, "URL : " + (MainActivity.BASE_URL + "/sensors/2/"+date1+"/"+date2));

        String tag_string_req = "req_chart";

        StringRequest strReq = new StringRequest(Request.Method.GET, MainActivity.BASE_URL + "/sensors/2/"+date1+"/"+date2,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "Response: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String id = jsonObject.getString("id");
                            String type = jsonObject.getString("type");

                            JSONArray jsonArray = jsonObject.getJSONArray("measurements");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                double value = object.getDouble("value");
                                float value2 = (float) value;
                                String date = object.getString("time");
                                xV.add(new Entry(value2, i));
                                yV.add(date);
                            }

                            LineDataSet set1 = new LineDataSet(xV, "Data Value");
                            set1.setLineWidth(1.5f);
                            set1.setCircleRadius(4f);
                            LineData data = new LineData(yV, set1);
                            mChart1.setData(data);
                            mChart1.invalidate();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
        strReq.setRetryPolicy(new RetryPolicy() {

            @Override
            public void retry(VolleyError arg0) throws VolleyError {
            }

            @Override
            public int getCurrentTimeout() {
                return 0;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;
            }
        });
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void drawChart2() {

        Date datePoc = pocetakDatum.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = format1.format(datePoc);
        Date dateKr = krajDatum.getTime();
        String date2 = format1.format(dateKr);

        Log.e(TAG, "URL : " + (MainActivity.BASE_URL + "/sensors/3/"+date1+"/"+date2));

        String tag_string_req = "req_chart";

        StringRequest strReq = new StringRequest(Request.Method.GET, MainActivity.BASE_URL + "/sensors/3/"+date1+"/"+date2,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "Response: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String id = jsonObject.getString("id");
                            String type = jsonObject.getString("type");

                            JSONArray jsonArray = jsonObject.getJSONArray("measurements");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                double value = object.getDouble("value");
                                float value2 = (float) value;
                                String date = object.getString("time");
                                xS.add(new Entry(value2, i));
                                yS.add(date);
                            }

                            LineDataSet set1 = new LineDataSet(xS, "Data Value");
                            set1.setLineWidth(1.5f);
                            set1.setCircleRadius(4f);
                            LineData data = new LineData(yS, set1);
                            mChart2.setData(data);
                            mChart2.invalidate();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
        strReq.setRetryPolicy(new RetryPolicy() {

            @Override
            public void retry(VolleyError arg0) throws VolleyError {
            }

            @Override
            public int getCurrentTimeout() {
                return 0;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;
            }
        });
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
            drawChart();
            drawChart1();
            drawChart2();
        }
        return super.onOptionsItemSelected(item);}


}