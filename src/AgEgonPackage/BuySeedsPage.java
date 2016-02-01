package AgEgonPackage;

import javax.swing.*;
import java.awt.*;
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
    private JLabel welcomeLabel;
    private JPanel rootPanel;
    private JButton logoutButton, buyNowButtonA, buyNowButtonB, buyNowButtonC, buyNowButtonD, buyNowButtonE;
    private JLabel earlyAmntLabelA, midAmntLabelA, fullAmntLabelA, earlyPriceLabelA, midPriceLabelA, fullPriceLabelA;
    private JLabel earlyAmntLabelB, midAmntLabelB, fullAmntLabelB, earlyPriceLabelB, midPriceLabelB, fullPriceLabelB;
    private JLabel earlyAmntLabelC, midAmntLabelC, fullAmntLabelC, earlyPriceLabelC, midPriceLabelC, fullPriceLabelC;
    private JLabel earlyAmntLabelD, midAmntLabelD, fullAmntLabelD, earlyPriceLabelD, midPriceLabelD, fullPriceLabelD;
    private JLabel earlyAmntLabelE, midAmntLabelE, fullAmntLabelE, earlyPriceLabelE, midPriceLabelE, fullPriceLabelE;
    private JLabel neededLabel;
    private JLabel onHandLabel;
    private JLabel stuEarlyLabel, stuMidLabel, stuFullLabel;
    private JButton plantButton;
    private String stuName;
    private Student stu;
    private JLabel companyALabel;
    private JLabel companyBLabel;
    private JLabel companyCLabel;
    private JLabel companyDLabel;
    private JLabel companyELabel;

    public BuySeedsPage(Student student) {
        super("Buy Seeds Page");
        setContentPane(rootPanel);
        setResizable(false);
        setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
        pack();
        setLocationRelativeTo(null);
        if (!Consts.DB.NNgetGameFlow().isInpuSect()) {
            new WaitPage();
            setVisible(false);
            dispose();
        }
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.stu = student;
        stuName = student.uName;
        welcomeLabel.setText("Hey " + student.uName + "!");
        logoutButton.addActionListener(e -> {
            try {
                sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            new WelcomePage();
            setVisible(false);
            dispose();
        });
        neededLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(student.getFarm().getSeedsNeeded()));
        plantButton.addActionListener(e -> {
            try {
                sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            String msg = Consts.htmlWrapper("Are you sure you are done ordering seeds? You will not be able to make any" +
                    " additional orders after continuing.", 4);
            int option = JOptionPane.showConfirmDialog(rootPanel, msg, "Order confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                callPlantAction(stu);
                new MarketingDealsPage(Consts.DB.getStudent(stu.uName));
                setVisible(false);
                dispose();
            }
        });

        new Thread(() -> {
            while (isVisible()) {
                try {
                    stu = Consts.DB.getStudent(stuName);
                    while (stu == null) {
                        stu = Consts.DB.getStudent(stuName);
                    }
                    onHandLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(stu.getFarm().getTtlSeedsOwned()));
                    HashMap<Consts.Seed_Type, Integer> stuSeeds = stu.getFarm().getSeedsOwned();
                    stuEarlyLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(stuSeeds.get(Consts.Seed_Type.EARLY)));
                    stuMidLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(stuSeeds.get(Consts.Seed_Type.MID)));
                    stuFullLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(stuSeeds.get(Consts.Seed_Type.FULL)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        startCompThreads();

        final ActionListener buyNowAction = e -> {
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

    public void callPlantAction(Student stu) {
        stu.getFarm().plantAction();
        stu.getFarm().setStage(Consts.Student_Stage.Sell_Yields);
        Consts.DB.saveStudent(stu);
    }
}
