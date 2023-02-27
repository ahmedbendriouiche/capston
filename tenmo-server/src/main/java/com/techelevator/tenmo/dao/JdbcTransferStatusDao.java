package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferStatusDao implements TransferStatusDao{
    JdbcTemplate jdbcTemplate;

    public JdbcTransferStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Lists all statuses in the table
    @Override
    public List<TransferStatus> listAll() {
        List<TransferStatus> statuses = new ArrayList<>();
        String sql = "SELECT * FROM transfer_status;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while (result.next()) {
            statuses.add(mapRowToStatus(result));
        }
        return statuses;
    }

    // Retrieves a TransferStatus from the database by its id
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

    // Retrieves a TransferStatus from the database by its name
    @Override
    public TransferStatus getStatusByName(String name) {
        if (name == null) throw new IllegalArgumentException("Name cannot be null");

        TransferStatus status = null;
        String sql = "SELECT * FROM transfer_status WHERE transfer_status_desc ILIKE ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, name);
        if (result.next()) {
            status = mapRowToStatus(result);
        }
        return status;
    }

    // Creates a TransferStatus from an SqlRowSet
    private TransferStatus mapRowToStatus(SqlRowSet rowSet) {
        TransferStatus ts = new TransferStatus();
        ts.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        ts.setTransferStatusDesc(rowSet.getString("transfer_status_desc"));

        return ts;
    }
}
