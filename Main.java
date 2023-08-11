import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        public TestPane() {
            setBorder(new EmptyBorder(52, 52, 52, 52));
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = gbc.LINE_END;
            gbc.insets = new Insets(6, 8, 6, 8);
            
            add(new JLabel("User Name:"), gbc);
            gbc.gridy++;
            gbc.ipady=4;
            add(new JLabel("Password:"), gbc);

            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.anchor = gbc.LINE_START;
            add(new JTextField(10), gbc);
            gbc.gridy++;
            add(new JTextField(10), gbc);

            JPanel actionPane = new JPanel(new GridBagLayout());
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = gbc.HORIZONTAL;
            gbc.insets = new Insets(6, 12, 6, 12);

            actionPane.add(new JButton("encyprt!"), gbc);
            gbc.gridx++;
            actionPane.add(new JButton("decyprt!"), gbc);

            gbc = new GridBagConstraints();
            gbc.gridwidth = gbc.REMAINDER;
            gbc.gridy = 2;
  
            add(actionPane, gbc);
            gbc.gridy++;
            gbc.insets = new Insets(6, 0, 6, 0);
            add(new JLabel("© Project realized by Ayoub Touti"), gbc);
        }
    }
}
