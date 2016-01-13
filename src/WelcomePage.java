import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.MinimalBalloonStyle;
import net.java.balloontip.utils.TimingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Lorenzo on 9/22/2015.
 *
 */
public class WelcomePage extends JFrame implements ActionListener {
    private JPanel rootPanel;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JRadioButton studentRadioButton;
    private JRadioButton instructorRadioButton;
    private JTextField usernameTextField;
    private JPasswordField confirmPasswordPasswordField;
    private JPasswordField passwordPasswordField;
    private JButton submitButton;
    private MinimalBalloonStyle modern;
    private BalloonTip createBalloon;
    private UNameVerifier usernameVerifier;
    private PassVerifier passwordVerifier;
    private PassVerifier confPassVerifier;
    private BalloonTip loginBalloon;

    public WelcomePage() {
        super("Welcome Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modern = new MinimalBalloonStyle(Color.yellow, 5);

        createBalloon = new BalloonTip(usernameTextField, new JLabel(), modern,
                BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.createBalloon.setVisible(false);

        loginBalloon = new BalloonTip(userNameField, new JLabel(), modern,
                BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        loginBalloon.setVisible(false);

        submitButton.addActionListener(this);

        usernameVerifier = new UNameVerifier(createBalloon);
        usernameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameTextField.getBackground() != Color.GREEN) {
                    usernameTextField.setText("");
                    usernameTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                usernameVerifier.verify(usernameTextField, Student.class);
            }
        });
        passwordVerifier = new PassVerifier(createBalloon);
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
        confPassVerifier = new PassVerifier(createBalloon);
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

        userNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userNameField.getForeground().equals(Color.GRAY)) {
                    userNameField.setText("");
                    userNameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordField.getForeground().equals(Color.GRAY)) {
                    passwordField.setEchoChar('•');
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        loginButton.addActionListener(this);
        studentRadioButton.setSelected(true);

    }

