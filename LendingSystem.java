import java.sql.*;
import java.util.Scanner;

public class LendingSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/laindain_project4";
    private static final String USER = "root"; 
    private static final String PASS = "#6006289501";  
    
    private static Connection conn = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");

           
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to the database successfully!");

            while (true) {
                System.out.println("\n--- Lending System ---");
                System.out.println("1. Add Entry");
                System.out.println("2. View All Transactions");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        addEntry();
                        break;
                    case 2:
                        viewTransactions();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    private static void addEntry() {
        System.out.print("Enter Attendee Name: ");
        String attendee = scanner.nextLine();

        System.out.print("Enter Event ID: ");
        int eventId = scanner.nextInt();
        
        System.out.print("Enter Transaction Date (yyyy-mm-dd): ");
        String date = scanner.next();
        
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        
        String query = "INSERT INTO transactions (attendee, event_id, transaction_date, amount) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, attendee);
            pstmt.setInt(2, eventId);
            pstmt.setString(3, date);
            pstmt.setDouble(4, amount);
            pstmt.executeUpdate();
            System.out.println("Entry added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding entry.");
            e.printStackTrace();
        }
    }

    private static void viewTransactions() {
        String query = "SELECT * FROM transactions";
        
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String attendee = rs.getString("attendee");
                int eventId = rs.getInt("event_id");
                String date = rs.getString("transaction_date");
                double amount = rs.getDouble("amount");
                System.out.println("ID: " + id + ", Attendee: " + attendee + ", Event ID: " + eventId + ", Date: " + date + ", Amount: " + amount);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving transactions.");
            e.printStackTrace();
        }
    }
}
