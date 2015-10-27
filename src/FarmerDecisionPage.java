import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by Lorenzo on 10/22/2015.
 */
public class FarmerDecisionPage extends JFrame implements ActionListener {
    Student student;
    FarmSector studentSector;
    HashMap<Character, Integer> farmSizeAmounts;
    private JPanel rootPanel;
    private JLabel farmerLabel;
    private JButton smallFarmBtn;
    private JButton medFarmBtn;
    private JButton largeFarmBtn;
    private JButton refreshBtn;
    private JLabel smallAmntLbl;
    private JLabel medAmntLbl;
    private JLabel largeAmntLbl;


    public FarmerDecisionPage(Student student) {
        super("Input Supply Decisions");

        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        farmerLabel.setText("Hey " + student.uName + "!");

        this.student = student;
        this.studentSector = (FarmSector) this.student.sector;

        smallFarmBtn.addActionListener(this);
        medFarmBtn.addActionListener(this);
        largeFarmBtn.addActionListener(this);

        updateBtns();

        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBtns();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (farmCheck(button)) {
            if (button.equals(smallFarmBtn)) {
                studentSector.updateSize(GameDriver.SMALL_FARM);
            } else if (button.equals(medFarmBtn)) {
                studentSector.updateSize(GameDriver.MED_FARM);
            } else {
                studentSector.updateSize(GameDriver.LARGE_FARM);
            }
        }
        GameDriver.DB.updateStudent(student);
        new HomePage(student.uName, student.sector);
        setVisible(false);
        dispose();
    }

    private void updateBtns() {
        farmSizeAmounts = GameDriver.DB.numInFarms();
        int dbNumOfSmall = farmSizeAmounts.get(GameDriver.SMALL_FARM);
        setAvailableLabel(smallAmntLbl, dbNumOfSmall, GameDriver.S_FARM_CAP);

        int dbNumOfMed = farmSizeAmounts.get(GameDriver.MED_FARM);
        setAvailableLabel(medAmntLbl, dbNumOfMed, GameDriver.M_FARM_CAP);

        int dbNumOfLrg = farmSizeAmounts.get(GameDriver.LARGE_FARM);
        setAvailableLabel(largeAmntLbl, dbNumOfLrg, GameDriver.L_FARM_CAP);

        if (dbNumOfSmall >= GameDriver.S_FARM_CAP) {
            smallFarmBtn.setEnabled(false);
        } else
            smallFarmBtn.setEnabled(true);

        if (dbNumOfMed >= GameDriver.M_FARM_CAP) {
            medFarmBtn.setEnabled(false);
        } else
            medFarmBtn.setEnabled(true);

        if (dbNumOfLrg >= GameDriver.L_FARM_CAP) {
            largeFarmBtn.setEnabled(false);
        } else
            largeFarmBtn.setEnabled(true);
    }

    private void setAvailableLabel(JLabel label, int num, int limit) {
        int available = limit - num;
        label.setText(String.valueOf(available));

        float ratio = (float) num / (float) limit;
        //System.out.println(ratio);
        if (ratio <= .33) {
            label.setForeground(new Color(0, 102, 0));
        } else if (ratio <= .66) {
            label.setForeground(new Color(207, 174, 0));
        } else
            label.setForeground(Color.RED);
    }

    private boolean farmCheck(JButton farm) {
        int farmLimit;
        char farmSize;
        if (farm.equals(smallFarmBtn)) {
            farmLimit = GameDriver.S_FARM_CAP;
            farmSize = GameDriver.SMALL_FARM;

        } else if (farm.equals(medFarmBtn)) {
            farmLimit = GameDriver.M_FARM_CAP;
            farmSize = GameDriver.MED_FARM;
        } else {
            farmLimit = GameDriver.L_FARM_CAP;
            farmSize = GameDriver.LARGE_FARM;
        }

        if (farmSizeAmounts.get(farmSize) >= farmLimit) {
            JOptionPane.showMessageDialog(rootPanel, "This farm size has reached its limit. Please select another farm size.", "Farm Size Error", JOptionPane.ERROR_MESSAGE);
            updateBtns();
            return false;
        }
        return true;
    }

}
