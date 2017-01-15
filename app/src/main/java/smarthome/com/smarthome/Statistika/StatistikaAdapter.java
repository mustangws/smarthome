package smarthome.com.smarthome.Statistika;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import smarthome.com.smarthome.R;

public class StatistikaAdapter extends RecyclerView.Adapter<StatistikaAdapter.ViewHolder> {
    private ArrayList<Measurements> measurement;

    public StatistikaAdapter(ArrayList<Measurements> measurement) {
        this.measurement = measurement;
    }

    @Override
    public StatistikaAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.statistika_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatistikaAdapter.ViewHolder viewHolder, int i) {

        String datum = measurement.get(i).getTime().replace("T","  ");
        String vrijeme = datum.substring(0, datum.length()-4);
        viewHolder.senzorTime.setText(vrijeme);
        String stringdouble= Double.toString(measurement.get(i).getValue());
        viewHolder.senzorValue.setText(stringdouble);
    }

    @Override
    public int getItemCount() {
        return measurement.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView senzorTime,senzorValue;
        public ViewHolder(View view) {
            super(view);

            senzorTime = (TextView)view.findViewById(R.id.senzorTime);
            senzorValue = (TextView)view.findViewById(R.id.senzorValue);

        }
    }
}
