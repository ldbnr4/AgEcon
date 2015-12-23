import javax.swing.*;

/**
 * Created by Lorenzo on 9/22/2015.
 *
 */
public class WelcomePage extends JFrame {
    private JPanel rootPanel;
    private JButton loginButton;
    private JButton newUserButton;

    public WelcomePage() {
        super("Welcome Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        loginButton.addActionListener(e -> {
            new LoginPage();
            setVisible(false);
            dispose();
        });
        newUserButton.addActionListener(e -> {
            new CreatePage();
            setVisible(false);
            dispose();
        });
    }
}
