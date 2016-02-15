/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEgonPackage;

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
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Lorenzo on 12/15/2015.
 *
 */
public class MarketingDealsPage extends JFrame implements ActionListener {
    private JPanel rootPanel;
    private JLabel compABushels, compAPrice, compADate, compBBushels, compBDate, compBPrice, compCDate, compCBushels,
            compCPrice, compDDate, compDBushels, compDPrice, compEDate, compEBushels, compEPrice, welcomeLabel;
    private JButton compABtn, compBBtn, compCBtn, compDBtn, logoutButton, compEBtn;
    private JTextField compAAmount, compBAmount, compCAmount, compDAmount, compEAmount;
    private JTable bshlBalSheet;
    private JDatePickerImpl datePickerA, datePickerB, datePickerC, datePickerD, datePickerE;
    private JButton endSeasonButton;
    private JButton viewSeedOrdersButton;
    private JTable tbl_currentDeals;

    private BalloonTip balloonTip, successBalloon;

    private Student stu;
    private String stuName;

    public MarketingDealsPage(Student student) {
        super("Markets Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        if (!Consts.DB.NNgetGameFlow().getCurrGameMarket()) {
            new WaitPage();
            setVisible(false);
            dispose();
            return;
        }
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.stu = student;
        this.stuName = student.uName;
        welcomeLabel.setText("Hey " + this.stuName + "!");
        logoutButton.addActionListener(e -> {
            new WelcomePage();
            setVisible(false);
            dispose();
        });

        printBalSheet();
        printCurrentDeals();
        initCompThreads();

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
            String msg = Consts.htmlWrapper("Are you sure you are done selling your crop? You will not be able to make any" +
                    " additional deals after continuing.", 4);
            int option = JOptionPane.showConfirmDialog(rootPanel, msg, "Deals confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                stu.getFarm().setStage(Consts.Student_Stage.End_of_Season);
                Consts.DB.saveStudent(stu);
                new EndofSeasonPage(stu);
                setVisible(false);
                dispose();
            }
        });

        viewSeedOrdersButton.addActionListener(e -> new ViewSeedOrdersPage(stu));

        compAAmount.addKeyListener(Consts.customKeyListner(compAAmount));
        compBAmount.addKeyListener(Consts.customKeyListner(compBAmount));
        compCAmount.addKeyListener(Consts.customKeyListner(compCAmount));
        compDAmount.addKeyListener(Consts.customKeyListner(compDAmount));
        compEAmount.addKeyListener(Consts.customKeyListner(compEAmount));

        compAAmount.addFocusListener(Consts.greyFocusListener(compAAmount));
        compBAmount.addFocusListener(Consts.greyFocusListener(compBAmount));
        compCAmount.addFocusListener(Consts.greyFocusListener(compCAmount));
        compDAmount.addFocusListener(Consts.greyFocusListener(compDAmount));
        compEAmount.addFocusListener(Consts.greyFocusListener(compEAmount));

    }

    void initCompThreads() {
        new Thread(new CompanyThread(this, Consts.MARKETING_COMPANY_A_NAME, compABushels, compAPrice, compADate)).start();
        new Thread(new CompanyThread(this, Consts.MARKETING_COMPANY_B_NAME, compBBushels, compBPrice, compBDate)).start();
        new Thread(new CompanyThread(this, Consts.MARKETING_COMPANY_C_NAME, compCBushels, compCPrice, compCDate)).start();
        new Thread(new CompanyThread(this, Consts.MARKETING_COMPANY_D_NAME, compDBushels, compDPrice, compDDate)).start();
        new Thread(new CompanyThread(this, Consts.MARKETING_COMPANY_E_NAME, compEBushels, compEPrice, compEDate)).start();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        UtilDateModel model = new UtilDateModel();
        Date initDate = null;
        try {
            /*String string = "January 2, 2010";
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            Date date = format.parse();*/
            initDate = Consts.sd2.parse(Consts.getEarlyHarvDt());
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

        compAAmount = new Consts.RoundJTextField();
        compAAmount.setBorder(BorderFactory.createCompoundBorder(
                compAAmount.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        compAAmount.setText("Enter amount");
        compAAmount.setForeground(Color.GRAY);

        compBAmount = new Consts.RoundJTextField();
        compBAmount.setBorder(BorderFactory.createCompoundBorder(
                compBAmount.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        compBAmount.setText("Enter amount");
        compBAmount.setForeground(Color.GRAY);

        compCAmount = new Consts.RoundJTextField();
        compCAmount.setBorder(BorderFactory.createCompoundBorder(
                compCAmount.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        compCAmount.setText("Enter amount");
        compCAmount.setForeground(Color.GRAY);

        compDAmount = new Consts.RoundJTextField();
        compDAmount.setBorder(BorderFactory.createCompoundBorder(
                compDAmount.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        compDAmount.setText("Enter amount");
        compDAmount.setForeground(Color.GRAY);

        compEAmount = new Consts.RoundJTextField();
        compEAmount.setBorder(BorderFactory.createCompoundBorder(
                compEAmount.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        compEAmount.setText("Enter amount");
        compEAmount.setForeground(Color.GRAY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (btn.equals(compABtn)) {
            buttonHandler(datePickerA, compADate, compAAmount, Consts.MARKETING_COMPANY_A_NAME,
                    Double.valueOf(compAPrice.getText().replace("$", "")));
        } else if (btn.equals(compBBtn)) {
            buttonHandler(datePickerB, compBDate, compBAmount, Consts.MARKETING_COMPANY_B_NAME,
                    Double.valueOf(compBPrice.getText().replace("$", "")));
        } else if (btn.equals(compCBtn)) {
            buttonHandler(datePickerC, compCDate, compCAmount, Consts.MARKETING_COMPANY_C_NAME,
                    Double.valueOf(compCPrice.getText().replace("$", "")));
        } else if (btn.equals(compDBtn)) {
            buttonHandler(datePickerD, compDDate, compDAmount, Consts.MARKETING_COMPANY_D_NAME,
                    Double.valueOf(compDPrice.getText().replace("$", "")));
        } else if (btn.equals(compEBtn)) {
            buttonHandler(datePickerE, compEDate, compEAmount, Consts.MARKETING_COMPANY_E_NAME,
                    Double.valueOf(compEPrice.getText().replace("$", "")));
        }
    }

    void printBalSheet() {
        DefaultTableModel nModel = new DefaultTableModel(new String[]{"Date", "Amount(cwt)"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        int runTtl = 0;
        //int row = 0;
        for (HarvestEntry ledger : stu.getFarm().getYieldRecords()) {
            runTtl += ledger.getAmount();
/*            if (row > 0) {
                if (ledger.getDate().equals(nModel.getValueAt(row - 1, 0))) {
                    nModel.setValueAt(NumberFormat.getNumberInstance(Locale.US).format(runTtl), row - 1, 1);
                    continue;
                }
            }*/
            try {
                nModel.addRow(new Object[]{Consts.sd2.format(Consts.sd2.parse(ledger.getDate())),
                        NumberFormat.getNumberInstance(Locale.US).format(runTtl)});
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //row++;
        }
        bshlBalSheet.setFont(new Font("Segoe UI", 0, 18));
        bshlBalSheet.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        bshlBalSheet.setFillsViewportHeight(true);
        bshlBalSheet.setRowHeight(25);
        bshlBalSheet.setModel(nModel);
    }

    void printCurrentDeals() {
        DefaultTableModel nModel = new DefaultTableModel(new String[]{
                "Company Name",
                "Date of yield transfer",
                "Amount(cwt)"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        stu.getFarm().getSaleRecords().forEach(entry -> nModel.addRow(new Object[]{
                entry.getSeller(),
                entry.getDate(),
                NumberFormat.getNumberInstance(Locale.US).format(-entry.getAmount()),
        }));

        tbl_currentDeals.setFont(new Font("Segoe UI", 0, 18));
        tbl_currentDeals.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        tbl_currentDeals.setFillsViewportHeight(true);
        tbl_currentDeals.setRowHeight(25);
        tbl_currentDeals.setModel(nModel);
    }

    boolean attemptTrans(BushelLedgerEntry sell) {
        ArrayList<HarvestEntry> usableHarvs = new ArrayList<>();
        stu.getFarm().getYieldRecords().stream().filter(harvestEntry -> {
            try {
                return (Consts.sd2.parse(harvestEntry.getDate()).before(Consts.sd2.parse(sell.getDate())) ||
                        sell.getDate().equals(harvestEntry.getDate())
                );
            } catch (ParseException e) {
                e.printStackTrace();
                return true;
            }
        }).forEach(usableHarvs::add);

        if (usableHarvs.size() == 0) return false;

        ArrayList<HarvestEntry> tmpEntries = new ArrayList<>();
        int sellAmnt = sell.getAmount();
        for (HarvestEntry usableEntry : usableHarvs) {
            if (sellAmnt > usableEntry.getAmount()) {
                sellAmnt -= usableEntry.getAmount();
                tmpEntries.add(new HarvestEntry(usableEntry.getDate(), -usableEntry.getAmount()));
            } else {
                tmpEntries.add(new BushelLedgerEntry(usableEntry.getDate(), -sellAmnt, 0, ""));
                stu.getFarm().getYieldRecords().addAll(tmpEntries);
                condenseYieldRecords();
                sell.setAmount(-sell.getAmount());
                stu.getFarm().addToSaleRecords(sell);
                return true;
            }
        }
        return false;
    }

    void buttonHandler(JDatePickerImpl jDatePicker, JLabel dateLabel, JTextField amntField, String compName,
                       double bndlPrice) {
        if (amntField.getText().isEmpty() || amntField.getForeground().equals(Color.GRAY)) {
            balloonTip.setAttachedComponent(amntField);
            balloonTip.setTextContents("Please enter a number to sell.");
            TimingUtils.showTimedBalloon(balloonTip, 3500);
            return;
        }
        if (dateLabel.getText().equals("SOLD OUT")) {
            balloonTip.setAttachedComponent(amntField);
            balloonTip.setTextContents("This marketing seller has no more to sell.");
            TimingUtils.showTimedBalloon(balloonTip, 3500);
            return;
        }
        String selectedDate = Consts.sd2.format(jDatePicker.getModel().getValue());
        try {
            if (Consts.sd2.parse(selectedDate).after(
                    Consts.sd2.parse(dateLabel.getText()))) {
                balloonTip.setAttachedComponent(jDatePicker);
                balloonTip.setTextContents("Please select a date before or on the sellers needed by date.");
                TimingUtils.showTimedBalloon(balloonTip, 4500);
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
        try {
            if (Consts.sd2.parse(selectedDate).before(
                    Consts.sd2.parse(Consts.getEarlyHarvDt()))) {
                balloonTip.setAttachedComponent(amntField);
                balloonTip.setTextContents("Please select a date after the first harvest date.");
                TimingUtils.showTimedBalloon(balloonTip, 4500);
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (!Consts.DB.getMarketingComp(compName).subtractBshls(amount)) {
            balloonTip.setAttachedComponent(amntField);
            balloonTip.setTextContents("This seller does not need this much.");
            TimingUtils.showTimedBalloon(balloonTip, 3500);
            return;
        }
        if (!attemptTrans(new BushelLedgerEntry(selectedDate, amount, bndlPrice, compName))) {
            balloonTip.setAttachedComponent(amntField);
            balloonTip.setTextContents("You will not have enough available to do this deal.");
            TimingUtils.showTimedBalloon(balloonTip, 4500);

            Consts.DB.getMarketingComp(compName).subtractBshls(-amount);
            //Consts.DB.getMarketingComp(compName).subtractBshls(-amount);
            return;
        }
        printBalSheet();
        printCurrentDeals();
        amntField.setText("");
        Consts.DB.saveStudent(stu);

        successBalloon.setAttachedComponent(amntField);
        successBalloon.setTextContents("Successful deal.");
        TimingUtils.showTimedBalloon(successBalloon, 3500);

    }

    private void condenseYieldRecords() {
        ArrayList<HarvestEntry> harvList = new ArrayList<>();
        int first = 0, second = 0, third = 0;

        for (HarvestEntry ledger : stu.getFarm().getYieldRecords()) {
            if (ledger.getDate().equals(Consts.getEarlyHarvDt())) {
                first += ledger.getAmount();
            } else if (ledger.getDate().equals(Consts.getMidHarvDt())) {
                second += ledger.getAmount();
            } else {
                third += ledger.getAmount();
            }
        }

        harvList.add(new HarvestEntry(Consts.getEarlyHarvDt(), first));
        harvList.add(new HarvestEntry(Consts.getMidHarvDt(), second));
        harvList.add(new HarvestEntry(Consts.getFullHarvDt(), third));

        stu.getFarm().setYieldRecords(harvList);
    }

    class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private String datePattern = "MMMM dd, yyyy";
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

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */