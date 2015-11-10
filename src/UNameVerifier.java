import net.java.balloontip.BalloonTip;
import net.java.balloontip.utils.TimingUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Lorenzo on 9/24/2015.
 */
public class UNameVerifier {
    BalloonTip balloonTip;

    public UNameVerifier(BalloonTip balloonTip) {
        this.balloonTip = balloonTip;
    }

    public boolean verify(JComponent input) {
        JTextField tf = (JTextField) input;
        String in = tf.getText().trim();
        boolean length = in.length() >= 5 && in.length() <= 10;
        boolean b = in.matches("\\w+");
        //System.out.println(tf);
        balloonTip.setAttachedComponent(tf);
        Student student = GameDriver.DB.getStudent(in, GameDriver.GAME_FLOW.currentYear);
        if (!length || !b || student != null) {
            tf.setBackground(Color.red);
            if (!length) {
                balloonTip.setTextContents("Usernames needs to be between 5 - 10 characters long.");
            } else if (!b) {
                balloonTip.setTextContents("Usernames can only contain number and letters.");
            } else {
                balloonTip.setTextContents("This username has already has already been used.");
            }
            TimingUtils.showTimedBalloon(balloonTip, 2500);
            return false;
        } else {
            balloonTip.setVisible(false);
            tf.setBackground(Color.green);
            return true;
        }
    }
}
