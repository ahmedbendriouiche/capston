package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferHistoryDto {
    private long transferId;
    private String userFrom;
    private String userTo;
    private BigDecimal amount;

    public TransferHistoryDto() {}

    public TransferHistoryDto(long transferId, String userFrom, String userTo, BigDecimal amount) {
        this.transferId = transferId;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
    }

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return getTransferId() + " " + getUserFrom() + " " + getUserTo() + " " + getAmount();
    }
}
