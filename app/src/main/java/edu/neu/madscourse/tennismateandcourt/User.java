package edu.neu.madscourse.tennismateandcourt;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String name;
    private String email;
    private String dateOfBirth;
    private String gender;
    private String NTRPRating;
    private Double lat;
    private Double lon;

    public User(String name, String email, String dateOfBirth, String gender, String NTRPRating, Double lat, Double lon) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.NTRPRating = NTRPRating;
        this.lat = lat;
        this.lon = lon;
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

    public String getNTRPRating() {
        return NTRPRating;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}
