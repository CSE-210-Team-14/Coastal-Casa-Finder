package com0.coastalcasa.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com0.coastalcasa.Models.Landlord;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JWTUtil {

    private static final int EXPIRE_TIME = 30 * 60 * 1000;  // expiration time: 30 mins
    private static final String SECRET_KEY = "123"; // key

    public static String sign(Landlord user) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        HashMap<String, Object> head = new HashMap<String, Object>() {
            {
                put("typ", "JWT");
                put("alg", "HS256");
            }
        };
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withHeader(head)
                .withClaim("email", user.getEmail())
                .withExpiresAt(date).sign(algorithm);
    }

    public static boolean verity(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(" email:" + jwt.getClaim("email").asString());  // get email from token
            return true;
        } catch (IllegalArgumentException | JWTVerificationException e) {
            return false;
        }
    }
}

