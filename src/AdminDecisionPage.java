import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lorenzo on 10/27/2015.
 */
public class AdminDecisionPage extends JFrame implements ActionListener {
    private JLabel nameLabel;
    private JLabel numOfPlayersLabel;
    private JPanel rootPanel;
    private JLabel gameYearLabel;
    private JLabel startingYearLabel;
    private JLabel currentNumOfPlayersLabel;
    private JButton nextYearBtn;
    private JButton prevYearBtn;

    public AdminDecisionPage(Admin admin) {
        super("Admin Page");
        setContentPane(rootPanel);
        setResizable(false);
        nameLabel.setText("Welcome " + admin.name + "!");
        //GameDriver.DB.saveGameFlow();
        numOfPlayersLabel.setText(String.valueOf(GameDriver.GAME_FLOW.totalPlayers));
        startingYearLabel.setText(String.valueOf(GameDriver.GAME_FLOW.startingYear));
        setLabels();
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        nextYearBtn.addActionListener(this);

        prevYearBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().equals(nextYearBtn.getText())) {
            GameDriver.GAME_FLOW.nextYear();
        } else {
            GameDriver.GAME_FLOW.prevYear();
        }
        GameDriver.DB.saveGameFlow();
        setGameYearLabel();
        setNumOfPlayersLabel();
    }

    private void setLabels() {
        setNumOfPlayersLabel();
        setGameYearLabel();
    }

    private void setGameYearLabel() {
        gameYearLabel.setText(String.valueOf(GameDriver.GAME_FLOW.currentYear));
    }

    private void setNumOfPlayersLabel() {
        currentNumOfPlayersLabel.setText(String.valueOf(GameDriver.GAME_FLOW.currentPlayers));
    }
}
