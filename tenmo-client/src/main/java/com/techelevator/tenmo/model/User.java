package com.techelevator.tenmo.model;

import java.util.Objects;

public class User {

    private Long id;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof User) {
            User otherUser = (User) other;
            return Objects.equals(otherUser.getId(), id)
                    && otherUser.getUsername().equals(username);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
