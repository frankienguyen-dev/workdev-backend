package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.authentication.LoginDto;
import com.frankie.workdev.dto.authentication.LoginResponse;
import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.security.JwtTokenProvider;
import com.frankie.workdev.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public ApiResponse<LoginResponse> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        return ApiResponse.success(
                "Login successfully",
                HttpStatus.OK,
                loginResponse
        );
    }
}
