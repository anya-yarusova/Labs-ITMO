package com.anyarusova.common.utility;

import org.apache.commons.codec.digest.DigestUtils;

public final class Encryptor {

    private Encryptor() {

    }

    public static String encryptThisString(String input) {
        String sha256hex = DigestUtils.sha256Hex(input);
        return sha256hex;
    }
}
