package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferTypeDao implements TransferTypeDao {
    JdbcTemplate jdbcTemplate;

    public JdbcTransferTypeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TransferType> listAll() {
        List<TransferType> type = new ArrayList<>();

        String sql = "SELECT * FROM transfer_type;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while (result.next()) {
            type.add(mapRowToType(result));
        }
        return type;
    }

    @Override
    public TransferType getTypeById(int id) {
        TransferType type = null;

        String sql = "SELECT * FROM transfer_type WHERE transfer_type_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);

        if(result.next()) {
            type = mapRowToType(result);
        }
        return type;
    }

    @Override
    public TransferType getTypeByName(String name) {
        TransferType type = null;

        String sql = "SELECT * FROM transfer_type WHERE transfer_type_desc = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, name);

        if(result.next()) {
            type = mapRowToType(result);
        }
        return type;
    }

    private TransferType mapRowToType(SqlRowSet rowSet) {
        TransferType tt = new TransferType();
        tt.setTransferTypeId(rowSet.getInt("transfer_type_id"));
        tt.setTransferTypeDesc(rowSet.getString("transfer_status_desc"));

        return tt;
    }
}
