import java.util.Random;
import java.util.Scanner;

public class NumberGuessGame {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random rand = new Random();

        int maxAttempts = 5;
        int lowerBound = 1;
        int upperBound = 100;
        int roundsPlayed = 0;
        int roundsWon = 0;

        System.out.println("🎯 Welcome to the Number Guessing Game!");

        boolean playAgain = true;

        while (playAgain) {
            int target = rand.nextInt(upperBound - lowerBound + 1) + lowerBound;
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("\n🔢 I'm thinking of a number between " + lowerBound + " and " + upperBound + ".");
            System.out.println("You have " + maxAttempts + " attempts. Good luck!");

            while (attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                int guess;

                // Handle invalid input
                if (input.hasNextInt()) {
                    guess = input.nextInt();
                } else {
                    System.out.println("🚫 Please enter a valid number!");
                    input.next(); // clear invalid input
                    continue;
                }

                attempts++;

                if (guess == target) {
                    System.out.println("✅ Correct! You guessed it in " + attempts + " attempt(s).");
                    guessedCorrectly = true;
                    roundsWon++;
                    break;
                } else if (guess < target) {
                    System.out.println("📉 Too low!");
                } else {
                    System.out.println("📈 Too high!");
                }

                System.out.println("Remaining attempts: " + (maxAttempts - attempts));
            }

            if (!guessedCorrectly) {
                System.out.println("❌ You've used all attempts. The number was: " + target);
            }

            roundsPlayed++;

            // Ask to play again
            System.out.print("Do you want to play another round? (yes/no): ");
            String response = input.next().toLowerCase();
            playAgain = response.equals("yes") || response.equals("y");
        }

        System.out.println("\n🎉 Game Over! You played " + roundsPlayed + " round(s) and won " + roundsWon + " round(s).");
        System.out.println("Thanks for playing!");
        input.close();
    }
}
