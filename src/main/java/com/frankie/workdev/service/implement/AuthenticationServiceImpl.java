package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.authentication.*;
import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.entity.Role;
import com.frankie.workdev.entity.User;
import com.frankie.workdev.exception.ApiException;
import com.frankie.workdev.exception.ResourceExistingException;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.RoleRepository;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.security.JwtTokenProvider;
import com.frankie.workdev.service.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;

    @Override
    public ApiResponse<LoginResponse> login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = jwtTokenProvider.generateAccessToken(authentication);
            String refreshToken = createOrUpdateRefreshToken(authentication);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAccessToken(accessToken);
            User findUserLogin = userRepository.findByEmail(loginDto.getEmail());
            if (findUserLogin.getRefreshToken() == null) {
                findUserLogin.setRefreshToken(refreshToken);
                userRepository.save(findUserLogin);
            }
            return ApiResponse.success(
                    "Login successfully",
                    HttpStatus.OK,
                    loginResponse
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private String createOrUpdateRefreshToken(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        String refreshToken = user.getRefreshToken();
        if (refreshToken == null || jwtTokenProvider.isRefreshTokenExpired(refreshToken)) {
            refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
        }
        return refreshToken;
    }

    @Override
    public ApiResponse<RegisterResponse> register(RegisterDto registerDto) {
        User existingEmail = userRepository.findByEmail(registerDto.getEmail());
        if (existingEmail != null) {
            throw new ResourceExistingException("User", "email", registerDto.getEmail());
        }

        User registerUser = new User();
        registerUser.setEmail(registerDto.getEmail());
        registerUser.setFullName(registerDto.getFullName());
        List<Role> roles = new ArrayList<>();
        if (registerDto.getRole().equalsIgnoreCase("ROLE_USER")) {
            Role findRole = roleRepository.findByName("ROLE_USER");
            roles.add(findRole);
        } else if (registerDto.getRole().equalsIgnoreCase("ROLE_HR")) {
            Role findRole = roleRepository.findByName("ROLE_HR");
            roles.add(findRole);
        }
        registerUser.setRoles(roles);
        registerUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        User saveRegister = userRepository.save(registerUser);
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setEmail(saveRegister.getEmail());
        registerResponse.setFullName(saveRegister.getFullName());
        registerResponse.setRole(saveRegister.getRoles().get(0).getName());
        return ApiResponse.success(
                "Register successfully",
                HttpStatus.CREATED,
                registerResponse
        );
    }

    @Override
    public ApiResponse<AccessTokenResponse> createAccessTokenFromRefreshToken
            (String refreshToken) {
        try {
            if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
                String email = jwtTokenProvider.getInformationFromRefreshToken(refreshToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                String accessToken = jwtTokenProvider.generateAccessToken(authentication);
                AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
                accessTokenResponse.setAccessToken(accessToken);
                return ApiResponse.success(
                        "Create new access token successfully",
                        HttpStatus.OK,
                        accessTokenResponse
                );
            } else {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid Refresh Token");
            }
        } catch (Exception e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Unable to create Access Token from Refresh Token");
        }
    }

    @Override
    public ApiResponse<RefreshTokenResponse> getRefreshToken() {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User findUser = userRepository.findByEmail(email);
            if (findUser == null) {
                throw new ResourceNotFoundException("User", "email", email);
            }

            RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
            refreshTokenResponse.setRefreshToken(findUser.getRefreshToken());
            return ApiResponse.success(
                    "Get refresh token successfully",
                    HttpStatus.OK,
                    refreshTokenResponse
            );
        } catch (ExpiredJwtException e) {
            return ApiResponse.error(
                "Unable to get refresh token, please login again",
                HttpStatus.BAD_REQUEST,
                null
            );
        }
    }
}
