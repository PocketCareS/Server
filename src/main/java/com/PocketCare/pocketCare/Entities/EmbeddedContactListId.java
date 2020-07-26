package com.PocketCare.pocketCare.Entities;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class EmbeddedContactListId implements Serializable {

    @NotNull
    private String deviceId;

    @NotNull
    private long date;

    private long hour;

    public EmbeddedContactListId(@NotNull String deviceId, @NotNull long date, long hour) {
        this.deviceId = deviceId;
        this.date = date;
        this.hour = hour;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmbeddedContactListId)) return false;
        EmbeddedContactListId that = (EmbeddedContactListId) o;
        return getDate() == that.getDate() &&
                getDeviceId().equals(that.getDeviceId())&& getHour() == that.getHour();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeviceId());
    }
}
