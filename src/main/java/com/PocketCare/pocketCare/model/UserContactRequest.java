package com.PocketCare.pocketCare.model;

import java.util.List;

public class UserContactRequest {

    private List<UserContact> userContacts;

    public List<UserContact> getUserContacts() {
        return userContacts;
    }

    public void setUserContacts(List<UserContact> userContacts) {
        this.userContacts = userContacts;
    }
}

