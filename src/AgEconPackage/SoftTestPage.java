/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static AgEconPackage.Consts.Farm_Size.MED_FARM;
import static AgEconPackage.Consts.Farm_Size.SMALL_FARM;
import static AgEconPackage.Consts.Seed_Type.MID;
import static java.lang.Thread.sleep;

/**
 * Created by Lorenzo on 1/10/2016.
 *
 */
public class SoftTestPage extends JFrame implements ActionListener {
    private JButton buyingSeedsLoadTestButton;
    private JPanel rootPanel;
    private JButton generateFarmersButton;
    private JTextField TF_stuName;
    private JButton removeStudentButton;
    private JButton removeAllStudentsButton;

    public SoftTestPage() {
        super("Soft Test Page");
        setContentPane(this.rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        buyingSeedsLoadTestButton.addActionListener(this);
        generateFarmersButton.addActionListener(this);
        removeAllStudentsButton.addActionListener(this);
        removeStudentButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (btn.equals(buyingSeedsLoadTestButton)) {
            new Thread(() -> {
                if (Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).getAmnt(MID) < 100) {
                    Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).updateAmnt(MID, 1000);
                }
                System.out.println("Starting amount: " + Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).getAmnt(MID));
                while (Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).getAmnt(MID) > 0) {
                    try {
                        sleep(new Random().nextInt(new Random().nextInt(3000)));
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).updateAmnt(MID, -10);
                    System.out.println("After thread 1 transaction: " + Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).getAmnt(MID));
                }
            }).start();

            new Thread(() -> {
                //System.out.println("HERE");
                while (Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).getAmnt(MID) > 0) {
                    Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).updateAmnt(MID, -10);
                    System.out.println("After thread 2 transaction: " + Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).getAmnt(MID));
                    try {
                        sleep(new Random().nextInt(new Random().nextInt(3000)));
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }).start();
        } else if (btn.equals(generateFarmersButton)) {
            int sCnt = 0, mCnt = 0, lCnt = 0;
            for (int i = 0; i < Consts.TOTAL_STUS - 1; i++) {
                Student student = new Student("farmer" + i, "password");
                student.addReplaceFarm(new Farm(Consts.randomFarmSize()));

                if (student.getSector().getSize().equals(SMALL_FARM)) sCnt++;
                else if (student.getSector().getSize().equals(MED_FARM)) mCnt++;
                else lCnt++;
                System.out.println("Created Farmer " + i);
            }
        } else if (btn.equals(removeStudentButton)) {
            Consts.DB.removeStudent(Consts.DB.getStudent(TF_stuName.getText()));
        } else if (btn.equals(removeAllStudentsButton)) {
            Consts.DB.removeAllStudents();
        }
    }


}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */