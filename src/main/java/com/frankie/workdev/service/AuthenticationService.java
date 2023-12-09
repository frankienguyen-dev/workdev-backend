package com.frankie.workdev.service;

import com.frankie.workdev.dto.authentication.LoginDto;
import com.frankie.workdev.dto.authentication.LoginResponse;
import com.frankie.workdev.dto.apiResponse.ApiResponse;

public interface AuthenticationService {
    ApiResponse<LoginResponse> login(LoginDto loginDto);
}
