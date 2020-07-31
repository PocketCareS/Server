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
