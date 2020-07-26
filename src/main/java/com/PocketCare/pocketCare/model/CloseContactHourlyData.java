package com.PocketCare.pocketCare.model;

public class CloseContactHourlyData {
    private Integer numberOfContacts;
    private Integer duration;
    public CloseContactHourlyData(Integer numberOfContacts, int duration){
        this.numberOfContacts = numberOfContacts;
        this.duration = duration;
    }

    public Integer getNumberOfContacts() {
        return numberOfContacts;
    }

    public void setNumberOfContacts(Integer numberOfContacts) {
        this.numberOfContacts = numberOfContacts;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
