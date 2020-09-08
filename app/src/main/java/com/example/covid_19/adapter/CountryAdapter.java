package com.example.covid_19.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covid_19.R;
import com.example.covid_19.activity.ItemClickListener;
import com.example.covid_19.model.CountryData;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> implements Filterable {

    private List<CountryData> countryDataList;
    private List<CountryData> countryDataListFull;
    private Context context;
    private ItemClickListener itemClickListener;

    public CountryAdapter(List<CountryData> countryDataList, Context context, ItemClickListener itemClickListener) {
        this.countryDataList = countryDataList;
        this.countryDataListFull = new ArrayList<>(countryDataList);
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CountryData countryData = countryDataList.get(position);

        holder.tvCountryName.setText(countryData.getCountryName());
        Glide.with(context).load(countryData.getFlagUrl()).into(holder.flagImageView);
    }

    @Override
    public int getItemCount() {
        return countryDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return countriesFilter;
    }
    private Filter countriesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<CountryData> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(countryDataListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (CountryData item : countryDataListFull) {
                    if (item.getCountryName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            LinkedHashSet<CountryData> countryDataLinkedHashSet = new LinkedHashSet<>((List) filterResults.values);
            countryDataList.clear();
            countryDataList.addAll(countryDataLinkedHashSet);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView flagImageView;
        public TextView tvCountryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            flagImageView = itemView.findViewById(R.id.flagImage);
            tvCountryName = itemView.findViewById(R.id.tv_country_name);
            itemView.setOnClickListener((View.OnClickListener) this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition());
        }
    }
}
