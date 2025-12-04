package ATM2;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {
	private static final long serialVersionUID = 123456789987654321L;
    JLabel l1, l2;
    JButton b1, b2, b3, b4, b5, b6, b7, b8;
    JTextField t1;
    String mode,amount;
    String pin;

    FastCash(String pin , String mode, String amount) {
        this.pin = pin;
        this.mode=mode;
        this.amount=amount;
        Icon icon = new ImageIcon("G:\\atm.JPG");
		 Image i2 = ((ImageIcon) icon).getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
		 ImageIcon i3 = new ImageIcon(i2);
		 JLabel label = new JLabel(i3);
		 label.setBounds(0,0,960,1080);
		 add(label);

        l1 = new JLabel("SELECT WITHDRAWL AMOUNT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        b1 = new JButton("50$");
        b2 = new JButton("100$");
        b3 = new JButton("200$");
        b4 = new JButton("500$");
        b5 = new JButton("1000$");
        b6 = new JButton("2000$");
        b7 = new JButton("BACK");

        setLayout(null);

        l1.setBounds(235, 400, 700, 35);
        label.add(l1);

        b1.setBounds(170, 499, 150, 35);
        label.add(b1);

        b2.setBounds(390, 499, 150, 35);
        label.add(b2);

        b3.setBounds(170, 543, 150, 35);
        label.add(b3);

        b4.setBounds(390, 543, 150, 35);
        label.add(b4);

        b5.setBounds(170, 588, 150, 35);
        label.add(b5);

        b6.setBounds(390, 588, 150, 35);
        label.add(b6);

        b7.setBounds(390, 633, 150, 35);
        label.add(b7);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);

        setSize(960, 1080);
        setLocation(500, 0);
        setUndecorated(true);
        setVisible(true);

    }



    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b7) {
            setVisible(false);
            new Transaction(pin).setVisible(true);
            return;
        }
        try {
            double selectedAmount = 0;
            boolean validSelection = true;

            if (ae.getSource() == b1) {
                selectedAmount = 50;
            } else if (ae.getSource() == b2) {
                selectedAmount = 100;
            } else if (ae.getSource() == b3) {
                selectedAmount = 200;
            } else if (ae.getSource() == b4) {
                selectedAmount = 500;
            } else if (ae.getSource() == b5) {
                selectedAmount = 1000;
            } else if (ae.getSource() == b6) {
                selectedAmount = 2000;
            } else {
                validSelection = false;
            }

            if (validSelection) {
                Date date = new Date();
                String checkBalanceQuery = "SELECT bank_amount FROM bank WHERE bank_pin = ?";
                String updateBalanceQuery = "UPDATE bank SET bank_amount = bank_amount - ? WHERE bank_pin = ?";
                String insertTransactionQuery = "INSERT INTO withdraw (withdraw_pin, withdraw_type, withdraw_amount, withdraw_date) VALUES (?, ?, ?, ?)";
                String fetchSignupQuery = "SELECT atype FROM signupthree WHERE signupthree_pin = ?";
                String fetchLoginQuery = "SELECT login_formno FROM login WHERE login_pin = ?";
                String updateBankQuery = "UPDATE bank SET bank_date = ?, bank_type = ?, login_formno = ? WHERE bank_pin = ?";

                try (Connection conn = ConnectionManager.getConnection();
                     PreparedStatement checkStmt = conn.prepareStatement(checkBalanceQuery);
                     PreparedStatement updateStmt = conn.prepareStatement(updateBalanceQuery);
                     PreparedStatement insertStmt = conn.prepareStatement(insertTransactionQuery);
                     PreparedStatement fetchSignupStmt = conn.prepareStatement(fetchSignupQuery);
                     PreparedStatement fetchLoginStmt = conn.prepareStatement(fetchLoginQuery);
                     PreparedStatement updateBankStmt = conn.prepareStatement(updateBankQuery)) {

                    conn.setAutoCommit(false); // Start transaction

                    // Check current balance
                    double currentBalance = 0;
                    checkStmt.setString(1, pin);
                    ResultSet rs = checkStmt.executeQuery();
                    if (rs.next()) {
                        currentBalance = rs.getDouble("bank_amount");
                        if (currentBalance < selectedAmount) {
                            JOptionPane.showMessageDialog(null, "Insufficient Balance");
                            conn.rollback(); // Rollback transaction if not enough money
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Account not found");
                        conn.rollback(); // Rollback transaction if account not found
                        return;
                    }


                    updateStmt.setDouble(1, selectedAmount);
                    updateStmt.setString(2, pin);
                    updateStmt.executeUpdate();


                    insertStmt.setString(1, pin);
                    insertStmt.setString(2, "Withdrawal");
                    insertStmt.setDouble(3, selectedAmount);
                    insertStmt.setTimestamp(4, new Timestamp(date.getTime()));
                    insertStmt.executeUpdate();


                    fetchSignupStmt.setString(1, pin);
                    ResultSet signupRs = fetchSignupStmt.executeQuery();
                    String atype = null;
                    if (signupRs.next()) {
                        atype = signupRs.getString("atype");
                    }


                    fetchLoginStmt.setString(1, pin);
                    ResultSet loginRs = fetchLoginStmt.executeQuery();
                    int loginFormNo = 0;
                    if (loginRs.next()) {
                        loginFormNo = loginRs.getInt("login_formno");
                    }

                    // Update bank table with the fetched values
                    updateBankStmt.setTimestamp(1, new Timestamp(date.getTime()));
                    updateBankStmt.setString(2, atype);
                    updateBankStmt.setInt(3, loginFormNo);
                    updateBankStmt.setString(4, pin);
                    updateBankStmt.executeUpdate();

                    conn.commit(); // Commit transaction

                    JOptionPane.showMessageDialog(this ,"$ " + amount + mode);
                    setVisible(false);
                    new Transaction(pin).setVisible(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Transaction failed");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid selection, please choose a valid amount.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric value.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred. Please try again.");
        }

    }

}


