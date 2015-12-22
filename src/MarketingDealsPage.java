import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.MinimalBalloonStyle;
import net.java.balloontip.utils.TimingUtils;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

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
    private JDatePickerImpl datePickerA, datePickerB, datePickerC, datePickerD, datePickerE;

    private MinimalBalloonStyle modern;
    private BalloonTip balloonTip;

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

        modern = new MinimalBalloonStyle(Color.yellow, 5);
        balloonTip = new BalloonTip(compAAmount, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE,
                BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.balloonTip.setVisible(false);
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

        UtilDateModel model = new UtilDateModel();
        Date initDate = null;
        try {
            initDate = Consts.sd.parse(Consts.getEarlyHarvDt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        if (initDate != null) cal.setTime(initDate);
        else cal.setTime(null);
        model.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        model.setSelected(true);

        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        datePickerA = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePickerB = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePickerC = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePickerD = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePickerE = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        int amount = 0;
        MarketingSector marketingSector = null;
        String compDate, selectedDate = "";
        if (btn.equals(compABtn)) {
            selectedDate = Consts.sd.format(datePickerA.getModel().getValue());
            compDate = compADate.getText();
            if (compAAmount.getText().isEmpty()) {
                balloonTip.setAttachedComponent(compAAmount);
                balloonTip.setTextContents("Please enter a number to sell.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                compAAmount.setBackground(Color.RED);
                return;
            } else if (compABushels.getText().equals("SOLD OUT")) {
                balloonTip.setAttachedComponent(compAAmount);
                balloonTip.setTextContents("This marketing seller has no more to sell.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                compAAmount.setBackground(Color.RED);
                return;
            }
            try {
                if (Consts.sd.parse(selectedDate).after(Consts.sd.parse(compDate))) {
                    balloonTip.setAttachedComponent(compAAmount);
                    balloonTip.setTextContents("Please select a date before or on the sellers needed by date.");
                    TimingUtils.showTimedBalloon(balloonTip, 2500);
                    compAAmount.setBackground(Color.RED);
                    return;
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            while (marketingSector == null) {
                marketingSector = Consts.DB.getMarketingComp(Consts.COMPANY_A_NAME);
            }
            amount = Integer.valueOf(compAAmount.getText());
            compAAmount.setText("");
        } else if (btn.equals(compBBtn)) {
            selectedDate = Consts.sd.format(datePickerB.getModel().getValue());
            compDate = compBDate.getText();
            if (compBAmount.getText().isEmpty()) {
                balloonTip.setAttachedComponent(compBAmount);
                balloonTip.setTextContents("Please enter a number to sell.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                compBAmount.setBackground(Color.RED);
                return;
            } else if (compBBushels.getText().equals("SOLD OUT")) {
                balloonTip.setAttachedComponent(compBAmount);
                balloonTip.setTextContents("This marketing seller has no more to sell.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                compBAmount.setBackground(Color.RED);
                return;
            }
            try {
                if (Consts.sd.parse(selectedDate).after(Consts.sd.parse(compDate))) {
                    balloonTip.setAttachedComponent(compBAmount);
                    balloonTip.setTextContents("Please select a date before or on the sellers needed by date.");
                    TimingUtils.showTimedBalloon(balloonTip, 2500);
                    compBAmount.setBackground(Color.RED);
                    return;
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            while (marketingSector == null) {
                marketingSector = Consts.DB.getMarketingComp(Consts.COMPANY_B_NAME);
            }
            amount = Integer.valueOf(compBAmount.getText());
            compBAmount.setText("");
        } else if (btn.equals(compCBtn)) {
            selectedDate = Consts.sd.format(datePickerC.getModel().getValue());
            compDate = compCDate.getText();
            if (compCAmount.getText().isEmpty()) {
                balloonTip.setAttachedComponent(compCAmount);
                balloonTip.setTextContents("Please enter a number to sell.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                compCAmount.setBackground(Color.RED);
                return;
            } else if (compCBushels.getText().equals("SOLD OUT")) {
                balloonTip.setAttachedComponent(compCAmount);
                balloonTip.setTextContents("This marketing seller has no more to sell.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                compCAmount.setBackground(Color.RED);
                return;
            }
            try {
                if (Consts.sd.parse(selectedDate).after(Consts.sd.parse(compDate))) {
                    balloonTip.setAttachedComponent(compCAmount);
                    balloonTip.setTextContents("Please select a date before or on the sellers needed by date.");
                    TimingUtils.showTimedBalloon(balloonTip, 2500);
                    compCAmount.setBackground(Color.RED);
                    return;
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            while (marketingSector == null) {
                marketingSector = Consts.DB.getMarketingComp(Consts.COMPANY_C_NAME);
            }
            amount = Integer.valueOf(compCAmount.getText());
            compCAmount.setText("");
        } else if (btn.equals(compDBtn)) {
            selectedDate = Consts.sd.format(datePickerD.getModel().getValue());
            compDate = compDDate.getText();
            if (compDAmount.getText().isEmpty()) {
                balloonTip.setAttachedComponent(compDAmount);
                balloonTip.setTextContents("Please enter a number to sell.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                compDAmount.setBackground(Color.RED);
                return;
            } else if (compDBushels.getText().equals("SOLD OUT")) {
                balloonTip.setAttachedComponent(compDAmount);
                balloonTip.setTextContents("This marketing seller has no more to sell.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                compDAmount.setBackground(Color.RED);
                return;
            }
            try {
                if (Consts.sd.parse(selectedDate).after(Consts.sd.parse(compDate))) {
                    balloonTip.setAttachedComponent(compDAmount);
                    balloonTip.setTextContents("Please select a date before or on the sellers needed by date.");
                    TimingUtils.showTimedBalloon(balloonTip, 2500);
                    compDAmount.setBackground(Color.RED);
                    return;
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            while (marketingSector == null) {
                marketingSector = Consts.DB.getMarketingComp(Consts.COMPANY_D_NAME);
            }
            amount = Integer.valueOf(compDAmount.getText());
            compDAmount.setText("");
        } else if (btn.equals(compEBtn)) {
            selectedDate = Consts.sd.format(datePickerE.getModel().getValue());
            compDate = compEDate.getText();
            if (compEAmount.getText().isEmpty()) {
                balloonTip.setAttachedComponent(compEAmount);
                balloonTip.setTextContents("Please enter a number to sell.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                compEAmount.setBackground(Color.RED);
                return;
            } else if (compEBushels.getText().equals("SOLD OUT")) {
                balloonTip.setAttachedComponent(compEAmount);
                balloonTip.setTextContents("This marketing seller has no more to sell.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                compEAmount.setBackground(Color.RED);
                return;
            }
            try {
                if (Consts.sd.parse(selectedDate).after(Consts.sd.parse(compDate))) {
                    balloonTip.setAttachedComponent(compEAmount);
                    balloonTip.setTextContents("Please select a date before or on the sellers needed by date.");
                    TimingUtils.showTimedBalloon(balloonTip, 2500);
                    compEAmount.setBackground(Color.RED);
                    return;
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            while (marketingSector == null) {
                marketingSector = Consts.DB.getMarketingComp(Consts.COMPANY_E_NAME);
            }
            amount = Integer.valueOf(compEAmount.getText());
            compEAmount.setText("");
        }
        if (amount < 0) return;
        BushelLegerEntry varEntry = new BushelLegerEntry(selectedDate, -amount);
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

    class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private String datePattern = "MMM dd yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public String stringToValue(String text) throws ParseException {
            return String.valueOf(dateFormatter.parseObject(text));
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }
}
