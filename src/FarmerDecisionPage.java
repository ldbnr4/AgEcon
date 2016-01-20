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
    boolean updateFlag = true;
    Thread t1;
    private Student student;
    private HashMap<Consts.Farm_Size, Integer> farmSizeAmounts;
    private JPanel rootPanel;
    private JLabel farmerLabel;
    private JButton smallFarmBtn;
    private JButton medFarmBtn;
    private JButton largeFarmBtn;
    private JLabel smallAmntLbl;
    private JLabel medAmntLbl;
    private JLabel largeAmntLbl;
    private JButton backBtn;
    private JLabel lbl_acre_large;
    private JLabel lbl_acre_sml;
    private JLabel lbl_acre_med;


    public FarmerDecisionPage(final Student student) {
        super("Farm Size Decision");

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

        lbl_acre_large.setText(Consts.L_ACRE + " acres");
        lbl_acre_sml.setText(Consts.M_ACRE + " acres");
        lbl_acre_med.setText(Consts.S_ACRE + " acres");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (farmCheck(button)) {
            if (button.equals(smallFarmBtn)) {
                student.farm = new Farm(Consts.Farm_Size.SMALL_FARM);
            } else if (button.equals(medFarmBtn)) {
                student.farm = new Farm(Consts.Farm_Size.MED_FARM);
            } else {
                student.farm = new Farm(Consts.Farm_Size.LARGE_FARM);
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
            new BuySeedsPage(student);
            setVisible(false);
            dispose();
        }
    }

    private void updateBtns() {
        farmSizeAmounts = Consts.DB.numInEachFarm();
        while (farmSizeAmounts == null) {
            farmSizeAmounts = Consts.DB.numInEachFarm();
        }
        //System.out.println(farmSizeAmounts);
        int dbNumOfSmall = farmSizeAmounts.get(Consts.Farm_Size.SMALL_FARM);
        setAvailableLabel(smallAmntLbl, dbNumOfSmall, Consts.S_FARM_CAP);

        int dbNumOfMed = farmSizeAmounts.get(Consts.Farm_Size.MED_FARM);
        setAvailableLabel(medAmntLbl, dbNumOfMed, Consts.M_FARM_CAP);

        int dbNumOfLrg = farmSizeAmounts.get(Consts.Farm_Size.LARGE_FARM);
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
        Consts.Farm_Size farmSize;
        if (farm.equals(smallFarmBtn)) {
            farmLimit = Consts.S_FARM_CAP;
            farmSize = Consts.Farm_Size.SMALL_FARM;

        } else if (farm.equals(medFarmBtn)) {
            farmLimit = Consts.M_FARM_CAP;
            farmSize = Consts.Farm_Size.MED_FARM;
        } else {
            farmLimit = Consts.L_FARM_CAP;
            farmSize = Consts.Farm_Size.LARGE_FARM;
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

    public void setLargeFarmBtn(JButton largeFarmBtn) {
        this.largeFarmBtn = largeFarmBtn;
    }

    public void setMedFarmBtn(JButton medFarmBtn) {
        this.medFarmBtn = medFarmBtn;
    }

    public void setSmallFarmBtn(JButton smallFarmBtn) {
        this.smallFarmBtn = smallFarmBtn;
    }

    public void setFarmerLabel(JLabel farmerLabel) {
        this.farmerLabel = farmerLabel;
    }

    public void setRootPanel(JPanel rootPanel) {
        this.rootPanel = rootPanel;
    }
}
