package com.techelevator.tenmo.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class Transfer {

    private long transferId;

    @NotBlank(message = "Transfer type ID must not be null")
    private long transferTypeId;

    @NotBlank(message = "Transfer status ID must not be null")
    private long transferStatusId;

    @NotBlank(message = "Account from must not be null")
    private long accountFrom;

    @NotBlank(message = "Account to must not be null")
    private long accountTo;

    @NotBlank(message = "Amount must not be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;


    public Transfer() {}

    public Transfer(long transferId, long transferTypeId, long transferStatusId, long accountFrom, long accountTo, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(long accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    @Override
    public String toString() {
        return "Transfer {" +
                "Transfer ID: " + transferId +
                ", Transfer Type ID: " + transferTypeId +
                ", Transfer Status ID: " + transferStatusId +
                ", Account From: " + accountFrom +
                ", Account To: " + accountTo +
                ", Amount: " + amount +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transfer)) return false;
        Transfer transfer = (Transfer) o;
        return Objects.equals(transferId, transfer.transferId) &&
                Objects.equals(transferTypeId, transfer.transferTypeId) &&
                Objects.equals(transferStatusId, transfer.transferStatusId) &&
                Objects.equals(accountFrom, transfer.accountFrom) &&
                Objects.equals(accountTo, transfer.accountTo) &&
                Objects.equals(amount, transfer.amount);
    }
    @Override
    public int hashCode() {
        return Objects.hash(transferId, transferTypeId, transferStatusId, accountFrom, accountTo, amount);
    }
}