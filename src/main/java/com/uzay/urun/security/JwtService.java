package com.uzay.urun.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final String SECRET_KEY = "KQ2X3AiSGhRYHN2MK/10oqWnx/XoePoK2DnrcdzF/aokrD/XAJ4A6jV6cd5hVyQQ4xjCHpR4xpBITL8CDAwqpA==";

    public String generateToken(UserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .claim("roles", roles)
                .signWith(getSignKey(SECRET_KEY))
                .compact();
    }

    public String generateToken2(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .subject(authentication.getName())
                .claim("roles", roles)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignKey(SECRET_KEY))
                .compact();
    }

    private SecretKey getSignKey(String secretKey) {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey(SECRET_KEY))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsernameFromToken(String token) {
        try {
            return getAllClaimsFromToken(token).getSubject();
        } catch (JwtException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<? extends GrantedAuthority> getAuthoritiesFromToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            List<String> roles = claims.get("roles", List.class);
            return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } catch (JwtException e) {
            return Collections.emptyList();
        }
    }

    public Date getExpirationDateFromToken(String token) {
        try {
            return getAllClaimsFromToken(token).getExpiration();
        } catch (JwtException e) {
            return null;
        }
    }

    public boolean isExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration != null && expiration.before(new Date());
    }

    public boolean isValid(String token, UserDetails userDetails) {
        try {
            final String username = getUsernameFromToken(token);
            return username != null &&
                    username.equals(userDetails.getUsername()) &&
                    !isExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValid2(String token, Authentication authentication) {
        try {
            final String username = getUsernameFromToken(token);
            return username != null &&
                    username.equals(authentication.getName()) &&
                    !isExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}