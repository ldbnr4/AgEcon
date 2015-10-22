import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.MinimalBalloonStyle;
import net.java.balloontip.utils.TimingUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Lorenzo on 10/8/2015.
 */
public class InputDecisionPage extends JFrame implements ActionListener {
    private Student student;
    private InputSector studentSector;
    private JPanel rootPanel;
    private JPanel earlyNumsPanel;
    private JPanel midNumsPanel;
    private JPanel fullNumsPanel;
    private JTextField earlyUnitSale;
    private JTextField midUnitSale;
    private JTextField fullUnitSale;
    private JTextField earlyUnitPrice;
    private JTextField midUnitPrice;
    private JTextField fullUnitPrice;
    private JRadioButton earlyButton;
    private JRadioButton midButton;
    private JRadioButton fullButton;
    private JLabel inputSupplyLabel;
    private JButton submitButton;
    private JPanel decisionsPanel;
    private JLabel seedMaturityLabel;
    private BalloonTipStyle modern = new MinimalBalloonStyle(Color.white, 5);
    private BalloonTip balloonTip = new BalloonTip(earlyUnitSale, new JLabel(), modern, BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
    private MongoDBConnection db = MongoDBConnection.getInstance();

    public InputDecisionPage(Student student) {
        super("Input Supply Decisions");

        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        inputSupplyLabel.setText("Hey " + student.uName + "!");

        setUpRadioButtons();
        this.student = student;
        this.studentSector = (InputSector) this.student.sector;

        submitButton.addActionListener(this);
    }

    private void setUpRadioButtons() {
        earlyNumsPanel.setVisible(false);
        midNumsPanel.setVisible(false);
        fullNumsPanel.setVisible(false);

        ClassLoader classLoader = getClass().getClassLoader();
        BufferedImage uncheckedImg = null;
        try {
            uncheckedImg = ImageIO.read(new File(classLoader.getResource("img/unchecked.png").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage checkedImg = null;
        try {
            checkedImg = ImageIO.read(new File(classLoader.getResource("img/checked.png").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        earlyButton.setSelectedIcon(new ImageIcon(checkedImg));
        midButton.setSelectedIcon(new ImageIcon(checkedImg));
        fullButton.setSelectedIcon(new ImageIcon(checkedImg));
        earlyButton.setDisabledSelectedIcon(new ImageIcon(uncheckedImg));
        midButton.setDisabledSelectedIcon(new ImageIcon(uncheckedImg));
        fullButton.setDisabledSelectedIcon(new ImageIcon(uncheckedImg));

        earlyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (earlyButton.isSelected())
                    earlyNumsPanel.setVisible(true);
                else {
                    earlyNumsPanel.setVisible(false);
                }
            }
        });
        midButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (midButton.isSelected())
                    midNumsPanel.setVisible(true);
                else {
                    midNumsPanel.setVisible(false);
                }
            }
        });
        fullButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fullButton.isSelected())
                    fullNumsPanel.setVisible(true);
                else {
                    fullNumsPanel.setVisible(false);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean clean = true;
        if (earlyButton.isSelected()) {
            if (!"\\d+".matches(earlyUnitSale.getText())) {
                clean = false;
                earlyUnitSale.setBackground(Color.RED);
                balloonTip.setAttachedComponent(earlyUnitSale);
                balloonTip.setTextContents("You must enter a valid number for this field.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
            } else {
                earlyUnitSale.setBackground(Color.green);
            }
            if (!"\\d+".matches(earlyUnitPrice.getText())) {
                clean = false;
                earlyUnitPrice.setBackground(Color.RED);
                balloonTip.setAttachedComponent(earlyUnitPrice);
                balloonTip.setTextContents("You must enter a valid number for this field.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
            } else {
                earlyUnitPrice.setBackground(Color.GREEN);
            }
        }
        if (midButton.isSelected()) {
            if (!"\\d+".matches(midUnitSale.getText())) {
                clean = false;
                midUnitSale.setBackground(Color.RED);
                balloonTip.setAttachedComponent(midUnitSale);
                balloonTip.setTextContents("You must enter a valid number for this field.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
            } else {
                midUnitSale.setBackground(Color.green);
            }
            if (!"\\d+".matches(midUnitPrice.getText())) {
                clean = false;
                midUnitPrice.setBackground(Color.RED);
                balloonTip.setAttachedComponent(midUnitPrice);
                balloonTip.setTextContents("You must enter a valid number for this field.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
            } else {
                midUnitPrice.setBackground(Color.GREEN);
            }
        }
        if (fullButton.isSelected()) {
            if (!"\\d+".matches(fullUnitSale.getText())) {
                clean = false;
                fullUnitSale.setBackground(Color.RED);
                balloonTip.setAttachedComponent(fullUnitSale);
                balloonTip.setTextContents("You must enter a valid number for this field.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
            } else {
                fullUnitSale.setBackground(Color.green);
            }
            if (!"\\d+".matches(fullUnitPrice.getText())) {
                clean = false;
                fullUnitPrice.setBackground(Color.RED);
                balloonTip.setAttachedComponent(fullUnitPrice);
                balloonTip.setTextContents("You must enter a valid number for this field.");
                TimingUtils.showTimedBalloon(balloonTip, 2500);
            } else {
                fullUnitPrice.setBackground(Color.GREEN);
            }
        }
        if (clean) {
            int unitSale;
            int unitPrice;
            if (earlyButton.isSelected()) {
                //System.out.println();
                unitSale = Integer.parseInt(earlyUnitSale.getText());
                unitPrice = Integer.parseInt(earlyUnitPrice.getText());
                studentSector.updateEarly(unitSale, unitPrice);
            }
            if (midButton.isSelected()) {
                unitSale = Integer.parseInt(midUnitSale.getText());
                unitPrice = Integer.parseInt(midUnitPrice.getText());
                studentSector.updateMid(unitSale, unitPrice);
            }
            if (fullButton.isSelected()) {
                unitSale = Integer.parseInt(fullUnitSale.getText());
                unitPrice = Integer.parseInt(fullUnitPrice.getText());
                studentSector.updateFull(unitSale, unitPrice);

            }
            db.updateStudent(student);
            new HomePage(student.uName, student.sector);
            setVisible(false);
            dispose();
        }
    }
}
