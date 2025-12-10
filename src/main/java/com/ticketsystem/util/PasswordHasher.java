package com.ticketsystem.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordHasher {
    public static String hash(String password) {
        return DigestUtils.sha256Hex(password);
    }
}
