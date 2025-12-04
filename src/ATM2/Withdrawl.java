package ATM2;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class Withdrawl extends JFrame implements ActionListener {
    private static final long serialVersionUID = 123456789987654321L;
    JTextField t1;
    JButton b1, b2, b3;
    JLabel l1, l2;
    String pin;

    Withdrawl(String pin) {
        this.pin = pin;
        Icon icon = new ImageIcon("G:\\atm.JPG");
        Image i2 = ((ImageIcon) icon).getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(0, 0, 960, 1080);
        add(label);

        l1 = new JLabel("MAXIMUM WITHDRAWAL IS 2000$");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        l2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("System", Font.BOLD, 16));

        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));

        b1 = new JButton("WITHDRAW");
        b2 = new JButton("BACK");
        b3 = new JButton("Withdrawal History");

        setLayout(null);

        l1.setBounds(190, 350, 400, 20);
        label.add(l1);

        l2.setBounds(190, 400, 400, 20);
        label.add(l2);

        t1.setBounds(190, 450, 330, 30);
        label.add(t1);

        b1.setBounds(390, 588, 150, 35);
        label.add(b1);

        b2.setBounds(390, 633, 150, 35);
        label.add(b2);

        b3.setBounds(200, 633, 150, 35);
        label.add(b3);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        setSize(960, 1080);
        setLocation(500, 0);
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
            String amountStr = t1.getText();
            double amount = 0;

            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numeric amount");
                return;
            }

            if (ae.getSource() == b1) {
                if (amountStr.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the amount you want to withdraw");
                } else {
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

                        conn.setAutoCommit(false);


                        double currentBalance = 0;
                        checkStmt.setString(1, pin);
                        ResultSet rs = checkStmt.executeQuery();
                        if (rs.next()) {
                            currentBalance = rs.getDouble("bank_amount");
                            if (currentBalance < amount) {
                                JOptionPane.showMessageDialog(null, "Insufficient Balance");
                                conn.rollback(); // Rollback transaction if not enough money
                                return;
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Account not found");
                            conn.rollback(); // Rollback transaction if account not found
                            return;
                        }


                        updateStmt.setDouble(1, amount);
                        updateStmt.setString(2, pin);
                        updateStmt.executeUpdate();


                        insertStmt.setString(1, pin);
                        insertStmt.setString(2, "Withdrawal");
                        insertStmt.setDouble(3, amount);
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

                        JOptionPane.showMessageDialog(this , "$ " + amount + " Withdrawn Successfully");
                        WithdrawHistory.refresh(pin);
                        BankHistory.refresh(pin);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Transaction failed");
                    }
                }
            }
             else if (ae.getSource() == b3) {
                WithdrawHistory.refresh(pin);
                BankHistory.refresh(pin);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e);
        }
    }
}
