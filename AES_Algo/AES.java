import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES {

    public static void main(String[] args) throws Exception {
        // Example URL to encrypt
        String url = "https://example.com/user?id=123";

        // Step 1: Create AES key (128-bit)
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // You can use 192 or 256 if Java supports it
        SecretKey secretKey = keyGen.generateKey();

        // Convert key to bytes and back (for demonstration)
        byte[] keyBytes = secretKey.getEncoded();
        SecretKeySpec aesKey = new SecretKeySpec(keyBytes, "AES");

        // Step 2: Create Cipher instance
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // Step 3: Convert URL string to byte array
        byte[] plainText = url.getBytes();

        // Step 4: Encrypt the URL
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encryptedBytes = cipher.doFinal(plainText);
        String encryptedUrl = Base64.getEncoder().encodeToString(encryptedBytes);

        // Step 5: Decrypt the URL
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedUrl));
        String decryptedUrl = new String(decryptedBytes);

        // Output
        System.out.println("Original URL: " + url);
        System.out.println("Encrypted URL (Base64): " + encryptedUrl);
        System.out.println("Decrypted URL: " + decryptedUrl);
    }
}