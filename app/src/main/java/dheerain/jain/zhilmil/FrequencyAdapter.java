package dheerain.jain.zhilmil;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
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
    SharedPreferences sharedPreferences;
    AsycObj asycObj=new AsycObj();
    public FrequencyAdapter(ArrayList<frequency> frequencies,Context c,SharedPreferences sharedPreferences) {

        this.sharedPreferences=sharedPreferences;
        asycObj.refrence((Communicator) c);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.name.setText(frequencies.get(position).getFreq()+"hrtez Frequency");
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                notifyDataSetChanged();
                MainActivity.delay=frequencies.get(position).getMilisec();
                if(sharedPreferences.getBoolean("key",false))
                asycObj.startFunction();
                if(!frequencies.get(position).isClicked())
                    frequencies.get(position).setClicked(true);
                else
                    frequencies.get(position).setClicked(false);
                notifyDataSetChanged();
            }
        });
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.delay=frequencies.get(position).getMilisec();
            }
        });
        if(frequencies.get(position).isClicked())
        holder.cardView.setCardBackgroundColor(Color.LTGRAY);
        else{
            holder.cardView.setCardBackgroundColor(Color.WHITE);
        }
//        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return frequencies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView icon;
        CardView cardView;
        public ViewHolder(View v) {
            super(v);
            cardView= (CardView) v.findViewById(R.id.cardView);
            name = (TextView) v.findViewById(R.id.frequencies);
            icon= (ImageView) v.findViewById(R.id.icon_frequency);
            icon.setImageResource(R.drawable.pulse);
        }
    }
    void reset()
    {
        for(int i=0;i<frequencies.size();i++)
        {
            frequencies.get(i).setClicked(false);
        }
    }
}
