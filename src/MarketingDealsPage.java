import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lorenzo on 12/15/2015.
 *
 */
public class MarketingDealsPage extends JFrame {
    JPanel rootPanel;
    JLabel compABushels, compAPrice, compADate, compBBushels, compBDate, compBPrice, compCDate, compCBushels,
            compCPrice, compDDate, compDBushels, compDPrice, compEDate, compEBushels, compEPrice, welcomeLabel,
            fullAmountLabel, midAmountLabel, earlyAmountLabel, earlyDateLabel, midDateLabel, fullDateLabel;
    JButton compABtn, compBBtn, compCBtn, compDBtn, logoutButton;

    private Student stu;
    private String stuName;
    private Boolean marketsAvail;

    public MarketingDealsPage(Student student) {
        super("Markets Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.stu = student;
        this.stuName = student.uName;
        this.marketsAvail = false;
        welcomeLabel.setText("Welcome " + this.stuName + "!");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WelcomePage();
                setVisible(false);
                dispose();
            }
        });

        Object[] keys = stu.farm.getBshlHash().keySet().toArray();
        this.earlyDateLabel.setText(String.valueOf(keys[2]));
        this.earlyAmountLabel.setText(String.valueOf(stu.farm.bshlHash.get(String.valueOf(keys[2]))));
        this.midDateLabel.setText(String.valueOf(keys[1]));
        this.midAmountLabel.setText(String.valueOf(stu.farm.bshlHash.get(String.valueOf(keys[1]))));
        this.fullDateLabel.setText(String.valueOf(keys[0]));
        this.fullAmountLabel.setText(String.valueOf(stu.farm.bshlHash.get(String.valueOf(keys[0]))));

        final Runnable initMarket = new Runnable() {
            @Override
            public void run() {
                while (!marketsAvail) {
                    marketsAvail = !(Consts.DB.getMarketingComps()).isEmpty();
                }
                setCompLabels();
            }
        };
        new Thread(initMarket).start();
    }

    void setCompLabels() {
        new Thread(new CompanyThread(Consts.COMPANY_A_NAME, compABushels, compAPrice, compADate)).start();
        new Thread(new CompanyThread(Consts.COMPANY_B_NAME, compBBushels, compBPrice, compBDate)).start();
        new Thread(new CompanyThread(Consts.COMPANY_C_NAME, compCBushels, compCPrice, compCDate)).start();
        new Thread(new CompanyThread(Consts.COMPANY_D_NAME, compDBushels, compDPrice, compDDate)).start();
        new Thread(new CompanyThread(Consts.COMPANY_E_NAME, compEBushels, compEPrice, compEDate)).start();
    }
}
