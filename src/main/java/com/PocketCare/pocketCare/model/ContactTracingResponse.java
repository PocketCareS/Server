package com.PocketCare.pocketCare.model;

import java.util.List;

public class ContactTracingResponse {
    Integer maxContactDuration;
    Long Date;
    Integer totalContactDuration;
    Integer encounterCount;
    List<ContactTracingIndividualData> contactInformation;

    public Integer getMaxContactDuration() {
        return maxContactDuration;
    }

    public void setMaxContactDuration(Integer maxContactDuration) {
        this.maxContactDuration = maxContactDuration;
    }

    public Long getDate() {
        return Date;
    }

    public void setDate(Long date) {
        Date = date;
    }

    public Integer getTotalContactDuration() {
        return totalContactDuration;
    }

    public void setTotalContactDuration(Integer totalContactDuration) {
        this.totalContactDuration = totalContactDuration;
    }

    public Integer getEncounterCount() {
        return encounterCount;
    }

    public void setEncounterCount(Integer encounterCount) {
        this.encounterCount = encounterCount;
    }

    public List<ContactTracingIndividualData> getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(List<ContactTracingIndividualData> contactInformation) {
        this.contactInformation = contactInformation;
    }
}
