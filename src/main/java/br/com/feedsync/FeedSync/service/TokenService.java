package br.com.feedsync.FeedSync.service;

import br.com.feedsync.FeedSync.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    private static final String ISSUER = "FeedSync-API";
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.of("-03:00");

    public String generateToken(org.springframework.security.core.userdetails.UserDetails userDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(userDetails.getUsername()) // O getUsername() aqui é o email
                    .withExpiresAt(getExpirationTime())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject(); // Retorna o subject (email) se o token for válido
        } catch (JWTVerificationException exception) {
            return ""; // Retorna vazio se o token for inválido
        }
    }

    private Instant getExpirationTime() {
        // Token expira em 2 horas
        return LocalDateTime.now().plusHours(2).toInstant(ZONE_OFFSET);
    }
}