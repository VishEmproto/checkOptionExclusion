package com.vishu.test.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vishu.test.Callbacks.ItemSelectedListener;
import com.vishu.test.R;
import com.vishu.test.models.Exclusion;
import com.vishu.test.models.Facility;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    List<Facility> list = new ArrayList();
    Context context;
    RecyclerView optionsRecycler;
    ItemSelectedListener listener;
    List<Exclusion> exclusions;
    List<List<Exclusion>> exclusionData;
    OptionsAdapter optionsAdapter;

    public Adapter(List<Facility> list, List<List<Exclusion>> exclusionData, Context ctxt, ItemSelectedListener listener) {
        this.list = list;
        context = ctxt;
        this.exclusionData = exclusionData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Facility facility = list.get(position);
        String name = facility.getName();
        holder.title.setText(name);
        optionsAdapter = new OptionsAdapter(context, facility, list, facility.getOptions(), exclusionData, (view, facility1, option) -> {
            int position2 = holder.getAdapterPosition();
            if (position2 != RecyclerView.NO_POSITION) {
                listener.onOptionSelected(view, facility1, option);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        optionsRecycler.setLayoutManager(mLayoutManager);
        optionsRecycler.setItemAnimator(new DefaultItemAnimator());
        optionsRecycler.setAdapter(optionsAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void refreshList(List<Facility> facilityList) {
        this.list = facilityList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        RecyclerView recyclerView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            recyclerView = view.findViewById(R.id.optionsRecycler);
            optionsRecycler = recyclerView;
        }
    }
}
