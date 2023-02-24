package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;

public class TransferType {
    @NotBlank
    private int transferId;
    private String transferTypeDesc;

    public TransferType() {
    }

    public TransferType(int transferId, String transferTypeDesc) {
        this.transferId = transferId;
        this.transferTypeDesc = transferTypeDesc;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }
}
