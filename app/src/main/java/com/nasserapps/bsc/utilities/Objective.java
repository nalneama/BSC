package com.nasserapps.bsc.utilities;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseObject;

/**
 * Created by Nasser on 2/27/15.
 */
public class Objective implements Parcelable {

    private String mObjective;
    private String mTarget;
    private String mPerformance;
    private String mCategory;
    private String mUnit;
    private String mMeasure;

    public Objective(){}

    public Objective(ParseObject object) {
        mObjective = object.getString(ParseConstants.COLUMN_OBJECTIVE);
        mTarget = object.get(ParseConstants.COLUMN_TARGET).toString();
        mPerformance = object.get(ParseConstants.COLUMN_PERFORMANCE).toString();
        mCategory = object.getString(ParseConstants.COLUMN_CATEGORY);
        mUnit = object.getString(ParseConstants.COLUMN_UNIT);
        mMeasure = object.getString(ParseConstants.COLUMN_MEASURE);
    }

    public String getObjective() {
        return mObjective;
    }

    public String getTarget() {
        return mTarget;
    }

    public String getPerformance() {
        return mPerformance;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getUnit() {
        return mUnit;
    }

    public String getMeasure() {
        return mMeasure;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mObjective);
        dest.writeString(mTarget);
        dest.writeString(mPerformance);
        dest.writeString(mCategory);
        dest.writeString(mUnit);
        dest.writeString(mMeasure);
    }

    private Objective(Parcel in){

        mObjective=in.readString();
        mTarget=in.readString();
        mPerformance=in.readString();
        mCategory=in.readString();
        mUnit=in.readString();
        mMeasure=in.readString();
    }

    public static final Creator<Objective> CREATOR = new Creator<Objective>() {
        @Override
        public Objective createFromParcel(Parcel source) {
            return new Objective(source);
        }

        @Override
        public Objective[] newArray(int size) {
            return new Objective[size];
        }
    };
}
