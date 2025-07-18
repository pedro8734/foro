package aluracursos.foro.infra.security;

import aluracursos.foro.usuario.Usuario;
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
    @Value("${api.security.token.secret}")
    private String secret;

    public String generarToken(Usuario usuario) {
        try {
           var  algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API foro")
                    .withSubject(usuario.getCorreoElectronico())
                    .withExpiresAt(generarFechaExpiracionUnAnio())  // aquí cambiamos
                    .sign(algoritmo);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error al generar el token JWT", e);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
           var  algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API foro")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token JWT inválido o expirado");
        }
    }

    private Instant generarFechaExpiracionUnAnio() {
        return LocalDateTime.now()
                .plusYears(1)
                .toInstant(ZoneOffset.of("-05:00")); // ajusta la zona horaria si es necesario
    }
}
