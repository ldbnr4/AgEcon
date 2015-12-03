import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lorenzo on 10/1/2015.
 *
 */
public class HomePage extends JFrame {
    JLabel welcomeLabel;
    JPanel rootPanel;
    JButton logoutButton;
    JButton buyNowButtonA;
    JButton buyNowButtonB;
    JButton buyNowButtonC;
    JButton buyNowButtonD;
    JButton buyNowButtonE;
    JLabel earlyAmntLabelA;
    JLabel midAmntLabelA;
    JLabel fullAmntLabelA;
    JLabel earlyPriceLabelA;
    JLabel midPriceLabelA;
    JLabel fullPriceLabelA;
    JLabel earlyAmntLabelB;
    JLabel midAmntLabelB;
    JLabel fullAmntLabelB;
    JLabel earlyPriceLabelB;
    JLabel midPriceLabelB;
    JLabel fullPriceLabelB;
    JLabel earlyAmntLabelC;
    JLabel midAmntLabelC;
    JLabel fullAmntLabelC;
    JLabel earlyPriceLabelC;
    JLabel midPriceLabelC;
    JLabel fullPriceLabelC;
    JLabel earlyAmntLabelD;
    JLabel midAmntLabelD;
    JLabel fullAmntLabelD;
    JLabel earlyPriceLabelD;
    JLabel midPriceLabelD;
    JLabel fullPriceLabelD;
    JLabel earlyAmntLabelE;
    JLabel midAmntLabelE;
    JLabel fullAmntLabelE;
    JLabel earlyPriceLabelE;
    JLabel midPriceLabelE;
    JLabel fullPriceLabelE;

    public HomePage(String name, FarmSector sector) {
        super("Welcome Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeLabel.setText("Welcome " + name + "!");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WelcomePage();
                setVisible(false);
                dispose();
            }
        });

        Thread thread1 = new Thread(new CompanyThread("Company1", earlyAmntLabelA, earlyPriceLabelA, midAmntLabelA, midPriceLabelA, fullAmntLabelA,
                fullPriceLabelA));
        thread1.start();
        Thread thread2 = new Thread(new CompanyThread("Company2", earlyAmntLabelB, earlyPriceLabelB, midAmntLabelB, midPriceLabelB, fullAmntLabelB,
                fullPriceLabelB));
        thread2.start();
        Thread thread3 = new Thread(new CompanyThread("Company3", earlyAmntLabelC, earlyPriceLabelC, midAmntLabelC, midPriceLabelC, fullAmntLabelC,
                fullPriceLabelC));
        thread3.start();
        Thread thread4 = new Thread(new CompanyThread("Company4", earlyAmntLabelD, earlyPriceLabelD, midAmntLabelD, midPriceLabelD, fullAmntLabelD,
                fullPriceLabelD));
        thread4.start();
        Thread thread5 = new Thread(new CompanyThread("Company5", earlyAmntLabelE, earlyPriceLabelE, midAmntLabelE, midPriceLabelE, fullAmntLabelE,
                fullPriceLabelE));
        thread5.start();
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
            this.DBinput = Consts.DB.getInputSeller(name, Consts.GAME_FLOW.currentYear);
        }

        @Override
        public void run() {
            //System.out.println(Consts.DB.getInputSeller(name, Consts.GAME_FLOW.currentYear));
            while (true) {
                erlA.setText(String.valueOf(DBinput.getEarlyAmnt()));
                erlP.setText(String.valueOf(DBinput.getEarlyPrice()));
                midA.setText(String.valueOf(DBinput.getMidAmnt()));
                midP.setText(String.valueOf(DBinput.getMidPrice()));
                fullA.setText(String.valueOf(DBinput.getFullAmnt()));
                fullP.setText(String.valueOf(DBinput.getFullPrice()));
                DBinput = Consts.DB.getInputSeller(name, Consts.GAME_FLOW.currentYear);
            }
        }
    }
}
