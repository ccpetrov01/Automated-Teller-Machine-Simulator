package ATM2;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SignupTwo extends JFrame implements ActionListener {
    private static final long serialVersionUID = 123456789987654321L;
    JLabel l1, l2, l4, l5, l6, l10, l11, l12, l13;
    JButton b;
    JRadioButton r1, r2;
    JTextField t1, t2;
    JComboBox<String> c1, c3, c4, c5;

    String formno;

    SignupTwo(String formno) {
        Icon icon = new ImageIcon("G:\\logo.JPG");
        Image i2 = ((ImageIcon) icon).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(150, 0, 100, 100);
        add(label);

        this.formno = formno;
        setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 2");

        l1 = new JLabel("Page 2: Additional Details");
        l1.setFont(new Font("Raleway", Font.BOLD, 22));

        l2 = new JLabel("Religion:");
        l2.setFont(new Font("Raleway", Font.BOLD, 18));

        l4 = new JLabel("Income:");
        l4.setFont(new Font("Raleway", Font.BOLD, 18));

        l5 = new JLabel("Educational");
        l5.setFont(new Font("Raleway", Font.BOLD, 18));

        l11 = new JLabel("Qualification:");
        l11.setFont(new Font("Raleway", Font.BOLD, 18));

        l6 = new JLabel("Occupation:");
        l6.setFont(new Font("Raleway", Font.BOLD, 18));

        l10 = new JLabel("Existing Account:");
        l10.setFont(new Font("Raleway", Font.BOLD, 18));

        l12 = new JLabel("Form No:");
        l12.setFont(new Font("Raleway", Font.BOLD, 13));

        l13 = new JLabel(formno);
        l13.setFont(new Font("Raleway", Font.BOLD, 13));

        b = new JButton("Next");
        b.setFont(new Font("Raleway", Font.BOLD, 14));
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);

        r1 = new JRadioButton("Yes");
        r1.setFont(new Font("Raleway", Font.BOLD, 14));
        r1.setBackground(Color.WHITE);

        r2 = new JRadioButton("No");
        r2.setFont(new Font("Raleway", Font.BOLD, 14));
        r2.setBackground(Color.WHITE);

        ButtonGroup Radio1 = new ButtonGroup();
        Radio1.add(r1);
        Radio1.add(r2);

        String religion[] = {" ","Hindu", "Muslim", "Sikh", "Christian", "Other"};
        c1 = new JComboBox<String>(religion);
        c1.setBackground(Color.WHITE);
        c1.setFont(new Font("Raleway", Font.BOLD, 14));

        String income[] = {" ", "500-1000$", "1000-1500$", "1500-2500$", "2500-5000$", "Above 5000$"};
        c3 = new JComboBox<String>(income);
        c3.setBackground(Color.WHITE);
        c3.setFont(new Font("Raleway", Font.BOLD, 14));

        String education[] = {" ","Non Graduate", "Graduate", "Post Graduate", "Doctorate", "Others"};
        c4 = new JComboBox<String>(education);
        c4.setBackground(Color.WHITE);
        c4.setFont(new Font("Raleway", Font.BOLD, 14));

        String occupation[] = {" ","Salaried", "Self Employed", "Business", "Student", "Retired", "Others"};
        c5 = new JComboBox<String>(occupation);
        c5.setBackground(Color.WHITE);
        c5.setFont(new Font("Raleway", Font.BOLD, 14));

        setLayout(null);

        l12.setBounds(700, 10, 60, 30);
        add(l12);

        l13.setBounds(760, 10, 60, 30);
        add(l13);

        l1.setBounds(280, 30, 600, 40);
        add(l1);

        l2.setBounds(100, 120, 100, 30);
        add(l2);

        c1.setBounds(350, 120, 320, 30);
        add(c1);

        l4.setBounds(100, 170, 100, 30);
        add(l4);

        c3.setBounds(350, 170, 320, 30);
        add(c3);

        l5.setBounds(100, 220, 150, 30);
        add(l5);

        l11.setBounds(100, 250, 150, 30);
        add(l11);

        c4.setBounds(350, 250, 320, 30);
        add(c4);

        l6.setBounds(100, 300, 150, 30);
        add(l6);

        c5.setBounds(350, 300, 320, 30);
        add(c5);

        l10.setBounds(100, 350, 180, 30);
        add(l10);

        r1.setBounds(350, 350, 100, 30);
        add(r1);

        r2.setBounds(460, 350, 100, 30);
        add(r2);

        b.setBounds(570, 400, 100, 30);
        add(b);

        b.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);

        setSize(850, 750);
        setLocation(500, 120);
        setVisible(true);
    }





    @Override
    public void actionPerformed(ActionEvent ae) {
        String regex = "^[a-zA-Z\\s]+$";
        String incomeRegex = "^(\\d{3,4})-(\\d{3,4})\\$$";
        String religion = (String) c1.getSelectedItem();
        String income = (String) c3.getSelectedItem();
        String education = (String) c4.getSelectedItem();
        String occupation = (String) c5.getSelectedItem();

        if (religion == null || !religion.matches(regex)) {
            JOptionPane.showMessageDialog(null, "Invalid input in religion: Please enter only alphabetic characters.");
            return;
        }

        if (income == null || !income.matches(incomeRegex)) {
            JOptionPane.showMessageDialog(null, "Invalid input in income: Please enter only alphabetic characters.");
            return;
        }

        if (education == null || !education.matches(regex)) {
            JOptionPane.showMessageDialog(null, "Invalid input in education: Please enter only alphabetic characters.");
            return;
        }

        if (occupation == null || !occupation.matches(regex)) {
            JOptionPane.showMessageDialog(null, "Invalid input in occupation: Please enter only alphabetic characters.");
            return;
        }


        //String formattedIncome = income.replace("$", "").replace(",", "").trim();


        try {
            Connection conn = ConnectionManager.getConnection();

            String query = "INSERT INTO \"signuptwo\" (\"signuptwo_formno\", \"religion\", \"income\", \"education\", \"occupation\", \"signuponeformno\") VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(formno));
            pstmt.setString(2, religion);
            pstmt.setString(3, income);
            pstmt.setString(4, education);
            pstmt.setString(5, occupation);
            pstmt.setInt(6, Integer.parseInt(formno));

            System.out.println("SQL Query: " + pstmt.toString());

            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            new SignupThree(formno).setVisible(true);
            setVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving data to database.");
        }
    }


    public static void main(String[] args) {

    }
}


