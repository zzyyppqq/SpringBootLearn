package com.zyp.springboot.learn.service.token;

import com.zyp.springboot.learn.constant.JwtConst;
import com.zyp.springboot.learn.util.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expire-minutes}")
    private Long expireMinutes;

    public Long verifyThenGetUid(String token) {
        return JwtUtils.verify(secret, token).getClaim(JwtConst.CLAIM_KEY_UID).asLong();
    }

    public String createToken(long uid, String username) {
        var token = JwtUtils.create(secret, expireMinutes,
                builder -> builder
                        .withClaim(JwtConst.CLAIM_KEY_UID, uid)
                        .withClaim(JwtConst.CLAIM_KEY_USERNAME, username)
        );
        return token;
    }

}
