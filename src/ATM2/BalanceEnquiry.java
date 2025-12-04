package ATM2;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


class BalanceEnquiry extends JFrame implements ActionListener {
	private static final long serialVersionUID = 123456789987654321L;
    JButton b1;
    JLabel l1;
    String pin;

    BalanceEnquiry(String pin) {
        this.pin = pin;

        Icon icon = new ImageIcon("G:\\atm.JPG");
        Image i2 = ((ImageIcon) icon).getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(0, 0, 960, 1080);
        add(label);

        l1 = new JLabel();
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        b1 = new JButton("BACK");

        setLayout(null);

        l1.setBounds(190, 350, 400, 35);
        label.add(l1);

        b1.setBounds(390, 633, 150, 35);
        label.add(b1);

        String query = "SELECT bank_amount FROM bank WHERE bank_pin = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pin);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int amount = rs.getInt("bank_amount");
                l1.setText("Your Current Account Balance is $ " + amount);
            } else {
                l1.setText("Account not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        b1.addActionListener(this);

        setSize(960, 1080);
        setUndecorated(true);
        setLocation(500, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transaction(pin).setVisible(true);
    }

    public static void main(String[] args) {

    }
}