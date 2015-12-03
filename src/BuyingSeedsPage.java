import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.MinimalBalloonStyle;
import net.java.balloontip.utils.TimingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lorenzo on 12/3/2015.
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
                } else if (Integer.valueOf(earlyTF.getText()) > Integer.valueOf(earlyAmntLabel.getText())) {
                    balloonTip.setAttachedComponent(earlyTF);
                    balloonTip.setTextContents("You can not order more than what the input supplier has available.");
                    TimingUtils.showTimedBalloon(balloonTip, 2500);
                    earlyTF.setBackground(Color.RED);
                } else {
                    earlyTF.setBackground(Color.GREEN);
                    balloonTip.setVisible(false);
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
                } else if (Integer.valueOf(midTF.getText()) > Integer.valueOf(midAmntLabel.getText())) {
                    balloonTip.setAttachedComponent(midTF);
                    balloonTip.setTextContents("You can not order more than what the input supplier has available.");
                    TimingUtils.showTimedBalloon(balloonTip, 2500);
                    midTF.setBackground(Color.RED);
                } else {
                    midTF.setBackground(Color.GREEN);
                    balloonTip.setVisible(false);
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
                } else if (Integer.valueOf(fullTF.getText()) > Integer.valueOf(fullAmntLabel.getText())) {
                    balloonTip.setAttachedComponent(fullTF);
                    balloonTip.setTextContents("You can not order more than what the input supplier has available.");
                    TimingUtils.showTimedBalloon(balloonTip, 2500);
                    fullTF.setBackground(Color.RED);
                } else {
                    fullTF.setBackground(Color.GREEN);
                    balloonTip.setVisible(false);
                }
            }
        });
    }

    private void updateLabels() {
        input = Consts.DB.getInputSeller(input.getName(), Consts.GAME_FLOW.currentYear);
        earlyAmntLabel.setText(String.valueOf(input.getEarlyAmnt()));
        earlyPriceLabel.setText(String.valueOf(input.getEarlyPrice()));
        midAmntLabel.setText(String.valueOf(input.getMidAmnt()));
        midPriceLabel.setText(String.valueOf(input.getMidPrice()));
        fullAmntLabel.setText(String.valueOf(input.getFullAmnt()));
        fullPriceLabel.setText(String.valueOf(input.getFullPrice()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }
}
