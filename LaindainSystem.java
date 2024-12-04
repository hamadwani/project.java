import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class LaindainSystem {
   
    static final String DB_URL = "jdbc:mysql://localhost:3306/laindain_project4";  
    static final String USER = "root"; 
    static final String PASSWORD = "#6006289501";  

    
    private JFrame frame;
    private JTextField txtAttendeeName;
    private JTextField txtEventId;
    private JTextField txtEventDate;
    private JTextField txtAmount;

    
    public static void main(String[] args) {
       
        EventQueue.invokeLater(() -> {
            try {
                LaindainSystemGUI window = new LaindainSystemGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    
    public LaindainSystemGUI() {
       
        frame = new JFrame("Laindain System");
        frame.setBounds(100, 100, 450, 300);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.getContentPane().setLayout(new FlowLayout());  

       
        JLabel lblAttendeeName = new JLabel("Attendee Name:");
        frame.getContentPane().add(lblAttendeeName);
        
        txtAttendeeName = new JTextField();
        frame.getContentPane().add(txtAttendeeName);
        txtAttendeeName.setColumns(20); 

        JLabel lblEventId = new JLabel("Event ID:");
        frame.getContentPane().add(lblEventId);
        
        txtEventId = new JTextField();
        frame.getContentPane().add(txtEventId);
        txtEventId.setColumns(10); 

        JLabel lblEventDate = new JLabel("Event Date (yyyy-mm-dd):");
        frame.getContentPane().add(lblEventDate);
        
        txtEventDate = new JTextField();
        frame.getContentPane().add(txtEventDate);
        txtEventDate.setColumns(10);  

        JLabel lblAmount = new JLabel("Amount:");
        frame.getContentPane().add(lblAmount);
        
        txtAmount = new JTextField();
        frame.getContentPane().add(txtAmount);
        txtAmount.setColumns(10);  

       
        JButton btnSubmit = new JButton("Submit");
        frame.getContentPane().add(btnSubmit);


        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                String attendeeName = txtAttendeeName.getText();
                String eventId = txtEventId.getText();
                String eventDate = txtEventDate.getText();
                String amount = txtAmount.getText();

               
                insertRecord(attendeeName, eventId, eventDate, amount);
            }
        });
    }

   
    public static Connection connect() {
        try {
           
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
       
            JOptionPane.showMessageDialog(null, "Connection failed: " + e.getMessage());
            return null;
        }
    }

   
    public static void insertRecord(String attendeeName, String eventId, String eventDate, String amount) {
        String query = "INSERT INTO event_records (attendee_name, event_id, event_date, amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, attendeeName);  
            stmt.setInt(2, Integer.parseInt(eventId));  
            stmt.setDate(3, Date.valueOf(eventDate)); 
            stmt.setDouble(4, Double.parseDouble(amount));  
          
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
               
                JOptionPane.showMessageDialog(null, "Record inserted successfully.");
            }
        } catch (SQLException e) {
      
            JOptionPane.showMessageDialog(null, "Error inserting record: " + e.getMessage());
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Invalid input data: " + e.getMessage());
        }
    }
}