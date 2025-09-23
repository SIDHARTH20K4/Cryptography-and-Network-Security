import java.util.Scanner;

public class Vigenere {

private static int charToInt(char c) {
return c - 'A';
}

private static char intToChar(int i) {
return (char) (i + 'A');
}

private static String generateKey(String message, String key) {
StringBuilder newKey = new StringBuilder();
key = key.toUpperCase();
int keyIndex = 0;
for (int i = 0; i < message.length(); i++) {
if (Character.isLetter(message.charAt(i))) {
newKey.append(key.charAt(keyIndex % key.length()));
keyIndex++;
} else {
newKey.append(message.charAt(i));
}
}
return newKey.toString();
}

public static String encrypt(String message, String key) {
message = message.toUpperCase();
key = generateKey(message, key);
StringBuilder cipherText = new StringBuilder();

for (int i = 0; i < message.length(); i++) {
char msgChar = message.charAt(i);
char keyChar = key.charAt(i);
if (Character.isLetter(msgChar)) {
int encryptedChar = (charToInt(msgChar) + charToInt(keyChar)) % 26;
cipherText.append(intToChar(encryptedChar));
} else {
cipherText.append(msgChar);
}
}
return cipherText.toString();
}

public static String decrypt(String cipherText, String key) {
cipherText = cipherText.toUpperCase();
key = generateKey(cipherText, key);
StringBuilder originalText = new StringBuilder();

for (int i = 0; i < cipherText.length(); i++) {
char cipherChar = cipherText.charAt(i);
char keyChar = key.charAt(i);
if (Character.isLetter(cipherChar)) {
int decryptedChar = (charToInt(cipherChar) - charToInt(keyChar) + 26) % 26;
originalText.append(intToChar(decryptedChar));
} else {
originalText.append(cipherChar);
}
}
return originalText.toString();
}

public static void main(String[] args) {
System.out.println("Simulating Vigenere Cipher");

Scanner sc = new Scanner(System.in);
System.out.print("Input Message : ");
String message = sc.nextLine().replaceAll("\\s+", "");

System.out.print("Input Key : ");
String key = sc.nextLine();

String encrypted = encrypt(message, key);
System.out.println("Encrypted Message : " + encrypted);

String decrypted = decrypt(encrypted, key);
System.out.println("Decrypted Message : " + decrypted);

sc.close();
}
}

