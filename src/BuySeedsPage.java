import javax.swing.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import static java.lang.Thread.sleep;

/**
 * Created by Lorenzo on 10/1/2015.
 *
 */
public class BuySeedsPage extends JFrame {
    JLabel welcomeLabel;
    JPanel rootPanel;
    JButton logoutButton, buyNowButtonA, buyNowButtonB, buyNowButtonC, buyNowButtonD, buyNowButtonE;
    JLabel earlyAmntLabelA, midAmntLabelA, fullAmntLabelA, earlyPriceLabelA, midPriceLabelA, fullPriceLabelA;
    JLabel earlyAmntLabelB, midAmntLabelB, fullAmntLabelB, earlyPriceLabelB, midPriceLabelB, fullPriceLabelB;
    JLabel earlyAmntLabelC, midAmntLabelC, fullAmntLabelC, earlyPriceLabelC, midPriceLabelC, fullPriceLabelC;
    JLabel earlyAmntLabelD, midAmntLabelD, fullAmntLabelD, earlyPriceLabelD, midPriceLabelD, fullPriceLabelD;
    JLabel earlyAmntLabelE, midAmntLabelE, fullAmntLabelE, earlyPriceLabelE, midPriceLabelE, fullPriceLabelE;
    JLabel neededLabel;
    JLabel onHandLabel;
    JLabel stuEarlyLabel, stuMidLabel, stuFullLabel;
    JButton plantButton;
    Student stu;
    String stuName;
    boolean inSectsAvail;
    private JLabel companyALabel;
    private JLabel companyBLabel;
    private JLabel companyCLabel;
    private JLabel companyDLabel;
    private JLabel companyELabel;

    public BuySeedsPage(Student student) {
        super("Buy Seeds Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.stu = student;
        stuName = student.uName;
        inSectsAvail = false;
        welcomeLabel.setText("Hey " + stu.uName + "!");
        logoutButton.addActionListener(e -> {
            inSectsAvail = false;
            try {
                sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            new WelcomePage();
            setVisible(false);
            dispose();
        });
        neededLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(stu.farm.getSeedsNeeded()));
        plantButton.addActionListener(e -> {
            inSectsAvail = false;
            try {
                sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            String msg = Consts.htmlWrapper("Are you sure you are done ordering seeds? You will not be able to make any" +
                    " additional orders after continuing.", 4);
            int option = JOptionPane.showConfirmDialog(rootPanel, msg, "Order confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                stu.farm.plantAction();
                stu.setStage(Consts.Student_Stage.Sell_Yields);
                Consts.DB.saveStudent(stu);
                new MarketingDealsPage(stu);
                setVisible(false);
                dispose();
            }
        });

        new Thread(() -> {
            while (isVisible()) {
                try {
                    if (inSectsAvail) {
                        stu = Consts.DB.getStudent(stuName);
                        while (stu == null) {
                            stu = Consts.DB.getStudent(stuName);
                        }
                    }
                    onHandLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(stu.farm.getTtlSeedsOwned()));
                    HashMap<Consts.Seed_Type, Integer> stuSeeds = stu.farm.getSeedsOwned();
                    stuEarlyLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(stuSeeds.get(Consts.Seed_Type.EARLY)));
                    stuMidLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(stuSeeds.get(Consts.Seed_Type.MID)));
                    stuFullLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(stuSeeds.get(Consts.Seed_Type.FULL)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        final Runnable initInSects = () -> {
            while (!inSectsAvail && isVisible()) {
                inSectsAvail = !(Consts.DB.getInputSectorSellers()).isEmpty();
            }
            startCompThreads();
        };
        new Thread(initInSects).start();

        final ActionListener buyNowAction = e -> {
            if (inSectsAvail) {
                JButton btn = (JButton) e.getSource();
                InputSector company = null;
                if (btn.equals(buyNowButtonA)) {
                    while (company == null) {
                        company = Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_A_NAME);
                    }
                } else if (btn.equals(buyNowButtonB)) {
                    while (company == null) {
                        company = Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_B_NAME);
                    }
                } else if (btn.equals(buyNowButtonC)) {
                    while (company == null) {
                        company = Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_C_NAME);
                    }
                } else if (btn.equals(buyNowButtonD)) {
                    while (company == null) {
                        company = Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_D_NAME);
                    }
                } else if (btn.equals(buyNowButtonE)) {
                    while (company == null) {
                        company = Consts.DB.getInputSeller(Consts.SUPPLY_COMPANY_E_NAME);
                    }
                } else {
                    System.out.println(btn.getName() + " does not have a case.");
                }
                new BuyingSeedsPage(stu, company);
            }
        };

        buyNowButtonA.addActionListener(buyNowAction);
        buyNowButtonB.addActionListener(buyNowAction);
        buyNowButtonC.addActionListener(buyNowAction);
        buyNowButtonD.addActionListener(buyNowAction);
        buyNowButtonE.addActionListener(buyNowAction);

        buyNowButtonA.setText(buyNowButtonA.getText() + " " + Consts.SUPPLY_COMPANY_A_NAME);
        buyNowButtonB.setText(buyNowButtonB.getText() + " " + Consts.SUPPLY_COMPANY_B_NAME);
        buyNowButtonC.setText(buyNowButtonC.getText() + " " + Consts.SUPPLY_COMPANY_C_NAME);
        buyNowButtonD.setText(buyNowButtonD.getText() + " " + Consts.SUPPLY_COMPANY_D_NAME);
        buyNowButtonE.setText(buyNowButtonE.getText() + " " + Consts.SUPPLY_COMPANY_E_NAME);

        companyALabel.setText(Consts.SUPPLY_COMPANY_A_NAME);
        companyBLabel.setText(Consts.SUPPLY_COMPANY_B_NAME);
        companyCLabel.setText(Consts.SUPPLY_COMPANY_C_NAME);
        companyDLabel.setText(Consts.SUPPLY_COMPANY_D_NAME);
        companyELabel.setText(Consts.SUPPLY_COMPANY_E_NAME);

    }

    void startCompThreads() {
        new Thread(new CompanyThread(this, Consts.SUPPLY_COMPANY_A_NAME, earlyAmntLabelA, earlyPriceLabelA, midAmntLabelA,
                midPriceLabelA, fullAmntLabelA, fullPriceLabelA)).start();
        new Thread(new CompanyThread(this, Consts.SUPPLY_COMPANY_B_NAME, earlyAmntLabelB, earlyPriceLabelB, midAmntLabelB,
                midPriceLabelB, fullAmntLabelB, fullPriceLabelB)).start();
        new Thread(new CompanyThread(this, Consts.SUPPLY_COMPANY_C_NAME, earlyAmntLabelC, earlyPriceLabelC, midAmntLabelC,
                midPriceLabelC, fullAmntLabelC, fullPriceLabelC)).start();
        new Thread(new CompanyThread(this, Consts.SUPPLY_COMPANY_D_NAME, earlyAmntLabelD, earlyPriceLabelD, midAmntLabelD,
                midPriceLabelD, fullAmntLabelD, fullPriceLabelD)).start();
        new Thread(new CompanyThread(this, Consts.SUPPLY_COMPANY_E_NAME, earlyAmntLabelE, earlyPriceLabelE, midAmntLabelE,
                midPriceLabelE, fullAmntLabelE, fullPriceLabelE)).start();
    }
}
