package com.scdmonitor.app.utils

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

/**
 * EncryptionUtils provides simple AES-256 encryption helpers for local storage.
 * NOTE: For production, use AndroidKeyStore-backed keys and SQLCipher for database encryption.
 */
object EncryptionUtils {
    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"
    private const val ALGORITHM = "AES"

    fun generateKeyFromPassword(password: CharArray, salt: ByteArray): SecretKey {
        val spec = PBEKeySpec(password, salt, 10000, 256)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val bytes = factory.generateSecret(spec).encoded
        return SecretKeySpec(bytes, ALGORITHM)
    }

    fun encrypt(plain: ByteArray, key: SecretKey): String {
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(iv))
        val ct = cipher.doFinal(plain)
        val combined = iv + ct
        return Base64.encodeToString(combined, Base64.NO_WRAP)
    }

    fun decrypt(base64: String, key: SecretKey): ByteArray {
        val combined = Base64.decode(base64, Base64.NO_WRAP)
        val iv = combined.copyOfRange(0, 16)
        val ct = combined.copyOfRange(16, combined.size)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        return cipher.doFinal(ct)
    }
}
