import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lorenzo on 10/1/2015.
 *
 */
public class HomePage extends JFrame {
    JLabel welcomeLabel;
    JPanel rootPanel;
    JButton logoutButton;

    public HomePage(String name, FarmSector sector) {
        super("Welcome Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        welcomeLabel.setText("Welcome " + name + "!");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WelcomePage();
                setVisible(false);
                dispose();
            }
        });

    }
}
