package edu.neu.madscourse.tennismateandcourt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String name;
    private String email;
    private String dateOfBirth;
    private String gender;
    private String ntrprating;
    private Double lat;
    private Double lon;
    private String uid;

    public User() {
    }

    public User(String name, String email, String dateOfBirth, String gender, String ntrprating, Double lat, Double lon) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.ntrprating = ntrprating;
        this.lat = lat;
        this.lon = lon;
    }

    public boolean isComplete() {
        return name != null && email != null && dateOfBirth != null && gender != null
                && ntrprating != null && lat != null && lon != null;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private double distance;

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    private List<String> friends;

    public List<String> getFriends() {
        if (friends==null){
            return new ArrayList<>();
        }
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getNtrprating() {
        return ntrprating;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}
