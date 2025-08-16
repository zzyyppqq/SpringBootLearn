package com.zyp.springboot.learn.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.LocalDateTime;
import java.util.function.Consumer;


public class JwtUtils {
    private final static String ISSUER = "com.zyp.springboot.learn";

    /**
     * 在登录成功后，生成一个JWT Token来表示用户的登录状态
     */
    public static String create(String secret, long expireMinutes, Consumer<JWTCreator.Builder> customClaim) {
        LocalDateTime now = LocalDateTime.now();
        Algorithm algorithm = Algorithm.HMAC512(secret);
        JWTCreator.Builder builder = JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(TimeUtils.toDate(now))
                .withExpiresAt(TimeUtils.toDate(now.plusMinutes(expireMinutes)));
        customClaim.accept(builder);
        return builder.sign(algorithm);
    }

    public static DecodedJWT verify(String secret, String token) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        return JWT.require(algorithm).build().verify(token);
    }

    public static void main(String[] args) {
        String secret = "EJogTbY8zF5ANCWy8oxpJaqAbs6t5ZE4bGjxgO5hxfXmKUn3enqBX7jifUgfV5Z0";
        String jwtToken = create(secret, 1, builder -> builder.withClaim("username", "bytewise"));
        System.out.println(jwtToken);
        var jwt = verify(secret, jwtToken);
        System.out.println(jwt.getClaim("username").asString());
    }
}
