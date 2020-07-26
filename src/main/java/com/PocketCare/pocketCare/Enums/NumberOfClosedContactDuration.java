package com.PocketCare.pocketCare.Enums;

public enum NumberOfClosedContactDuration {

    LESS_THAN_5(" less than 5",5,0),GREATER_THAN_5_LESS_THAN_10(" greater than or equal to 5 and less than 10",10,5),
    GREATER_THAN_10("greather than or equal to 10",5000000,10);

    private String name;
    private Integer lessThan;
    private Integer greaterThan;

    NumberOfClosedContactDuration(String name, Integer lessThan, Integer greaterThan ){
        this.name = name;
        this.greaterThan = greaterThan;
        this.lessThan = lessThan;
    }

    public String getName(){
        return this.name;
    }

    public static NumberOfClosedContactDuration from(Integer count){
        NumberOfClosedContactDuration [] numberOfCloseContactsDuration = NumberOfClosedContactDuration.values();
        for(NumberOfClosedContactDuration contacts: numberOfCloseContactsDuration){
            if(count< contacts.lessThan&&count>=contacts.greaterThan){
                return contacts;
            }
        }
        return null;
    }
}
