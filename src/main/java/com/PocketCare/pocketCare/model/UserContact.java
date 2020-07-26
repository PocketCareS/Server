package com.PocketCare.pocketCare.model;

import java.util.List;

public class UserContact {

    private long date;
    private List<String> contactList;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<String> getContactList() {
        return contactList;
    }

    public void setContactList(List<String> contactList) {
        this.contactList = contactList;
    }
}
