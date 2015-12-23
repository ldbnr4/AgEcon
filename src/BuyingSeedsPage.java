import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.MinimalBalloonStyle;
import net.java.balloontip.utils.TimingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lorenzo on 12/3/2015.
 *
 */
public class BuyingSeedsPage extends JFrame implements ActionListener {
    private final MinimalBalloonStyle modern;
    private final BalloonTip balloonTip;
    JPanel rootPanel;
    JLabel earlyAmntLabel, midAmntLabel, fullAmntLabel, earlyPriceLabel, midPriceLabel, fullPriceLabel, companyNameLabel;
    JButton earlyBuyBtn, midBuyBtn, fullBuyBtn;
    JTextField earlyTF, midTF, fullTF;
    JButton doneButton;

    InputSector input;
    String inName;
    Student stu;
    String stuName;

    public BuyingSeedsPage(Student student, InputSector inputSector) {
        super("Company Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        companyNameLabel.setText(inputSector.getName());
        input = inputSector;
        inName = input.name;
        stu = student;
        stuName = student.uName;
        modern = new MinimalBalloonStyle(Color.yellow, 5);
        balloonTip = new BalloonTip(earlyTF, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE,
                BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.balloonTip.setVisible(false);

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
    }

    private void updateLabels() {
        input = Consts.DB.getInputSeller(inName);
        while (input == null) {
            input = Consts.DB.getInputSeller(inName);
        }
        Consts.checkSetSoldOut(earlyAmntLabel, earlyPriceLabel, input.getEarlyAmnt(), input.getEarlyPrice());
        Consts.checkSetSoldOut(midAmntLabel, midPriceLabel, input.getMidAmnt(), input.getMidPrice());
        Consts.checkSetSoldOut(fullAmntLabel, fullPriceLabel, input.getFullAmnt(), input.getFullPrice());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (btn.equals(doneButton)) {
            setVisible(false);
            dispose();
        } else if (btn.equals(earlyBuyBtn)) {
            if (!earlyTF.getText().matches("\\d+")) {
                balloonTip.setAttachedComponent(earlyTF);
                balloonTip.setTextContents("Please enter a positive number order.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                earlyTF.setBackground(Color.RED);
            } else if (earlyAmntLabel.getText().equals("SOLD OUT")) {
                balloonTip.setAttachedComponent(earlyTF);
                balloonTip.setTextContents("The input supplier is sold out of this variety.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                earlyTF.setBackground(Color.RED);
            } else if (Integer.valueOf(earlyTF.getText()) > input.getEarlyAmnt()) {
                balloonTip.setAttachedComponent(earlyTF);
                balloonTip.setTextContents("You cant purchase more than what is available.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                earlyTF.setBackground(Color.RED);
            } else {
                earlyTF.setBackground(Color.GREEN);
                balloonTip.setVisible(false);
                input.setEarlyAmnt(input.getEarlyAmnt() - Integer.valueOf(earlyTF.getText()));
                while (stu == null) {
                    stu = Consts.DB.getStudent(stuName);
                }
                stu.farm.updateSeedsOwned(Integer.valueOf(earlyTF.getText()), 0, 0);
                while (input == null) {
                    input = Consts.DB.getInputSeller(inName);
                }
                stu.farm.addSeedCost(inName, Consts.Seed_Name.EARLY, Integer.valueOf(earlyTF.getText()), input.getEarlyPrice());
                Consts.DB.saveStudent(stu);
            }
        } else if (btn.equals(midBuyBtn)) {
            if (!midTF.getText().matches("\\d+")) {
                balloonTip.setAttachedComponent(midTF);
                balloonTip.setTextContents("Please enter a positive number order.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                midTF.setBackground(Color.RED);
            } else if (midAmntLabel.getText().equals("SOLD OUT")) {
                balloonTip.setAttachedComponent(midTF);
                balloonTip.setTextContents("The input supplier is sold out of this variety.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                midTF.setBackground(Color.RED);
            } else if (Integer.valueOf(midTF.getText()) > input.getMidAmnt()) {
                balloonTip.setAttachedComponent(midTF);
                balloonTip.setTextContents("You cant purchase more than what is available.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                midTF.setBackground(Color.RED);
            } else {
                midTF.setBackground(Color.GREEN);
                balloonTip.setVisible(false);
                input.setMidAmnt(input.getMidAmnt() - Integer.valueOf(midTF.getText()));
                while (stu == null) {
                    stu = Consts.DB.getStudent(stuName);
                }
                stu.farm.updateSeedsOwned(0, Integer.valueOf(midTF.getText()), 0);
                while (input == null) {
                    input = Consts.DB.getInputSeller(inName);
                }
                stu.farm.addSeedCost(inName, Consts.Seed_Name.MID, Integer.valueOf(midTF.getText()), input.getMidPrice());
                Consts.DB.saveStudent(stu);
            }
        } else if (btn.equals(fullBuyBtn)) {
            if (!fullTF.getText().matches("\\d+")) {
                balloonTip.setAttachedComponent(fullTF);
                balloonTip.setTextContents("Please enter a positive number order.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                fullTF.setBackground(Color.RED);
            } else if (fullAmntLabel.getText().equals("SOLD OUT")) {
                balloonTip.setAttachedComponent(fullTF);
                balloonTip.setTextContents("The input supplier is sold out of this variety.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                fullTF.setBackground(Color.RED);
            } else if (Integer.valueOf(fullTF.getText()) > input.getFullAmnt()) {
                balloonTip.setAttachedComponent(fullTF);
                balloonTip.setTextContents("You cant purchase more than what is available.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
                fullTF.setBackground(Color.RED);
            } else {
                fullTF.setBackground(Color.GREEN);
                balloonTip.setVisible(false);
                input.setFullAmnt(input.getFullAmnt() - Integer.valueOf(fullTF.getText()));
                while (stu == null) {
                    stu = Consts.DB.getStudent(stuName);
                }
                stu.farm.updateSeedsOwned(0, 0, Integer.valueOf(fullTF.getText()));
                while (input == null) {
                    input = Consts.DB.getInputSeller(inName);
                }
                stu.farm.addSeedCost(inName, Consts.Seed_Name.FULL, Integer.valueOf(fullTF.getText()), input.getFullPrice());
                Consts.DB.saveStudent(stu);
            }
        }
    }
}
