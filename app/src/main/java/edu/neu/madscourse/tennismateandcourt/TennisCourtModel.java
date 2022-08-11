package edu.neu.madscourse.tennismateandcourt;

import android.os.Parcel;
import android.os.Parcelable;

public class TennisCourtModel implements Parcelable{
    String id;
    String name;
    String rating;
    String address;
    String hoursOfOperations;
    String website;
    String phone;
    String lastUpdateTime;

    public TennisCourtModel(String id, String name, String rating, String address, String hoursOfOperations, String website, String phone, String lastUpdateTime){
        this.id=id;
        this.name=name;
        this.rating = rating;
        this.address = address;
        this.hoursOfOperations = hoursOfOperations;
        this.website = website;
        this.phone = phone;
        this.lastUpdateTime = lastUpdateTime;


    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getRating() {
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
    public TennisCourtModel(Parcel in){
        String[] data = new String[8];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.id=data[0];
        this.name=data[1];
        this.rating = data[2];
        this.address = data[3];
        this.hoursOfOperations = data[4];
        this.website = data[5];
        this.phone = data[6];
        this.lastUpdateTime = data[7];
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.id,
                this.name,
                this.rating,
                this.address,
                this.hoursOfOperations,
                this.website,
                this.phone,
                this.lastUpdateTime
        });
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
