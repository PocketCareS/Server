/*
 * Copyright 2020 University at Buffalo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
