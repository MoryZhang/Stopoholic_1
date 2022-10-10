package com.example.stopoholic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList type, content, volume, weight, date;

    CustomAdapter(Context context,
                  ArrayList type,
                  ArrayList content,
                  ArrayList volume,
                  ArrayList weight,
                  ArrayList date){
        this.context = context;
        this.type = type;
        this.content = content;
        this.volume = volume;
        this.weight = weight;
        this.date = date;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.type_text.setText(String.valueOf(type.get(position)));
        holder.content_text.setText(String.valueOf(content.get(position)) + "%");
        holder.volume_text.setText(String.valueOf(volume.get(position)) + "ml");
        holder.weight_text.setText(String.valueOf(weight.get(position)) + "g");
        holder.date_text.setText(String.valueOf(date.get(position)));

    }

    @Override
    public int getItemCount() {
        return type.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView type_text, content_text, volume_text, weight_text, date_text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type_text = itemView.findViewById(R.id.textView);
            content_text = itemView.findViewById(R.id.textView2);
            volume_text = itemView.findViewById(R.id.textView3);
            weight_text = itemView.findViewById(R.id.textView4);
            date_text = itemView.findViewById(R.id.textView5);
        }
    }
}
