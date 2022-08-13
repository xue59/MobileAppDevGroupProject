package edu.neu.madscourse.tennismateandcourt;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TennisCourtModel implements Parcelable{
    int id;
    String name;
    Double rating;
    Double latitudes;
    Double longitudes;
    String address;
    String hoursOfOperations;
    String website;
    String phone;
    String lastUpdateTime;
    List<String> photos;
    String key;

    public TennisCourtModel(int id, String name, Double rating,Double latitudes,Double longitudes, String address, String hoursOfOperations, String website, String phone, String lastUpdateTime){
        this.id=id;
        this.name=name;
        this.rating = rating;
        this.latitudes = latitudes;
        this.longitudes = longitudes;
        this.address = address;
        this.hoursOfOperations = hoursOfOperations;
        this.website = website;
        this.phone = phone;
        this.lastUpdateTime = lastUpdateTime;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getLatitudes() {
        return latitudes;
    }

    public void setLatitudes(Double latitudes) {
        this.latitudes = latitudes;
    }

    public Double getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(Double longitudes) {
        this.longitudes = longitudes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHoursOfOperations() {
        return hoursOfOperations;
    }

    public void setHoursOfOperations(String hoursOfOperations) {
        this.hoursOfOperations = hoursOfOperations;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    protected TennisCourtModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
        if (in.readByte() == 0) {
            latitudes = null;
        } else {
            latitudes = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitudes = null;
        } else {
            longitudes = in.readDouble();
        }
        address = in.readString();
        hoursOfOperations = in.readString();
        website = in.readString();
        phone = in.readString();
        lastUpdateTime = in.readString();
        photos = in.createStringArrayList();
        key = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
        if (latitudes == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitudes);
        }
        if (longitudes == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitudes);
        }
        dest.writeString(address);
        dest.writeString(hoursOfOperations);
        dest.writeString(website);
        dest.writeString(phone);
        dest.writeString(lastUpdateTime);
        dest.writeStringList(photos);
        dest.writeString(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TennisCourtModel> CREATOR = new Creator<TennisCourtModel>() {
        @Override
        public TennisCourtModel createFromParcel(Parcel in) {
            return new TennisCourtModel(in);
        }

        @Override
        public TennisCourtModel[] newArray(int size) {
            return new TennisCourtModel[size];
        }
    };
}