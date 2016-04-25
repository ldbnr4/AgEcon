/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */


package AgEconPackage;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.MinimalBalloonStyle;
import net.java.balloontip.utils.TimingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static AgEconPackage.Consts.*;
import static AgEconPackage.Consts.Farm_Size.NO_FARM;

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
    private BalloonTip createBalloon;
    private UNameVerifier usernameVerifier;
    private PassVerifier passwordVerifier;
    private PassVerifier confPassVerifier;
    private BalloonTip loginBalloon;

    WelcomePage() {
        super("Welcome Page");
        setContentPane(rootPanel);
        setResizable(false);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setMaximumSize(new Dimension(200, (int) screen.getHeight() - 50));
        //System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //System.out.println("Frame Size: " + getSize());
        //System.out.println("Resolution: " + Toolkit.getDefaultToolkit().getScreenResolution());

        MinimalBalloonStyle modern = new MinimalBalloonStyle(Color.yellow, 5);

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
                if (usernameTextField.getBackground().equals(Color.RED) || usernameTextField.getForeground().equals(Color.GRAY)) {
                    usernameTextField.setText("");
                    usernameTextField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (!usernameTextField.getText().isEmpty()) {
                    usernameVerifier.verify(usernameTextField, Student.class);
                } else {
                    usernameTextField.setBackground(Color.WHITE);
                    usernameTextField.setForeground(Color.GRAY);
                    usernameTextField.setText("Username");
                }
            }
        });

        passwordVerifier = new PassVerifier(createBalloon);
        passwordPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordPasswordField.getBackground().equals(Color.RED) || passwordPasswordField.getForeground().equals(Color.GRAY)) {
                    passwordPasswordField.setEchoChar('•');
                    passwordPasswordField.setText("");
                    passwordPasswordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordPasswordField.getPassword()).isEmpty()) {
                    passwordPasswordField.setEchoChar((char) 0);
                    passwordPasswordField.setBackground(Color.WHITE);
                    passwordPasswordField.setForeground(Color.GRAY);
                    passwordPasswordField.setText("Password");
                } else if (usernameTextField.getBackground().equals(Color.green)) {
                    passwordVerifier.verifyLive(passwordPasswordField);
                }
            }
        });

        confPassVerifier = new PassVerifier(createBalloon);
        confirmPasswordPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (confirmPasswordPasswordField.getBackground().equals(Color.RED) || confirmPasswordPasswordField.getForeground().equals(Color.GRAY)) {
                    confirmPasswordPasswordField.setEchoChar('•');
                    confirmPasswordPasswordField.setText("");
                    confirmPasswordPasswordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(confirmPasswordPasswordField.getPassword()).isEmpty()) {
                    confirmPasswordPasswordField.setEchoChar((char) 0);
                    confirmPasswordPasswordField.setBackground(Color.WHITE);
                    confirmPasswordPasswordField.setForeground(Color.GRAY);
                    confirmPasswordPasswordField.setText("Confirm Password");
                } else if (usernameTextField.getBackground() == Color.green && passwordPasswordField.getBackground() == Color.green) {
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
                if (userNameField.getText().isEmpty()) {
                    userNameField.setBackground(Color.WHITE);
                    userNameField.setForeground(Color.GRAY);
                    userNameField.setText("Username");
                }
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
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setBackground(Color.WHITE);
                    passwordField.setText("Password");
                }
            }
        });

        loginButton.addActionListener(this);
        studentRadioButton.setSelected(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        usernameTextField = new RoundJTextField();
        usernameTextField.setBorder(BorderFactory.createCompoundBorder(
                usernameTextField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        usernameTextField.setText("Username");
        usernameTextField.setForeground(Color.GRAY);

        passwordPasswordField = new RoundPasswordField();
        passwordPasswordField.setBorder(BorderFactory.createCompoundBorder(
                passwordPasswordField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        passwordPasswordField.setEchoChar((char) 0);
        passwordPasswordField.setText("Password");
        passwordPasswordField.setForeground(Color.GRAY);

        confirmPasswordPasswordField = new RoundPasswordField();
        confirmPasswordPasswordField.setBorder(BorderFactory.createCompoundBorder(
                confirmPasswordPasswordField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        confirmPasswordPasswordField.setEchoChar((char) 0);
        confirmPasswordPasswordField.setText("Confirm Password");
        confirmPasswordPasswordField.setForeground(Color.GRAY);

        userNameField = new RoundJTextField();
        userNameField.setBorder(BorderFactory.createCompoundBorder(
                userNameField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        userNameField.setText("Username");
        userNameField.setForeground(Color.GRAY);

        passwordField = new RoundPasswordField();
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
                Student student = new Student(usernameTextField.getText(), String.valueOf(passwordPasswordField.getPassword()), new Farm(NO_FARM));
                new FarmerDecisionPage(student);
                DB.addStudent(student);
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
                    Student grabbedStudent = DB.getStudent(inUse);
                    if (grabbedStudent == null) {
                        JOptionPane.showMessageDialog(rootPanel, "User account not found. Make sure you are registered and selected the correct user type.", "Username error", JOptionPane.ERROR_MESSAGE);
                        userNameField.setBackground(Color.RED);
                    } else {
                        String salt = grabbedStudent.salt;
                        String inEncrypt = EncryptPassword.encrpyt(inPass, salt);
                        if (inEncrypt.equals(grabbedStudent.password)) {
                            passwordField.setBackground(Color.GREEN);
                            switch (grabbedStudent.getFarm().getStage()) {
                                case Select_Size:
                                    new FarmerDecisionPage(grabbedStudent);
                                    break;
                                case Buy_Seeds:
                                    new BuySeedsPage(grabbedStudent);
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
                    Admin grabbedAdmin = DB.getAdmin(inUse);
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

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */