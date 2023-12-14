package com.frankie.workdev.security;

import com.frankie.workdev.exception.ApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret-access-token}")
    private String accessTokenSecretKey;

    @Value("${app.jwt-access-token-expiration-milliseconds}")
    private Long accessTokenExpirationMilliseconds;

    @Value("${app.jwt-secret-refresh-token}")
    private String refreshTokenSecretKey;

    @Value("${app.jwt-refresh-token-expiration-milliseconds}")
    private Long refreshTokenExpirationMilliseconds;

    public String generateAccessToken(Authentication authentication) {
        String email = authentication.getName();
        String userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() +
                accessTokenExpirationMilliseconds);
        return Jwts.builder()
                .claim("id", userId)
                .subject(email)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(accessTokenKey())
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        String email = authentication.getName();
        String userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + refreshTokenExpirationMilliseconds);
        return Jwts.builder()
                .claim("id", userId)
                .subject(email)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(refreshTokenKey())
                .compact();
    }

    private Key accessTokenKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessTokenSecretKey));
    }

    private Key refreshTokenKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshTokenSecretKey));
    }

    public String getInformationFromAccessToken(String accessToken) {
        Claims claims = Jwts.parser().verifyWith((SecretKey) accessTokenKey()).build()
                .parseSignedClaims(accessToken).getPayload();
        return claims.getSubject();
    }

    public String getInformationFromRefreshToken(String refreshToken) {
        Claims claims = Jwts.parser().verifyWith((SecretKey) refreshTokenKey()).build()
                .parseSignedClaims(refreshToken).getPayload();
        return claims.getSubject();
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            Jwts.parser().verifyWith((SecretKey) accessTokenKey()).build()
                    .parseSignedClaims(accessToken);
            return true;
        } catch (IllegalArgumentException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        } catch (ExpiredJwtException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
        } catch (MalformedJwtException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jwts.parser().verifyWith((SecretKey) refreshTokenKey()).build()
                    .parseSignedClaims(refreshToken);
            return true;
        } catch (IllegalArgumentException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        } catch (ExpiredJwtException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
        } catch (MalformedJwtException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
        }
    }

    public boolean isAccessTokenExpired(String accessToken) {
        try {
            Claims claims = Jwts.parser().verifyWith((SecretKey) accessTokenKey()).build()
                    .parseSignedClaims(accessToken).getPayload();
            Date expirationDate = claims.getExpiration();
            return expirationDate.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public boolean isRefreshTokenExpired(String refreshToken) {
        try {
            Claims claims = Jwts.parser().verifyWith((SecretKey) refreshTokenKey()).build()
                    .parseSignedClaims(refreshToken).getPayload();
            Date expirationDate = claims.getExpiration();
            return expirationDate.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public String generateAccessTokenFromRefreshToken(String refreshToken) {
        try {
            Claims claims = Jwts.parser().verifyWith((SecretKey) refreshTokenKey()).build()
                    .parseSignedClaims(refreshToken).getPayload();
            String email = claims.getSubject();
            String userId = claims.get("id", String.class);
            Date currentDate = new Date();
            Date expirationDate = new Date(currentDate.getTime()
                    + accessTokenExpirationMilliseconds);
            return Jwts.builder()
                    .claim("id", userId)
                    .subject(email)
                    .issuedAt(currentDate)
                    .expiration(expirationDate)
                    .signWith(accessTokenKey())
                    .compact();
        } catch (Exception e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
        }
    }

    public Long getRefreshTokenExpirationMilliseconds(String refreshToken) {
        Claims claims = Jwts.parser().verifyWith((SecretKey) refreshTokenKey()).build()
                .parseSignedClaims(refreshToken).getPayload();
        Date expirationDate = claims.getExpiration();
        return expirationDate.getTime();

    }
}
