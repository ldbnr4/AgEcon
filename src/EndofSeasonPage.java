import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by Lorenzo on 12/26/2015.
 *
 */
public class EndofSeasonPage extends JFrame {
    private JPanel rootPanel;
    private JLabel incomeStatementLabel;
    private JTable expenseTable;


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

        nModel.addRow(new Object[]{"Fixed Costs"});
        final double[] fixedCost = {0};
        student.farm.getStaticCosts().forEach((name, amount) -> {
            nModel.addRow(new Object[]{name, amount});
            fixedCost[0] += amount;
        });
        nModel.addRow(new Object[]{"Total Fixed Costs", fixedCost[0]});

        nModel.addRow(new Object[]{"Seed Costs"});
        final double[] seedCost = {0};
        student.farm.getSeedLedger().forEach(entry1 -> {
            nModel.addRow(new Object[]{entry1.getSeller(), entry1.getAmount() * entry1.getPrice()});
            seedCost[0] += entry1.getAmount() * entry1.getPrice();
        });
        nModel.addRow(new Object[]{"Total Seed Costs", seedCost[0]});
        nModel.addRow(new Object[]{"TOTAL OVERALL COSTS", seedCost[0] + fixedCost[0]});

        nModel.addRow(new Object[]{"Revenue"});
        final double[] rev = {0};
        student.farm.getBshlLedger().stream().filter(entry -> entry.getSeller() != null).forEach(
                entry -> {
                    nModel.addRow(new Object[]{entry.getSeller(), -entry.getAmount() * entry.getPpbndl()});
                    rev[0] += (-entry.getAmount()) * entry.getPpbndl();
                }
        );
        nModel.addRow(new Object[]{"Total Sales Revenue", rev[0]});

        expenseTable.setModel(nModel);

    }
}
