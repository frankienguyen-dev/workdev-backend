package com.frankie.workdev.security;

import com.frankie.workdev.entity.User;
import com.frankie.workdev.exception.ResourceNotFoundException;
import com.frankie.workdev.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user != null) {

            Set<GrantedAuthority> authorities = user.getRoles().stream()
                    .flatMap(role -> role.getPermissions().stream())
                    .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                    .collect(Collectors.toSet());


            return new CustomUserDetails(
                    user.getId(),
                    user.getEmail(),
                    user.getPassword(),
                    authorities
            );
        } else {
            throw new ResourceNotFoundException("User", "email", email);
        }
    }
}
