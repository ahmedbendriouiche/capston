package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferTypeDao;
import com.techelevator.tenmo.model.TransferType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTransferTypeDaoTests extends BaseDaoTests{
    private static final TransferType REQUEST = new TransferType(1, "Request");
    private static final TransferType SEND = new TransferType(2, "Send");

    private JdbcTransferTypeDao dao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcTransferTypeDao(jdbcTemplate);
    }

    @Test
    public void testGetTypeById() {
        TransferType type = dao.getTypeById(REQUEST.getTransferTypeId());

        Assert.assertEquals(REQUEST, type);
    }

    @Test
    public void testGetTypeById_invalid_negative_id() {
        TransferType type = dao.getTypeById(-1);

        Assert.assertNull(type);
    }

    @Test
    public void testGetTypeById_invalid_0() {
        TransferType type = dao.getTypeById(0);

        Assert.assertNull(type);
    }

    @Test
    public void testGetTypeByName() {
        TransferType type = dao.getTypeByName(SEND.getTransferTypeDesc());

        Assert.assertEquals(SEND, type);
    }

    @Test
    public void testGetTypeByName_invalid_name() {
        TransferType type = dao.getTypeByName("Receive");

        Assert.assertNull(type);
    }
}
