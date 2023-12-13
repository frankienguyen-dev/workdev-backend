package com.frankie.workdev.service;

import com.frankie.workdev.dto.authentication.*;
import com.frankie.workdev.dto.apiResponse.ApiResponse;

public interface AuthenticationService {
    ApiResponse<LoginResponse> login(LoginDto loginDto);

    ApiResponse<RegisterResponse> register(RegisterDto registerDto);

    ApiResponse<AccessTokenResponse> createAccessTokenFromRefreshToken(String refreshToken);

    ApiResponse<RefreshTokenResponse> getRefreshToken();

}
