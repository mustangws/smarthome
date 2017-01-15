package smarthome.com.smarthome.Main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import smarthome.com.smarthome.R;

public class SenzoriAdapter extends RecyclerView.Adapter<SenzoriAdapter.ViewHolder>{

    private ArrayList<Measurement> measurements;

    @Override
    public SenzoriAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.statistika_item, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SenzoriAdapter.ViewHolder viewHolder, int i) {

        String datum = measurements.get(i).getTime().replace('T',' ');
        String vrijeme = datum.substring(0, datum.length()-4);
        viewHolder.senzoriTime.setText(vrijeme);
        String stringdouble= Double.toString(measurements.get(i).getValue());
        viewHolder.senzoriValue.setText(stringdouble);
    }

    @Override
    public int getItemCount() {
        return measurements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView senzoriTime,senzoriValue;
        public ViewHolder(View view) {
            super(view);
            senzoriTime = (TextView)view.findViewById(R.id.senzorTime);
            senzoriValue = (TextView)view.findViewById(R.id.senzorValue);

        }
    }
}
