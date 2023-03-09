package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Get all transfers
     * @return a list of Transfer objects
     */
    @Override
    public List<Transfer> getAllTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    /**
     * Get all transfers for a user
     * @param userId the ID of the user
     * @return a list of Transfer objects
     */
//    @Override
//    public List<Transfer> getTransfersByUserId(long userId) {
//        List<Transfer> transfers = new ArrayList<>();
//        String sql = "SELECT t.transfer_id, t.transfer_type_id, t.transfer_status_id, " +
//                "t.account_from, t.account_to, t.amount, u.username AS to_username " +
//                "FROM transfer t " +
//                "JOIN accounts a ON t.account_from = a.account_id OR t.account_to = a.account_id " +
//                "JOIN users u ON u.user_id = a.user_id " +
//                "WHERE u.user_id = ?";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
//        while (results.next()) {
//            transfers.add(mapRowToTransfer(results));
//        }
//        return transfers;
//    }
    /**
     * Get a transfer by ID
     * @param transferId the ID of the transfer
     * @return a Transfer object, or null if not found
     */
    @Override
    public Transfer getTransferById(long transferId) {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, " +
                "account_from, account_to, amount " +
                "FROM transfer WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }
    /**
     * Get all transfers for a user, whether as the sender or receiver
     * @param accountId the account_id of the user
     * @return a list of Transfer objects
     */
    @Override
    public List<Transfer> getAllTransfersByUser(long accountId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT t.transfer_id, t.transfer_type_id, tt.transfer_type_desc, t.transfer_status_id, " +
                "ts.transfer_status_desc, t.account_from, t.account_to, t.amount " +
                "FROM transfer t " +
                "JOIN transfer_type tt ON t.transfer_type_id = tt.transfer_type_id " +
                "JOIN transfer_status ts ON t.transfer_status_id = ts.transfer_status_id " +
                "WHERE t.account_from = ? OR t.account_to = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }
    /**
     Creates a new transfer with the given details and inserts it into the transfers table.
     @param typeId the transfer type ID
     @param statusId the transfer status ID
     @param accountFrom the ID of the account sending the transfer
     @param accountTo the ID of the account receiving the transfer
     @param amount the amount being transferred
     @return the newly created Transfer object with the generated transfer ID
     */
    @Override
    public Transfer createTransfer(long typeId, long statusId, long accountFrom, long accountTo, BigDecimal amount) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, " +
                "account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";
        return getTransferById(jdbcTemplate.queryForObject(sql, Integer.class, typeId, statusId, accountFrom, accountTo, amount));
    }

    /**
     Updates the specified transfer in the database.
     @param transfer the transfer to be updated
     */
    @Override
    public void updateTransfer(Transfer transfer) {
       // String sql = "UPDATE transfer SET transfer_status_id = ?, updated_at = now() WHERE transfer_id = ?";
        String sql = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?";
        jdbcTemplate.update(sql, transfer.getTransferStatusId(), transfer.getTransferId());
    }

    /**
     Deletes the transfer with the given ID from the transfers table.
     @param transferId the ID of the transfer to delete
     */
    @Override
    public void deleteTransfer(long transferId) {
        String sql = "DELETE FROM transfer WHERE transfer_id = ?";
        jdbcTemplate.update(sql, transferId);
    }

    /**
     Maps a row from the result set to a Transfer object.
     @param rs the result set containing the transfer row data
     @return the Transfer object created from the row data
     */
    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }
}