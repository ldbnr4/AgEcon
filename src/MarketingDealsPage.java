import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * Created by Lorenzo on 12/15/2015.
 *
 */
public class MarketingDealsPage extends JFrame implements ActionListener {
    JPanel rootPanel;
    JLabel compABushels, compAPrice, compADate, compBBushels, compBDate, compBPrice, compCDate, compCBushels,
            compCPrice, compDDate, compDBushels, compDPrice, compEDate, compEBushels, compEPrice, welcomeLabel;
    JButton compABtn, compBBtn, compCBtn, compDBtn, logoutButton, compEBtn;
    JFormattedTextField compAAmount, compBAmount, compCAmount, compDAmount, compEAmount;
    JTable bshlBalSheet;

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

        printBalSheet();

        final Runnable initMarket = new Runnable() {
            @Override
            public void run() {
                while (!marketsAvail) {
                    marketsAvail = !(Consts.DB.getMarketingComps()).isEmpty();
                }
                initCompThreads();
            }
        };
        new Thread(initMarket).start();

        compABtn.addActionListener(this);
        compBBtn.addActionListener(this);
        compCBtn.addActionListener(this);
        compDBtn.addActionListener(this);
        compEBtn.addActionListener(this);
    }

    void initCompThreads() {
        new Thread(new CompanyThread(Consts.COMPANY_A_NAME, compABushels, compAPrice, compADate)).start();
        new Thread(new CompanyThread(Consts.COMPANY_B_NAME, compBBushels, compBPrice, compBDate)).start();
        new Thread(new CompanyThread(Consts.COMPANY_C_NAME, compCBushels, compCPrice, compCDate)).start();
        new Thread(new CompanyThread(Consts.COMPANY_D_NAME, compDBushels, compDPrice, compDDate)).start();
        new Thread(new CompanyThread(Consts.COMPANY_E_NAME, compEBushels, compEPrice, compEDate)).start();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        // create the formatters, default, display, edit
        NumberFormatter defaultFormatter = new NumberFormatter(new DecimalFormat("#"));
        NumberFormatter displayFormatter =
                new NumberFormatter(new DecimalFormat("#"));
        NumberFormatter editFormatter = new NumberFormatter(new DecimalFormat("#"));
        // set their value classes
        defaultFormatter.setValueClass(Integer.class);
        displayFormatter.setValueClass(Integer.class);
        editFormatter.setValueClass(Integer.class);
        // create and set the DefaultFormatterFactory
        DefaultFormatterFactory salaryFactory =
                new DefaultFormatterFactory(defaultFormatter, displayFormatter, editFormatter);

        this.compAAmount = new JFormattedTextField();
        compAAmount.setFormatterFactory(salaryFactory);

        this.compBAmount = new JFormattedTextField();
        compBAmount.setFormatterFactory(salaryFactory);

        this.compCAmount = new JFormattedTextField();
        compCAmount.setFormatterFactory(salaryFactory);

        this.compDAmount = new JFormattedTextField();
        compDAmount.setFormatterFactory(salaryFactory);

        this.compEAmount = new JFormattedTextField();
        compEAmount.setFormatterFactory(salaryFactory);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        int amount = 0;
        MarketingSector marketingSector = null;
        String compDate = "";
        if (btn.equals(compABtn)) {
            if (compAAmount.getText().isEmpty() || compAAmount.getText().equals("SOLD OUT")) return;
            amount = Integer.valueOf(compAAmount.getText());
            compDate = compADate.getText();
            compAAmount.setText("");
            while (marketingSector == null) {
                marketingSector = Consts.DB.getMarketingComp(Consts.COMPANY_A_NAME);
            }
        } else if (btn.equals(compBBtn)) {
            if (compBAmount.getText().isEmpty() || compBAmount.getText().equals("SOLD OUT")) return;
            amount = Integer.valueOf(compBAmount.getText());
            compDate = compBDate.getText();
            compBAmount.setText("");
            while (marketingSector == null) {
                marketingSector = Consts.DB.getMarketingComp(Consts.COMPANY_B_NAME);
            }
        } else if (btn.equals(compCBtn)) {
            if (compCAmount.getText().isEmpty() || compCAmount.getText().equals("SOLD OUT")) return;
            amount = Integer.valueOf(compCAmount.getText());
            compDate = compCDate.getText();
            compCAmount.setText("");
            while (marketingSector == null) {
                marketingSector = Consts.DB.getMarketingComp(Consts.COMPANY_C_NAME);
            }
        } else if (btn.equals(compDBtn)) {
            if (compDAmount.getText().isEmpty() || compDAmount.getText().equals("SOLD OUT")) return;
            amount = Integer.valueOf(compDAmount.getText());
            compDate = compDDate.getText();
            compDAmount.setText("");
            while (marketingSector == null) {
                marketingSector = Consts.DB.getMarketingComp(Consts.COMPANY_D_NAME);
            }
        } else if (btn.equals(compEBtn)) {
            if (compEAmount.getText().isEmpty() || compEAmount.getText().equals("SOLD OUT")) return;
            amount = Integer.valueOf(compEAmount.getText());
            compDate = compEDate.getText();
            compEAmount.setText("");
            while (marketingSector == null) {
                marketingSector = Consts.DB.getMarketingComp(Consts.COMPANY_E_NAME);
            }
        }
        if (amount < 0) {
            return;
        }
        BushelLegerEntry varEntry = new BushelLegerEntry(compDate, -amount);
        stu.farm.addToLedger(varEntry);
        //tableModel.addRow(new Object[]{compDate, amount});
        if (marketingSector != null && (!printBalSheet() || !marketingSector.updateBshls(amount))) {
            //invalid request error
            stu.farm.removeFromLedger(varEntry);
            printBalSheet();
        }
        Consts.DB.saveStudent(stu);
    }

    boolean printBalSheet() {
        int runTtl = 0;
        DefaultTableModel nModel = new DefaultTableModel(new String[]{"Date", "Amount of Bushels"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        int row = 0;
        for (BushelLegerEntry ledger : stu.farm.getBshlLedger()) {
            runTtl += ledger.getAmount();
            if (runTtl < 0) {
                return false;
            }
            if (row != 0) {
                if (ledger.getDate().equals(nModel.getValueAt(row - 1, 0))) {
                    nModel.setValueAt(runTtl, row - 1, 1);
                    continue;
                }
            }
            nModel.addRow(new Object[]{ledger.getDate(), runTtl});
            row++;
        }
        bshlBalSheet.setModel(nModel);
        return true;
    }
}
