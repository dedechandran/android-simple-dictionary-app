package com.example.genjeh.mydictionary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.genjeh.mydictionary.Activity.DetailActivity;
import com.example.genjeh.mydictionary.CustomListener.OnCustomClickListener;
import com.example.genjeh.mydictionary.ModelData.Dictionary;
import com.example.genjeh.mydictionary.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryHolder> {
    private ArrayList<Dictionary> data = new ArrayList<>();
    private Context context;

    public DictionaryAdapter(Context context) {
        this.context = context;
    }


    public ArrayList<Dictionary> getData() {
        return data;
    }

    public void setData(ArrayList<Dictionary> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public DictionaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_item, parent, false);

        return new DictionaryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DictionaryHolder holder, int position) {
        holder.tvDictionary.setText(getData().get(position).getDictWord());
        holder.cvDictionary.setOnClickListener(new OnCustomClickListener(position, new OnCustomClickListener.OnItemClickListener() {
            @Override
            public void onItemClicked(View v, int position) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_DICTIONARY,getData().get(position));
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clearData() {
        getData().clear();
    }

    class DictionaryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv_dictionary)
        CardView cvDictionary;
        @BindView(R.id.tv_dictionary)
        TextView tvDictionary;

        DictionaryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
