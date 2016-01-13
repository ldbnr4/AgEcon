import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.MinimalBalloonStyle;
import net.java.balloontip.utils.TimingUtils;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
    private JButton endSeasonButton;

    private BalloonTip balloonTip, successBalloon;

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
        welcomeLabel.setText("Hey " + this.stuName + "!");
        logoutButton.addActionListener(e -> {
            new WelcomePage();
            setVisible(false);
            dispose();
        });

        printBalSheet();

        final Runnable initMarket = () -> {
            while (!marketsAvail) {
                marketsAvail = !(Consts.DB.getMarketingComps()).isEmpty();
            }
            initCompThreads();
        };
        new Thread(initMarket).start();

        compABtn.addActionListener(this);
        compBBtn.addActionListener(this);
        compCBtn.addActionListener(this);
        compDBtn.addActionListener(this);
        compEBtn.addActionListener(this);


        MinimalBalloonStyle modern = new MinimalBalloonStyle(Color.yellow, 5);
        balloonTip = new BalloonTip(compAAmount, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE,
                BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.balloonTip.setVisible(false);

        MinimalBalloonStyle greenModern = new MinimalBalloonStyle(Color.GREEN, 5);
        successBalloon = new BalloonTip(bshlBalSheet, new JLabel(), greenModern, BalloonTip.Orientation.RIGHT_ABOVE,
                BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.successBalloon.setVisible(false);

        endSeasonButton.addActionListener(e -> {
            stu.setStage(Consts.Student_Stage.End_of_Season);
            Consts.DB.saveStudent(stu);
            new EndofSeasonPage(stu);
            setVisible(false);
            dispose();
        });

        compAAmount.addKeyListener(customKeyListner(compAAmount));
        compBAmount.addKeyListener(customKeyListner(compBAmount));
        compCAmount.addKeyListener(customKeyListner(compCAmount));
        compDAmount.addKeyListener(customKeyListner(compDAmount));
        compEAmount.addKeyListener(customKeyListner(compEAmount));

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
        if (btn.equals(compABtn)) {
            buttonHandler(datePickerA, compADate, compAAmount, Consts.COMPANY_A_NAME,
                    Double.valueOf(compAPrice.getText().replaceAll("$", "")));
        } else if (btn.equals(compBBtn)) {
            buttonHandler(datePickerB, compBDate, compBAmount, Consts.COMPANY_B_NAME,
                    Double.valueOf(compBPrice.getText().replaceAll("$", "")));
        } else if (btn.equals(compCBtn)) {
            buttonHandler(datePickerC, compCDate, compCAmount, Consts.COMPANY_C_NAME,
                    Double.valueOf(compCPrice.getText().replaceAll("$", "")));
        } else if (btn.equals(compDBtn)) {
            buttonHandler(datePickerD, compDDate, compDAmount, Consts.COMPANY_D_NAME,
                    Double.valueOf(compDPrice.getText().replaceAll("$", "")));
        } else if (btn.equals(compEBtn)) {
            buttonHandler(datePickerE, compEDate, compEAmount, Consts.COMPANY_E_NAME,
                    Double.valueOf(compEPrice.getText().replaceAll("$", "")));
        }
    }

    boolean printBalSheet() {
        int runTtl = 0;
        DefaultTableModel nModel = new DefaultTableModel(new String[]{"Date", "Amount of Bushels"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        int row = 0;
        for (BushelLedgerEntry ledger : stu.farm.getBshlLedger()) {
            runTtl += ledger.getAmount();
            if (runTtl < 0) {
                return false;
            }
            if (row != 0) {
                if (ledger.getDate().equals(nModel.getValueAt(row - 1, 0))) {
                    nModel.setValueAt(NumberFormat.getNumberInstance(Locale.US).format(runTtl), row - 1, 1);
                    continue;
                }
            }
            try {
                nModel.addRow(new Object[]{Consts.sd2.format(Consts.sd.parse(ledger.getDate())), NumberFormat.getNumberInstance(Locale.US).format(runTtl)});
            } catch (ParseException e) {
                e.printStackTrace();
            }
            row++;
        }
        bshlBalSheet.setFont(new Font("Segoe UI", 0, 18));
        bshlBalSheet.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        bshlBalSheet.setFillsViewportHeight(true);
        bshlBalSheet.setRowHeight(25);
        bshlBalSheet.setModel(nModel);
        return true;
    }

    void buttonHandler(JDatePickerImpl jDatePicker, JLabel dateLabel, JFormattedTextField amntField, String compName,
                       double bndlPrice) {
        if (amntField.getText().isEmpty()) {
            balloonTip.setAttachedComponent(amntField);
            balloonTip.setTextContents("Please enter a number to sell.");
            TimingUtils.showTimedBalloon(balloonTip, 2500);
            return;
        }
        if (dateLabel.getText().equals("SOLD OUT")) {
            balloonTip.setAttachedComponent(amntField);
            balloonTip.setTextContents("This marketing seller has no more to sell.");
            TimingUtils.showTimedBalloon(balloonTip, 2500);
            return;
        }
        String selectedDate = Consts.sd.format(jDatePicker.getModel().getValue());
        try {
            if (Consts.sd.parse(selectedDate).after(
                    Consts.sd.parse(dateLabel.getText()))) {
                balloonTip.setAttachedComponent(jDatePicker);
                balloonTip.setTextContents("Please select a date before or on the sellers needed by date.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                return;
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        int amount = Integer.valueOf(amntField.getText().replaceAll(",", ""));
        if (amount <= 0) return;
        if (Consts.DB.getMarketingComp(compName).getBshls() == 0) {
            balloonTip.setAttachedComponent(amntField);
            balloonTip.setTextContents("This marketing seller has no more to sell.");
            TimingUtils.showTimedBalloon(balloonTip, 2500);

            return;
        }
        if (!Consts.DB.getMarketingComp(compName).updateBshls(amount)) {
            balloonTip.setAttachedComponent(amntField);
            balloonTip.setTextContents("This seller does not need this much.");
            TimingUtils.showTimedBalloon(balloonTip, 2500);
            return;
        }
        BushelLedgerEntry varEntry = new BushelLedgerEntry(selectedDate, -amount, bndlPrice, compName);
        stu.farm.addToBshlLedger(varEntry);
        if (!printBalSheet()) {
            balloonTip.setAttachedComponent(amntField);
            balloonTip.setTextContents("You will not have enough available to do this deal on this day.");
            TimingUtils.showTimedBalloon(balloonTip, 2500);

            Consts.DB.getMarketingComp(compName).updateBshls(-amount);
            stu.farm.removeFromBshlLedger(varEntry);
            printBalSheet();
            return;
        }
        amntField.setText("");
        Consts.DB.saveStudent(stu);

        successBalloon.setAttachedComponent(amntField);
        successBalloon.setTextContents("Successful deal.");
        TimingUtils.showTimedBalloon(successBalloon, 2500);

    }

    KeyListener customKeyListner(JFormattedTextField textField) {
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!String.valueOf(e.getKeyChar()).matches("\\d+")) {
                    e.setKeyChar('\0');
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (textField.getText().length() >= 3) {
                    textField.setText(NumberFormat.getNumberInstance().format(
                            Integer.valueOf(textField.getText().replaceAll(",", ""))
                    ));
                }
            }
        };
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
