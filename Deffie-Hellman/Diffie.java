import java.util.Scanner;

public class Diffie {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input modulus p and base g
        System.out.print("Enter prime modulus p: ");
        int p = sc.nextInt();

        System.out.print("Enter base g (primitive root modulo p): ");
        int g = sc.nextInt();

        // Input Alice's secret a
        System.out.print("Enter Alice's secret integer a: ");
        int a = sc.nextInt();

        // Input Bob's secret b
        System.out.print("Enter Bob's secret integer b: ");
        int b = sc.nextInt();

        // Step 2: Alice computes A = g^a mod p and sends to Bob
        int A = modExp(g, a, p);
        System.out.println("Alice Sends : " + A + " to Bob");

        // Step 3: Bob computes B = g^b mod p and sends to Alice
        int B = modExp(g, b, p);
        System.out.println("Bob Sends : " + B + " to Alice");

        // Step 4: Alice computes secret s = B^a mod p
        int sAlice = modExp(B, a, p);
        System.out.println("Alice Computes : " + sAlice);

        // Step 5: Bob computes secret s = A^b mod p
        int sBob = modExp(A, b, p);
        System.out.println("Bob Computes : " + sBob);

        // Final shared secret
        System.out.println("Shared Secret : " + sAlice);

        // Check if shared secrets match
        if (sAlice == sBob) {
            System.out.println("Success: Shared Secrets Match! " + sAlice);
        } else {
            System.out.println("Error: Shared Secrets Do Not Match!");
        }

        sc.close();
    }

    // Modular exponentiation function (to compute base^exp mod mod)
    public static int modExp(int base, int exp, int mod) {
        int result = 1;
        base = base % mod;

        while (exp > 0) {
            if ((exp & 1) == 1)  // if exp is odd
                result = (result * base) % mod;

            base = (base * base) % mod;
            exp = exp >> 1;  // divide exp by 2
        }
        return result;
    }
}