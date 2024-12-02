import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

// Class representing a registered user
class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter method for username
    public String getUsername() {
        return username;
    }

    // Method to validate the password
    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }
}


// Class representing a train reservation
class Reservation {
    private String pnr;
    private String passengerName;
    private int trainNumber;
    private String trainName;
    private String classType;
    private String journeyDate;
    private String fromStation;
    private String toStation;

    public Reservation(String passengerName, int trainNumber, String trainName, String classType, 
                       String journeyDate, String fromStation, String toStation) {
        this.pnr = UUID.randomUUID().toString().substring(0, 8); // Generate unique PNR
        this.passengerName = passengerName;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.journeyDate = journeyDate;
        this.fromStation = fromStation;
        this.toStation = toStation;
    }

    public String getPnr() {
        return pnr;
    }

    @Override
    public String toString() {
        return "PNR: " + pnr + "\nPassenger Name: " + passengerName + "\nTrain Number: " + trainNumber +
               "\nTrain Name: " + trainName + "\nClass: " + classType + "\nJourney Date: " + journeyDate +
               "\nFrom: " + fromStation + "\nTo: " + toStation;
    }
}

// Main class for the Online Reservation System
public class OnlineReservationSystem {
    private static ArrayList<User> userList = new ArrayList<>();
    private static ArrayList<Reservation> reservationList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeUsers();

        System.out.println("=== Welcome to the Online Reservation System ===");
        if (login()) {
            boolean exit = false;
            while (!exit) {
                System.out.println("\nMain Menu:");
                System.out.println("1. Make a Reservation");
                System.out.println("2. Cancel a Reservation");
                System.out.println("3. View All Reservations");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1 -> makeReservation();
                    case 2 -> cancelReservation();
                    case 3 -> viewAllReservations();
                    case 4 -> {
                        System.out.println("Thank you for using the Online Reservation System. Goodbye!");
                        exit = true;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Login failed. Exiting the system.");
        }
    }

    // Method to initialize predefined users
    private static void initializeUsers() {
        userList.add(new User("admin", "admin123"));
        userList.add(new User("user1", "password1"));
    }

    // Login method
    private static boolean login() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (User user : userList) {
            if (user.getUsername().equals(username) && user.validatePassword(password)) {
                System.out.println("Login successful!");
                return true;
            }
        }
        System.out.println("Invalid username or password.");
        return false;
    }

    // Method to make a reservation
    private static void makeReservation() {
        System.out.print("Enter Passenger Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Train Number: ");
        int trainNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Example train names based on train number
        String trainName = switch (trainNumber) {
            case 101 -> "Rajdhani Express";
            case 102 -> "Shatabdi Express";
            case 103 -> "Garib Rath";
            default -> "Generic Train";
        };

        System.out.print("Enter Class Type (e.g., AC/Sleeper): ");
        String classType = scanner.nextLine();
        System.out.print("Enter Journey Date (YYYY-MM-DD): ");
        String journeyDate = scanner.nextLine();
        System.out.print("Enter From Station: ");
        String fromStation = scanner.nextLine();
        System.out.print("Enter To Station: ");
        String toStation = scanner.nextLine();

        // Create and store reservation
        Reservation reservation = new Reservation(name, trainNumber, trainName, classType, journeyDate, fromStation, toStation);
        reservationList.add(reservation);

        System.out.println("Reservation successful!");
        System.out.println("Your PNR: " + reservation.getPnr());
    }

    // Method to cancel a reservation
    private static void cancelReservation() {
        System.out.print("Enter PNR Number to Cancel: ");
        String pnr = scanner.nextLine();

        Reservation reservationToCancel = null;
        for (Reservation reservation : reservationList) {
            if (reservation.getPnr().equalsIgnoreCase(pnr)) {
                reservationToCancel = reservation;
                break;
            }
        }

        if (reservationToCancel != null) {
            System.out.println("Reservation Details:");
            System.out.println(reservationToCancel);
            System.out.print("Are you sure you want to cancel this reservation? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                reservationList.remove(reservationToCancel);
                System.out.println("Reservation canceled successfully!");
            } else {
                System.out.println("Cancellation aborted.");
            }
        } else {
            System.out.println("PNR not found. Please check and try again.");
        }
    }

    // Method to view all reservations
    private static void viewAllReservations() {
        if (reservationList.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("All Reservations:");
            for (Reservation reservation : reservationList) {
                System.out.println("--------------------");
                System.out.println(reservation);
            }
        }
    }
}
