import javax.swing.*;
import java.awt.event.ActionEvent;
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

    Student stu;
    String stuName;

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
        welcomeLabel.setText("Welcome " + stu.uName + "!");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WelcomePage();
                setVisible(false);
                dispose();
            }
        });

        neededLabel.setText(String.valueOf(stu.farm.getSeedsNeeded()));
        Runnable t = new Runnable() {
            @Override
            public void run() {
                while (isVisible()) {
                    try {
                        stu = Consts.DB.getStudent(stuName);
                        while (stu == null) {
                            stu = Consts.DB.getStudent(stuName);
                        }
                        onHandLabel.setText(String.valueOf(stu.farm.getTtlSeedsOwned()));
                        HashMap<Consts.Seed_Name, Integer> stuSeeds = stu.farm.getSeedsOwned();
                        stuEarlyLabel.setText(String.valueOf(stuSeeds.get(Consts.Seed_Name.EARLY)));
                        stuMidLabel.setText(String.valueOf(stuSeeds.get(Consts.Seed_Name.MID)));
                        stuFullLabel.setText(String.valueOf(stuSeeds.get(Consts.Seed_Name.FULL)));
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        };
        new Thread(t).start();

        Thread thread1 = new Thread(new CompanyThread(Consts.COMPANY_A_NAME, earlyAmntLabelA, earlyPriceLabelA, midAmntLabelA, midPriceLabelA, fullAmntLabelA,
                fullPriceLabelA));
        thread1.start();
        Thread thread2 = new Thread(new CompanyThread(Consts.COMPANY_B_NAME, earlyAmntLabelB, earlyPriceLabelB, midAmntLabelB, midPriceLabelB, fullAmntLabelB,
                fullPriceLabelB));
        thread2.start();
        Thread thread3 = new Thread(new CompanyThread(Consts.COMPANY_C_NAME, earlyAmntLabelC, earlyPriceLabelC, midAmntLabelC, midPriceLabelC, fullAmntLabelC,
                fullPriceLabelC));
        thread3.start();
        Thread thread4 = new Thread(new CompanyThread(Consts.COMPANY_D_NAME, earlyAmntLabelD, earlyPriceLabelD, midAmntLabelD, midPriceLabelD, fullAmntLabelD,
                fullPriceLabelD));
        thread4.start();
        Thread thread5 = new Thread(new CompanyThread(Consts.COMPANY_E_NAME, earlyAmntLabelE, earlyPriceLabelE, midAmntLabelE, midPriceLabelE, fullAmntLabelE,
                fullPriceLabelE));
        thread5.start();

        final ActionListener buyNowAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

    public static class CompanyThread implements Runnable {
        JLabel erlA, erlP, midA, midP, fullA, fullP;
        String name;
        InputSector DBinput;


        public CompanyThread(String name, JLabel eAmnt, JLabel ePrice, JLabel mAmnt, JLabel mPrice, JLabel fAmnt, JLabel fPrice) {
            this.erlA = eAmnt;
            this.erlP = ePrice;
            this.midA = mAmnt;
            this.midP = mPrice;
            this.fullA = fAmnt;
            this.fullP = fPrice;
            this.name = name;
            this.DBinput = Consts.DB.getInputSeller(name);
        }

        @Override
        public void run() {
            //System.out.println(Consts.DB.getInputSeller(name, Consts.GAME_FLOW.currentYear));
            while (true) {
                try {
                    Consts.checkSetSoldOut(erlA, erlP, DBinput.getEarlyAmnt(), DBinput.getEarlyPrice());
                    Consts.checkSetSoldOut(midA, midP, DBinput.getMidAmnt(), DBinput.getMidPrice());
                    Consts.checkSetSoldOut(fullA, fullP, DBinput.getFullAmnt(), DBinput.getFullPrice());
                    DBinput = Consts.DB.getInputSeller(name);
                    while (DBinput == null) {
                        DBinput = Consts.DB.getInputSeller(name);
                    }
                } catch (Exception e) {
                    //System.out.println("Trouble updating labels on Home Page.");
                    e.getMessage();

                }
            }
        }
    }
}
