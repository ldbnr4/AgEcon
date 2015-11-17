import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lorenzo on 10/27/2015.
 *
 */
public class AdminDecisionPage extends JFrame implements ActionListener {
    private JLabel nameLabel;
    private JLabel numOfPlayersLabel;
    private JPanel rootPanel;
    private JLabel gameYearLabel;
    private JLabel startingYearLabel;
    private JButton nextYearBtn;
    private JButton prevYearBtn;

    public AdminDecisionPage(Admin admin) {
        super("Admin Page");
        setContentPane(rootPanel);
        setResizable(false);
        nameLabel.setText("Welcome " + admin.name + "!");
        //Consts.DB.saveGameFlow();
        numOfPlayersLabel.setText(String.valueOf(Consts.GAME_FLOW.totalPlayers));
        startingYearLabel.setText(String.valueOf(Consts.GAME_FLOW.startingYear));
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
            Consts.GAME_FLOW.nextYear();
            Consts.DB.yearChange(Consts.FORWARD);
        } else {
            Consts.GAME_FLOW.prevYear();
            Consts.DB.yearChange(Consts.BACK);
        }
        setGameYearLabel();
    }

    private void setLabels() {
        setGameYearLabel();
    }

    private void setGameYearLabel() {
        gameYearLabel.setText(String.valueOf(Consts.GAME_FLOW.currentYear));
    }

}
