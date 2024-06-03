package com.frankie.workdev.service;

import com.frankie.workdev.dto.authentication.*;
import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.authentication.ChangePasswordDto;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    ApiResponse<LoginResponse> login(LoginDto loginDto, HttpServletResponse response);

    ApiResponse<RegisterResponse> register(RegisterDto registerDto);

    ApiResponse<AccessTokenResponse> createAccessTokenFromRefreshToken(String refreshToken);

    ApiResponse<RefreshTokenResponse> getRefreshToken(String refreshToken);

    ApiResponse<String> logout(HttpServletResponse response);

    ApiResponse<ChangePasswordResponse> changePassword(ChangePasswordDto changePasswordDto);

}
