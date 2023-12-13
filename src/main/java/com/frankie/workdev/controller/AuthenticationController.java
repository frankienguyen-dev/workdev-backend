package com.frankie.workdev.controller;

import com.frankie.workdev.dto.authentication.*;
import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @RequestBody RegisterDto registerDto) {
        ApiResponse<RegisterResponse> register = authenticationService.register(registerDto);
        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }

    @GetMapping("/refresh")
    public ResponseEntity<ApiResponse<AccessTokenResponse>> refreshAccessToken(
            @RequestHeader("Refresh-Token") String refreshToken) {
        ApiResponse<AccessTokenResponse> accessToken = authenticationService
                .createAccessTokenFromRefreshToken(refreshToken);
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

    @GetMapping("/getRefreshToken")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> getRefreshToken() {
        ApiResponse<RefreshTokenResponse> getRefreshToken = authenticationService
                .getRefreshToken();
        return new ResponseEntity<>(getRefreshToken, HttpStatus.OK);
    }
}
