package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDetailsDto {
    private long transferId;
    private String accountFrom;
    private String accountTo;
    private String transferType;
    private String transferStatus;
    private BigDecimal amount;

    public TransferDetailsDto() {

    }

    public TransferDetailsDto(long transferId, String accountFrom, String accountTo, String transferType,
                              String transferStatus, BigDecimal amount) {
        this.transferId = transferId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.amount = amount;
    }

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
