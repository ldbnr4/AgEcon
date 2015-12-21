import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

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
    JTable table1;

    String[] stuKeys;
    DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Date", "Amount of Bushels"}, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

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

        for (BushelLegerEntry ledger : stu.farm.getBshlLedger()) {
            tableModel.addRow(new Object[]{ledger.getDate(), ledger.getAmount()});
        }
        table1.setModel(tableModel);



        /*stuKeys = (String[]) stu.farm.getBshlLedger().keySet().toArray();
        this.earlyDateLabel.setText(String.valueOf(stuKeys[2]));
        this.earlyAmountLabel.setText(String.valueOf(stu.farm.bshlLedger.get(String.valueOf(stuKeys[2]))));
        this.midDateLabel.setText(String.valueOf(stuKeys[1]));
        this.midAmountLabel.setText(String.valueOf(stu.farm.bshlLedger.get(String.valueOf(stuKeys[1]))));
        this.fullDateLabel.setText(String.valueOf(stuKeys[0]));
        this.fullAmountLabel.setText(String.valueOf(stu.farm.bshlLedger.get(String.valueOf(stuKeys[0]))));*/

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
        // create the JFormattedTextField
        this.compAAmount = new JFormattedTextField();
        // create the formatters, default, display, edit
        NumberFormatter defaultFormatter = new NumberFormatter();
        NumberFormatter displayFormatter =
                new NumberFormatter(new DecimalFormat("#,###"));
        NumberFormatter editFormatter = new NumberFormatter();
        // set their value classes
        defaultFormatter.setValueClass(Integer.class);
        displayFormatter.setValueClass(Integer.class);
        editFormatter.setValueClass(Integer.class);
        // create and set the DefaultFormatterFactory
        DefaultFormatterFactory salaryFactory =
                new DefaultFormatterFactory(defaultFormatter, displayFormatter, editFormatter);
        compAAmount.setFormatterFactory(salaryFactory);

        this.compBAmount = new JFormattedTextField();
        compBAmount.setFormatterFactory(salaryFactory);

        this.compCAmount = new JFormattedTextField();
        compCAmount.setFormatterFactory(salaryFactory);

        this.compDAmount = new JFormattedTextField();
        compDAmount.setFormatterFactory(salaryFactory);

        this.compEAmount = new JFormattedTextField();
        compEAmount.setFormatterFactory(salaryFactory);

        /*String[] columnNames = {"Date", "Amount of Bushels"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };*/

        //table1 = new JTable();
        //table1.setPreferredScrollableViewportSize(new Dimension(300, 200));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        int amount;
        MarketingSector marketingSector = null;
        if (btn.equals(compABtn)) {
            amount = Integer.valueOf(compAAmount.getText());
            while (marketingSector == null) {
                marketingSector = Consts.DB.getMarketingComp(Consts.COMPANY_A_NAME);
            }
            if (!marketingSector.updateBshls(amount)) {

            } else {
                Date compDate = null, stuDate = null;
                try {
                    compDate = Consts.sd.parse(compADate.getText());
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                for (String date : stuKeys) {
                    try {
                        stuDate = Consts.sd.parse(date);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    /*if(){

                    }*/

                }
            }
        } else if (btn.equals(compBBtn)) {
            amount = Integer.valueOf(compBAmount.getText());
            while (marketingSector == null) {
                marketingSector = Consts.DB.getMarketingComp(Consts.COMPANY_B_NAME);
            }
            if (!marketingSector.updateBshls(amount)) {

            } else {
                DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
                tableModel.addRow(new String[]{"today", "here"});
                table1.setModel(tableModel);
            }
        } else if (btn.equals(compCBtn)) {
            amount = Integer.valueOf(compCAmount.getText());
            while (marketingSector == null) {
                marketingSector = Consts.DB.getMarketingComp(Consts.COMPANY_C_NAME);
            }
            if (!marketingSector.updateBshls(amount)) {

            }
        } else if (btn.equals(compDBtn)) {
            amount = Integer.valueOf(compDAmount.getText());
            while (marketingSector == null) {
                marketingSector = Consts.DB.getMarketingComp(Consts.COMPANY_D_NAME);
            }
            if (!marketingSector.updateBshls(amount)) {

            }
        } else if (btn.equals(compEBtn)) {
            amount = Integer.valueOf(compEAmount.getText());
            while (marketingSector == null) {
                marketingSector = Consts.DB.getMarketingComp(Consts.COMPANY_E_NAME);
            }
            if (!marketingSector.updateBshls(amount)) {

            }
        }
    }
}
