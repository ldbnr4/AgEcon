import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by Lorenzo on 1/10/2016.
 */
public class SoftTestPage extends JFrame implements ActionListener {
    private JButton buyingSeedsLoadTestButton;
    private JPanel rootPanel;

    public SoftTestPage() {
        super("Soft Test Page");
        setContentPane(rootPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        buyingSeedsLoadTestButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (btn.equals(buyingSeedsLoadTestButton)) {
            new Thread(() -> {
                if (Consts.DB.getInputSeller(Consts.COMPANY_A_NAME).getMidAmnt() < 100) {
                    Consts.DB.getInputSeller(Consts.COMPANY_A_NAME).updateMidAmnt(1000);
                }
                System.out.println("Starting amount: " + Consts.DB.getInputSeller(Consts.COMPANY_A_NAME).getMidAmnt());
                while (Consts.DB.getInputSeller(Consts.COMPANY_A_NAME).getMidAmnt() > 0) {
                    try {
                        sleep(new Random().nextInt(new Random().nextInt(3000)));
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    Consts.DB.getInputSeller(Consts.COMPANY_A_NAME).updateMidAmnt(-10);
                    System.out.println("After thread 1 transaction: " + Consts.DB.getInputSeller(Consts.COMPANY_A_NAME).getMidAmnt());
                }
            }).start();

            new Thread(() -> {
                //System.out.println("HERE");
                while (Consts.DB.getInputSeller(Consts.COMPANY_A_NAME).getMidAmnt() > 0) {
                    Consts.DB.getInputSeller(Consts.COMPANY_A_NAME).updateMidAmnt(-10);
                    System.out.println("After thread 2 transaction: " + Consts.DB.getInputSeller(Consts.COMPANY_A_NAME).getMidAmnt());
                    try {
                        sleep(new Random().nextInt(new Random().nextInt(3000)));
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }).start();
        }
    }


}
