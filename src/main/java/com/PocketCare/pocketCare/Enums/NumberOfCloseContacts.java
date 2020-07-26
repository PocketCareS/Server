package com.PocketCare.pocketCare.Enums;

public enum NumberOfCloseContacts {
    LESS_THAN_5(" less than 5",5,0),GREATER_THAN_5_LESS_THAN_10(" greater than or equal to 5 and less than 10",10,5),
    GREATER_THAN_10("greather than or equal to 10",5000000,10);

    private String name;
    private Integer lessThan;
    private Integer greaterThan;

    NumberOfCloseContacts(String name, Integer lessThan, Integer greaterThan ){
        this.name = name;
        this.greaterThan = greaterThan;
        this.lessThan = lessThan;
    }

    public static NumberOfCloseContacts from(Integer count){
        NumberOfCloseContacts [] numberOfCloseContacts = NumberOfCloseContacts.values();
        for(NumberOfCloseContacts contacts: numberOfCloseContacts){
            if(count< contacts.lessThan&&count>=contacts.greaterThan){
                return contacts;
            }
        }
        return null;
    }
}
