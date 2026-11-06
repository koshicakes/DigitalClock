import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class DigitalClock {

    private static final DateTimeFormatter TIME_12 = DateTimeFormatter.ofPattern("hh:mm:ss a");
    private static final DateTimeFormatter TIME_24 = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");

    private static boolean use24Hour = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JLabel timeLabel = new JLabel("", SwingConstants.CENTER);
            timeLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 64));
            timeLabel.setForeground(new Color(0, 255, 140));

            JLabel dateLabel = new JLabel("", SwingConstants.CENTER);
            dateLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            dateLabel.setForeground(Color.LIGHT_GRAY);

            JPanel display = new JPanel(new GridLayout(2, 1));
            display.setBackground(Color.BLACK);
            display.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
            display.add(timeLabel);
            display.add(dateLabel);

            // Refreshes both labels with the current time
            Runnable update = () -> {
                LocalDateTime now = LocalDateTime.now();
                timeLabel.setText(now.format(use24Hour ? TIME_24 : TIME_12));
                dateLabel.setText(now.format(DATE));
            };

            JButton toggle = new JButton("Switch to 24-hour");
            toggle.addActionListener(e -> {
                use24Hour = !use24Hour;
                toggle.setText(use24Hour ? "Switch to 12-hour" : "Switch to 24-hour");
                update.run();
            });

            update.run();
            new Timer(1000, e -> update.run()).start();

            JFrame frame = new JFrame("Digital Clock");
            frame.add(display, BorderLayout.CENTER);
            frame.add(toggle, BorderLayout.SOUTH);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}