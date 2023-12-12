package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.authentication.LoginDto;
import com.frankie.workdev.dto.authentication.LoginResponse;
import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.authentication.RegisterDto;
import com.frankie.workdev.dto.authentication.RegisterResponse;
import com.frankie.workdev.entity.Role;
import com.frankie.workdev.entity.User;
import com.frankie.workdev.exception.ResourceExistingException;
import com.frankie.workdev.repository.RoleRepository;
import com.frankie.workdev.repository.UserRepository;
import com.frankie.workdev.security.JwtTokenProvider;
import com.frankie.workdev.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
}
