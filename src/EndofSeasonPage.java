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


    public EndofSeasonPage(Student student) {
        super("End Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        incomeStatementLabel.setText(student.uName + " " + incomeStatementLabel.getText());

        DefaultTableModel nModel = new DefaultTableModel(new String[]{"Category", "Dollar Amount"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        nModel.addRow(new Object[]{Consts.htmlWrapper("<b>Fixed Costs</b>", 5)});
        final double[] fixedCost = {0};
        student.farm.getStaticCosts().forEach((name, amount) -> {
            nModel.addRow(new Object[]{name, NumberFormat.getCurrencyInstance(Locale.US).format(amount)});
            fixedCost[0] += amount;
        });
        nModel.addRow(new Object[]{Consts.htmlWrapper("<b>Total Fixed Costs</b>", 5),
                Consts.htmlWrapper("<b>" + NumberFormat.getCurrencyInstance(Locale.US).format(fixedCost[0]) + "</b>", 5)});

        nModel.addRow(new Object[]{Consts.htmlWrapper("<b>Seed Costs</b>", 5)});
        final double[] seedCost = {0};
        student.farm.getSeedLedger().forEach(entry1 -> {
            nModel.addRow(new Object[]{entry1.getSeller(),
                    NumberFormat.getCurrencyInstance(Locale.US).format(entry1.getAmount() * entry1.getPrice())});
            seedCost[0] += entry1.getAmount() * entry1.getPrice();
        });
        nModel.addRow(new Object[]{Consts.htmlWrapper("<b>Total Seed Cost</b>", 5),
                Consts.htmlWrapper("<b>" + NumberFormat.getCurrencyInstance(Locale.US).format(seedCost[0]) + "</b>", 5)});
        nModel.addRow(new Object[]{Consts.htmlWrapper("<i><b>OVERALL TOTAL COST</b></i>", 5),
                Consts.htmlWrapper("<i><b>" + NumberFormat.getCurrencyInstance(Locale.US).format(seedCost[0] + fixedCost[0]) + "</b></i>", 5)});

        nModel.addRow(new Object[]{Consts.htmlWrapper("<b>Revenue<b>", 5)});
        final double[] rev = {0};
        student.farm.getBshlLedger().stream().filter(entry -> entry.getSeller() != null).forEach(
                entry -> {
                    nModel.addRow(new Object[]{entry.getSeller(),
                            NumberFormat.getCurrencyInstance(Locale.US).format(-entry.getAmount() * entry.getPpbndl())});
                    rev[0] += (-entry.getAmount()) * entry.getPpbndl();
                }
        );
        nModel.addRow(new Object[]{Consts.htmlWrapper("<i><b>TOTAL SALES REVENUE</b></i>", 5),
                Consts.htmlWrapper("<i><b>" + NumberFormat.getCurrencyInstance(Locale.US).format(rev[0]) + "</b></i>", 5)});

        expenseTable.setFont(new Font("Segoe UI", 0, 18));
        expenseTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        expenseTable.setFillsViewportHeight(true);
        expenseTable.setRowHeight(25);
        expenseTable.setModel(nModel);

        //logoutButton.addActionListener();

    }
}
