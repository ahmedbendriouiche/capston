package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferStatusDao implements TransferStatusDao{
    JdbcTemplate jdbcTemplate;

    public JdbcTransferStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TransferStatus getStatusById(int id) {
        TransferStatus status = null;
        String sql = "SELECT * FROM transfer_status WHERE transfer_status_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if (result.next()) {
            status = mapRowToStatus(result);
        }
        return status;
    }

    @Override
    public TransferStatus getStatusByName(String name) {
        TransferStatus status = null;
        String sql = "SELECT * FROM transfer_status WHERE transfer_status_desc ILIKE ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, name);
        if (result.next()) {
            status = mapRowToStatus(result);
        }
        return status;
    }

    private TransferStatus mapRowToStatus(SqlRowSet rowSet) {
        TransferStatus ts = new TransferStatus();
        ts.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        ts.setTransferStatusDesc(rowSet.getString("transfer_status_desc"));

        return ts;
    }
}
