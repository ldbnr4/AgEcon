import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lorenzo on 9/29/2015.
 */
public class LoginPage extends JFrame implements ActionListener {
    private JPasswordField passwordField;
    private JTextField userNameField;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel rootPanel;
    private JRadioButton studentRadioButton;
    private JRadioButton instructorRadioButton;

    public LoginPage() {
        super("Login Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreatePage();
                setVisible(false);
                dispose();
            }
        });

        loginButton.addActionListener(this);

        studentRadioButton.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(studentRadioButton);
        group.add(instructorRadioButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String inUse = userNameField.getText();
        String inPass = String.valueOf(passwordField.getPassword());
        if (GameDriver.checkEmpty(inUse)) {
            JOptionPane.showMessageDialog(rootPanel, "You did not enter a username.", "Username error", JOptionPane.ERROR_MESSAGE);
            userNameField.setBackground(Color.RED);
        } else if (GameDriver.checkEmpty(inPass)) {
            JOptionPane.showMessageDialog(rootPanel, "You did not enter a password.", "Password error", JOptionPane.ERROR_MESSAGE);
            passwordField.setBackground(Color.RED);
        } else if (GameDriver.DB.getAdmin(inUse) == null && GameDriver.DB.getStudent(inUse) == null) {
            JOptionPane.showMessageDialog(rootPanel, "User account not found. Make sure you are registered.", "Username error", JOptionPane.ERROR_MESSAGE);
            userNameField.setBackground(Color.RED);
        } else {
            passwordField.setBackground(Color.GREEN);
            userNameField.setBackground(Color.GREEN);
            if (studentRadioButton.isSelected()) {
                Student grabbedStudent = GameDriver.DB.getStudent(inUse);
                String salt = grabbedStudent.salt;
                String inEncrypt = EncryptPassword.encrpyt(inPass, salt);
                if (inEncrypt.equals(grabbedStudent.password)) {
                    passwordField.setBackground(Color.GREEN);
                    //System.out.println(grabbedStudent.sector);
                    Sector studentSector = grabbedStudent.sector;
                    boolean emptyStudent = studentSector.checkIfEmpty();
                    if (emptyStudent) {
                        switch (grabbedStudent.sector.name) {
                            case GameDriver.INPUT_SECTOR_NAME:
                                new InputDecisionPage(grabbedStudent);
                                break;
                            case GameDriver.FARM_SECTOR_NAME:
                                new FarmerDecisionPage(grabbedStudent);
                                break;
                            case GameDriver.FOOD_SECTOR_NAME:

                                break;
                            default:
                                break;
                        }
                        setVisible(false);
                        dispose();
                    } else {
                        new HomePage(grabbedStudent.uName, grabbedStudent.sector);
                        setVisible(false);
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPanel, "Password did not match the account for " + inUse + ".", "Password error", JOptionPane.ERROR_MESSAGE);
                    passwordField.setBackground(Color.red);
                }
            } else {
                Admin grabbedAdmin = GameDriver.DB.getAdmin(inUse);
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

