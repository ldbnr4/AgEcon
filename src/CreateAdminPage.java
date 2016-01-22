import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.MinimalBalloonStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Lorenzo on 11/22/2015.
 *
 */
public class CreateAdminPage extends JFrame implements ActionListener {
    JPanel rootPanel;
    JButton backBtn;
    JTextField username_tf;
    JPasswordField password_pf;
    JPasswordField confimpassword_pf;
    JButton submitBtn;
    private BalloonTipStyle modern;
    private BalloonTip uNameBalloonTip;
    private BalloonTip passBalloonTip;
    private BalloonTip confPassBalloonTip;
    private UNameVerifier usernameVerifier;
    private PassVerifier passwordVerifier;
    private PassVerifier confPassVerifier;
    private Admin admin;

    public CreateAdminPage(final Admin admin) {
        super("Create Admin");
        setContentPane(rootPanel);

        this.admin = admin;
        modern = new MinimalBalloonStyle(Color.white, 5);
        uNameBalloonTip = new BalloonTip(username_tf, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.uNameBalloonTip.setVisible(false);
        passBalloonTip = new BalloonTip(password_pf, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.passBalloonTip.setVisible(false);
        confPassBalloonTip = new BalloonTip(confimpassword_pf, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.confPassBalloonTip.setVisible(false);

        backBtn.addActionListener(e -> {
            new AdminDecisionPage(admin);
            setVisible(false);
            dispose();
        });
        
        submitBtn.addActionListener(this);

        usernameVerifier = new UNameVerifier(uNameBalloonTip);
        username_tf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!username_tf.getBackground().equals(Color.GREEN)) {
                    username_tf.setText("");
                    username_tf.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                usernameVerifier.verify(username_tf, admin);
            }
        });

        passwordVerifier = new PassVerifier(passBalloonTip);
        password_pf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (password_pf.getBackground() != Color.GREEN) {
                    password_pf.setEchoChar('•');
                    password_pf.setText("");
                    password_pf.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (username_tf.getBackground() == Color.green) {
                    passwordVerifier.verifyLive(password_pf);
                }
            }
        });

        confPassVerifier = new PassVerifier(confPassBalloonTip);
        confimpassword_pf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (confimpassword_pf.getBackground() != Color.GREEN) {
                    confimpassword_pf.setEchoChar('•');
                    confimpassword_pf.setText("");
                    confimpassword_pf.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (username_tf.getBackground() == Color.green && password_pf.getBackground() == Color.green) {
                    if (confPassVerifier.verifyLive(confimpassword_pf)) {
                        confPassVerifier.verifyMatch(password_pf, confimpassword_pf);
                    }
                }

            }
        });

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (usernameVerifier.verify(username_tf, admin) && passwordVerifier.verifyLive(password_pf) &&
                passwordVerifier.verifyLive(confimpassword_pf) &&
                passwordVerifier.verifyMatch(password_pf, confimpassword_pf)) {

            username_tf.setBackground(Color.green);
            confimpassword_pf.setBackground(Color.GREEN);
            password_pf.setBackground(Color.GREEN);
            Admin admin = new Admin(username_tf.getText(), String.valueOf(password_pf.getPassword()));
            Consts.DB.addAdmin(admin);
            new AdminDecisionPage(admin);
            setVisible(false);
            dispose();
        }

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        username_tf = new Consts.RoundJTextField();
        username_tf.setBorder(BorderFactory.createCompoundBorder(
                username_tf.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        username_tf.setText("Username");
        username_tf.setForeground(Color.GRAY);

        password_pf = new Consts.RoundPasswordField();
        password_pf.setBorder(BorderFactory.createCompoundBorder(
                password_pf.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        password_pf.setEchoChar((char) 0);
        password_pf.setText("Password");
        password_pf.setForeground(Color.GRAY);

        confimpassword_pf = new Consts.RoundPasswordField();
        confimpassword_pf.setBorder(BorderFactory.createCompoundBorder(
                confimpassword_pf.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        confimpassword_pf.setEchoChar((char) 0);
        confimpassword_pf.setText("Confirm Password");
        confimpassword_pf.setForeground(Color.GRAY);
    }
}
