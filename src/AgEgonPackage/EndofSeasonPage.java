/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEgonPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Lorenzo on 12/26/2015.
 *
 */
public class EndofSeasonPage extends JFrame {
    private JPanel rootPanel;
    private JLabel incomeStatementLabel;
    private JTable expenseTable;
    private JButton logoutButton;
    private JButton viewSeedOrdersButton;
    private JButton viewMarketingDealsButton;
    private JLabel lbl_year;


    public EndofSeasonPage(Student student) {
        super("End Page");
        setContentPane(rootPanel);

        incomeStatementLabel.setText(incomeStatementLabel.getText() + " for " + student.uName);
        lbl_year.setText("for the year " + Consts.DB.NNgetGameFlow().getCurrentYear());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        viewSeedOrdersButton.addActionListener(e -> new ViewSeedOrdersPage(student));

        viewMarketingDealsButton.addActionListener(e -> new ViewMarketDealsPage(student));

        DefaultTableModel nModel = new DefaultTableModel(new String[]{"Category", ""}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //nModel.addRow(new Object[]{Consts.htmlWrapper("<b>Revenue<b>", 5)});
        final double[] rev = {0};
        student.getFarm().getSaleRecords().forEach(
                entry -> rev[0] += (-entry.getAmount()) * entry.getPpbndl()
        );
        nModel.addRow(new Object[]{Consts.htmlWrapper("<i><b>NET SALES REVENUE</b></i>", 5),
                Consts.htmlWrapper("<i><b>" + NumberFormat.getCurrencyInstance(Locale.US).format(rev[0]) + "</b></i>", 5)});
        student.getFarm().getSaleRecords().forEach(
                entry -> nModel.addRow(new Object[]{entry.getSeller(),
                        NumberFormat.getCurrencyInstance(Locale.US).format(-entry.getAmount() * entry.getPpbndl())})
        );
        nModel.addRow(new Object[]{});

        //nModel.addRow(new Object[]{Consts.htmlWrapper("<b>Fixed Costs</b>", 5)});
        final double[] fixedCost = {0};
        student.getFarm().getStaticCosts().forEach((name, amount) -> fixedCost[0] += -amount);
        final double[] seedCost = {0};
        student.getFarm().getSeedLedger().forEach(entry1 -> seedCost[0] += -entry1.getAmount() * entry1.getPrice());

        double overallTotalCost = seedCost[0] + fixedCost[0];
        nModel.addRow(new Object[]{Consts.htmlWrapper("<i><b>OVERALL TOTAL COST</b></i>", 5),
                Consts.htmlWrapper("<i><b>" + NumberFormat.getCurrencyInstance(Locale.US).format(overallTotalCost) + "</b></i>", 5)});


        nModel.addRow(new Object[]{Consts.htmlWrapper("<b>Total Production Cost</b>", 5),
                Consts.htmlWrapper("<b>" + NumberFormat.getCurrencyInstance(Locale.US).format(fixedCost[0]) + "</b>", 5)});
        student.getFarm().getStaticCosts().forEach((name, amount) -> nModel.addRow(new Object[]{name,
                NumberFormat.getCurrencyInstance(Locale.US).format(-amount)}));

        //nModel.addRow(new Object[]{Consts.htmlWrapper("<b>Seed Costs</b>", 5)});
        nModel.addRow(new Object[]{Consts.htmlWrapper("<b>Total Seed Cost</b>", 5),
                Consts.htmlWrapper("<b>" + NumberFormat.getCurrencyInstance(Locale.US).format(seedCost[0]) + "</b>", 5)});
        student.getFarm().getSeedLedger().forEach(entry1 -> nModel.addRow(new Object[]{entry1.getSeller(),
                NumberFormat.getCurrencyInstance(Locale.US).format(-entry1.getAmount() * entry1.getPrice())}));
        nModel.addRow(new Object[]{});

        double taxableIncome = rev[0] + overallTotalCost;
        double taxes = 0;
        if (taxableIncome > 0) {
            taxes = -taxableIncome * .34;
            nModel.addRow(new Object[]{Consts.htmlWrapper("<b>Taxable Income</b>", 5),
                    Consts.htmlWrapper("<b>" + NumberFormat.getCurrencyInstance(Locale.US).format(taxableIncome) + "</b>", 5)});
            nModel.addRow(new Object[]{Consts.htmlWrapper("<b>Taxes @34%</b>", 5), Consts.htmlWrapper("<b>" +
                    NumberFormat.getCurrencyInstance(Locale.US).format(taxes) + "</b>", 5)});
            nModel.addRow(new Object[]{});
        }

        nModel.addRow(new Object[]{Consts.htmlWrapper("<b><i>NET INCOME</i></b>", 5), Consts.htmlWrapper("<b><i>" +
                NumberFormat.getCurrencyInstance(Locale.US).format(taxableIncome + taxes) + "</i></b>", 5)});

        expenseTable.setFont(new Font("Segoe UI", 0, 18));
        expenseTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        expenseTable.setFillsViewportHeight(true);
        expenseTable.setRowHeight(25);
        expenseTable.setModel(nModel);

        logoutButton.addActionListener(e -> {
            setVisible(false);
            dispose();
            new WelcomePage();
        });

    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */