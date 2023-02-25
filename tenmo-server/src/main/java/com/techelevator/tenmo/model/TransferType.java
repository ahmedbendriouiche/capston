package com.techelevator.tenmo.model;

import javax.validation.constraints.NotNull;

public class TransferType {
    @NotNull
    private int transferTypeId;
    @NotNull
    private String transferTypeDesc;

    public TransferType() {
    }

    public TransferType(int transferTypeId, String transferTypeDesc) {
        this.transferTypeId = transferTypeId;
        this.transferTypeDesc = transferTypeDesc;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferId) {
        this.transferTypeId = transferId;
    }

    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }
}
