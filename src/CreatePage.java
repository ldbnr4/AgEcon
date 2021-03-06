import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.MinimalBalloonStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

/**
 * Created by Lorenzo on 9/18/2015.
 */
public class CreatePage extends JFrame implements ActionListener/*, Runnable */ {
    private JPanel rootPanel;
    private JButton foodMarketingButton;
    private JButton inputSupplyButton;
    private JButton farmProductionButton;
    private JTextField usernameTextField;
    private JPasswordField passwordPasswordField;
    private JPasswordField confirmPasswordPasswordField;
    private JButton loginButton;
    private JLabel inputSupplyNumber;
    private JLabel farmProductionNumber;
    private JLabel foodMarketingNumber;
    private JButton refreshButton;
    private JPanel loginPanel;
    private JLabel loginLabel;
    private JLabel selectSectLabel;
    private JPanel createAcntPanel;
    private JPanel SelectSectPanel;
    private JPanel buttonsPanel;
    private BalloonTipStyle modern;
    private BalloonTip uNameBalloonTip;
    private BalloonTip passBalloonTip;
    private BalloonTip confPassBalloonTip;
    private UNameVerifier usernameVerifier;
    private PassVerifier passwordVerifier;
    private PassVerifier confPassVerifier;
    private HashMap<String, Integer> sectorAmounts;
    private volatile boolean isRunning = true;

