package com.alibaba.drivermanagement.util;

import com.alibaba.drivermanagement.advice.ErrorHandler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final HttpServletRequest httpServletRequest;
    @Value("${secured.key}")
    private String SECRET_KEY;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername() {
        try {
            String authHeader = httpServletRequest.getHeader("Authorization");
            String token = authHeader.substring(7);
            return extractClaim(token, Claims::getSubject);
        }catch (Exception e){
            throw new ErrorHandler.CustomBadRequest(ErrorHandler.SystemErrors.TOKEN_NOT_VALID);
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}
