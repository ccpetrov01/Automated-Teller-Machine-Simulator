package ATM2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BankHistory extends JFrame {
    private static final long serialVersionUID = 123456789987654321L;
    private static BankHistory bankWindow;
    private JTextArea textArea;
    private String bankPin;


    public BankHistory(String bankPin) {
        this.bankPin = bankPin;
        setTitle("Bank History");
        setUndecorated(true);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        updateContent();

        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 1080);
        setLocation(1450, 0);
        setVisible(true);
        bankWindow = this;

        addComponentListener(new ComponentAdapter() {
            public void componentMoved(ComponentEvent e) {
                setLocation(1450, 0);
            }
        });
    }

    public static void refresh(String bankPin) {
        if (bankWindow == null) {
            bankWindow = new BankHistory(bankPin);
        } else {
            bankWindow.bankPin = bankPin;
            bankWindow.updateContent();
        }
    }

    private void updateContent() {
        String query = "SELECT * FROM bank WHERE bank_pin = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, bankPin);
            try (ResultSet rs = stmt.executeQuery()) {
                StringBuilder content = new StringBuilder();

                while (rs.next()) {
                    int pin = rs.getInt("bank_pin");
                    String type = rs.getString("bank_type");
                    int amount = rs.getInt("bank_amount");
                    Timestamp date = rs.getTimestamp("bank_date");

                    content.append("PIN: ").append(pin)
                            .append(", Type: ").append(type)
                            .append(", Amount: $").append(amount)
                            .append(", Date: ").append(date.toString())
                            .append("\n");
                }


                System.out.println("Content: " + content.toString());

                textArea.setText(content.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e);
        }
    }}

