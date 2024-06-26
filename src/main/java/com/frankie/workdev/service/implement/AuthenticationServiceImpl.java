package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.authentication.*;
import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.authentication.ChangePasswordDto;
import com.frankie.workdev.dto.authentication.ChangePasswordResponse;
import com.frankie.workdev.dto.user.response.JwtUserInfo;
import com.frankie.workdev.entity.Role;
import com.frankie.workdev.entity.User;
import com.frankie.workdev.exception.ApiException;
import com.frankie.workdev.exception.ChangePasswordException;
import com.frankie.workdev.exception.EmailOrPasswordException;
import com.frankie.workdev.exception.ResourceExistingException;
import com.frankie.workdev.repository.RoleRepository;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.security.CustomUserDetails;
import com.frankie.workdev.security.JwtTokenProvider;
import com.frankie.workdev.service.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;


    @Override
    public ApiResponse<LoginResponse> login(LoginDto loginDto, HttpServletResponse response) {
        try {
            User findUserLogin = userRepository.findByEmail(loginDto.getEmail());
            if (findUserLogin == null || !passwordEncoder.matches(loginDto.getPassword(),
                    findUserLogin.getPassword())) {
                throw new EmailOrPasswordException("Email or password is incorrect");
            }

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
            loginResponse.setRefreshToken(refreshToken);
            loginResponse.setRole(findUserLogin.getRoles().get(0).getName());
            if (findUserLogin.getRefreshToken() == null) {
                findUserLogin.setRefreshToken(refreshToken);
                userRepository.save(findUserLogin);
            }

            long setAgeCookie = (jwtTokenProvider
                    .getRefreshTokenExpirationMilliseconds(refreshToken) - new Date().getTime()) / 1000;

            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int) setAgeCookie + 10);
            cookie.setPath("/");
            response.addCookie(cookie);
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
        registerResponse.setId(saveRegister.getId());
        registerResponse.setEmail(saveRegister.getEmail());
        registerResponse.setFullName(saveRegister.getFullName());
        registerResponse.setCreatedAt(saveRegister.getCreatedAt());
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

                String accessToken = jwtTokenProvider
                        .generateAccessTokenFromRefreshToken(refreshToken);
                AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
                accessTokenResponse.setAccessToken(accessToken);
                return ApiResponse.success(
                        "Create new access token successfully",
                        HttpStatus.OK,
                        accessTokenResponse
                );
            } else {
                return ApiResponse.error(
                        "Unable to create Access Token from Refresh Token",
                        HttpStatus.BAD_REQUEST,
                        null
                );
            }
        } catch (Exception e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Unable to create Access Token from " +
                    "Refresh Token, Please login again");
        }
    }

    @Override
    public ApiResponse<RefreshTokenResponse> getRefreshToken(String refreshToken) {

        try {
            RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
            refreshTokenResponse.setRefreshToken(refreshToken);
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

    @Override
    public ApiResponse<String> logout(HttpServletResponse response) {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String getUserEmail = authentication.getName();
            User findUserLogout = userRepository.findByEmail(getUserEmail);
            findUserLogout.setRefreshToken(null);
            Cookie cookie = new Cookie("refreshToken", null);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            userRepository.save(findUserLogout);
            return ApiResponse.success(
                    "Logout successfully",
                    HttpStatus.OK,
                    "Logout successfully"
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ApiResponse<ChangePasswordResponse> changePassword(ChangePasswordDto changePasswordDto) {
        JwtUserInfo getUserInfoFromToken = getUserInfoFromToken();
        User findUser = userRepository.findByEmail(getUserInfoFromToken.getEmail());
        String currentPassword = changePasswordDto.getCurrentPassword();
        String newPassword = changePasswordDto.getNewPassword();
        String confirmPassword = changePasswordDto.getConfirmPassword();
        if (passwordEncoder.matches(currentPassword, findUser.getPassword())) {
            if (!newPassword.matches(confirmPassword)) {
                throw new ChangePasswordException("Password does not match.");
            } else {
                findUser.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(findUser);
                ChangePasswordResponse changePasswordResponse = new ChangePasswordResponse();
                changePasswordResponse.setNewPassword(newPassword);
                return ApiResponse.success(
                        "Password changed successfully",
                        HttpStatus.OK,
                        changePasswordResponse
                );
            }
        } else {
            throw new ChangePasswordException("Current password is incorrect.");
        }
    }

    private JwtUserInfo getUserInfoFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String getUserId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        String getUserEmail = authentication.getName();
        JwtUserInfo getUserInfo = new JwtUserInfo();
        getUserInfo.setId(getUserId);
        getUserInfo.setEmail(getUserEmail);
        return getUserInfo;
    }

}
