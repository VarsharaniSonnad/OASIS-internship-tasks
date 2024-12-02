import java.util.HashMap;
import java.util.Scanner;

public class OnlineExamination {
    private static HashMap<String, String> userDatabase = new HashMap<>();
    private static String currentUser = null;
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isExamActive = false;

    public static void main(String[] args) {
        // Adding a default user for demonstration
        userDatabase.put("user1", "user123");

        System.out.println("=== Welcome to the Online Examination System ===");
        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                if (login()) {
                    showDashboard();
                }
            } else if (choice == 2) {
                System.out.println("Thank you for using the system. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static boolean login() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            currentUser = username;
            System.out.println("Login successful! Welcome, " + username);
            return true;
        } else {
            System.out.println("Invalid credentials. Please try again.");
            return false;
        }
    }

    private static void showDashboard() {
        while (true) {
            System.out.println("\n=== Dashboard ===");
            System.out.println("1. Update Profile and Password");
            System.out.println("2. Start Examination");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    updateProfile();
                    break;
                case 2:
                    startExam();
                    break;
                case 3:
                    System.out.println("Logged out successfully.");
                    currentUser = null;
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void updateProfile() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        userDatabase.put(currentUser, newPassword);
        System.out.println("Password updated successfully!");
    }

    private static void startExam() {
        if (isExamActive) {
            System.out.println("You are already in an ongoing exam.");
            return;
        }

        isExamActive = true;
        System.out.println("\n=== Exam Started ===");
        String[] questions = {
            "Q1: What is the capital of France? \n1. Paris  2. Berlin  3. Madrid  4. Rome",
            "Q2: Which is the largest planet? \n1. Mars  2. Earth  3. Jupiter  4. Venus",
            "Q3: What is the boiling point of water? \n1. 50°C  2. 100°C  3. 200°C  4. 150°C"
        };
        int[] answers = {1, 3, 2}; // Correct answers
        int score = 0;

        long examStartTime = System.currentTimeMillis();
        int timeLimit = 60; // Time limit in seconds
        for (int i = 0; i < questions.length; i++) {
            long elapsedTime = (System.currentTimeMillis() - examStartTime) / 1000;
            if (elapsedTime >= timeLimit) {
                System.out.println("Time's up! Exam auto-submitted.");
                break;
            }

            System.out.println(questions[i]);
            System.out.print("Enter your answer (1-4): ");
            int userAnswer = scanner.nextInt();

            if (userAnswer == answers[i]) {
                score++;
            }
        }

        System.out.println("\n=== Exam Ended ===");
        System.out.println("Your score: " + score + "/" + questions.length);
        isExamActive = false;
    }
}
