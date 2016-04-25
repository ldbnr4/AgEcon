/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

import AgEconPackage.Consts.Farm_Size;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

import static AgEconPackage.Consts.Farm_Size.*;
import static java.lang.Thread.sleep;

/**
 * Created by Lorenzo on 10/22/2015.
 *
 */
public class FarmerDecisionPage extends JFrame implements ActionListener {
    private Student student;
    private HashMap<Farm_Size, Integer> farmSizeAmounts;
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
    private JButton submitButton;
    private JRadioButton yesRadioButton;
    private JRadioButton noRadioButton;

    private Farm_Size farmSizeChoice;


    FarmerDecisionPage(final Student student) {
        super("Farm Size Decision");

        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        farmerLabel.setText("Hey " + student.uName + "!");
        this.student = student;

        Image image = null;
        try {
            image = ImageIO.read(getClass().getResource("/img/check_icon.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ImageIcon checkIcon = new ImageIcon(image);

        smallFarmBtn.addActionListener(this);
        smallFarmBtn.setSelectedIcon(checkIcon);

        medFarmBtn.addActionListener(this);
        medFarmBtn.setSelectedIcon(checkIcon);

        largeFarmBtn.addActionListener(this);
        largeFarmBtn.setSelectedIcon(checkIcon);

        submitButton.addActionListener(this);

        Runnable r = () -> {
            while (isVisible()) {
                updateBtns();
            }
            //System.out.println("Killed a thread.");
        };
        new Thread(r).start();

        backBtn.addActionListener(e -> {
            if (student.numOfSeasonsPlayed() < 2) {
                Consts.DB.removeStudent(student);
            }
            new WelcomePage();
            setVisible(false);
            dispose();
        });

        lbl_acre_large.setText(Consts.L_ACRE + " acres");
        lbl_acre_sml.setText(Consts.S_ACRE + " acres");
        lbl_acre_med.setText(Consts.M_ACRE + " acres");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if(button.equals(submitButton)){
            student.addReplaceFarm(new Farm(farmSizeChoice));
            student.getFarm().setIrrigated(yesRadioButton.isSelected());
            Consts.DB.saveStudent(student);
            new BuySeedsPage(student);
            setVisible(false);
            dispose();
        }
        else {
            if (farmCheck(button)) {
                button.setSelected(true);
                if (button.equals(smallFarmBtn)) {
                    medFarmBtn.setSelected(false);
                    largeFarmBtn.setSelected(false);
                    farmSizeChoice = SMALL_FARM;
                } else if (button.equals(medFarmBtn)) {
                    smallFarmBtn.setSelected(false);
                    largeFarmBtn.setSelected(false);
                    farmSizeChoice = MED_FARM;
                } else if (button.equals(largeFarmBtn)) {
                    medFarmBtn.setSelected(false);
                    smallFarmBtn.setSelected(false);
                    farmSizeChoice = LARGE_FARM;
                }
            }
        }
    }

    private void updateBtns() {
        farmSizeAmounts = Consts.DB.numInEachFarm();
        while (farmSizeAmounts == null) {
            farmSizeAmounts = Consts.DB.numInEachFarm();
        }
        //System.out.println(farmSizeAmounts);
        try {
            int dbNumOfSmall = farmSizeAmounts.get(SMALL_FARM);
            setAvailableLabel(smallAmntLbl, dbNumOfSmall, Consts.S_FARM_CAP);

            int dbNumOfMed = farmSizeAmounts.get(MED_FARM);
            setAvailableLabel(medAmntLbl, dbNumOfMed, Consts.M_FARM_CAP);

            int dbNumOfLrg = farmSizeAmounts.get(LARGE_FARM);
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
        } catch (NullPointerException e) {
            try {
                sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.out.println("Ran into a problem " + e.getMessage());
        }
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
        Farm_Size farmSize;
        if (farm.equals(smallFarmBtn)) {
            farmLimit = Consts.S_FARM_CAP;
            farmSize = SMALL_FARM;

        } else if (farm.equals(medFarmBtn)) {
            farmLimit = Consts.M_FARM_CAP;
            farmSize = MED_FARM;
        } else {
            farmLimit = Consts.L_FARM_CAP;
            farmSize = LARGE_FARM;
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

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */