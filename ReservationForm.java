import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.UUID;

public class ReservationForm extends JFrame implements ActionListener {
    JLabel nameLabel, trainNoLabel, trainNameLabel, classLabel, dateLabel, fromLabel, toLabel;
    JTextField nameField, trainNoField, trainNameField, classField, dateField, fromField, toField;
    JButton insertButton;

    public ReservationForm() {
        setTitle("Reservation Form");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        nameLabel = new JLabel("Name:");
        trainNoLabel = new JLabel("Train Number:");
        trainNameLabel = new JLabel("Train Name:");
        classLabel = new JLabel("Class:");
        dateLabel = new JLabel("Date:");
        fromLabel = new JLabel("From:");
        toLabel = new JLabel("To:");

        nameField = new JTextField(15);
        trainNoField = new JTextField(15);
        trainNameField = new JTextField(15);
        classField = new JTextField(15);
        dateField = new JTextField(15);
        fromField = new JTextField(15);
        toField = new JTextField(15);

        insertButton = new JButton("Insert");

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(trainNoLabel);
        panel.add(trainNoField);
        panel.add(trainNameLabel);
        panel.add(trainNameField);
        panel.add(classLabel);
        panel.add(classField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(fromLabel);
        panel.add(fromField);
        panel.add(toLabel);
        panel.add(toField);
        panel.add(new JLabel());
        panel.add(insertButton);

        add(panel);
        insertButton.addActionListener(this);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText().trim();
        String trainNo = trainNoField.getText().trim();
        String trainName = trainNameField.getText().trim();
        String cls = classField.getText().trim();
        String date = dateField.getText().trim();
        String from = fromField.getText().trim();
        String to = toField.getText().trim();

        // Generate unique PNR using UUID short string
        String pnr = UUID.randomUUID().toString().substring(0, 8);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reservation.txt", true))) {
            writer.write(pnr + "|" + name + "|" + trainNo + "|" + trainName + "|" + cls + "|" + date + "|" + from + "|" + to);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Ticket booked!\nPNR: " + pnr);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ReservationForm();
    }
}