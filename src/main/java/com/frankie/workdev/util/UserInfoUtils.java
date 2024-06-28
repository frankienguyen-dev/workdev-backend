package com.frankie.workdev.util;

import com.frankie.workdev.dto.user.response.JwtUserInfo;
import com.frankie.workdev.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserInfoUtils {
    public JwtUserInfo getJwtUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String getUserEmail = authentication.getName();
        String getUserId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        JwtUserInfo getUserInfo = new JwtUserInfo();
        getUserInfo.setId(getUserId);
        getUserInfo.setEmail(getUserEmail);
        return getUserInfo;
    }
}
