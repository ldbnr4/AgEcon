import javax.swing.*;

/**
 * Created by Lorenzo on 10/22/2015.
 */
public class FarmerDecisionPage extends JFrame {
    private JPanel rootPanel;
    private JLabel farmerLabel;
    private JButton smallFarmBtn;
    private JButton medFarmBtn;
    private JButton largeFarmBtn;

    public FarmerDecisionPage(Student student) {
        super("Input Supply Decisions");

        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        farmerLabel.setText("Hey " + student.uName + "!");

        /*setUpRadioButtons();
        this.student = student;
        this.studentSector = (InputSector) this.student.sector;

        submitButton.addActionListener(this);*/
    }
}
