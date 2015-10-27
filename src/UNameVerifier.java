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
        boolean b = "\\w+".matches(in);
        balloonTip.setAttachedComponent(tf);
        if (!length || !b || GameDriver.DB.userInDB(in)) {
            tf.setBackground(Color.red);
            if (!length) {
                balloonTip.setTextContents("Usernames needs to be between 5 - 10 characters long.");
            } else if (!b) {
                balloonTip.setTextContents("Usernames can only contain number and letters.");
            } else if (GameDriver.DB.userInDB(in)) {
                balloonTip.setTextContents("This user already has an account.");
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
