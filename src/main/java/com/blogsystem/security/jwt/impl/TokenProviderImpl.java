package com.blogsystem.security.jwt.impl;

import com.blogsystem.security.common.constants.SecurityConstants;
import com.blogsystem.security.jwt.TokenProvider;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Log4j2
public class TokenProviderImpl implements TokenProvider, InitializingBean {
    private final String base64Secret;
    private final long tokenValidityInMilliseconds;
    private final long tokenValidityInMillisecondsForRefreshToken;
    private Key key;

    public TokenProviderImpl(
            @Value("${jwt.secret}") String base64Secret,
            @Value("${jwt.validity-in-seconds}") long tokenValidityInMilliseconds,
            @Value("${jwt.validity-in-seconds-for-refresh-token}") long tokenValidityInMillisecondsForRefreshToken) {
        this.base64Secret = base64Secret;
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000;
        this.tokenValidityInMillisecondsForRefreshToken = tokenValidityInMillisecondsForRefreshToken * 1000;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generateAccessToken(Authentication authentication) {
        return createToken(authentication, tokenValidityInMilliseconds);
    }

    @Override
    public String generateRefreshToken(Authentication authentication) {
        return createToken(authentication, tokenValidityInMillisecondsForRefreshToken);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

    @Override
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(SecurityConstants.ROLES).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private String createToken(Authentication authentication, long validity) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        var now = new Date();
        var validityAccessToken = new Date(now.getTime() + validity);

        return Jwts.builder()
                .setHeaderParam(JwsHeader.ALGORITHM, SignatureAlgorithm.HS256)
                .setSubject(authentication.getName())
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(validityAccessToken)
                .claim(SecurityConstants.ROLES, authorities)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
