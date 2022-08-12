package edu.neu.madscourse.tennismateandcourt;

import android.os.Parcel;
import android.os.Parcelable;

public class TennisCourtModel implements Parcelable{
    int id;
    String name;
    Double rating;
    String address;
    String hoursOfOperations;
    String website;
    String phone;
    String lastUpdateTime;

    public TennisCourtModel(int id, String name, Double rating, String address, String hoursOfOperations, String website, String phone, String lastUpdateTime){
        this.id=id;
        this.name=name;
        this.rating = rating;
        this.address = address;
        this.hoursOfOperations = hoursOfOperations;
        this.website = website;
        this.phone = phone;
        this.lastUpdateTime = lastUpdateTime;


    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getRating() {
        return rating;
    }
    public String getAddress() {
        return address;
    }
    public String getHoursOfOperations() {
        return hoursOfOperations;
    }
    public String getWebsite() {
        return website;
    }
    public String getPhone() {
        return phone;
    }
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    // Parcelling part 打包传输model; 用于 activity 到activity间传输object
    protected TennisCourtModel(Parcel in){
        // the order needs to be the same as in writeToParcel() method
        this.id=in.readInt();
        this.name=in.readString();
        this.rating = in.readDouble();
        this.address = in.readString();
        this.hoursOfOperations = in.readString();
        this.website = in.readString();
        this.phone = in.readString();
        this.lastUpdateTime = in.readString();


    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeDouble(this.rating);
        dest.writeString(this.address);
        dest.writeString(this.hoursOfOperations);
        dest.writeString(this.website);
        dest.writeString(this.phone);
        dest.writeString(this.lastUpdateTime);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TennisCourtModel createFromParcel(Parcel in) {
            return new TennisCourtModel(in);
        }

        public TennisCourtModel[] newArray(int size) {
            return new TennisCourtModel[size];
        }
    };
}
