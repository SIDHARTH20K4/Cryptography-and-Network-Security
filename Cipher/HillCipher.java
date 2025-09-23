import java.util.*;
public class HillCipher {
    private int[][] keyMatrix;
    private int[][] inverseKeyMatrix;
    private int n;

    public HillCipher(int[][] keyMatrix) {
        this.keyMatrix = keyMatrix;
        this.n = keyMatrix.length;
        this.inverseKeyMatrix = invertKeyMatrix(keyMatrix);
        if (inverseKeyMatrix == null) {
            throw new IllegalArgumentException("Key matrix is not invertible mod 26");
        }
    }

    private int charToInt(char c) {
        return c - 'A';
    }

    private char intToChar(int i) {
        return (char) (i + 'A');
    }

    private String padMessage(String msg) {
        int padding = n - (msg.length() % n);
        if (padding == n) return msg;
        StringBuilder sb = new StringBuilder(msg);
        for (int i = 0; i < padding; i++) sb.append('X');
        return sb.toString();
    }

    public String encrypt(String plaintext) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        plaintext = padMessage(plaintext);
        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += n) {
            int[] block = new int[n];
            for (int j = 0; j < n; j++) {
                block[j] = charToInt(plaintext.charAt(i + j));
            }
            int[] encryptedBlock = multiplyMatrixVector(keyMatrix, block);
            for (int val : encryptedBlock) {
                cipherText.append(intToChar(val));
            }
        }
        return cipherText.toString();
    }

    public String decrypt(String ciphertext) {
        ciphertext = ciphertext.toUpperCase().replaceAll("[^A-Z]", "");
        if (ciphertext.length() % n != 0) {
            throw new IllegalArgumentException("Ciphertext length must be multiple of block size");
        }
        StringBuilder plainText = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += n) {
            int[] block = new int[n];
            for (int j = 0; j < n; j++) {
                block[j] = charToInt(ciphertext.charAt(i + j));
            }
            int[] decryptedBlock = multiplyMatrixVector(inverseKeyMatrix, block);
            for (int val : decryptedBlock) {
                plainText.append(intToChar(val));
            }
        }
        return plainText.toString();
    }

    private int[] multiplyMatrixVector(int[][] matrix, int[] vector) {
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                sum += matrix[i][j] * vector[j];
            }
            result[i] = ((sum % 26) + 26) % 26;
        }
        return result;
    }

    private int determinant(int[][] matrix, int size) {
        if (size == 1) return matrix[0][0] % 26;
        int det = 0;
        int sign = 1;
        for (int f = 0; f < size; f++) {
            int[][] temp = getCofactor(matrix, 0, f, size);
            det += sign * matrix[0][f] * determinant(temp, size - 1);
            sign = -sign;
        }
        det = ((det % 26) + 26) % 26;
        return det;
    }

    private int[][] getCofactor(int[][] matrix, int p, int q, int size) {
        int[][] temp = new int[size - 1][size - 1];
        int row = 0, col = 0;
        for (int i = 0; i < size; i++) {
            if (i == p) continue;
            col = 0;
            for (int j = 0; j < size; j++) {
                if (j == q) continue;
                temp[row][col] = matrix[i][j];
                col++;
            }
            row++;
        }
        return temp;
    }

    private int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) return x;
        }
        return -1;
    }

    private int[][] adjoint(int[][] matrix) {
        int[][] adj = new int[n][n];
        if (n == 1) {
            adj[0][0] = 1;
            return adj;
        }
        int sign;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int[][] temp = getCofactor(matrix, i, j, n);
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj[j][i] = (sign * determinant(temp, n - 1) + 26) % 26;
            }
        }
        return adj;
    }

    private int[][] invertKeyMatrix(int[][] matrix) {
        int det = determinant(matrix, n);
        int detInv = modInverse(det, 26);
        if (detInv == -1) return null;
        int[][] adj = adjoint(matrix);
        int[][] inv = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inv[i][j] = (adj[i][j] * detInv) % 26;
                if (inv[i][j] < 0) inv[i][j] += 26;
            }
        }
        return inv;
    }

    public static void main(String[] args) {
        System.out.println("Simulating Hill Cipher");

        int[][] key = {
            {6, 24, 1},
            {13, 16, 10},
            {20, 17, 15}
        };

        HillCipher cipher = new HillCipher(key);

        String inputMessage = "Hema";
        String paddedMessage = cipher.padMessage(inputMessage.toUpperCase().replaceAll("[^A-Z]", ""));

        System.out.println("Input Message : " + inputMessage);
        System.out.println("Padded Message : " + paddedMessage);

        String encrypted = cipher.encrypt(inputMessage);
        System.out.println("Encrypted Message : " + encrypted);

        String decrypted = cipher.decrypt(encrypted);
        System.out.println("Decrypted Message : " + decrypted);
    }
}

