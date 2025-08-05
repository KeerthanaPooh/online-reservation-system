import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class CancellationForm extends JFrame implements ActionListener {
    JLabel pnrLabel;
    JTextField pnrField;
    JButton searchButton, cancelButton;
    JTextArea outputArea;

    public CancellationForm() {
        setTitle("Cancellation Form");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pnrLabel = new JLabel("Enter PNR Number:");
        pnrField = new JTextField(15);
        searchButton = new JButton("Search");
        cancelButton = new JButton("Cancel Ticket");
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.add(pnrLabel);
        panel.add(pnrField);
        panel.add(searchButton);
        panel.add(cancelButton);
        panel.add(new JScrollPane(outputArea));

        add(panel);
        setVisible(true);

        searchButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        String pnr = pnrField.getText().trim();
        File inputFile = new File("reservation.txt");
        File tempFile = new File("temp.txt");

        boolean found = false;
        StringBuilder ticketDetails = new StringBuilder();

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 8 && parts[0].equals(pnr)) {
                    found = true;
                    ticketDetails.append("PNR: ").append(parts[0]).append("\n")
                            .append("Name: ").append(parts[1]).append("\n")
                            .append("Train Number: ").append(parts[2]).append("\n")
                            .append("Train Name: ").append(parts[3]).append("\n")
                            .append("Class: ").append(parts[4]).append("\n")
                            .append("Date: ").append(parts[5]).append("\n")
                            .append("From: ").append(parts[6]).append("\n")
                            .append("To: ").append(parts[7]).append("\n\n")
                            .append("Ticket with this PNR has been cancelled.");
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage());
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
            outputArea.setText(ticketDetails.toString());
        } else {
            outputArea.setText("No ticket found with this PNR.");
        }
    }

    public static void main(String[] args) {
        new CancellationForm();
    }
}