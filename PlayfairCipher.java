import java.util.Scanner;

public class PlayfairCipher {
    static char[][] keyMatrix = new char[5][5];
    static boolean[] visited = new boolean[26];

    // Generate key matrix
    static void generateKeyMatrix(String key) {
        int k = 0;
        key = key.toLowerCase().replace("j", "i").replaceAll("[^a-z]", "");
        for (char ch : key.toCharArray()) {
            if (Character.isLetter(ch) && !visited[ch - 'a']) {
                keyMatrix[k / 5][k % 5] = ch;
                visited[ch - 'a'] = true;
                k++;
            }
        }
        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (ch != 'j' && !visited[ch - 'a']) {
                keyMatrix[k / 5][k % 5] = ch;
                visited[ch - 'a'] = true;
                k++;
            }
        }
    }

    // Encrypt the text
    static String encrypt(String text) {
        StringBuilder encryptedText = new StringBuilder();
        text = text.toLowerCase().replace("j", "i").replaceAll("[^a-z]", "");
        if (text.length() % 2 != 0)
            text += "x"; // Append 'x' if length is odd

        for (int i = 0; i < text.length(); i += 2) {
            int[] coord1 = findCoordinates(text.charAt(i));
            int[] coord2 = findCoordinates(text.charAt(i + 1));

            if (coord1[0] == coord2[0]) { // Same row
                encryptedText.append(keyMatrix[coord1[0]][(coord1[1] + 1) % 5]);
                encryptedText.append(keyMatrix[coord2[0]][(coord2[1] + 1) % 5]);
            } else if (coord1[1] == coord2[1]) { // Same column
                encryptedText.append(keyMatrix[(coord1[0] + 1) % 5][coord1[1]]);
                encryptedText.append(keyMatrix[(coord2[0] + 1) % 5][coord2[1]]);
            } else { // Rectangle rule
                encryptedText.append(keyMatrix[coord1[0]][coord2[1]]);
                encryptedText.append(keyMatrix[coord2[0]][coord1[1]]);
            }
        }
        return encryptedText.toString();
    }

    // Find coordinates of a character in the key matrix
    static int[] findCoordinates(char ch) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyMatrix[i][j] == ch)
                    return new int[] { i, j };
            }
        }
        return null; // Should never reach here if inputs are valid
    }

    // Display the key matrix (for debugging purposes)
    static void displayKeyMatrix() {
        System.out.println("Key Matrix:");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(keyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the key: ");
        String key = scanner.nextLine();

        if (key.isEmpty()) {
            System.out.println("Error: Key cannot be empty.");
            scanner.close();
            return;
        }

        System.out.print("Enter the plaintext: ");
        String plaintext = scanner.nextLine();

        if (plaintext.isEmpty()) {
            System.out.println("Error: Plaintext cannot be empty.");
            scanner.close();
            return;
        }

        generateKeyMatrix(key);
        displayKeyMatrix(); // Optional: Display the key matrix
        String encryptedText = encrypt(plaintext);

        System.out.println("Encrypted text: " + encryptedText);
        scanner.close();
    }
}
