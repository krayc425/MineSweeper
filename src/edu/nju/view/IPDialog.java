package edu.nju.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IPDialog {

    public IPDialog(JFrame parent){

        panel = new JPanel();
        dialog = new JDialog(parent, "IP Setting", true);
        IPField = new JTextField();
        IPLabel = new JLabel("Enter Server's IP:");
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");

        Font font = new Font("Monospaced", Font.PLAIN, 12);

        IPLabel.setFont(font);
        okBtn.setFont(font);
        cancelBtn.setFont(font);

        IPLabel.setBounds(30, 15, 150, 25);
        IPField.setBounds(30, 50, 150, 25);
        okBtn.setBounds(30, 90, 70, 25);
        cancelBtn.setBounds(110, 90, 70, 25);

        //默认是回环地址
        IPField.setText("127.0.0.1");

        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ok = true;
                dialog.setVisible(false);
            }

        });

        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }

        });

        panel.setLayout(null);
        panel.add(IPLabel);
        panel.add(IPField);
        panel.add(okBtn);
        panel.add(cancelBtn);

        dialog.setContentPane(panel);
        this.parent = parent;
    }

    public boolean show() {
        ok = false;
        dialog.setBounds(parent.getX() + 50, parent.getY() + 50, 215, 175);
        dialog.setVisible(true);
        parse();
        return ok;
    }

    public String getIP() {
        return IP;
    }

    private boolean parse() {
        try {
            this.IP = IPField.getText();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private JFrame parent;

    private JPanel panel;

    private JDialog dialog;

    private JTextField IPField;

    private JLabel IPLabel;

    private JButton okBtn;

    private JButton cancelBtn;

    private boolean ok;

    private String IP;
}