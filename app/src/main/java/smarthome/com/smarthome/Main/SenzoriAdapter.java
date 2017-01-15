package smarthome.com.smarthome.Main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import smarthome.com.smarthome.R;

public class SenzoriAdapter extends RecyclerView.Adapter<SenzoriAdapter.Holder>{

private List<Senzori> mSenzori;

public SenzoriAdapter(){
    mSenzori = new ArrayList<>();
}

@Override
public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
    return new Holder(row);
}

@Override
public void onBindViewHolder(Holder holder, int position) {
    Senzori curr = mSenzori.get(position);
    holder.senzoriType.setText(curr.getType());

}

@Override
public int getItemCount() {
    return mSenzori.size();
}

public void addSenzori(Senzori senzori) {
    mSenzori.add(senzori);
    notifyDataSetChanged();
}


public class Holder extends RecyclerView.ViewHolder {
    private TextView senzoriType, senzoriLastAlive, senzoriMeasurement;
    public Holder(View itemView) {
        super(itemView);
        senzoriType = (TextView) itemView.findViewById(R.id.senzoriType);
    }
}
}
