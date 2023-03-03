package com.techelevator.tenmo.model;


public class CustomerDto {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomerDto(String name) {
        this.name = name;
    }
    public CustomerDto(long id,String name) {
        this.name = name;
        this.id = id;
    }
    public CustomerDto() {}

    public void setName(String name) {
        this.name = name;
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
