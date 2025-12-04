package ATM2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedReader;
import java.io.FileReader;
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


public class DepositHistory extends JFrame {
    private static final long serialVersionUID = 123456789987654321L;
    private static DepositHistory historyWindow;
    private JTextArea textArea;
    private String deposit_pin;

    public DepositHistory(String deposit_pin) {
        this.deposit_pin = deposit_pin;
        setTitle("Deposit History");
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
        setLocation(0, 0);
        setVisible(true);
        historyWindow = this;

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setLocation(0, 0);
            }
        });
    }

    public static void refresh(String deposit_pin) {
        if (historyWindow == null) {
            historyWindow = new DepositHistory(deposit_pin);
        } else {
            historyWindow.deposit_pin = deposit_pin;
            historyWindow.updateContent();
        }
    }

    public void updateContent() {
        String query = "SELECT * FROM deposit WHERE deposit_pin = ? ORDER BY id DESC";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, deposit_pin);
            try (ResultSet rs = stmt.executeQuery()) {
                StringBuilder content = new StringBuilder();

                while (rs.next()) {
                    int pin = rs.getInt("deposit_pin");
                    String type = rs.getString("deposit_type");
                    int amount = rs.getInt("deposit_amount");
                    Timestamp date = rs.getTimestamp("deposit_date");

                    content.append("PIN: ").append(pin)
                            .append(", Type: ").append(type)
                            .append(", Amount: $").append(amount)
                            .append(", Date: ").append(date.toString())
                            .append("\n");
                }

                textArea.setText(content.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e);
        }
    }
}