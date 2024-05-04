package org.main.server.network;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenManager {

    private static final String SECRET_KEY = "zalupazalupa";
    private static final long EXPIRATION_TIME = 300000; // 5 мин говна

    public static String generateToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static String validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

            String username = claims.getBody().getSubject();
            return username;
        } catch (Exception e) {
            // Если возникает ошибка при проверке токена
            return null;
        }
    }
}
