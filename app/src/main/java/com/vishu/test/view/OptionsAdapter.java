package com.vishu.test.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vishu.test.Callbacks.ItemSelectedListener;
import com.vishu.test.R;
import com.vishu.test.models.Exclusion;
import com.vishu.test.models.Facility;
import com.vishu.test.models.Option;

import java.util.ArrayList;
import java.util.List;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.MyOptionsHolder> {
    List<Option> options = new ArrayList<>();
    Facility facility;
    List<List<Exclusion>> exclusionData;
    List<Exclusion> exclusionFacilities = new ArrayList<>();
    ItemSelectedListener listener;
    List<Facility> facilityList;
    List<Facility> selectedFacilities = new ArrayList<>();
    Context ctxt;
    private List<Exclusion> exclusionList = new ArrayList<>();

    public OptionsAdapter(Context context, Facility facility, List<Facility> facilityList, List<Option> options, List<List<Exclusion>> exclusionData, ItemSelectedListener listener) {
        this.options = options;
        this.ctxt = context;
        this.facility = facility;
        this.facilityList = facilityList;
        this.exclusionData = exclusionData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyOptionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.options, parent, false);
        return new MyOptionsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOptionsHolder holder, int position) {

        Option option = options.get(position);
        String optionName = option.getName();
        if (option.isSelected())
            holder.radio1.setChecked(true);
        else
            holder.radio1.setChecked(false);

        holder.radio1.setText(optionName);
        holder.radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onOptionSelected(v, facility, option);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class MyOptionsHolder extends RecyclerView.ViewHolder {
        public RadioButton radio1;

        public MyOptionsHolder(@NonNull View view) {
            super(view);
            radio1 = view.findViewById(R.id.radio1);

        }
    }
}