    public CreatePage() {
        super("Create Account");
        setContentPane(rootPanel);
        updateBtns();

        modern = new MinimalBalloonStyle(Color.white, 5);
        uNameBalloonTip = new BalloonTip(usernameTextField, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.uNameBalloonTip.setVisible(false);
        passBalloonTip = new BalloonTip(passwordPasswordField, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.passBalloonTip.setVisible(false);
        confPassBalloonTip = new BalloonTip(confirmPasswordPasswordField, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.confPassBalloonTip.setVisible(false);

        inputSupplyButton.addActionListener(this);
        farmProductionButton.addActionListener(this);
        foodMarketingButton.addActionListener(this);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage();
                setVisible(false);
                dispose();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBtns();
            }
        });

        usernameVerifier = new UNameVerifier(uNameBalloonTip);
        usernameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                usernameVerifier.verify(usernameTextField);
            }
        });
        passwordVerifier = new PassVerifier(passBalloonTip);
        passwordPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameTextField.getBackground() == Color.green) {
                    passwordVerifier.verifyLive(passwordPasswordField);
                }
            }
        });
        confPassVerifier = new PassVerifier(confPassBalloonTip);
        confirmPasswordPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameTextField.getBackground() == Color.green && passwordPasswordField.getBackground() == Color.green) {
                    if (!confPassVerifier.verifyLive(confirmPasswordPasswordField)) {
                    } else confPassVerifier.verifyMatch(passwordPasswordField, confirmPasswordPasswordField);
                }

            }
        });

        inputSupplyButton.setText(Consts.INPUT_SECTOR_NAME);
        farmProductionButton.setText(Consts.FARM_SECTOR_NAME);
        foodMarketingButton.setText(Consts.FOOD_SECTOR_NAME);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public CreatePage(String username) {
        super("Create Account");
        setContentPane(rootPanel);
        updateBtns();

        modern = new MinimalBalloonStyle(Color.white, 5);
        uNameBalloonTip = new BalloonTip(usernameTextField, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.uNameBalloonTip.setVisible(false);
        passBalloonTip = new BalloonTip(passwordPasswordField, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.passBalloonTip.setVisible(false);
        confPassBalloonTip = new BalloonTip(confirmPasswordPasswordField, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
        this.confPassBalloonTip.setVisible(false);

        inputSupplyButton.addActionListener(this);
        farmProductionButton.addActionListener(this);
        foodMarketingButton.addActionListener(this);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage();
                setVisible(false);
                dispose();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBtns();
            }
        });

        usernameVerifier = new UNameVerifier(uNameBalloonTip);
        usernameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                usernameVerifier.verify(usernameTextField);
            }
        });
        passwordVerifier = new PassVerifier(passBalloonTip);
        passwordPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameTextField.getBackground() == Color.green) {
                    passwordVerifier.verifyLive(passwordPasswordField);
                }
            }
        });
        confPassVerifier = new PassVerifier(confPassBalloonTip);
        confirmPasswordPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameTextField.getBackground() == Color.green && passwordPasswordField.getBackground() == Color.green) {
                    if (!confPassVerifier.verifyLive(confirmPasswordPasswordField)) {
                    } else confPassVerifier.verifyMatch(passwordPasswordField, confirmPasswordPasswordField);
                }

            }
        });

        inputSupplyButton.setText(Consts.INPUT_SECTOR_NAME);
        farmProductionButton.setText(Consts.FARM_SECTOR_NAME);
        foodMarketingButton.setText(Consts.FOOD_SECTOR_NAME);

        usernameTextField.setText(username);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String selectedSect = button.getText();
        if (usernameVerifier.verify(usernameTextField) && passwordVerifier.verifyLive(passwordPasswordField) &&
                passwordVerifier.verifyLive(confirmPasswordPasswordField) &&
                passwordVerifier.verifyMatch(passwordPasswordField, confirmPasswordPasswordField)) {
            if (!sectCheck(button.getText())) {
                button.setEnabled(false);
            } else {
                usernameTextField.setBackground(Color.green);
                confirmPasswordPasswordField.setBackground(Color.GREEN);
                passwordPasswordField.setBackground(Color.GREEN);
                button.setEnabled(true);
                Student student = null;

                switch (selectedSect) {
                    case Consts.INPUT_SECTOR_NAME:
                        student = new Student(usernameTextField.getText(), String.valueOf(passwordPasswordField.getPassword()), new InputSector());
                        new InputDecisionPage(student);
                        break;
                    case Consts.FARM_SECTOR_NAME:
                        student = new Student(usernameTextField.getText(), String.valueOf(passwordPasswordField.getPassword()), new FarmSector());
                        new FarmerDecisionPage(student);
                        break;
                    case Consts.FOOD_SECTOR_NAME:
                        break;
                }
                Consts.DB.addStudent(student);
                //new HomePage(student);
                setVisible(false);
                dispose();
            }
        }

    }

    private boolean sectCheck(String sector) {
        int sectLimit;
        switch (sector) {
            case Consts.INPUT_SECTOR_NAME:
                sectLimit = Consts.SUPPLY_CAP;
                break;
            case Consts.FARM_SECTOR_NAME:
                sectLimit = Consts.FARM_CAP;
                break;
            case Consts.FOOD_SECTOR_NAME:
                sectLimit = Consts.FOOD_CAP;
                break;
            default:
                sectLimit = 0;
                break;
        }

        if (sectorAmounts.get(sector) >= sectLimit) {
            JOptionPane.showMessageDialog(rootPanel, "This sector has reached its limit. Please select another sector.", "Sector error", JOptionPane.ERROR_MESSAGE);
            updateBtns();
            return false;
        }
        return true;
    }

    private void updateBtns() {
        //(new Thread(new CreatePage())).start();
        //System.out.println(Consts.GAME_FLOW.currentYear);
        sectorAmounts = Consts.DB.numInSectors(Consts.GAME_FLOW.currentYear);
        //System.out.println(sectorAmounts);
        int dbNumInputSupply = sectorAmounts.get(Consts.INPUT_SECTOR_NAME);
        setAvailableLabel(inputSupplyNumber, dbNumInputSupply, Consts.SUPPLY_CAP);

        int dbNumFarmProduction = sectorAmounts.get(Consts.FARM_SECTOR_NAME);
        setAvailableLabel(farmProductionNumber, dbNumFarmProduction, Consts.FARM_CAP);

        int dbNumFoodMarketing = sectorAmounts.get(Consts.FOOD_SECTOR_NAME);
        setAvailableLabel(foodMarketingNumber, dbNumFoodMarketing, Consts.FOOD_CAP);

        if (dbNumInputSupply >= Consts.SUPPLY_CAP) {
            inputSupplyButton.setEnabled(false);
        } else
            inputSupplyButton.setEnabled(true);

        if (dbNumFarmProduction >= Consts.FARM_CAP) {
            farmProductionButton.setEnabled(false);
        } else
            farmProductionButton.setEnabled(true);

        if (dbNumFoodMarketing >= Consts.FOOD_CAP) {
            foodMarketingButton.setEnabled(false);
        } else
            foodMarketingButton.setEnabled(true);

        //kill();
    }

    private void setAvailableLabel(JLabel label, int num, int limit) {
        int available = limit - num;
        label.setText(String.valueOf(available));

        float ratio = (float) num / (float) limit;
        //System.out.println(ratio);
        if (ratio <= .33) {
            label.setForeground(new Color(0, 102, 0));
        } else if (ratio <= .66) {
            label.setForeground(new Color(207, 174, 0));
        } else
            label.setForeground(Color.RED);
    }
}
