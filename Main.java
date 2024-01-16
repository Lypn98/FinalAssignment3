// MyProject/src/com/example/Main.java



import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("JCalendar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JDateChooser dateChooser = new JDateChooser();

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            Date selectedDate = dateChooser.getDate();
            if (selectedDate != null) {
                System.out.println("Selected Date: " + selectedDate);
            } else {
                System.out.println("No date selected.");
            }
        });

        JPanel panel = new JPanel();
        panel.add(dateChooser);
        panel.add(okButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
