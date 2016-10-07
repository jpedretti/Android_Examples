package com.example.jpedretti.chapter4recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jpedretti on 05/10/2016.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private ArrayList<MainDataDef> mainData;

    public MainAdapter(ArrayList<MainDataDef> mainDataDefs) {
        this.mainData = mainDataDefs;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_main, parent,false);

        view.setOnClickListener(MainActivity.mainOnClickListener);

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        ImageView imageIcon = holder.imageIcon;
        TextView textName = holder.textName;
        TextView textInfo =  holder.textInfo;

        imageIcon.setImageResource(mainData.get(position).getImage());
        textName.setText(mainData.get(position).getName());
        textInfo.setText(mainData.get(position).getInfo());
    }

    @Override
    public int getItemCount() {
        return mainData.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        ImageView imageIcon;
        TextView textName;
        TextView textInfo;

        public MainViewHolder(View itemView) {
            super(itemView);

            this.imageIcon = (ImageView) itemView.findViewById(R.id.card_image);
            this.textName = (TextView) itemView.findViewById(R.id.card_name);
            this.textInfo = (TextView) itemView.findViewById(R.id.card_info);
        }
    }
}
