package com.mycompany.Calc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class calculator extends JFrame implements ActionListener {

    JTextField display;
    JLabel expressionLabel;
    JButton[] numberButtons = new JButton[10];
    JButton addBtn, subBtn, mulBtn, divBtn, equBtn, clrBtn;
    double num1 = 0, num2 = 0, result = 0;
    char operator = '\0';
    boolean startNewNumber = true;
    boolean errorState = false;

    public calculator() {
        setTitle("Calculator");
        setSize(330, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(245, 245, 245));

        expressionLabel = new JLabel("");
        expressionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        expressionLabel.setForeground(Color.GRAY);
        expressionLabel.setHorizontalAlignment(JLabel.RIGHT);
        expressionLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 15));

        display = new JTextField();
        display.setFont(new Font("Segoe UI", Font.BOLD, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        topPanel.add(expressionLabel, BorderLayout.NORTH);
        topPanel.add(display, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 4, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(245, 245, 245));

        Font btnFont = new Font("Segoe UI", Font.BOLD, 16);
        Color numColor = Color.WHITE;
        Color opColor = new Color(220, 220, 220);
        Color eqColor = new Color(180, 200, 255);

        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            styleButton(numberButtons[i], btnFont, numColor);
            numberButtons[i].addActionListener(this);
        }

        addBtn = new JButton("+");
        subBtn = new JButton("-");
        mulBtn = new JButton("*");
        divBtn = new JButton("/");
        equBtn = new JButton("=");
        clrBtn = new JButton("C");

        JButton[] ops = { addBtn, subBtn, mulBtn, divBtn, clrBtn };
        for (JButton b : ops) {
            styleButton(b, btnFont, opColor);
            b.addActionListener(this);
        }

        styleButton(equBtn, btnFont, eqColor);
        equBtn.addActionListener(this);

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(divBtn);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(mulBtn);
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(subBtn);
        panel.add(clrBtn);
        panel.add(numberButtons[0]);
        panel.add(equBtn);
        panel.add(addBtn);

        add(panel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton btn, Font font, Color bg) {
        btn.setFont(font);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setRolloverEnabled(false);
    }

    private String formatNumber(double n) {
        if (n == (long) n) return String.valueOf((long) n);
        return String.valueOf(n);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (errorState) {
            if (e.getSource() == clrBtn) {
                display.setText("");
                expressionLabel.setText("");
                operator = '\0';
                startNewNumber = true;
                errorState = false;
                return;
            }
            for (int i = 0; i < 10; i++) {
                if (e.getSource() == numberButtons[i]) {
                    display.setText(String.valueOf(i));
                    expressionLabel.setText("");
                    operator = '\0';
                    startNewNumber = false;
                    errorState = false;
                    return;
                }
            }
            return;
        }

        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                if (startNewNumber) {
                    display.setText(String.valueOf(i));
                    startNewNumber = false;
                } else {
                    display.setText(display.getText() + i);
                }
                if (operator != '\0') {
                    expressionLabel.setText(
                        formatNumber(num1) + " " + operator + " " + display.getText()
                    );
                }
                return;
            }
        }

        if (e.getSource() == clrBtn) {
            display.setText("");
            expressionLabel.setText("");
            operator = '\0';
            startNewNumber = true;
            return;
        }

        if (e.getSource() == addBtn || e.getSource() == subBtn ||
            e.getSource() == mulBtn || e.getSource() == divBtn) {

            num1 = Double.parseDouble(display.getText());
            operator = ((JButton) e.getSource()).getText().charAt(0);
            expressionLabel.setText(formatNumber(num1) + " " + operator);
            startNewNumber = true;
            return;
        }

        if (e.getSource() == equBtn) {

            if (operator == '\0') return;

            num2 = Double.parseDouble(display.getText());

            switch (operator) {
                case '+': result = num1 + num2; break;
                case '-': result = num1 - num2; break;
                case '*': result = num1 * num2; break;
                case '/':
                    if (num2 == 0) {
                        display.setText("Not Defined");
                        expressionLabel.setText("");
                        operator = '\0';
                        startNewNumber = true;
                        errorState = true;
                        return;
                    }
                    result = num1 / num2;
                    break;
            }

            display.setText(formatNumber(result));
            expressionLabel.setText("");
            num1 = result;
            operator = '\0';
            startNewNumber = true;
        }
    }

    public static void main(String[] args) {
        new calculator();
    }
}

