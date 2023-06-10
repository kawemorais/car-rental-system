package br.com.system.carrental.security;

import br.com.system.carrental.models.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final String ISSUER = "car-rental";
    private final String JWT_SECRET = "chrn0inwTbfnaChsymGOixlIFah5JTQA";

    public String generateToken(UserModel userAuth) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(userAuth.getUsername())
                .withClaim("id", userAuth.getId())
                .withExpiresAt(LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("-03:00")))
                .sign(Algorithm.HMAC256(JWT_SECRET));
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256("chrn0inwTbfnaChsymGOixlIFah5JTQA"))
                .withIssuer(ISSUER)
                .build().verify(token).getSubject();
    }
}
