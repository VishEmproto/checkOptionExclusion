
package com.vishu.test.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Property implements Parcelable
{

    @SerializedName("facilities")
    @Expose
    private List<Facility> facilities = null;
    @SerializedName("exclusions")
    @Expose
    private List<List<Exclusion>> exclusions = null;
    public final static Creator<Property> CREATOR = new Creator<Property>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Property createFromParcel(Parcel in) {
            return new Property(in);
        }

        public Property[] newArray(int size) {
            return (new Property[size]);
        }

    }
    ;

    protected Property(Parcel in) {
        in.readList(this.facilities, (Facility.class.getClassLoader()));
        //in.readList(this.exclusions, (List<Exclusion>.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Property() {
    }

    /**
     * 
     * @param exclusions
     * @param facilities
     */
    public Property(List<Facility> facilities, List<List<Exclusion>> exclusions) {
        super();
        this.facilities = facilities;
        this.exclusions = exclusions;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }

    public List<List<Exclusion>> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<List<Exclusion>> exclusions) {
        this.exclusions = exclusions;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(facilities);
        dest.writeList(exclusions);
    }

    public int describeContents() {
        return  0;
    }

}
