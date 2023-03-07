package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.model.TransferHistoryDto;
import com.techelevator.tenmo.model.UserInfoDto;
import com.techelevator.tenmo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/listall")
    public ResponseEntity<List<UserInfoDto>> getUserInfo(@RequestParam long userId) {
        List<UserInfoDto> userInfoDtos = userService.getUserInfo(userId);
        return ResponseEntity.ok(userInfoDtos);
    }
}
