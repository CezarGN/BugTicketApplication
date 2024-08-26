package org.example.springskeleton.auth;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.example.springskeleton.config.AppProperties;
import org.springframework.beans.factory.annotation.Value;
import org.example.springskeleton.auth.user.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtUtilsService {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtilsService.class);

  private final AppProperties appProperties;


  public String generateJwtToken(Authentication authentication) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
            .subject((userPrincipal.getUsername()))
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + appProperties.getJwtExpirationMs()))
            .signWith(getSignKey(), Jwts.SIG.HS512)
            .compact();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      getUsernameFromJwtToken(authToken);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

  public String getUsernameFromJwtToken(String jwt) {
    return Jwts
            .parser()
            .verifyWith(getSignKey())
            .build()
            .parseSignedClaims(jwt)
            .getPayload()
            .getSubject();
  }

  private SecretKey getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(appProperties.getJwtSecret());

    return Keys.hmacShaKeyFor(keyBytes);
  }
}