    public WelcomePage(String name) {
        super("Welcome Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modern = new MinimalBalloonStyle(Color.yellow, 5);

        createBalloon = new BalloonTip(usernameTextField, new JLabel(), modern,
                BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.createBalloon.setVisible(false);

        loginBalloon = new BalloonTip(userNameField, new JLabel(), modern,
                BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        loginBalloon.setVisible(false);

        submitButton.addActionListener(this);

        usernameVerifier = new UNameVerifier(createBalloon);
        usernameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameTextField.getBackground() != Color.GREEN) {
                    usernameTextField.setText("");
                    usernameTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                usernameVerifier.verify(usernameTextField, Student.class);
            }
        });
        passwordVerifier = new PassVerifier(createBalloon);
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
        confPassVerifier = new PassVerifier(createBalloon);
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

        userNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userNameField.getForeground().equals(Color.GRAY)) {
                    userNameField.setText("");
                    userNameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordField.getForeground().equals(Color.GRAY)) {
                    passwordField.setEchoChar('•');
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        loginButton.addActionListener(this);
        studentRadioButton.setSelected(true);

        usernameTextField.setText(name);
        usernameTextField.setForeground(Color.BLACK);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        usernameTextField = new Consts.RoundJTextField();
        usernameTextField.setBorder(BorderFactory.createCompoundBorder(
                usernameTextField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        usernameTextField.setText("Username");
        usernameTextField.setForeground(Color.GRAY);

        passwordPasswordField = new Consts.RoundPasswordField();
        passwordPasswordField.setBorder(BorderFactory.createCompoundBorder(
                passwordPasswordField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        passwordPasswordField.setEchoChar((char) 0);
        passwordPasswordField.setText("Password");
        passwordPasswordField.setForeground(Color.GRAY);

        confirmPasswordPasswordField = new Consts.RoundPasswordField();
        confirmPasswordPasswordField.setBorder(BorderFactory.createCompoundBorder(
                confirmPasswordPasswordField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        confirmPasswordPasswordField.setEchoChar((char) 0);
        confirmPasswordPasswordField.setText("Confirm Password");
        confirmPasswordPasswordField.setForeground(Color.GRAY);

        userNameField = new Consts.RoundJTextField();
        userNameField.setBorder(BorderFactory.createCompoundBorder(
                userNameField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        userNameField.setText("Username");
        userNameField.setForeground(Color.GRAY);

        passwordField = new Consts.RoundPasswordField();
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                passwordField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        passwordField.setEchoChar((char) 0);
        passwordField.setText("Password");
        passwordField.setForeground(Color.GRAY);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (btn.equals(submitButton)) {
            if (usernameTextField.getForeground().equals(Color.GRAY)) {
                createBalloon.setAttachedComponent(usernameTextField);
                createBalloon.setTextContents("You did not enter a username.");
                TimingUtils.showTimedBalloon(createBalloon, 2500);
            } else if (passwordPasswordField.getForeground().equals(Color.GRAY)) {
                createBalloon.setAttachedComponent(passwordPasswordField);
                createBalloon.setTextContents("You did not enter a password.");
                TimingUtils.showTimedBalloon(createBalloon, 2500);
            } else if (confirmPasswordPasswordField.getForeground().equals(Color.GRAY)) {
                createBalloon.setAttachedComponent(confirmPasswordPasswordField);
                createBalloon.setTextContents("You did not enter a confirmation password.");
                TimingUtils.showTimedBalloon(createBalloon, 2500);
            } else if (usernameVerifier.verify(usernameTextField, Student.class) && passwordVerifier.verifyLive(passwordPasswordField) &&
                    passwordVerifier.verifyLive(confirmPasswordPasswordField) &&
                    passwordVerifier.verifyMatch(passwordPasswordField, confirmPasswordPasswordField)) {

                usernameTextField.setBackground(Color.green);
                confirmPasswordPasswordField.setBackground(Color.GREEN);
                passwordPasswordField.setBackground(Color.GREEN);
                Student student = new Student(usernameTextField.getText(), String.valueOf(passwordPasswordField.getPassword()), new FarmTypes());
                new FarmerDecisionPage(student);
                Consts.DB.addStudent(student);
                setVisible(false);
                dispose();
            }
        } else if (btn.equals(loginButton)) {
            String inUse = userNameField.getText();
            String inPass = String.valueOf(passwordField.getPassword());
            if (inUse.isEmpty() || userNameField.getForeground().equals(Color.GRAY)) {
                loginBalloon.setAttachedComponent(userNameField);
                loginBalloon.setTextContents("You did not enter a username.");
                TimingUtils.showTimedBalloon(loginBalloon, 2500);
            } else if (inPass.isEmpty() || passwordField.getForeground().equals(Color.GRAY)) {
                loginBalloon.setAttachedComponent(passwordField);
                loginBalloon.setTextContents("You did not enter a password.");
                TimingUtils.showTimedBalloon(loginBalloon, 2500);
            } else {
                passwordField.setBackground(Color.GREEN);
                userNameField.setBackground(Color.GREEN);
                if (studentRadioButton.isSelected()) {
                    Student grabbedStudent = Consts.DB.getStudent(inUse);
                    if (grabbedStudent == null) {
                        JOptionPane.showMessageDialog(rootPanel, "User account not found. Make sure you are registered and selected the correct user type.", "Username error", JOptionPane.ERROR_MESSAGE);
                        userNameField.setBackground(Color.RED);
                    } else {
                        String salt = grabbedStudent.salt;
                        String inEncrypt = EncryptPassword.encrpyt(inPass, salt);
                        if (inEncrypt.equals(grabbedStudent.password)) {
                            passwordField.setBackground(Color.GREEN);
                            switch (grabbedStudent.getStage()) {
                                case Select_Size:
                                    new FarmerDecisionPage(grabbedStudent);
                                    break;
                                case Buy_Seeds:
                                    new HomePage(grabbedStudent);
                                    break;
                                case Sell_Yields:
                                    new MarketingDealsPage(grabbedStudent);
                                    break;
                                case End_of_Season:
                                    new EndofSeasonPage(grabbedStudent);
                                    break;
                            }
                            setVisible(false);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(rootPanel, "Password did not match the account for " + inUse + ".", "Password error", JOptionPane.ERROR_MESSAGE);
                            passwordField.setBackground(Color.red);
                        }
                    }
                } else {
                    Admin grabbedAdmin = Consts.DB.getAdmin(inUse);
                    if (grabbedAdmin == null) {
                        JOptionPane.showMessageDialog(rootPanel, "User account not found. Make sure you are registered and selected the correct user type.", "Username error", JOptionPane.ERROR_MESSAGE);
                        userNameField.setBackground(Color.RED);
                    } else {
                        if (EncryptPassword.encrpyt(inPass, grabbedAdmin.salt).equals(grabbedAdmin.password)) {
                            new AdminDecisionPage(grabbedAdmin);
                            setVisible(false);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(rootPanel, "Password did not match the account for " + inUse + ".", "Password error", JOptionPane.ERROR_MESSAGE);
                            passwordField.setBackground(Color.red);
                        }
                    }
                }
            }
        }
    }
}
