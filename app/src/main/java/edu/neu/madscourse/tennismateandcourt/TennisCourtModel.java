package edu.neu.madscourse.tennismateandcourt;

public class TennisCourtModel {
    String name;
    int id;
//    Float rating;
//    String address;
//    String hoursOfOperations;
//    String website;
//    String phone;
//    String lastUpdateTime;

    public TennisCourtModel(int id, String name){
        this.name=name;
        this.id=id;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

}
