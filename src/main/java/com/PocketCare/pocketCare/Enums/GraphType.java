package com.PocketCare.pocketCare.Enums;

public enum GraphType {
    CLOSE_CONTACTS("close contacts"),
    CLOSE_CONTACTS_PERECENTAGE("close_contacts_percentage"),
    NUMBER_OF_USERS("number of users");
    String name;

    GraphType(String name){
        this.name = name;
    }
    String getName(){
        return this.name;
    }
    public static GraphType from(String type){
        GraphType [] graphTypes = GraphType.values();
        for(GraphType graphType: graphTypes){
            if(type.equalsIgnoreCase(graphType.getName())){
                return graphType;
            }
        }
        return CLOSE_CONTACTS;
    }
}
