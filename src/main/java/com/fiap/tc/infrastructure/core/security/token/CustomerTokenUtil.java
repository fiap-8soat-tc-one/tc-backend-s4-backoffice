package com.fiap.tc.infrastructure.core.security.token;

import com.fiap.tc.domain.entities.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.String.format;

@Component
public class CustomerTokenUtil {

    @Value("${app.jwt.secret}")
    private String secretKey;

    public Jws<Claims> validateToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (Exception e) {
            throw new UnauthorizedUserException(format("Invalid JWT Token: %s", token));
        }
    }

    public String getCustomerIdFromToken(String token) {
        Claims claims = validateToken(token).getBody();
        return claims.get("id", String.class);
    }

    public String getDocumentFromToken(String token) {
        Claims claims = validateToken(token).getBody();
        return claims.get("document", String.class);
    }
}