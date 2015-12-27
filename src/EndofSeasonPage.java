import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by Lorenzo on 12/26/2015.
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

        final int[] rev = {0};
        student.farm.getBshlLedger().stream().filter(entry -> entry.getSeller() != null).forEach(
                entry -> rev[0] += (-entry.getAmount()) * entry.getPpbndl()
        );

        nModel.addRow(new Object[]{"Gross Sales Revenue", rev[0]});
        nModel.addRow(new Object[]{"Static Cost", student.farm});

        expenseTable.setModel(nModel);

    }
}
