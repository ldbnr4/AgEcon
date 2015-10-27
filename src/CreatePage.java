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
    private BalloonTipStyle modern = new MinimalBalloonStyle(Color.white, 5);
    private BalloonTip uNameBalloonTip = new BalloonTip(usernameTextField, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
    private BalloonTip passBalloonTip = new BalloonTip(passwordPasswordField, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
    private BalloonTip confPassBalloonTip = new BalloonTip(confirmPasswordPasswordField, new JLabel(), modern, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.ALIGNED, 10, 10, false);
    private UNameVerifier usernameVerifier = new UNameVerifier(uNameBalloonTip);
    private PassVerifier passwordVerifier = new PassVerifier(passBalloonTip);
    private PassVerifier confPassVerifier = new PassVerifier(confPassBalloonTip);
    private HashMap<String, Integer> sectorAmounts;
    private volatile boolean isRunning = true;

    public CreatePage() {
        super("Create Account");

        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        updateBtns();

        this.uNameBalloonTip.setVisible(false);
        this.passBalloonTip.setVisible(false);
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

        //System.out.println(getContentPane().getSize());

        usernameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                usernameVerifier.verify(usernameTextField);
            }
        });
        passwordPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                passwordVerifier.verifyLive(passwordPasswordField);
            }
        });
        confirmPasswordPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!confPassVerifier.verifyLive(confirmPasswordPasswordField)) {
                } else confPassVerifier.verifyMatch(passwordPasswordField, confirmPasswordPasswordField);

            }
        });

        inputSupplyButton.setText(GameDriver.INPUT_SECTOR_NAME);
        farmProductionButton.setText(GameDriver.FARM_SECTOR_NAME);
        foodMarketingButton.setText(GameDriver.FOOD_SECTOR_NAME);

        /*System.out.println(inputSupplyButton.getSize());
        System.out.println(farmProductionButton.getSize());
        System.out.println(foodMarketingButton.getSize());*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String selectedSect = button.getText();
        if (!usernameVerifier.verify(usernameTextField) || !passwordVerifier.verifyLive(passwordPasswordField) ||
                !passwordVerifier.verifyLive(confirmPasswordPasswordField) ||
                !passwordVerifier.verifyMatch(passwordPasswordField, confirmPasswordPasswordField)) {
        } else if (!sectCheck(button.getText())) {
            button.setEnabled(false);
        } else {
            usernameTextField.setBackground(Color.green);
            confirmPasswordPasswordField.setBackground(Color.GREEN);
            passwordPasswordField.setBackground(Color.GREEN);
            button.setEnabled(true);
            Student student = null;

            switch (selectedSect) {
                case GameDriver.INPUT_SECTOR_NAME:
                    student = new Student(usernameTextField.getText(), String.valueOf(passwordPasswordField.getPassword()), new InputSector());
                    new InputDecisionPage(student);
                    break;
                case GameDriver.FARM_SECTOR_NAME:
                    student = new Student(usernameTextField.getText(), String.valueOf(passwordPasswordField.getPassword()), new FarmSector());
                    new FarmerDecisionPage(student);
                    break;
                case GameDriver.FOOD_SECTOR_NAME:
                    break;
            }
            GameDriver.DB.addUser(student);
            //new HomePage(student);
            setVisible(false);
            dispose();
        }
    }

    private boolean sectCheck(String sector) {
        int sectLimit;
        switch (sector) {
            case GameDriver.INPUT_SECTOR_NAME:
                sectLimit = GameDriver.SUPPLY_CAP;
                break;
            case GameDriver.FARM_SECTOR_NAME:
                sectLimit = GameDriver.FARM_CAP;
                break;
            case GameDriver.FOOD_SECTOR_NAME:
                sectLimit = GameDriver.FOOD_CAP;
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
        sectorAmounts = GameDriver.DB.numInSectors();
        //System.out.println(sectorAmounts);
        int dbNumInputSupply = sectorAmounts.get(GameDriver.INPUT_SECTOR_NAME);
        setAvailableLabel(inputSupplyNumber, dbNumInputSupply, GameDriver.SUPPLY_CAP);

        int dbNumFarmProduction = sectorAmounts.get(GameDriver.FARM_SECTOR_NAME);
        setAvailableLabel(farmProductionNumber, dbNumFarmProduction, GameDriver.FARM_CAP);

        int dbNumFoodMarketing = sectorAmounts.get(GameDriver.FOOD_SECTOR_NAME);
        setAvailableLabel(foodMarketingNumber, dbNumFoodMarketing, GameDriver.FOOD_CAP);

        if (dbNumInputSupply >= GameDriver.SUPPLY_CAP) {
            inputSupplyButton.setEnabled(false);
        } else
            inputSupplyButton.setEnabled(true);

        if (dbNumFarmProduction >= GameDriver.FARM_CAP) {
            farmProductionButton.setEnabled(false);
        } else
            farmProductionButton.setEnabled(true);

        if (dbNumFoodMarketing >= GameDriver.FOOD_CAP) {
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

/*    @Override
    public void run() {
        while (isRunning) {
            JFrame frame = new JFrame("Test");
            try {
                frame.add(new JComponent() {

                    ClassLoader classLoader = getClass().getClassLoader();
                    BufferedImage img = ImageIO.read(new File(classLoader.getResource("img/checked.png").getFile()));

                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        // create the transform, note that the transformations happen
                        // in reversed order (so check them backwards)
                        AffineTransform at = new AffineTransform();

                        // 4. translate it to the center of the component
                        at.translate(getWidth() / 2, getHeight() / 2);

                        // 3. do the actual rotation
                        at.rotate(Math.PI / 4);

                        // 2. just a scale because this image is big
                        at.scale(0.5, 0.5);

                        // 1. translate the object so that you rotate it around the
                        //    center (easier :))
                        at.translate(-img.getWidth() / 2, -img.getHeight() / 2);

                        // draw the image
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.drawImage(img, at, null);

                        // continue drawing other stuff (non-transformed)
                        //...

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        }
    }
    public void kill() {
        isRunning = false;
    }*/
}
