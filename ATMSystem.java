import java.util.ArrayList;
import java.util.Scanner;

class User {
    private String userId;
    private String pin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean verifyPin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: $" + amount);
            System.out.println("Successfully deposited $" + amount);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount);
            System.out.println("Successfully withdrew $" + amount);
        } else {
            System.out.println("Insufficient balance or invalid amount!");
        }
    }

    public void transfer(User recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.deposit(amount); // Use recipient's deposit method
            transactionHistory.add("Transferred: $" + amount + " to " + recipient.getUserId());
            System.out.println("Successfully transferred $" + amount + " to " + recipient.getUserId());
        } else {
            System.out.println("Insufficient balance or invalid amount!");
        }
    }

    public void showTransactionHistory() {
        System.out.println("=== Transaction History ===");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}

public class ATMSystem {
    private static User currentUser = null;
    private static ArrayList<User> users = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Sample user accounts for demo
        users.add(new User("user1", "1234"));
        users.add(new User("user2", "5678"));

        System.out.println("Welcome to the ATM System!");
        if (authenticateUser()) {
            displayMenu();
        } else {
            System.out.println("Authentication failed. Exiting system.");
        }
    }

    private static boolean authenticateUser() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        for (User user : users) {
            if (user.getUserId().equals(userId) && user.verifyPin(pin)) {
                currentUser = user;
                System.out.println("Login successful!");
                return true;
            }
        }
        return false;
    }

    private static void displayMenu() {
        boolean continueSession = true;

        while (continueSession) {
            System.out.println("\n=== ATM Menu ===");
            System.out.println("1. View Transaction History");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Deposit Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Exit");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    currentUser.showTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    currentUser.withdraw(withdrawalAmount);
                    break;
                case 3:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    currentUser.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient User ID: ");
                    scanner.nextLine(); // Consume newline
                    String recipientId = scanner.nextLine();
                    User recipient = findUserById(recipientId);
                    if (recipient != null) {
                        System.out.print("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();
                        currentUser.transfer(recipient, transferAmount);
                    } else {
                        System.out.println("Recipient not found!");
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM system. Goodbye!");
                    continueSession = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}

