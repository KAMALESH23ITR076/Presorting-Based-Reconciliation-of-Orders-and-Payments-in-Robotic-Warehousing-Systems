import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class WarehousePresortingGUI extends JFrame {

    private JTextArea ordersTextArea;
    private JTextArea paymentsTextArea;
    private JTextArea resultTextArea;
    private JButton checkButton;

    public WarehousePresortingGUI() {
        setTitle("Presorting - Order Payment Validator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        ordersTextArea = new JTextArea(15, 30);
        paymentsTextArea = new JTextArea(15, 30);
        resultTextArea = new JTextArea(10, 60);
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane orderScroll = new JScrollPane(ordersTextArea);
        JScrollPane paymentScroll = new JScrollPane(paymentsTextArea);
        JScrollPane resultScroll = new JScrollPane(resultTextArea);

        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data"));

        inputPanel.add(wrapWithLabel("Orders (One per line):", orderScroll));
        inputPanel.add(wrapWithLabel("Payments (One per line):", paymentScroll));

        checkButton = new JButton("Check Orders and Payments");
        checkButton.addActionListener(this::checkPresorting);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(checkButton);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Result"));
        resultPanel.add(resultScroll, BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.SOUTH);
    }

    private JPanel wrapWithLabel(String label, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private void checkPresorting(ActionEvent e) {
        String[] orders = ordersTextArea.getText().split("\\R+");
        String[] payments = paymentsTextArea.getText().split("\\R+");

        Set<String> orderSet = new HashSet<>(Arrays.asList(orders));
        Set<String> paymentSet = new HashSet<>(Arrays.asList(payments));

        List<String> notPaidOrders = new ArrayList<>();
        List<String> unmatchedPayments = new ArrayList<>();

        for (String order : orderSet) {
            if (!paymentSet.contains(order)) {
                notPaidOrders.add(order);
            }
        }

        for (String payment : paymentSet) {
            if (!orderSet.contains(payment)) {
                unmatchedPayments.add(payment);
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("=== Orders NOT Paid For ===\n");
        if (notPaidOrders.isEmpty()) {
            result.append("✓ All orders are paid.\n");
        } else {
            for (String o : notPaidOrders) {
                result.append(" - ").append(o).append("\n");
            }
        }

        result.append("\n=== Payments NOT Matching Any Orders ===\n");
        if (unmatchedPayments.isEmpty()) {
            result.append("✓ All payments match orders.\n");
        } else {
            for (String p : unmatchedPayments) {
                result.append(" - ").append(p).append("\n");
            }
        }

        resultTextArea.setText(result.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WarehousePresortingGUI gui = new WarehousePresortingGUI();
            gui.setVisible(true);
        });
    }
}