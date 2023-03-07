package com.techelevator.tenmo.model;

public class UserInfoDto {
    long userId;
    String username;

    public UserInfoDto() {}

    public UserInfoDto(long id, String username) {
        this.userId = userId;
        this.username = username;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        String fString = String.format("| %-11d | %-25s |", getUserId(), getUsername());
        return  fString;
    }
}
