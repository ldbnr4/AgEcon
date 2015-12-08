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
    JLabel earlyAmntLabel;
    JLabel midAmntLabel;
    JLabel fullAmntLabel;
    JLabel earlyPriceLabel;
    JLabel midPriceLabel;
    JLabel fullPriceLabel;
    JButton earlyBuyBtn;
    JButton midBuyBtn;
    JButton fullBuyBtn;
    JTextField earlyTF;
    JLabel companyNameLabel;
    JTextField midTF;
    JTextField fullTF;
    JButton doneButton;

    InputSector input;
    String inName;
    Student stu;

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
        modern = new MinimalBalloonStyle(Color.yellow, 5);
        balloonTip = new BalloonTip(earlyTF, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE,
                BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.balloonTip.setVisible(false);

        Runnable r = new Runnable() {
            public void run() {
                while (isVisible()) {
                    updateLabels();
                }
            }
        };
        new Thread(r).start();

        doneButton.addActionListener(this);

        earlyBuyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                } else {
                    earlyTF.setBackground(Color.GREEN);
                    balloonTip.setVisible(false);
                    input.setEarlyAmnt(input.getEarlyAmnt() - Integer.valueOf(earlyTF.getText()));
                    stu.farm.updateSeedsOwned(Integer.valueOf(earlyTF.getText()), 0, 0);
                    Consts.DB.saveStudent(stu);
                }
            }
        });

        midBuyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                } else {
                    midTF.setBackground(Color.GREEN);
                    balloonTip.setVisible(false);
                    input.setMidAmnt(input.getMidAmnt() - Integer.valueOf(midTF.getText()));
                    stu.farm.updateSeedsOwned(0, Integer.valueOf(midTF.getText()), 0);
                    Consts.DB.saveStudent(stu);
                }
            }
        });

        fullBuyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!fullTF.getText().matches("\\d+")) {
                    balloonTip.setAttachedComponent(fullTF);
                    balloonTip.setTextContents("Please enter a positive number to order.");
                    TimingUtils.showTimedBalloon(balloonTip, 2500);
                    fullTF.setBackground(Color.RED);
                } else if (fullAmntLabel.getText().equals("SOLD OUT")) {
                    balloonTip.setAttachedComponent(fullTF);
                    balloonTip.setTextContents("The input supplier is sold out of this variety.");
                    TimingUtils.showTimedBalloon(balloonTip, 2500);
                    fullTF.setBackground(Color.RED);
                } else {
                    fullTF.setBackground(Color.GREEN);
                    balloonTip.setVisible(false);
                    input.setFullAmnt(input.getFullAmnt() - Integer.valueOf(fullTF.getText()));
                    stu.farm.updateSeedsOwned(0, 0, Integer.valueOf(fullTF.getText()));
                    Consts.DB.saveStudent(stu);
                }
            }
        });
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
        setVisible(false);
        dispose();
    }
}
