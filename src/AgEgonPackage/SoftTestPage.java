package AgEgonPackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static AgEgonPackage.Consts.Farm_Size.MED_FARM;
import static AgEgonPackage.Consts.Farm_Size.SMALL_FARM;
import static AgEgonPackage.Consts.Student_Stage.Buy_Seeds;
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
                if (Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).getMidAmnt() < 100) {
                    Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).updateMidAmnt(1000);
                }
                System.out.println("Starting amount: " + Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).getMidAmnt());
                while (Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).getMidAmnt() > 0) {
                    try {
                        sleep(new Random().nextInt(new Random().nextInt(3000)));
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).updateMidAmnt(-10);
                    System.out.println("After thread 1 transaction: " + Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).getMidAmnt());
                }
            }).start();

            new Thread(() -> {
                //System.out.println("HERE");
                while (Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).getMidAmnt() > 0) {
                    Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).updateMidAmnt(-10);
                    System.out.println("After thread 2 transaction: " + Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME).getMidAmnt());
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
                Student student = new Student("farmer" + i, "password", new Farm(Consts.randomFarmSize()));

                if (student.farm.getSize().equals(SMALL_FARM)) sCnt++;
                else if (student.farm.getSize().equals(MED_FARM)) mCnt++;
                else lCnt++;

                student.setStage(Buy_Seeds);
                Consts.DB.saveStudent(student);
                System.out.println("Created Farmer " + i);
            }
        } else if (btn.equals(removeStudentButton)) {
            Consts.DB.removeStudent(Consts.DB.getStudent(TF_stuName.getText()));
        } else if (btn.equals(removeAllStudentsButton)) {
            Consts.DB.removeAllStudents();
        }
    }


}
