import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Lorenzo on 10/27/2015.
 *
 */
public class AdminDecisionPage extends JFrame implements ActionListener {
    JLabel nameLabel;
    JLabel numOfPlayersLabel;
    JPanel rootPanel;
    JLabel gameYearLabel;
    JLabel startingYearLabel;
    JButton nextYearBtn;
    JButton prevYearBtn;
    JButton createAdminButton;
    JTable studentTable;
    JTable adminTbl;
    JLabel numOfAdminsLabel;
    private JButton generateInputSectorButton;
    private JButton generateMarketingSectorButton;

    public AdminDecisionPage(final Admin admin) {
        super("Admin Page");
        setContentPane(rootPanel);
        setResizable(false);
        nameLabel.setText("Welcome " + admin.name + "!");
        //Consts.DB.saveGameFlow();
        numOfPlayersLabel.setText(String.valueOf(Consts.GAME_FLOW.totalPlayers));
        startingYearLabel.setText(String.valueOf(Consts.GAME_FLOW.startingYear));
        setLabels();
        setNumOfAdminsLabel();
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        nextYearBtn.addActionListener(this);

        prevYearBtn.addActionListener(this);

        createAdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateAdminPage(admin);
                setVisible(false);
                dispose();
            }
        });

        //DefaultListModel<Object> listModel = new DefaultListModel<>();
        // studentList.setCellRenderer((ListCellRenderer<? super Object>) Consts.getRenderer());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String buttonText = button.getText();
        if (buttonText.equals(nextYearBtn.getText())) {
            Consts.GAME_FLOW.nextYear();
            Consts.DB.yearChange(Consts.FORWARD);
            setGameYearLabel();
        } else if (buttonText.equals(prevYearBtn.getText())) {
            Consts.GAME_FLOW.prevYear();
            Consts.DB.yearChange(Consts.BACK);
            setGameYearLabel();
        }

    }

    private void setLabels() {
        setGameYearLabel();
    }

    private void setGameYearLabel() {
        gameYearLabel.setText(String.valueOf(Consts.GAME_FLOW.currentYear));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        ArrayList<Student> students = Consts.DB.getAllStudents(Consts.GAME_FLOW.currentYear);
        String[] columnNames = {"Name", "Farm Size", "Acres"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        FarmSector sector;
        for (Student student : students) {
            sector = (FarmSector) student.sector;
            Object[] objs = {student.uName, sector.getFarmSize(), sector.getFarmAcres()};
            tableModel.addRow(objs);
        }
        studentTable.setPreferredScrollableViewportSize(new Dimension(300, 200));

        ArrayList<Admin> admins = Consts.DB.getAllAdmins();
        String[] columnNames2 = {"Name"};
        DefaultTableModel tableModel2 = new DefaultTableModel(columnNames2, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        adminTbl = new JTable(tableModel2);
        adminTbl.setDefaultRenderer(String.class, centerRenderer);
        for (Admin admin : admins) {
            Object[] objs = {admin.name};
            tableModel2.addRow(objs);
        }
        adminTbl.setPreferredScrollableViewportSize(new Dimension(100, 100));
    }

    public void setNumOfAdminsLabel() {
        numOfAdminsLabel.setText(Integer.toString(Consts.DB.getTotalAdmins()));
    }
}
