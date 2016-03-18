/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Lorenzo on 1/21/2016.
 *
 */
public class ViewMarketDealsPage extends JFrame {
    private JTable marketDeals;
    private JPanel rootPanel;

    public ViewMarketDealsPage(Student student) {
        super("View Marketing Deals Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        DefaultTableModel nModel = new DefaultTableModel(new String[]{
                "Company Name",
                "Date of yield transfer",
                "Amount(cwt)",
                "Price($/cwt)",
                "Sales Revenue"
        }, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        final double[] runningTtl = {0};
        student.getSector().getSaleRecords().forEach(
                entry -> runningTtl[0] += -entry.getAmount() * entry.getPpbndl());

        nModel.addRow(new Object[]{
                Consts.htmlWrapper("<b><i>TOTAL REVENUE</i></b>", 5),
                null,
                null,
                null,
                Consts.htmlWrapper("<b><i>" + NumberFormat.getCurrencyInstance(Locale.US).format(runningTtl[0]) + "</b></i>", 5)
        });

        student.getSector().getSaleRecords().forEach(entry -> nModel.addRow(new Object[]{
                entry.getSeller(),
                entry.getDate(),
                NumberFormat.getNumberInstance(Locale.US).format(-entry.getAmount()),
                NumberFormat.getCurrencyInstance(Locale.US).format(entry.getPpbndl()),
                NumberFormat.getCurrencyInstance(Locale.US).format(entry.getPpbndl() * -entry.getAmount())
        }));

        marketDeals.setFont(new Font("Segoe UI", 0, 18));
        marketDeals.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        marketDeals.setFillsViewportHeight(true);
        marketDeals.setRowHeight(25);
        marketDeals.setModel(nModel);
        marketDeals.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */