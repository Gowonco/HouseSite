package com.gowonco.house.common.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.io.File;
import java.nio.charset.Charset;
import java.time.Instant;

/**
 * @author gowonco
 * @date 2019-07-24 15:13
 */
public class HashUtils {
    private static final HashFunction FUNCTION = Hashing.md5();
    // 加盐
    private static final String SALT = "gowonco.io";

    // 密码加盐
    public static String encryPassword(String password) {
        HashCode hashCode = FUNCTION.hashString(password+SALT,Charset.forName("UTF-8"));
        return hashCode.toString();
    }

    public static void main(String[] args) {
        System.out.println(new File("").getAbsolutePath());
        System.out.println(Instant.now().getEpochSecond());
    }
}
