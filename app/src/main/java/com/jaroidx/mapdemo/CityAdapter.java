package com.jaroidx.mapdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    ArrayList<String> mListData;

    public CityAdapter(ArrayList<String> listData) {
        this.mListData = listData;
    }

    @NonNull
    @Override
    public CityAdapter.CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prefecture, parent, false);

        return new CityViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.CityViewHolder holder, int position) {
        holder.tvCityName.setText(mListData.get(position));
    }

    @Override
    public int getItemCount() {
        return (mListData == null || mListData.size() <= 0) ? 0 : mListData.size();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {
        TextView tvCityName;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tvName);
        }
    }
}
