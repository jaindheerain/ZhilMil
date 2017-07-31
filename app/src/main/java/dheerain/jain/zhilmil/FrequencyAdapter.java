package dheerain.jain.zhilmil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dheerain on 31-07-2017.
 */

public class FrequencyAdapter extends RecyclerView.Adapter<FrequencyAdapter.ViewHolder>{

    ArrayList<frequency> frequencies;
    Context context;
    public FrequencyAdapter(ArrayList<frequency> frequencies,Context c) {

        this.frequencies=frequencies;
        context=c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater l = LayoutInflater.from(context);
        View v = l.inflate(R.layout.layout_freq,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.name.setText(frequencies.get(position).getFreq()+"hrtez Frequency");
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.delay=frequencies.get(position).getMilisec();
            }
        });
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.delay=frequencies.get(position).getMilisec();
            }
        });

    }

    @Override
    public int getItemCount() {
        return frequencies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView icon;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.frequencies);
            icon= (ImageView) v.findViewById(R.id.icon_frequency);
        }
    }
}
