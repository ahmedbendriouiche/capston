package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RestCustomerService implements CustomerService {
   @Autowired
    UserDao userDao;

    @Override
    public ResponseEntity<Object> ListAllCustomer() {
        List<User> users = userDao.findAll();
        if(users==null){
        return     ResponseEntity.status(HttpStatus.NOT_FOUND).body("action can't be done");
        }
        return ResponseEntity.ok(users);
    }
}
