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
        if (Consts.checkEmpty(inUse)) {
            JOptionPane.showMessageDialog(rootPanel, "You did not enter a username.", "Username error", JOptionPane.ERROR_MESSAGE);
            userNameField.setBackground(Color.RED);
        } else if (Consts.checkEmpty(inPass)) {
            JOptionPane.showMessageDialog(rootPanel, "You did not enter a password.", "Password error", JOptionPane.ERROR_MESSAGE);
            passwordField.setBackground(Color.RED);
        }else {
            passwordField.setBackground(Color.GREEN);
            userNameField.setBackground(Color.GREEN);
            if (studentRadioButton.isSelected()) {
                Student grabbedStudent = Consts.DB.getStudent(inUse, Consts.GAME_FLOW.currentYear);
                if(grabbedStudent == null){
                    JOptionPane.showMessageDialog(rootPanel, "User account not found. Make sure you are registered and selected the correct user type.", "Username error", JOptionPane.ERROR_MESSAGE);
                    userNameField.setBackground(Color.RED);
                }
                else {
                    String salt = grabbedStudent.salt;
                    String inEncrypt = EncryptPassword.encrpyt(inPass, salt);
                    if (inEncrypt.equals(grabbedStudent.password)) {
                        passwordField.setBackground(Color.GREEN);
                        //System.out.println(grabbedStudent.sector);
                        Sector studentSector = grabbedStudent.sector;
                        boolean emptyStudent = studentSector.checkIfEmpty();
                        if (emptyStudent) {
                            switch (grabbedStudent.sector.name) {
                                case Consts.INPUT_SECTOR_NAME:
                                    new InputDecisionPage(grabbedStudent);
                                    break;
                                case Consts.FARM_SECTOR_NAME:
                                    new FarmerDecisionPage(grabbedStudent);
                                    break;
                                case Consts.FOOD_SECTOR_NAME:

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
                }
            }
            else {
                Admin grabbedAdmin = Consts.DB.getAdmin(inUse);
                if(grabbedAdmin == null){
                    JOptionPane.showMessageDialog(rootPanel, "User account not found. Make sure you are registered and selected the correct user type.", "Username error", JOptionPane.ERROR_MESSAGE);
                    userNameField.setBackground(Color.RED);
                }
                else{
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

