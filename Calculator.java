import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextField display;
    private double firstOperand = 0;
    private String operator = "";
    private boolean newInput = true;
    private StringBuilder currentInput = new StringBuilder();

    public Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout(5, 5));

        // Display
        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Segoe UI", Font.BOLD, 32));
        display.setBackground(new Color(20, 20, 20));
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        display.setPreferredSize(new Dimension(340, 75));
        add(display, BorderLayout.NORTH);

        // Button panel
        JPanel panel = new JPanel(new GridLayout(5, 4, 6, 6));
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        String[] labels = {
                "9", "8", "7", "/",
                "6", "5", "4", "*",
                "3", "2", "1", "-",
                "0", ".", "=", "+",
                "C", "", "", ""
        };

        for (String label : labels) {
            if (label.isEmpty()) {
                panel.add(new JLabel());
                continue;
            }
            panel.add(makeBtn(label));
        }

        add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton makeBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 22));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addActionListener(this);

        Color bg, fg;
        switch (text) {
            case "+":
            case "-":
            case "*":
            case "/":
                bg = new Color(255, 149, 0);
                fg = Color.WHITE;
                break;
            case "=":
                bg = new Color(10, 132, 255);
                fg = Color.WHITE;
                break;
            case "C":
                bg = new Color(200, 50, 50);
                fg = Color.WHITE;
                break;
            default:
                bg = new Color(70, 70, 70);
                fg = Color.WHITE;
        }

        b.setBackground(bg);
        b.setForeground(fg);
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {
            case "C":
                currentInput.setLength(0);
                firstOperand = 0;
                operator = "";
                newInput = true;
                display.setText("0");
                break;

            case "+":
            case "-":
            case "*":
            case "/":
                if (currentInput.length() > 0)
                    firstOperand = Double.parseDouble(currentInput.toString());
                operator = cmd;
                currentInput.setLength(0);
                newInput = false;
                break;

            case "=":
                if (!operator.isEmpty() && currentInput.length() > 0) {
                    double b = Double.parseDouble(currentInput.toString());
                    double result = calc(firstOperand, b, operator);
                    String txt = (result == (long) result)
                            ? String.valueOf((long) result)
                            : String.valueOf(result);
                    display.setText(txt);
                    currentInput.setLength(0);
                    currentInput.append(txt);
                    operator = "";
                    newInput = true;
                }
                break;

            case ".":
                if (!currentInput.toString().contains(".")) {
                    if (currentInput.length() == 0)
                        currentInput.append("0");
                    currentInput.append(".");
                    display.setText(currentInput.toString());
                }
                break;

            default:
                if (newInput && operator.isEmpty()) {
                    currentInput.setLength(0);
                    newInput = false;
                }
                currentInput.append(cmd);
                display.setText(currentInput.toString());
        }
    }

    private double calc(double a, double b, String op) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return b != 0 ? a / b : 0;
            default:
                return b;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}