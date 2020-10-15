package ch.ost.group20.speedcamerareminder.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpeedCamera {

    public SpeedCamera(String congregation, String place, String street){
        this.congregation = congregation;
        this.place = place;
        this.street = street;
    }

    @SerializedName("congregation")
    @Expose
    private String congregation;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("street")
    @Expose
    private String street;

    public String getCongregation() {
        return congregation;
    }

    public void setCongregation(String congregation) {
        this.congregation = congregation;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPlaceCombined(){
        if(this.congregation.equals(this.place)){
            return this.congregation;
        } else {
            return this.place + ", " + this.congregation;
        }
    }

}