package com.frankie.workdev.controller;

import com.frankie.workdev.dto.Authentication.LoginDto;
import com.frankie.workdev.dto.Authentication.LoginResponse;
import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginDto loginDto) {
        ApiResponse<LoginResponse> login = authenticationService.login(loginDto);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }
}
