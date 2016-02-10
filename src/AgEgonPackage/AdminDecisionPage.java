/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEgonPackage;

import com.mongodb.MongoException;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.ceil;

/**
 * Created by Lorenzo on 10/27/2015.
 *
 */
public class AdminDecisionPage extends JFrame implements ActionListener {
    JPanel rootPanel;
    JLabel nameLabel, numOfPlayersLabel, gameYearLabel, startingYearLabel, numOfAdminsLabel;
    JButton nextYearBtn, prevYearBtn, createAdminButton, generateInputSectorButton, generateMarketingSectorButton, logoutButton;
    JTable studentTable;
    JTable adminTbl;

    public AdminDecisionPage(final Admin admin) {
        super("Admin Page");
        setContentPane(rootPanel);
        setResizable(false);
        nameLabel.setText("Welcome " + admin.name + "!");
        //Consts.DB.saveGameFlow();
        numOfPlayersLabel.setText(String.valueOf(Consts.DB.getTotalPlayers(Consts.DB.NNgetGameFlow().getCurrentYear())));
        startingYearLabel.setText(String.valueOf(Consts.DB.NNgetGameFlow().getStartingYear()));
        setLabels();
        setNumOfAdminsLabel();
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        nextYearBtn.addActionListener(this);

        prevYearBtn.addActionListener(this);

        createAdminButton.addActionListener(e -> {
            new CreateAdminPage(admin);
            setVisible(false);
            dispose();
        });

        generateInputSectorButton.addActionListener(e -> {
            Consts.DB.setGenInput();
            int compMax = 0, early = 0, mid = 0, full = 0, rn = 0, var = 0;
            Double maxPrice = 3.50, minPrice = 1.50;
            double ttlSeedsNeeded = Consts.DB.getSeedsNeeded();
            int minAmnt = (int) ceil(ttlSeedsNeeded * .03);
            int maxAmnt = (int) ceil(ttlSeedsNeeded * .06);
            for (char i = 'A'; i <= 'E'; i++) {
                compMax = (int) ceil(ttlSeedsNeeded / 5d);
                early = 0;
                mid = 0;
                full = 0;
                while (compMax > 0) {
                    if (compMax < minAmnt) {
                        rn = minAmnt;
                    } else {
                        rn = minAmnt + new Random().nextInt(maxAmnt - minAmnt);
                    }
                    if (var > 2) {
                        var = 0;
                    }
                    switch (var) {
                        case 0:
                            early += rn;
                            break;
                        case 1:
                            mid += rn;
                            break;
                        case 2:
                            full += rn;
                            break;
                    }
                    compMax -= rn;
                    var++;
                }
                InputSector theInput = new InputSector("Supply Company " + i, early, Consts.round(minPrice +
                        new Random().nextDouble() * (maxPrice - minPrice)), mid, Consts.round(minPrice +
                        new Random().nextDouble() * (maxPrice - minPrice)), full,
                        Consts.round(minPrice + new Random().nextDouble() * (maxPrice - minPrice)));
                try {
                    Consts.DB.saveInput(theInput);
                } catch (MongoException v) {
                   v.printStackTrace();
                }
            }
        });

        generateMarketingSectorButton.addActionListener(e -> {
            Consts.DB.setGenMark();
            Double maxPrice = 12.50, minPrice = 15.25;
            int bshlsNeeded = Consts.DB.getBshlsNeeded();
            int var = 0, compBshls = 0;
            String compDate = "";
            int minAmnt = Consts.S_ACRE * Consts.ACRE_YIELD;

            for (char i = 'A'; i <= 'E'; i++) {
                var = new Random().nextInt(3);
                compBshls = 0;
                switch (var) {
                    case 0:
                        compDate = Consts.getEarlyHarvDt();
                        break;
                    case 1:
                        compDate = Consts.getMidHarvDt();
                        break;
                    case 2:
                        compDate = Consts.getFullHarvDt();
                        break;
                }
                switch (i) {
                    case 'A':
                        compBshls = (int) ceil(bshlsNeeded * .5);
                        break;
                    case 'B':
                        compBshls = (int) ceil(bshlsNeeded * .15);
                        break;
                    case 'C':
                        compBshls = (int) ceil(bshlsNeeded * .13);
                        break;
                    case 'D':
                        compBshls = (int) ceil(bshlsNeeded * .12);
                        break;
                    case 'E':
                        compBshls = (int) ceil(bshlsNeeded * .10);
                }
                if (compBshls < minAmnt) {
                    compBshls = minAmnt;
                }
                genDBMarketComp(new MarketingSector("Marketing Company " + i, compDate, Consts.round(minPrice +
                        new Random().nextDouble() * (maxPrice - minPrice)), compBshls));
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        logoutButton.addActionListener(e -> {
            new WelcomePage();
            setVisible(false);
            dispose();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String buttonText = button.getText();
        if (buttonText.equals(nextYearBtn.getText())) {
            Consts.DB.NNgetGameFlow().nextYear();
            Consts.DB.yearChange(Consts.FORWARD);
            setGameYearLabel();
        } else if (buttonText.equals(prevYearBtn.getText())) {
            if (Consts.DB.NNgetGameFlow().prevYear()) {
                Consts.DB.yearChange(Consts.BACK);
                setGameYearLabel();
            }
        }

    }

    private void setLabels() {
        setGameYearLabel();
    }

    private void setGameYearLabel() {
        gameYearLabel.setText(String.valueOf(Consts.DB.NNgetGameFlow().getCurrentYear()));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        ArrayList<Student> students = Consts.DB.getAllStudents();
        String[] columnNames = {"Name", "Farm Size", "Acres"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        for (Student student : students) {
            Object[] objs = {student.uName, student.getFarm().getSize(), student.getFarm().getAcres()};
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

    void setNumOfAdminsLabel() {
        numOfAdminsLabel.setText(Integer.toString(Consts.DB.getTotalAdmins()));
    }

    void genDBMarketComp(MarketingSector marketComp) {
        try {
            Consts.DB.saveMarketing(marketComp);
        } catch (MongoException v) {
            v.printStackTrace();
        }
    }
}
