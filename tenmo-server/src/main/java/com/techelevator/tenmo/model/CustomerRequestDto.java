package com.techelevator.tenmo.model;


public class CustomerRequestDto {
    private long id;
    private String name;

    public CustomerRequestDto(String name) {
        this.name = name;
    }
    public CustomerRequestDto() {}

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
        return "CustomerRequestDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
