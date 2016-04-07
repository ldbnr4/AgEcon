/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

import AgEconPackage.farmerPages.FarmerDecisionPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lorenzo on 3/11/2016.
 *
 */
public class SelectSectorPage extends JFrame implements ActionListener {
    private JPanel rootPanel;
    private JButton backBtn;
    private JButton productionButton;
    private JButton marketingButton;
    private JLabel heyPersonLabel;

    private Student student;

    SelectSectorPage(Student student) {
        super("Farm Size Decision");

        setContentPane(rootPanel);
        setResizable(false);
        heyPersonLabel.setText("Hey " + student.uName + "!");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        this.student = student;

        productionButton.addActionListener(this);
        marketingButton.addActionListener(this);
        backBtn.addActionListener(e -> {
            Consts.DB.removeStudent(student);
            new WelcomePage();
            setVisible(false);
            dispose();
        });


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        if (btn.equals(productionButton)){
            new FarmerDecisionPage(student);
        } else if (btn.equals(marketingButton)) {
            System.out.println("Marketing Decisions Page!");
        }

        setVisible(false);
        dispose();
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */
