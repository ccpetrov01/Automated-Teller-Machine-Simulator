package ATM2;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {
	private static final long serialVersionUID = 123456789987654321L;
	private static final int MAX_ATTEMPTS = 3;
	private static final long LOCKOUT_DURATION = 300000;

	private int attemptCounter = 0;
	private long lockoutEndTime = 0;
	JButton login, clear, signup;
	JLabel pin, cardNo, text, label;
	JTextField cardTextField;
	JPasswordField pinTextField;

	Login() {
		setTitle("ATM");
		setLayout(null);

		Icon icon = new ImageIcon("G:\\logo.JPG");
		Image i2 = ((ImageIcon) icon).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		label = new JLabel(i3);
		label.setBounds(70, 10, 100, 100);
		add(label);

		text = new JLabel("Welcome to ATM");
		text.setFont(new Font("Osward", Font.BOLD, 38));
		text.setBounds(200, 40, 400, 40);
		add(text);

		cardNo = new JLabel("Card No.");
		cardNo.setFont(new Font("Raleway", Font.BOLD, 28));
		cardNo.setBounds(120, 150, 400, 40);
		add(cardNo);

		cardTextField = new JTextField();
		cardTextField.setBounds(300, 150, 250, 30);
		cardTextField.setFont(new Font("Arial", Font.BOLD, 14));
		add(cardTextField);

		pin = new JLabel("PIN:");
		pin.setFont(new Font("Raleway", Font.BOLD, 28));
		pin.setBounds(120, 220, 400, 40);
		add(pin);

		pinTextField = new JPasswordField();
		pinTextField.setBounds(300, 220, 250, 30);
		pinTextField.setFont(new Font("Arial", Font.BOLD, 14));
		add(pinTextField);

		login = new JButton("SIGN IN");
		login.setBounds(300, 300, 100, 30);
		login.setBackground(Color.BLACK);
		login.setForeground(Color.WHITE);
		login.addActionListener(this);
		add(login);

		clear = new JButton("CLEAR");
		clear.setBounds(430, 300, 100, 30);
		clear.setBackground(Color.BLACK);
		clear.setForeground(Color.WHITE);
		clear.addActionListener(this);
		add(clear);


		signup = new JButton("SIGN UP");
		signup.setBounds(300, 350, 230, 30);
		signup.setBackground(Color.BLACK);
		signup.setForeground(Color.WHITE);
		signup.addActionListener(this);
		add(signup);


		getContentPane().setBackground(Color.WHITE);

		setSize(800, 480);
		setVisible(true);
		setLocation(350, 200);
	}

	public void actionPerformed(ActionEvent ae) {
		try {
			if (ae.getSource() == login) {
				String cardno = cardTextField.getText();
				char[] pinChars = pinTextField.getPassword();
				String pin = new String(pinChars);

				if (System.currentTimeMillis() < lockoutEndTime) {
					JOptionPane.showMessageDialog(null, "You are temporarily locked out. Please try again later.");
					return;
				}

				if (cardTextField.getText().isEmpty() || pinTextField.getPassword().length == 0) {
					JOptionPane.showMessageDialog(null, "Card Number or PIN is empty");
					return;
				}
				if (!cardno.matches("\\d{16}")) {
					JOptionPane.showMessageDialog(null, "Card Number must be a 16-digit number");
					return;
				}
				if (!pin.matches("\\d{4}")) {
					JOptionPane.showMessageDialog(null, "PIN must be a 4-digit number");
					return;
				}

				long finalcardno = Long.parseLong(cardno);
				Connection conn = ConnectionManager.getConnection();
				String query = "SELECT login_cardno, login_pin FROM login WHERE login_cardno = ? AND login_pin = ?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setLong(1, finalcardno);
				pstmt.setString(2, pin);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					setVisible(false);
					new Transaction(pin).setVisible(true);
				} else {
					attemptCounter++;
					if (attemptCounter >= MAX_ATTEMPTS) {
						lockoutEndTime = System.currentTimeMillis() + LOCKOUT_DURATION;
						JOptionPane.showMessageDialog(null, "Too many failed attempts. You are locked out for 300 seconds.");
						attemptCounter = 0;
					} else {
						JOptionPane.showMessageDialog(null, "Incorrect Card Number or PIN. Attempts left: " + (MAX_ATTEMPTS - attemptCounter));
					}
				}

				rs.close();
				pstmt.close();
				conn.close();
			} else if (ae.getSource() == clear) {
				cardTextField.setText("");
				pinTextField.setText("");
			} else if (ae.getSource() == signup) {
				setVisible(false);
				new SignupOne().setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		new Login();
	}
}