package com.zyp.springboot.learn.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptUtils {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encode(String plainText) {
        return bCryptPasswordEncoder.encode(plainText);
    }

    public static boolean matches(String rawPwd, String encodedPwd) {
        return bCryptPasswordEncoder.matches(rawPwd, encodedPwd);
    }

    public static void main(String[] args) {
        String encode = encode("123456");
        System.out.println(encode);
        System.out.println(matches("123456",
                "$2a$10$rvQgK4mbZwDvImKj2pfHH.z8nVNLhiq8fDf.mbzRC2KNf4zd7I7cG"));
    }
}
