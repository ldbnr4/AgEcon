/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.MinimalBalloonStyle;
import net.java.balloontip.utils.TimingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static AgEconPackage.Consts.Seed_Type.*;

/**
 * Created by Lorenzo on 12/3/2015.
 *
 */
public class BuyingSeedsPage extends JFrame implements ActionListener {
    private final BalloonTip balloonTip;
    private final BalloonTip greenBalloonTip;
    JPanel rootPanel;
    JLabel earlyAmntLabel, midAmntLabel, fullAmntLabel, earlyPriceLabel, midPriceLabel, fullPriceLabel, companyNameLabel;
    JButton earlyBuyBtn, midBuyBtn, fullBuyBtn;
    JTextField earlyTF, midTF, fullTF;
    JButton doneButton;

    private String inName;
    private Student stu;
    private String stuName;

    BuyingSeedsPage(Student student, InputSector inputSector) {
        super("Company Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        companyNameLabel.setText(inputSector.getName());
        inName = inputSector.getName();
        stu = student;
        stuName = student.uName;
        MinimalBalloonStyle modern = new MinimalBalloonStyle(Color.yellow, 5);
        MinimalBalloonStyle greenModern = new MinimalBalloonStyle(Color.green, 5);
        balloonTip = new BalloonTip(earlyTF, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE,
                BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        greenBalloonTip = new BalloonTip(earlyTF, new JLabel(), greenModern, BalloonTip.Orientation.RIGHT_ABOVE,
                BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        greenBalloonTip.setTextContents("Successful order");

        this.balloonTip.setVisible(false);
        greenBalloonTip.setVisible(false);

        Runnable r = () -> {
            while (isVisible()) {
                updateLabels();
            }
        };
        new Thread(r).start();

        doneButton.addActionListener(this);
        earlyBuyBtn.addActionListener(this);
        midBuyBtn.addActionListener(this);
        fullBuyBtn.addActionListener(this);

        earlyTF.addKeyListener(Consts.customKeyListner(earlyTF));
        midTF.addKeyListener(Consts.customKeyListner(midTF));
        fullTF.addKeyListener(Consts.customKeyListner(fullTF));

        earlyTF.addFocusListener(Consts.greyFocusListener(earlyTF));
        midTF.addFocusListener(Consts.greyFocusListener(midTF));
        fullTF.addFocusListener(Consts.greyFocusListener(fullTF));
    }

    private void updateLabels() {
        InputSector input = Consts.DB.getInputSeller(inName);
        Consts.checkSetSoldOut(earlyAmntLabel, earlyPriceLabel, input.getAmnt(EARLY), input.getPrice(EARLY));
        Consts.checkSetSoldOut(midAmntLabel, midPriceLabel, input.getAmnt(MID), input.getPrice(MID));
        Consts.checkSetSoldOut(fullAmntLabel, fullPriceLabel, input.getAmnt(FULL), input.getPrice(FULL));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (btn.equals(doneButton)) {
            setVisible(false);
            dispose();
        } else if (btn.equals(earlyBuyBtn)) {
            btnHandler(earlyTF, earlyAmntLabel, EARLY);
        }
        else if (btn.equals(midBuyBtn)) {
            btnHandler(midTF, midAmntLabel, MID);
        }
        else if (btn.equals(fullBuyBtn)) {
            btnHandler(fullTF, fullAmntLabel, FULL);
        }
    }

    private void btnHandler(JTextField txtField, JLabel amntLbl, Consts.Seed_Type seed_type){
        if (amntLbl.getText().equals("SOLD OUT")) {
            balloonTip.setAttachedComponent(txtField);
            balloonTip.setTextContents("The input supplier is sold out of this variety.");
            TimingUtils.showTimedBalloon(balloonTip, 2500);
        } else {
            boolean flag = true;
            int desireAmnt = Integer.valueOf(txtField.getText().replaceAll(",", ""));

            while (stu == null) {
                stu = Consts.DB.getStudent(stuName);
            }


            flag = Consts.DB.getInputSeller(inName).updateAmnt(seed_type, -desireAmnt);
            if(!flag){
                balloonTip.setAttachedComponent(txtField);
                balloonTip.setTextContents("You cant purchase more than what is available.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
            } else {
                stu.getFarm().updateSeedsOwned(seed_type, desireAmnt);
                stu.getFarm().addToSeedLedger(new SeedLedgerEntry(inName, seed_type, desireAmnt,
                        Consts.DB.getInputSeller(inName).getPrice(seed_type)));

                Consts.DB.saveStudent(stu);
                greenBalloonTip.setAttachedComponent(txtField);
                TimingUtils.showTimedBalloon(greenBalloonTip, 2500);
                txtField.setText("");
            }
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        earlyTF = new Consts.RoundJTextField();
        earlyTF.setBorder(BorderFactory.createCompoundBorder(
                earlyTF.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        earlyTF.setText("Enter amount to order");
        earlyTF.setForeground(Color.GRAY);

        midTF = new Consts.RoundJTextField();
        midTF.setBorder(BorderFactory.createCompoundBorder(
                midTF.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        midTF.setText("Enter amount to order");
        midTF.setForeground(Color.GRAY);

        fullTF = new Consts.RoundJTextField();
        fullTF.setBorder(BorderFactory.createCompoundBorder(
                fullTF.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        fullTF.setText("Enter amount to order");
        fullTF.setForeground(Color.GRAY);
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */