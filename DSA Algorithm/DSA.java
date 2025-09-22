import java.util.Scanner;
import java.security.*;
import java.util.Base64;

public class DSA{
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Enter some text:");
            String input = sc.nextLine();

            // Create a KeyPairGenerator for DSA
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");

            // Initialize with 2048 bits key size
            keyGen.initialize(2048);

            // Generate the key pair
            KeyPair pair = keyGen.generateKeyPair();

            PrivateKey privateKey = pair.getPrivate();

            // Use SHA256withDSA to sign (compatible with 2048-bit keys)
            Signature signature = Signature.getInstance("SHA256withDSA");

            // Initialize the Signature object with private key
            signature.initSign(privateKey);

            // Add data to be signed
            signature.update(input.getBytes());

            // Calculate digital signature bytes
            byte[] digitalSignature = signature.sign();

            // Encode signature bytes as Base64 string for readable output
            String signatureStr = Base64.getEncoder().encodeToString(digitalSignature);

            System.out.println("Digital signature for given text: " + signatureStr);

            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
