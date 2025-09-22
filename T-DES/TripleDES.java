import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Scanner;

public class TripleDES{

    		public static void main(String[] args) throws Exception {
       		 Scanner sc = new Scanner(System.in);

        		System.out.print("Enter plaintext to encrypt: ");
        		String plaintext = sc.nextLine();

        		// Generate Triple DES key (DESede)

        KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
        keyGen.init(168); // 3 * 56-bit keys = 168 bits
        SecretKey key = keyGen.generateKey();

        // Encrypt plaintext
        byte[] encrypted = tripleDESEncrypt(plaintext.getBytes(), key);

        // Decrypt ciphertext
        byte[] decrypted = tripleDESDecrypt(encrypted, key);

        System.out.println("Encrypted (Base64): " + Base64.getEncoder().encodeToString(encrypted));
        System.out.println("Decrypted Text: " + new String(decrypted));

        sc.close();
    }

    public static byte[] tripleDESEncrypt(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] tripleDESDecrypt(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }
}