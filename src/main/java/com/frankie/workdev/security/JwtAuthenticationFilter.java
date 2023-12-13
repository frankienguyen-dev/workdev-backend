package com.frankie.workdev.security;

import com.frankie.workdev.exception.ApiException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = getAccessTokenFromRequest(request);

        if (StringUtils.hasText(accessToken) && jwtTokenProvider
                .validateAccessToken(accessToken)) {

//            if (jwtTokenProvider.isAccessTokenExpired(accessToken)) {
//                String refreshToken = getRefreshTokenFromRequest(request);
//                if(StringUtils.hasText(refreshToken) && jwtTokenProvider
//                        .validateRefreshToken(refreshToken)) {
//                    String newAccessToken = jwtTokenProvider
//                            .generateAccessTokenFromRefreshToken(refreshToken);
//                    if(newAccessToken != null) {
//                        accessToken = newAccessToken;
//                    }
//                }
//            }
            String email = jwtTokenProvider.getInformationFromAccessToken(accessToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authenticationToken = new
                        UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");
        if (StringUtils.hasText(refreshToken)) {
            return refreshToken;
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Refresh Token not found");
        }
    }

    private String getAccessTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
