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
 * Created by Lorenzo on 9/18/2015.
 *
 */
public class CreatePage extends JFrame implements ActionListener {
    JPanel rootPanel;
    JTextField usernameTextField;
    JPasswordField passwordPasswordField;
    JPasswordField confirmPasswordPasswordField;
    JButton loginButton;
    JPanel loginPanel;
    JLabel loginLabel;
    JPanel createAcntPanel;
    JButton submitButton;
    private BalloonTipStyle modern;
    private BalloonTip uNameBalloonTip;
    private BalloonTip passBalloonTip;
    private BalloonTip confPassBalloonTip;
    private UNameVerifier usernameVerifier;
    private PassVerifier passwordVerifier;
    private PassVerifier confPassVerifier;

    public CreatePage() {
        super("Create Account");
        setContentPane(rootPanel);

        modern = new MinimalBalloonStyle(Color.white, 5);
        uNameBalloonTip = new BalloonTip(usernameTextField, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.uNameBalloonTip.setVisible(false);
        passBalloonTip = new BalloonTip(passwordPasswordField, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.passBalloonTip.setVisible(false);
        confPassBalloonTip = new BalloonTip(confirmPasswordPasswordField, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.confPassBalloonTip.setVisible(false);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage();
                setVisible(false);
                dispose();
            }
        });

        submitButton.addActionListener(this);

        usernameVerifier = new UNameVerifier(uNameBalloonTip);
        usernameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameTextField.getBackground() != Color.GREEN || usernameTextField.getBackground() != Color.RED) {
                    usernameTextField.setText("");
                    usernameTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                usernameVerifier.verify(usernameTextField, Student.class);
            }
        });
        passwordVerifier = new PassVerifier(passBalloonTip);
        passwordPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordPasswordField.getBackground() != Color.GREEN) {
                    passwordPasswordField.setEchoChar('•');
                    passwordPasswordField.setText("");
                    passwordPasswordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameTextField.getBackground() == Color.green) {
                    passwordVerifier.verifyLive(passwordPasswordField);
                }
            }
        });
        confPassVerifier = new PassVerifier(confPassBalloonTip);
        confirmPasswordPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (confirmPasswordPasswordField.getBackground() != Color.GREEN) {
                    confirmPasswordPasswordField.setEchoChar('•');
                    confirmPasswordPasswordField.setText("");
                    confirmPasswordPasswordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameTextField.getBackground() == Color.green && passwordPasswordField.getBackground() == Color.green) {
                    if (confPassVerifier.verifyLive(confirmPasswordPasswordField)) {
                        confPassVerifier.verifyMatch(passwordPasswordField, confirmPasswordPasswordField);
                    }
                }

            }
        });

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public CreatePage(String username) {
        super("Create Account");
        setContentPane(rootPanel);

        modern = new MinimalBalloonStyle(Color.white, 5);
        uNameBalloonTip = new BalloonTip(usernameTextField, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.uNameBalloonTip.setVisible(false);
        passBalloonTip = new BalloonTip(passwordPasswordField, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.passBalloonTip.setVisible(false);
        confPassBalloonTip = new BalloonTip(confirmPasswordPasswordField, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.confPassBalloonTip.setVisible(false);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage();
                setVisible(false);
                dispose();
            }
        });

        submitButton.addActionListener(this);

        usernameVerifier = new UNameVerifier(uNameBalloonTip);
        usernameTextField.setForeground(Color.BLACK);
        usernameTextField.setBackground(Color.GREEN);
        usernameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                usernameVerifier.verify(usernameTextField, Student.class);
            }
        });
        passwordVerifier = new PassVerifier(passBalloonTip);
        passwordPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordPasswordField.getBackground() != Color.GREEN) {
                    passwordPasswordField.setEchoChar('•');
                    passwordPasswordField.setText("");
                    passwordPasswordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameTextField.getBackground() == Color.green) {
                    passwordVerifier.verifyLive(passwordPasswordField);
                }
            }
        });
        confPassVerifier = new PassVerifier(confPassBalloonTip);
        confirmPasswordPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (confirmPasswordPasswordField.getBackground() != Color.GREEN) {
                    confirmPasswordPasswordField.setEchoChar('•');
                    confirmPasswordPasswordField.setText("");
                    confirmPasswordPasswordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameTextField.getBackground() == Color.green && passwordPasswordField.getBackground() == Color.green) {
                    if (confPassVerifier.verifyLive(confirmPasswordPasswordField)) {
                        confPassVerifier.verifyMatch(passwordPasswordField, confirmPasswordPasswordField);
                    }
                }

            }
        });


        usernameTextField.setText(username);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (usernameVerifier.verify(usernameTextField, Student.class) && passwordVerifier.verifyLive(passwordPasswordField) &&
                passwordVerifier.verifyLive(confirmPasswordPasswordField) &&
                passwordVerifier.verifyMatch(passwordPasswordField, confirmPasswordPasswordField)) {

                usernameTextField.setBackground(Color.green);
                confirmPasswordPasswordField.setBackground(Color.GREEN);
                passwordPasswordField.setBackground(Color.GREEN);
            Student student = new Student(usernameTextField.getText(), String.valueOf(passwordPasswordField.getPassword()), new FarmSector());
            new FarmerDecisionPage(student);
            Consts.DB.addStudent(student);
            //new HomePage(student);
            setVisible(false);
            dispose();
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        usernameTextField = new Consts.RoundJTextField(15);
        usernameTextField.setBorder(BorderFactory.createCompoundBorder(
                usernameTextField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        usernameTextField.setText("Username");
        usernameTextField.setForeground(Color.GRAY);

        passwordPasswordField = new Consts.RoundPasswordField(15);
        passwordPasswordField.setBorder(BorderFactory.createCompoundBorder(
                passwordPasswordField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        passwordPasswordField.setEchoChar((char) 0);
        passwordPasswordField.setText("Password");
        passwordPasswordField.setForeground(Color.GRAY);

        confirmPasswordPasswordField = new Consts.RoundPasswordField(15);
        confirmPasswordPasswordField.setBorder(BorderFactory.createCompoundBorder(
                confirmPasswordPasswordField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        confirmPasswordPasswordField.setEchoChar((char) 0);
        confirmPasswordPasswordField.setText("Confirm Password");
        confirmPasswordPasswordField.setForeground(Color.GRAY);
    }
}
