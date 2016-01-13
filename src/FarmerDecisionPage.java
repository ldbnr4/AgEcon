import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by Lorenzo on 10/22/2015.
 *
 */
public class FarmerDecisionPage extends JFrame implements ActionListener {
    Student student;
    HashMap<Character, Integer> farmSizeAmounts;
    JPanel rootPanel;
    JLabel farmerLabel;
    JButton smallFarmBtn;
    JButton medFarmBtn;
    JButton largeFarmBtn;
    JLabel smallAmntLbl;
    JLabel medAmntLbl;
    JLabel largeAmntLbl;
    JButton backBtn;

    boolean updateFlag = true;
    Thread t1;


    public FarmerDecisionPage(final Student student) {
        super("Input Supply Decisions");

        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        farmerLabel.setText("Hey " + student.uName + "!");

        this.student = student;

        smallFarmBtn.addActionListener(this);
        medFarmBtn.addActionListener(this);
        largeFarmBtn.addActionListener(this);

        Runnable r = () -> {
            while (updateFlag) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateBtns();
            }
        };

        t1 = new Thread(r);
        t1.start();

        backBtn.addActionListener(e -> {
            updateFlag = false;
            Consts.DB.removeStudent(student);
            new WelcomePage(student.uName);
            setVisible(false);
            dispose();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (farmCheck(button)) {
            if (button.equals(smallFarmBtn)) {
                student.farm = new FarmTypes(Consts.SMALL_FARM);
            } else if (button.equals(medFarmBtn)) {
                student.farm = new FarmTypes(Consts.MED_FARM);
            } else {
                student.farm = new FarmTypes(Consts.LARGE_FARM);
            }
            updateFlag = false;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            updateFlag = false;
            student.setStage(Consts.Student_Stage.Buy_Seeds);
            Consts.DB.saveStudent(student);
            new HomePage(student);
            setVisible(false);
            dispose();
        }
    }

    private void updateBtns() {
        farmSizeAmounts = Consts.DB.numInEachFarm();
        //System.out.println(farmSizeAmounts);
        int dbNumOfSmall = farmSizeAmounts.get(Consts.SMALL_FARM);
        setAvailableLabel(smallAmntLbl, dbNumOfSmall, Consts.S_FARM_CAP);

        int dbNumOfMed = farmSizeAmounts.get(Consts.MED_FARM);
        setAvailableLabel(medAmntLbl, dbNumOfMed, Consts.M_FARM_CAP);

        int dbNumOfLrg = farmSizeAmounts.get(Consts.LARGE_FARM);
        setAvailableLabel(largeAmntLbl, dbNumOfLrg, Consts.L_FARM_CAP);

        if (dbNumOfSmall >= Consts.S_FARM_CAP) {
            smallFarmBtn.setEnabled(false);
        } else
            smallFarmBtn.setEnabled(true);

        if (dbNumOfMed >= Consts.M_FARM_CAP) {
            medFarmBtn.setEnabled(false);
        } else
            medFarmBtn.setEnabled(true);

        if (dbNumOfLrg >= Consts.L_FARM_CAP) {
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
            farmLimit = Consts.S_FARM_CAP;
            farmSize = Consts.SMALL_FARM;

        } else if (farm.equals(medFarmBtn)) {
            farmLimit = Consts.M_FARM_CAP;
            farmSize = Consts.MED_FARM;
        } else {
            farmLimit = Consts.L_FARM_CAP;
            farmSize = Consts.LARGE_FARM;
        }

        try {
            if (farmSizeAmounts.get(farmSize) >= farmLimit) {
                JOptionPane.showMessageDialog(rootPanel, "This farm size has reached its limit. Please select another farm size.", "Farm Size Error", JOptionPane.ERROR_MESSAGE);
                updateBtns();
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

}
