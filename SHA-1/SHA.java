import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter text to hash using SHA-1: ");
            String input = sc.nextLine();

            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // Just print algorithm info (skip provider/version if deprecated)
            System.out.println("Algorithm = " + md.getAlgorithm());

            byte[] digestBytes = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : digestBytes) {
                sb.append(String.format("%02X", b));
            }

            System.out.println("SHA1(\"" + input + "\")=" + sb.toString());

            sc.close();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("SHA-1 algorithm not found.");
        }
    }
}

