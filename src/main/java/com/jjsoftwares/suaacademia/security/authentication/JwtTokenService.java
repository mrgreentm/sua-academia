package com.jjsoftwares.suaacademia.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jjsoftwares.suaacademia.security.userdetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@Service
public class JwtTokenService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiration.hours}")
    private int expirationHours;

    public String generateToken(UserDetailsImpl user) {
        try {
            Algorithm algorithm = this.getAlgorithm();
            return JWT.create()
                    .withIssuer(issuer)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(user.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token", exception);
        }
    }
    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = this.getAlgorithm();
            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token inválido ou expirado.");
        }
    }
    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Maceio")).toInstant();
    }
    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Maceio")).plusHours(expirationHours).toInstant();
    }
    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }

}
