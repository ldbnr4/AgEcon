import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by Lorenzo on 10/1/2015.
 *
 */
public class HomePage extends JFrame {
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
    Boolean inSectsAvail;

    public HomePage(Student student) {
        super("Home Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.stu = student;
        stuName = student.uName;
        inSectsAvail = false;
        welcomeLabel.setText("Welcome " + stu.uName + "!");
        logoutButton.addActionListener(e -> {
            new WelcomePage();
            setVisible(false);
            dispose();
        });
        neededLabel.setText(String.valueOf(stu.farm.getSeedsNeeded()));
        plantButton.addActionListener(e -> {
            stu.farm.plantAction();
            stu.setStage(Consts.Student_Stage.Sell_Yields);
            Consts.DB.saveStudent(stu);
            new MarketingDealsPage(stu);
            setVisible(false);
            dispose();
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
                    onHandLabel.setText(String.valueOf(stu.farm.getTtlSeedsOwned()));
                    HashMap<Consts.Seed_Type, Integer> stuSeeds = stu.farm.getSeedsOwned();
                    stuEarlyLabel.setText(String.valueOf(stuSeeds.get(Consts.Seed_Type.EARLY)));
                    stuMidLabel.setText(String.valueOf(stuSeeds.get(Consts.Seed_Type.MID)));
                    stuFullLabel.setText(String.valueOf(stuSeeds.get(Consts.Seed_Type.FULL)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        final Runnable initInSects = () -> {
            while (!inSectsAvail) {
                inSectsAvail = !(Consts.DB.getInputSectorSellers()).isEmpty();
            }
            startCompThreads();
        };
        new Thread(initInSects).start();

        final ActionListener buyNowAction = e -> {
            if (inSectsAvail) {
                JButton btn = (JButton) e.getSource();
                String btnTxt = btn.getText();
                InputSector company = null;
                if (btnTxt.contains(Consts.COMPANY_A_NAME)) {
                    while (company == null) {
                        company = Consts.DB.getInputSeller(Consts.COMPANY_A_NAME);
                    }
                } else if (btnTxt.contains(Consts.COMPANY_B_NAME)) {
                    while (company == null) {
                        company = Consts.DB.getInputSeller(Consts.COMPANY_B_NAME);
                    }
                } else if (btnTxt.contains(Consts.COMPANY_C_NAME)) {
                    while (company == null) {
                        company = Consts.DB.getInputSeller(Consts.COMPANY_C_NAME);
                    }
                } else if (btnTxt.contains(Consts.COMPANY_D_NAME)) {
                    while (company == null) {
                        company = Consts.DB.getInputSeller(Consts.COMPANY_D_NAME);
                    }
                } else if (btnTxt.contains(Consts.COMPANY_E_NAME)) {
                    while (company == null) {
                        company = Consts.DB.getInputSeller(Consts.COMPANY_E_NAME);
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
    }

    void startCompThreads() {
        new Thread(new CompanyThread(Consts.COMPANY_A_NAME, earlyAmntLabelA, earlyPriceLabelA, midAmntLabelA,
                midPriceLabelA, fullAmntLabelA, fullPriceLabelA)).start();
        new Thread(new CompanyThread(Consts.COMPANY_B_NAME, earlyAmntLabelB, earlyPriceLabelB, midAmntLabelB,
                midPriceLabelB, fullAmntLabelB, fullPriceLabelB)).start();
        new Thread(new CompanyThread(Consts.COMPANY_C_NAME, earlyAmntLabelC, earlyPriceLabelC, midAmntLabelC,
                midPriceLabelC, fullAmntLabelC, fullPriceLabelC)).start();
        new Thread(new CompanyThread(Consts.COMPANY_D_NAME, earlyAmntLabelD, earlyPriceLabelD, midAmntLabelD,
                midPriceLabelD, fullAmntLabelD, fullPriceLabelD)).start();
        new Thread(new CompanyThread(Consts.COMPANY_E_NAME, earlyAmntLabelE, earlyPriceLabelE, midAmntLabelE,
                midPriceLabelE, fullAmntLabelE, fullPriceLabelE)).start();
    }
}
