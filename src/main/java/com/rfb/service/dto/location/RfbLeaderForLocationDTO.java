package com.rfb.service.dto.location;

import java.util.Objects;

public class RfbLeaderForLocationDTO {

    private String userName;
    private int totalRuns;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTotalRuns() {
        return totalRuns;
    }

    public void setTotalRuns(int totalRuns) {
        this.totalRuns = totalRuns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RfbLeaderForLocationDTO that = (RfbLeaderForLocationDTO) o;
        return getTotalRuns() == that.getTotalRuns() &&
            Objects.equals(getUserName(), that.getUserName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUserName(), getTotalRuns());
    }
}
