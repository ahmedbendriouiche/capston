package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestUserService implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public List<UserInfoDto> getUserInfo(long userId) {
        List<User> users = userDao.getAllButCurrent(userId);
        List<UserInfoDto> userInfoDtos = new ArrayList<>();

        for (User user : users) {
            UserInfoDto userInfoDto = new UserInfoDto(user.getId(), user.getUsername());
            userInfoDtos.add(userInfoDto);
        }
        return userInfoDtos;
    }
}
