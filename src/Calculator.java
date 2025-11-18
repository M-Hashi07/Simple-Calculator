import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {

    int boardWidth = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    String[] buttonValues = {
        "AC", "+/-", "%", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };

    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%", "√"};

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    // Calculator internal state
    String A = "0";
    String operator = null;
    String B = null;

    public Calculator() {

        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // DISPLAY
        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        // BUTTON GRID
        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        // CREATE BUTTONS
        for (int i = 0; i < buttonValues.length; i++) {
            
            JButton button = new JButton();
            String buttonValue = buttonValues[i];

            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));

            // COLOR LOGIC
            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);

            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);

            } else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }

            buttonsPanel.add(button);

            // BUTTON ACTION
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();

                    // RIGHT SIDE OPERATORS
                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {

                        // "=" PRESSED
                        if (buttonValue.equals("=")) {

                            if (A != null && operator != null) {

                                B = displayLabel.getText();

                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                if (operator.equals("+"))
                                    displayLabel.setText(removeZeroDecimal(numA + numB));
                                else if (operator.equals("-"))
                                    displayLabel.setText(removeZeroDecimal(numA - numB));
                                else if (operator.equals("×"))
                                    displayLabel.setText(removeZeroDecimal(numA * numB));
                                else if (operator.equals("÷"))
                                    displayLabel.setText(removeZeroDecimal(numA / numB));

                                clearAll();
                            }

                        }
                        // OPERATOR PRESSED
                        else if ("+-×÷".contains(buttonValue)) {

                            if (operator == null) {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }

                            operator = buttonValue;
                        }
                    }

                    // TOP BUTTONS (AC, +/-, %, √)
                    else if (Arrays.asList(topSymbols).contains(buttonValue)) {

                        // AC
                        if (buttonValue.equals("AC")) {
                            clearAll();
                            displayLabel.setText("0");
                        }

                        // +/- SIGN CHANGE
                        else if (buttonValue.equals("+/-")) {
                            double num = Double.parseDouble(displayLabel.getText());
                            num *= -1;
                            displayLabel.setText(removeZeroDecimal(num));
                        }

                        // PERCENT
                        else if (buttonValue.equals("%")) {
                            double num = Double.parseDouble(displayLabel.getText());
                            num /= 100;
                            displayLabel.setText(removeZeroDecimal(num));
                        }

                        // SQUARE ROOT FUNCTION
                        else if (buttonValue.equals("√")) {
                            double num = Double.parseDouble(displayLabel.getText());
                            if (num >= 0) {
                                num = Math.sqrt(num);
                                displayLabel.setText(removeZeroDecimal(num));
                            } else {
                                displayLabel.setText("Error");
                            }
                        }
                    }

                    // DIGITS AND DECIMAL POINT
                    else {

                        // DECIMAL POINT
                        if (buttonValue.equals(".")) {
                            if (!displayLabel.getText().contains(".")) {
                                displayLabel.setText(displayLabel.getText() + ".");
                            }
                        }

                        // DIGITS 0-9
                        else if ("0123456789".contains(buttonValue)) {

                            if (displayLabel.getText().equals("0")) {
                                displayLabel.setText(buttonValue);
                            } else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                    }
                }
            });
        }

        frame.setVisible(true);
    }

    // RESET INTERNAL STATE
    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }

    // FORMAT NUMBERS (remove trailing .0)
    String removeZeroDecimal(double num) {
        if (num % 1 == 0) {
            return Integer.toString((int) num);
        }
        return Double.toString(num);
    }

    // PROGRAM ENTRY POINT
    public static void main(String[] args) {
        new Calculator();
    }
}
