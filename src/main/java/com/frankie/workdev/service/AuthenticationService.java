package com.frankie.workdev.service;

import com.frankie.workdev.dto.Authentication.LoginDto;
import com.frankie.workdev.dto.Authentication.LoginResponse;
import com.frankie.workdev.dto.apiResponse.ApiResponse;

public interface AuthenticationService {
    ApiResponse<LoginResponse> login(LoginDto loginDto);
}
