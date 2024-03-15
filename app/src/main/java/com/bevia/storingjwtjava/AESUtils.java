package com.bevia.storingjwtjava;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = "ThisIsASecretKey".getBytes(StandardCharsets.UTF_8);


    protected static Key generateKey() {
        return new SecretKeySpec(keyValue, ALGORITHM);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String data) {
        Key key = generateKey();
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encVal);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException("Error encrypting data: " + e.getMessage(), e);
        } catch (UnsupportedOperationException e) {
            throw new RuntimeException("Base64 encoding is not supported on this platform: " + e.getMessage(), e);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decrypt(String encryptedData) {
        Key key = generateKey();
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);

            byte[] decodedValue = Base64.getDecoder().decode(encryptedData);
            byte[] decVal = c.doFinal(decodedValue);

            if (decVal != null) {
                return new String(decVal, StandardCharsets.UTF_8);
            } else {
                throw new RuntimeException("Decrypted data is null.");
            }

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Error decrypting data: " + e.getMessage(), e);
        } catch (UnsupportedOperationException e) {
            throw new RuntimeException("Base64 decoding is not supported on this platform: " + e.getMessage(), e);
        }
    }
}
