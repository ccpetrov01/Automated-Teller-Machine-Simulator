package ATM2;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Pin extends JFrame implements ActionListener {
	private static final long serialVersionUID = 123456789987654321L;
    JPasswordField t1,t2;
    JButton b1,b2;                               
    JLabel l1,l2,l3;
    String pin;
    Pin(String pin){
        this.pin = pin;
        Icon icon = new ImageIcon("G:\\atm.JPG");
		 Image i2 = ((ImageIcon) icon).getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
		 ImageIcon i3 = new ImageIcon(i2);
		 JLabel label = new JLabel(i3);
		 label.setBounds(0,0,960,1080);
		 add(label);
        
        l1 = new JLabel("CHANGE YOUR PIN");
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setForeground(Color.WHITE);
        
        l2 = new JLabel("Old Pin:");
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setForeground(Color.WHITE);
        
        l3 = new JLabel("New Pin:");
        l3.setFont(new Font("System", Font.BOLD, 16));
        l3.setForeground(Color.WHITE);
        
        t1 = new JPasswordField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));
        
        t2 = new JPasswordField();
        t2.setFont(new Font("Raleway", Font.BOLD, 25));
        
        b1 = new JButton("CHANGE");
        b2 = new JButton("BACK");
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        setLayout(null);
        
        l1.setBounds(280,330,800,35);
        label.add(l1);
        
        l2.setBounds(180,390,150,35);
        label.add(l2);
        
        l3.setBounds(180,440,200,35);
        label.add(l3);
        
        t1.setBounds(350,390,180,25);
        label.add(t1);
        
        t2.setBounds(350,440,180,25);
        label.add(t2);
        
        b1.setBounds(390,588,150,35);
        label.add(b1);
        
        b2.setBounds(390,633,150,35);
        label.add(b2);
        
        setSize(960,1080);
        setLocation(500,0);
        setUndecorated(true);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b2) {
            setVisible(false);
            new Transaction(pin).setVisible(true);
            return;
        }

        try {
            char[] oldPinChars = t1.getPassword();
            char[] newPinChars = t2.getPassword();

            String oldPinStr = String.valueOf(oldPinChars);
            String newPinStr = String.valueOf(newPinChars);

            if (!oldPinStr.matches("\\d{4}") || !newPinStr.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(null, "PIN must be a 4-digit number.");
                return;
            }

            // Hash the old and new PINs
            String hashedOldPin = hashPin(oldPinStr);
            String hashedNewPin = hashPin(newPinStr);

            String checkOldPinQuery = "SELECT login_pin FROM login WHERE login_pin = ?";
            String updateLoginPinQuery = "UPDATE login SET login_pin = ? WHERE login_pin = ?";
            String updateBankPinQuery = "UPDATE bank SET bank_pin = ? WHERE bank_pin = ?";

            try (Connection conn = ConnectionManager.getConnection()) {
                conn.setAutoCommit(false);

                // Check if the old PIN is correct
                try (PreparedStatement checkStmt = conn.prepareStatement(checkOldPinQuery)) {
                    checkStmt.setString(1, hashedOldPin);
                    ResultSet rs = checkStmt.executeQuery();
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(null, "Old PIN is incorrect.");
                        conn.rollback();
                        return;
                    }
                }

                // Prevent using the same old PIN as the new PIN
                if (hashedOldPin.equals(hashedNewPin)) {
                    JOptionPane.showMessageDialog(null, "The new PIN cannot be the same as the old PIN.");
                    conn.rollback();
                    return;
                }

                // Update login table with the new PIN
                try (PreparedStatement updateLoginStmt = conn.prepareStatement(updateLoginPinQuery)) {
                    updateLoginStmt.setString(1, hashedNewPin);
                    updateLoginStmt.setString(2, hashedOldPin);
                    updateLoginStmt.executeUpdate();
                }

                // Update bank table with the new PIN
                try (PreparedStatement updateBankStmt = conn.prepareStatement(updateBankPinQuery)) {
                    updateBankStmt.setString(1, hashedNewPin);
                    updateBankStmt.setString(2, hashedOldPin);
                    updateBankStmt.executeUpdate();
                }

                conn.commit();

                JOptionPane.showMessageDialog(null, "PIN changed successfully");
                setVisible(false);
                new Transaction(newPinStr).setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to change PIN.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a 4-digit numeric value.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred. Please try again.");
        }
    }

    private String hashPin(String pin) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(pin.getBytes());

        // Convert the byte array into a hex string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
