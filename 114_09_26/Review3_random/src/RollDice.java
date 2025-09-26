import java.security.SecureRandom;

public class RollDice {
    public static void main(String[] args) {
        SecureRandom randomNumbers = new SecureRandom();

        int[] frequency = new int[7]; // Array to hold frequency of dice rolls (1-6)

        for (int roll = 1; roll <= 6000000; roll++) {
            int face = 1 + randomNumbers.nextInt(6); // Roll the die (1-6)
            frequency[face]++; // Increment the frequency for the rolled face
        }

        System.out.printf("%s%10s%n", "點數", "出現次數");
        for (int face = 1; face < frequency.length; face++) {
            System.out.printf("%4d%10d%n", face, frequency[face]);
        }

    }
}
