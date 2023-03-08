package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

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

    // Checks to see whether the current user sent or received the transfer, then prints who it was to or from
    public String toString(AuthenticatedUser currentUser) {
        DecimalFormat format = new DecimalFormat("#,##0.00"); /* format pattern for a numeric String */
        String amountString = format.format(getAmount()); /* Formats our BigDecimal (as a String) with commas and a
                                                             decimal that has a precision of 2 */
        String fString = null;

        if (currentUser.getUser().getUsername().equals(getUserFrom())) {
            fString = String.format("| %-5d |   To: %-50s | $ %15s |", getTransferId(), getUserTo(), amountString);
        } else {
            fString = String.format("| %-5d | From: %-50s | $ %15s |", getTransferId(), getUserFrom(), amountString);
        }

        return fString;
    }
}
