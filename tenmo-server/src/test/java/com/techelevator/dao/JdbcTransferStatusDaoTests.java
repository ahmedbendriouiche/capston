package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferStatusDao;
import com.techelevator.tenmo.model.TransferStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcTransferStatusDaoTests extends BaseDaoTests {
    protected static final TransferStatus PENDING = new TransferStatus(1, "Pending");
    protected static final TransferStatus APPROVED = new TransferStatus(2, "Approved");
    private static final TransferStatus REJECTED = new TransferStatus(3, "Rejected");

    private JdbcTransferStatusDao dao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcTransferStatusDao(jdbcTemplate);
    }

    @Test
    public void getStatusById_given_invalid_status_id_returns_null() {
        TransferStatus status = dao.getStatusById(-1);

        Assert.assertNull(status);
    }

    @Test
    public void getStatusById_given_valid_status_id_returns_status() {
        TransferStatus status = dao.getStatusById(PENDING.getTransferStatusId());

        Assert.assertEquals(PENDING, status);
    }

    @Test
    public void getStatusByName_given_invalid_status_name_returns_null() {
        TransferStatus status = dao.getStatusByName("invalid");

        Assert.assertNull(status);
    }

    @Test
    public void getStatusByName_given_valid_status_name_returns_status() {
        TransferStatus status = dao.getStatusByName(APPROVED.getTransferStatusDesc());

        Assert.assertEquals(APPROVED, status);
    }

    @Test
    public void listAll_returns_all_statuses_in_table() {
        List<TransferStatus> statuses = dao.listAll();

        Assert.assertNotNull(statuses);
        Assert.assertEquals(3, statuses.size());
        Assert.assertEquals(PENDING, statuses.get(0));
        Assert.assertEquals(APPROVED, statuses.get(1));
        Assert.assertEquals(REJECTED, statuses.get(2));
    }

}
