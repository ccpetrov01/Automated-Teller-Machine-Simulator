package ATM2;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Transaction extends JFrame implements ActionListener {
	private static final long serialVersionUID = 123456789987654321L;
    JLabel l1;
    JButton b1,b2,b3,b4,b5,b6,b7;
    String pin;
    String mode,amount;
    Transaction(String pin){
        this.pin = pin;
        Icon icon = new ImageIcon("G:\\atm.JPG");
		 Image i2 = ((ImageIcon) icon).getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
		 ImageIcon i3 = new ImageIcon(i2);
		 JLabel label = new JLabel(i3);
		 label.setBounds(0,0,960,1080);
		 add(label);
        
        l1 = new JLabel("Please Select Your Transaction");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        
       
        b1 = new JButton("DEPOSIT");
        b2 = new JButton("CASH WITHDRAWL");
        b3 = new JButton("FAST CASH");
        b4 = new JButton("MINI STATEMENT");
        b5 = new JButton("PIN CHANGE");
        b6 = new JButton("BALANCE ENQUIRY");
        b7 = new JButton("EXIT");
        
        setLayout(null);
        
        l1.setBounds(235,400,700,35);
        label.add(l1);
        
        b1.setBounds(170,499,150,35);
        label.add(b1);
        
        b2.setBounds(390,499,150,35);
        label.add(b2);
        
        b3.setBounds(170,543,150,35);
        label.add(b3);
        
        b4.setBounds(390,543,150,35);
        label.add(b4);
        
        b5.setBounds(170,588,150,35);
        label.add(b5);
        
        b6.setBounds(390,588,150,35);
        label.add(b6);
        
        b7.setBounds(390,633,150,35);
        label.add(b7);
        
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        
        
        setSize(960,1080);
        setLocation(500,0);
        setUndecorated(true);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            new Deposit(pin).setVisible(true);
        } else if (ae.getSource() == b2) {
            new Withdrawl(pin).setVisible(true);
        } else if (ae.getSource() == b3) {
            new FastCash(pin, mode, amount).setVisible(true);
        } else if (ae.getSource() == b4 || ae.getSource() == b5) {
            new Pin(pin).setVisible(true);
        } else if (ae.getSource() == b6) {
            new BalanceEnquiry(pin).setVisible(true);
        } else if (ae.getSource() == b7) {
            System.exit(0);
        } else {
            return;
        }
        dispose();
    }

}