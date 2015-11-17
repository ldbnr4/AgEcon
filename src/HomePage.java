import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by Lorenzo on 10/1/2015.
 */
public class HomePage extends JFrame {
    private JLabel titleLabel;
    private JLabel sectorLabel;
    private JPanel rootPanel;
    private JButton logoutButton;
    private JPanel titlePanel;
    private JList list_of_things;
    //private Student student;

    public HomePage(String name, Sector sector) {
        super("Welcome Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        titleLabel.setText("Welcome " + name + "!");
        this.validate();
        //System.out.println(getContentPane().getWidth()/2);
        titlePanel.setLocation((getContentPane().getWidth() / 2) - titlePanel.getWidth() / 2, titlePanel.getHeight() + 5);
        logoutButton.setLocation((getContentPane().getWidth()) - logoutButton.getWidth() - 5, logoutButton.getHeight() + 5);
        setComponentZOrder(titlePanel, 0);
        setComponentZOrder(logoutButton, 1);
        setComponentZOrder(getContentPane(), 1);
        //System.out.println(logoutButton.getLocation());

        sectorLabel.setText(sector.name);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WelcomePage();
                setVisible(false);
                dispose();
            }
        });

        DefaultListModel listModel = new DefaultListModel();
        HashMap<String, Student> inputSector = Consts.DB.getInputSectorStudents(Consts.GAME_FLOW.currentYear);
        System.out.println(inputSector.size());
        for (Object student : inputSector.values()) {
            System.out.println(student);
            listModel.addElement(student);
        }
        list_of_things.setModel(listModel);
    }
}
