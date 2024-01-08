
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;


public class ArithmeticCalculatorGUI {
    private JFrame frame;
    private JTextField inputField;
    private JTextArea resultArea;
    private JButton calculateButton;
    private JButton refreshButton;
    private ArithmeticParser parser;
    public ArithmeticCalculatorGUI() {
        frame = new JFrame("Arithmetic Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        inputField = new JTextField(20);
        resultArea = new JTextArea(10, 20);
        resultArea.setEditable(false);

        calculateButton = new JButton("Calculate");
        refreshButton = new JButton("Refresh");

        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);
        inputPanel.add(calculateButton);
        inputPanel.add(refreshButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                ArithmeticParser parser = new ArithmeticParser(new ByteArrayInputStream(input.getBytes()));
                try {
                    int result = parser.Start();
                    resultArea.setText("Result: " + result);
                } catch (ParseException ex) {
                    resultArea.setText("Error: " + ex.getMessage());
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.setText("");
                resultArea.setText("");
                calculateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String input = inputField.getText();
                        ArithmeticParser parser = new ArithmeticParser(new ByteArrayInputStream(input.getBytes()));
                        try {
                            int result = parser.Start();
                            resultArea.setText("Result: " + result);
                        }catch (ParseException ex) {
                            resultArea.setText("Error: " + ex.getMessage());
                        }
                    }
                });
            }
        });
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ArithmeticCalculatorGUI();
            }
        });
    }
}
