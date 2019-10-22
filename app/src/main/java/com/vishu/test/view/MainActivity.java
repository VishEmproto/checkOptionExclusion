package com.vishu.test.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vishu.test.Callbacks.ItemSelectedListener;
import com.vishu.test.R;
import com.vishu.test.models.Exclusion;
import com.vishu.test.models.Facility;
import com.vishu.test.models.Option;
import com.vishu.test.models.Property;
import com.vishu.test.repo.GetDataService;
import com.vishu.test.repo.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ItemSelectedListener {
    List<com.vishu.test.models.Facility> facilityList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Adapter mAdapter;
    Button refresh;
    Property property;
    List<Facility> selectedProperty = new ArrayList<>();
    List<Facility> selectedRoom = new ArrayList<>();
    List<Facility> selectedOther = new ArrayList<>();
    List<Exclusion> exclusionFacilities = new ArrayList<>();
    List<List<Exclusion>> exclusionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh = findViewById(R.id.refresh);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Property> call = service.getPropertyDetails();
        call.enqueue(new Callback<Property>() {
            @Override
            public void onResponse(Call<Property> call, Response<Property> response) {
                property = response.body();
                setPropertyAdapter(property.getFacilities(), property.getExclusions());
            }

            @Override
            public void onFailure(Call<Property> call, Throwable t) {

            }
        });
    }

    public void setPropertyAdapter(List<Facility> facilityList, List<List<Exclusion>> exclusionData) {
        this.facilityList = facilityList;
        this.exclusionData = exclusionData;
        recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new Adapter(facilityList, exclusionData, this, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onOptionSelected(View view, Facility facility, Option option) {
        if (facilityList.size() > 0) {
            if (!checkIfClickedHasAnExclusion(facility, option)) {
                setSelectedFacilities(facility, option);
                mAdapter.refreshList(facilityList);
            } else {
                Toast.makeText(this, "Excluded", Toast.LENGTH_SHORT).show();
            }
        } else {
            facilityList.add(facility);
        }

    }

    public List<Facility> segregateLists(Facility facility) {
        if (facility.getFacilityId().equals("1")) {
            selectedProperty = checkInclusion(selectedProperty, facility);
            return selectedProperty;
        }
        if (facility.getFacilityId().equals("2")) {
            selectedRoom = checkInclusion(selectedRoom, facility);
            return selectedRoom;
        }
        if (facility.getFacilityId().equals("3")) {
            selectedOther = checkInclusion(selectedOther, facility);
            return selectedOther;
        }
        return null;
    }

    private List<Facility> checkInclusion(List<Facility> includedFacilities, Facility facility) {
        if (includedFacilities.size() > 0) {
            for (int k = 0; k < includedFacilities.size(); k++) {
                if (includedFacilities.get(k).getFacilityId().equals(facility.getFacilityId())) {
                    includedFacilities.remove(k);
                    includedFacilities.add(facility);
                }
            }
        } else {
            includedFacilities.add(facility);
        }
        return includedFacilities;
    }

    private List<Exclusion> getExclusionList(Facility facility) {
        exclusionFacilities.clear();
        String selectedFacilityId = facility.getFacilityId();
        String optionId = "";
        facility.getOptions();
        for (int m = 0; m < facility.getOptions().size(); m++) {
            if (facility.getOptions().get(m).isSelected())
                optionId = facility.getOptions().get(m).getId();

        }
        Exclusion exclusion = new Exclusion(selectedFacilityId, optionId);
        for (List<Exclusion> exclusionList : exclusionData) {
            if (exclusionList.get(0).getFacilityId().equals(exclusion.getFacilityId())) {
                if (exclusionList.get(0).getOptionsId().equals(exclusion.getOptionsId()))
                    exclusionFacilities.add(exclusionList.get(1));
            } else if (exclusionList.get(1).getFacilityId().equals(exclusion.getFacilityId())) {
                if (exclusionList.get(1).getOptionsId().equals(exclusion.getOptionsId()))
                    exclusionFacilities.add(exclusionList.get(0));
            }
        }
        return exclusionFacilities;
    }

    public boolean checkIfClickedHasAnExclusion(Facility facility, Option option) {
        for (List<Exclusion> exclusionList : exclusionData) {
            for (Exclusion exclusion : exclusionList) {
                if (exclusion.getFacilityId().equals(facility.getFacilityId()) && exclusion.getOptionsId().equals(option.getId())) {
                    return checkIfExclusionAlreadySelected(exclusionList);
                }
            }
        }
        return false;
    }

    public boolean checkIfExclusionAlreadySelected(List<Exclusion> exclusions) {
        if (facilityList != null) {
            for (Facility facility : facilityList) {
                if (facility.getFacilityId().equals(exclusions.get(0).getFacilityId())) {
                    for (Option option : facility.getOptions()) {
                        if (option.isSelected() && (option.getId().equals(exclusions.get(0).getOptionsId()))) {
                            return true;
                        }
                    }
                } else if (facility.getFacilityId().equals(exclusions.get(1).getFacilityId())) {
                    for (Option option : facility.getOptions()) {
                        if (option.isSelected() && (option.getId().equals(exclusions.get(1).getOptionsId()))) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    /*

     */
    public void setSelectedFacilities(Facility facility, Option option) {
        for (int k = 0; k < facilityList.size(); k++) {
            if (facilityList.contains(facility)) {
                int index = facilityList.indexOf(facility);
                facilityList.remove(facility);
                List<Option> options = facility.getOptions();
                for (Option option1 : options) {
                    if (option1.getId().equals(option.getId()))
                        option1.setSelected(true);
                    else
                        option1.setSelected(false);
                }
                facility.setOptions(options);
                facilityList.add(index, facility);
            }
        }
    }
}
