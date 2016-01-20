import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Lorenzo on 1/20/2016.
 */
public class ViewSeedOrdersPage extends JFrame {
    private JPanel rootPanel;
    private JTable seedPurchs;

    public ViewSeedOrdersPage(Student student) {
        super("View Seed Orders Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        DefaultTableModel nModel = new DefaultTableModel(new String[]{"Company Name", "Variety", "Amount(lb)",
                "Price($/lb)", "Total Cost"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        double runningTtl = 0;
        for (SeedLedgerEntry entry : student.farm.getSeedLedger()) {
            runningTtl += entry.getAmount() * entry.getPrice();
            nModel.addRow(new Object[]{entry.getSeller(), entry.getSeedType(), NumberFormat.getNumberInstance(Locale.US).format(entry.getAmount()),
                    NumberFormat.getCurrencyInstance(Locale.US).format(entry.getPrice()), NumberFormat.getCurrencyInstance(Locale.US).format(entry.getPrice() * entry.getAmount())});
        }
        nModel.addRow(new Object[]{null, null, null, Consts.htmlWrapper("<b><i>OVERALL TOTAL COST</i></b>", 5),
                Consts.htmlWrapper("<b><i>" + NumberFormat.getCurrencyInstance(Locale.US).format(runningTtl) + "</b></i>", 5)});

        seedPurchs.setFont(new Font("Segoe UI", 0, 18));
        seedPurchs.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        seedPurchs.setFillsViewportHeight(true);
        seedPurchs.setRowHeight(25);
        seedPurchs.setModel(nModel);
        seedPurchs.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
}
