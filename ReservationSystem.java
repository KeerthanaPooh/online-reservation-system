import javax.swing.*;

public class ReservationSystem {
    public static void main(String[] args) {
        // Launch Login Form first
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginForm(); // opens login window
            }
        });
    }
}