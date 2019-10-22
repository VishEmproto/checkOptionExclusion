
package com.vishu.test.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Facility implements Parcelable
{

    @SerializedName("facility_id")
    @Expose
    private String facilityId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    public final static Creator<Facility> CREATOR = new Creator<Facility>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Facility createFromParcel(Parcel in) {
            return new Facility(in);
        }

        public Facility[] newArray(int size) {
            return (new Facility[size]);
        }

    }
    ;

    protected Facility(Parcel in) {
        this.facilityId = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.options, (com.vishu.test.models.Option.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Facility() {
    }

    /**
     * 
     * @param name
     * @param facilityId
     * @param options
     */
    public Facility(String facilityId, String name, List<Option> options) {
        super();
        this.facilityId = facilityId;
        this.name = name;
        this.options = options;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(facilityId);
        dest.writeValue(name);
        dest.writeList(options);
    }

    public int describeContents() {
        return  0;
    }

}
