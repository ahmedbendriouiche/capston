package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.UserInfoDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<UserInfoDto> getUserInfo(long userId);
}
