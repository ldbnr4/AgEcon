import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lorenzo on 9/29/2015.
 */
public class LoginPage extends JFrame implements ActionListener {
    MongoDBConnection db = MongoDBConnection.getInstance();
    private JPasswordField passwordField;
    private JTextField userNameField;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel rootPanel;
    private InputSector studentSector;

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
    }

    public static boolean checkEmpty(String field) {
        return field.isEmpty();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String inUse = userNameField.getText();
        String inPass = String.valueOf(passwordField.getPassword());
        if (checkEmpty(inUse)) {
            JOptionPane.showMessageDialog(rootPanel, "You did not enter a username.", "Username error", JOptionPane.ERROR_MESSAGE);
            userNameField.setBackground(Color.RED);
        } else if (checkEmpty(inPass)) {
            JOptionPane.showMessageDialog(rootPanel, "You did not enter a password.", "Password error", JOptionPane.ERROR_MESSAGE);
            passwordField.setBackground(Color.red);
        } else if (!db.userInDB(inUse)) {
            JOptionPane.showMessageDialog(rootPanel, "User account not found. Make sure you are registered.", "Username error", JOptionPane.ERROR_MESSAGE);
            userNameField.setBackground(Color.RED);
        } else {
            passwordField.setBackground(Color.GREEN);
            userNameField.setBackground(Color.GREEN);
            Student grabbedStudent = db.getStudent(userNameField.getText());
            String salt = grabbedStudent.salt;
            String inEncrypt = EncryptPassword.encrpyt(inPass, salt);

            if (inEncrypt.equals(grabbedStudent.password)) {
                passwordField.setBackground(Color.GREEN);
                //System.out.println(grabbedStudent.sector);
                if (grabbedStudent.sector.name.equals(GameDriver.INPUT_SECTOR_NAME)) {
                    studentSector = (InputSector) grabbedStudent.sector;
                    if (studentSector.checkIfEmpty()) {
                        new InputDecisionPage(grabbedStudent);
                        setVisible(false);
                        dispose();
                    }
                }
                new HomePage(grabbedStudent.uName, grabbedStudent.sector);
                setVisible(false);
                dispose();
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Password did not match the account for " + inUse + ".", "Password error", JOptionPane.ERROR_MESSAGE);
                passwordField.setBackground(Color.red);
            }
        }
    }
}

