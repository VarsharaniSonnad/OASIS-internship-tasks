import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random randomGenerator = new Random();

        System.out.println("=== Welcome to Number Guessing Game ===");
        System.out.println("Rules: Guess the number between 1 and 100.");

        boolean keepPlaying = true;
        int cumulativeScore = 0;

        while (keepPlaying) {
            int targetNumber = randomGenerator.nextInt(100) + 1; // Generate a random number
            int tries = 0;
            int maxTries = 10;
            boolean guessedCorrectly = false;

            System.out.println("New Round! You have " + maxTries + " attempts to guess the number.");

            while (tries < maxTries) {
                System.out.print("Enter your guess (Attempt " + (tries + 1) + "): ");
                int userGuess = input.nextInt();
                tries++;

                if (userGuess == targetNumber) {
                    System.out.println("🎉 Amazing! You guessed the number in " + tries + " attempts!");
                    // Calculate points: more attempts left = higher points
                    cumulativeScore += (maxTries - tries + 1) * 10;
                    guessedCorrectly = true;
                    break;
                } else if (userGuess < targetNumber) {
                    System.out.println("Hint: The number is higher!");
                } else {
                    System.out.println("Hint: The number is lower!");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("😞 You've used all your attempts. The number was: " + targetNumber);
            }

            System.out.println("Your current total score: " + cumulativeScore);

            System.out.print("Would you like to play another round? (yes/no): ");
            String userResponse = input.next();
            keepPlaying = userResponse.equalsIgnoreCase("yes");
        }

        System.out.println("Thanks for playing! Your final score is: " + cumulativeScore);
        input.close();
    }
}

