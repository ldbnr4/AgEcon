/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEgonPackage;

import javax.swing.*;

/**
 * Created by Lorenzo on 1/31/2016.
 *
 */
public class WaitPage extends JFrame {
    private JPanel rootPanel;
    private JButton logoutButton;

    public WaitPage() {
        super("Wait Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        logoutButton.addActionListener(e -> {
            new WelcomePage();
            setVisible(false);
            dispose();
        });
    }
}

