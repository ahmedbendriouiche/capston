package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class TransferStatus {

    private int transferStatusId;
    @NotBlank(message = "Description should not be blank")
    private String transferStatusDesc;

    public TransferStatus() {}

    public TransferStatus(int transferStatusId, String transferStatusDesc) {
        this.transferStatusId = transferStatusId;
        this.transferStatusDesc = transferStatusDesc;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == null) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TransferStatus status = (TransferStatus) obj;
        return transferStatusId == status.transferStatusId &&
                Objects.equals(transferStatusDesc, status.transferStatusDesc);
    }
}
