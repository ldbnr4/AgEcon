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

    BuyingSeedsPage(Student student, InputSector inputSector) {
        super("Company Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        companyNameLabel.setText(inputSector.getName());
        input = inputSector;
        inName = input.getName();
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
            btnHandler(earlyTF, earlyAmntLabel, Consts.Seed_Type.EARLY);
        }
        else if (btn.equals(midBuyBtn)) {
            btnHandler(midTF, midAmntLabel, Consts.Seed_Type.MID);
        }
        else if (btn.equals(fullBuyBtn)) {
            btnHandler(fullTF, fullAmntLabel, Consts.Seed_Type.FULL);
        }
    }

    void btnHandler(JTextField txtField, JLabel amntLbl, Consts.Seed_Type seed_type){
        if (!txtField.getText().matches("\\d+")) {
            balloonTip.setAttachedComponent(txtField);
            balloonTip.setTextContents("Please enter a positive number order.");
            TimingUtils.showTimedBalloon(balloonTip, 2500);
        } else if (amntLbl.getText().equals("SOLD OUT")) {
            balloonTip.setAttachedComponent(txtField);
            balloonTip.setTextContents("The input supplier is sold out of this variety.");
            TimingUtils.showTimedBalloon(balloonTip, 2500);
        } else {
            boolean flag = true;
            int desireAmnt = Integer.valueOf(txtField.getText());
            while (stu == null) {
                stu = Consts.DB.getStudent(stuName);
            }
            while (input == null) {
                input = Consts.DB.getInputSeller(inName);
            }
            double price = input.getFullPrice();
            switch (seed_type){
                case FULL:
                    flag = input.updateFullAmnt(-desireAmnt);
                    if(flag){
                        stu.farm.updateSeedsOwned(0, 0, desireAmnt);
                    }
                    break;
                case MID:
                    flag = input.updateMidAmnt(-desireAmnt);
                    if(flag){
                        stu.farm.updateSeedsOwned(0, desireAmnt, 0);
                    }
                    break;
                case EARLY:
                    flag = input.updateEarlyAmnt(-desireAmnt);
                    if(flag){
                        stu.farm.updateSeedsOwned(desireAmnt, 0, 0);
                    }
                    break;
            }
            if(!flag){
                balloonTip.setAttachedComponent(txtField);
                balloonTip.setTextContents("You cant purchase more than what is available.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
            }
            stu.farm.addToSeedLedger(new SeedLedgerEntry(inName, seed_type, desireAmnt,
                    price));
            Consts.DB.saveStudent(stu);
            Consts.DB.saveInput(input);
        }
    }
}
