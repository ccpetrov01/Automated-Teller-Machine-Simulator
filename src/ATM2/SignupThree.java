package ATM2;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupThree extends JFrame implements ActionListener{
	private static final long serialVersionUID = 123456789987654321L;
    JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12;
    JRadioButton r1,r2,r3,r4;
    JButton b1,b2;
    JCheckBox c1,c2,c3,c4,c5,c6,c7;
    String formno;
    
    
    SignupThree(String formno){
        this.formno = formno;
        setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 3");
    
        Icon icon = new ImageIcon("G:\\logo.JPG");
		 Image i2 = ((ImageIcon) icon).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		 ImageIcon i3 = new ImageIcon(i2);
		 JLabel label = new JLabel(i3);
		 label.setBounds(20,0,100,100);
		 add(label);
		
        
        l1 = new JLabel("Page 3: Account Details");
        l1.setFont(new Font("Raleway", Font.BOLD, 22));
        
        l2 = new JLabel("Account Type:");
        l2.setFont(new Font("Raleway", Font.BOLD, 18));
        
        l3 = new JLabel("Card Number:");
        l3.setFont(new Font("Raleway", Font.BOLD, 18));
        
        l4 = new JLabel("XXXX-XXXX-XXXX-4184");
        l4.setFont(new Font("Raleway", Font.BOLD, 18));
        
        l5 = new JLabel("(Your 16-digit Card number)");
        l5.setFont(new Font("Raleway", Font.BOLD, 12));
        
        l6 = new JLabel("It would appear on ATM Card/Cheque Book and Statements");
        l6.setFont(new Font("Raleway", Font.BOLD, 12));
        
        l7 = new JLabel("PIN:");
        l7.setFont(new Font("Raleway", Font.BOLD, 18));
        
        l8 = new JLabel("XXXX");
        l8.setFont(new Font("Raleway", Font.BOLD, 18));
    
        l9 = new JLabel("(4-digit password)");
        l9.setFont(new Font("Raleway", Font.BOLD, 12));
    
        l10 = new JLabel("Services Required:");
        l10.setFont(new Font("Raleway", Font.BOLD, 18));
        
        l11 = new JLabel("Form No:");
        l11.setFont(new Font("Raleway", Font.BOLD, 14));
        
        l12 = new JLabel(formno);
        l12.setFont(new Font("Raleway", Font.BOLD, 14));
        
        b1 = new JButton("Submit");
        b1.setFont(new Font("Raleway", Font.BOLD, 14));
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        
        b2 = new JButton("Cancel");
        b2.setFont(new Font("Raleway", Font.BOLD, 14));
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        
        
        c1 = new JCheckBox("ATM CARD");
        c1.setBackground(Color.WHITE);
        c1.setFont(new Font("Raleway", Font.BOLD, 16));
        
        c2 = new JCheckBox("Internet Banking");
        c2.setBackground(Color.WHITE);
        c2.setFont(new Font("Raleway", Font.BOLD, 16));
        
        c3 = new JCheckBox("Mobile Banking");
        c3.setBackground(Color.WHITE);
        c3.setFont(new Font("Raleway", Font.BOLD, 16));
        
        c4 = new JCheckBox("EMAIL Alerts");
        c4.setBackground(Color.WHITE);
        c4.setFont(new Font("Raleway", Font.BOLD, 16));
        
        c5 = new JCheckBox("Cheque Book");
        c5.setBackground(Color.WHITE);
        c5.setFont(new Font("Raleway", Font.BOLD, 16));
        
        c6 = new JCheckBox("E-Statement");
        c6.setBackground(Color.WHITE);
        c6.setFont(new Font("Raleway", Font.BOLD, 16));
        
        c7 = new JCheckBox("I hereby declares that the above entered details correct to th best of my knowledge.",true);
        c7.setBackground(Color.WHITE);
        c7.setFont(new Font("Raleway", Font.BOLD, 12));
         
        
        r1 = new JRadioButton("Saving Account");
        r1.setFont(new Font("Raleway", Font.BOLD, 16));
        r1.setBackground(Color.WHITE);
        
        r2 = new JRadioButton("Fixed Deposit Account");
        r2.setFont(new Font("Raleway", Font.BOLD, 16));
        r2.setBackground(Color.WHITE);
        
        r3 = new JRadioButton("Current Account");
        r3.setFont(new Font("Raleway", Font.BOLD, 16));
        r3.setBackground(Color.WHITE);
        
        r4 = new JRadioButton("Recurring Deposit Account");
        r4.setFont(new Font("Raleway", Font.BOLD, 16));
        r4.setBackground(Color.WHITE);
        
        ButtonGroup groupgender = new ButtonGroup();
        groupgender.add(r1);
        groupgender.add(r2);
        groupgender.add(r3);
        groupgender.add(r4);
        
        setLayout(null);
        
        l11.setBounds(700,10,70,30);
        add(l11);
        
        l12.setBounds(770,10,40,30);
        add(l12);
        
        l1.setBounds(280,40,400,40);
        add(l1); 
        
        l2.setBounds(100,140,200,30);
        add(l2);
        
        r1.setBounds(100,180,150,30);
        add(r1);
        
        r2.setBounds(350,180,300,30);
        add(r2);
        
        r3.setBounds(100,220,250,30);
        add(r3);
        
        r4.setBounds(350,220,250,30);
        add(r4);
        
        l3.setBounds(100,300,200,30);
        add(l3);
        
        l4.setBounds(330,300,250,30);
        add(l4);
        
        l5.setBounds(100,330,200,20);
        add(l5);
        
        l6.setBounds(330,330,500,20);
        add(l6);
        
        l7.setBounds(100,370,200,30);
        add(l7);
        
        l8.setBounds(330,370,200,30);
        add(l8);
        
        l9.setBounds(100,400,200,20);
        add(l9);
        
        l10.setBounds(100,450,200,30);
        add(l10);
        
        c1.setBounds(100,500,200,30);
        add(c1);
        
        c2.setBounds(350,500,200,30);
        add(c2);
        
        c3.setBounds(100,550,200,30);
        add(c3);
        
        c4.setBounds(350,550,200,30);
        add(c4);
        
        c5.setBounds(100,600,200,30);
        add(c5);
        
        c6.setBounds(350,600,200,30);
        add(c6);
        
        c7.setBounds(100,680,600,20);
        add(c7);
        
        b1.setBounds(250,720,100,30);
        add(b1);
        
        b2.setBounds(420,720,100,30);
        add(b2);
        
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        getContentPane().setBackground(Color.WHITE);
        
        setSize(850,850);
        setLocation(500,120);
        setVisible(true);
        

        
    }

    public void actionPerformed(ActionEvent ae) {
        String atype = null;
        if (r1.isSelected()) {
            atype = "Saving Account";
        } else if (r2.isSelected()) {
            atype = "Fixed Deposit Account";
        } else if (r3.isSelected()) {
            atype = "Current Account";
        } else if (r4.isSelected()) {
            atype = "Recurring Deposit Account";
        }

        Random ran = new Random();
        long first7 = (ran.nextLong() % 90000000L) + 5040936000000000L;
        String cardno = "" + Math.abs(first7);
        long finalcardno = Long.parseLong(cardno);

        long first3 = (ran.nextLong() % 9000L) + 1000L;
        Integer pin = Integer.parseInt(String.valueOf(Math.abs(first3)));


        String pinString = pin.toString();

        try {
            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            byte[] hashBytes = digest.digest(pinString.getBytes());


            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            String hashedPin = hexString.toString();

            String facility = "";
            if (c1.isSelected()) {
                facility = facility + " ATM Card";
            }
            if (c2.isSelected()) {
                facility = facility + " Internet Banking";
            }
            if (c3.isSelected()) {
                facility = facility + " Mobile Banking";
            }
            if (c4.isSelected()) {
                facility = facility + " EMAIL Alerts";
            }
            if (c5.isSelected()) {
                facility = facility + " Cheque Book";
            }
            if (c6.isSelected()) {
                facility = facility + " E-Statement";
            }

            try {
                if (ae.getSource() == b1) {
                    if (atype == null || atype.equals("")) {
                        JOptionPane.showMessageDialog(null, "Fill all the required fields");
                        return;
                    }


                    if (!cardno.matches("\\d{16}")) {
                        JOptionPane.showMessageDialog(null, "Invalid Card Number");
                        return;
                    }


                    if (!pin.toString().matches("\\d{4}")) {
                        JOptionPane.showMessageDialog(null, "Invalid PIN");
                        return;
                    }

                    Connection conn = ConnectionManager.getConnection();


                    String checkQuery = "SELECT signupthree_formno FROM signupthree WHERE signupthree_formno = ?";
                    PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                    checkStmt.setInt(1, Integer.parseInt(formno));
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next()) {
                        rs.close();
                        checkStmt.close();
                        conn.close();
                        JOptionPane.showMessageDialog(null, "Form number already exists");
                        return;
                    }
                    rs.close();
                    checkStmt.close();


                    String insertQuery = "INSERT INTO signupthree (signupthree_formno, atype, cardno, signuptwo_formno, signupthree_pin) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                    insertStmt.setInt(1, Integer.parseInt(formno));
                    insertStmt.setString(2, atype);
                    insertStmt.setLong(3, finalcardno);
                    insertStmt.setInt(4, Integer.parseInt(formno));
                    insertStmt.setString(5, hashedPin);
                    insertStmt.executeUpdate();
                    insertStmt.close();


                    String insertLoginQuery = "INSERT INTO login (login_formno, login_cardno, login_pin , signupthree_formno) VALUES (?, ?, ?, ?)";
                    PreparedStatement insertLoginStmt = conn.prepareStatement(insertLoginQuery);
                    insertLoginStmt.setInt(1, Integer.parseInt(formno));
                    insertLoginStmt.setLong(2, finalcardno);
                    insertLoginStmt.setString(3, hashedPin);
                    insertLoginStmt.setInt(4, Integer.parseInt(formno));
                    insertLoginStmt.executeUpdate();
                    insertLoginStmt.close();

                    conn.close();

                    JOptionPane.showMessageDialog(null, "Card Number: " + cardno + "\n Pin: " + pin);
                    new Login().setVisible(true);
                    setVisible(false);
                } else if (ae.getSource() == b2) {
                    System.exit(0);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving data to database");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }}

