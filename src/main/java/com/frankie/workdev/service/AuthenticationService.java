package com.frankie.workdev.service;

import com.frankie.workdev.dto.authentication.LoginDto;
import com.frankie.workdev.dto.authentication.LoginResponse;
import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.authentication.RegisterDto;
import com.frankie.workdev.dto.authentication.RegisterResponse;

public interface AuthenticationService {
    ApiResponse<LoginResponse> login(LoginDto loginDto);

    ApiResponse<RegisterResponse> register(RegisterDto registerDto);
}
