package com.techelevator.tenmo.model;

public class CustomerDto {
    private long id;
    private String name;

    public CustomerDto(String name) {
        this.name = name;
    }
    public CustomerDto() {}

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
