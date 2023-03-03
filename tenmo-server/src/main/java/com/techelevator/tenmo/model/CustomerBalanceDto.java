package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class CustomerBalanceDto {

    private User customer;
    private BigDecimal balance ;

    public CustomerBalanceDto(){}
    public CustomerBalanceDto(User customer, BigDecimal balance) {
        this.customer = customer;
        this.balance = balance;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public User getCustomer() {
        return customer;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
