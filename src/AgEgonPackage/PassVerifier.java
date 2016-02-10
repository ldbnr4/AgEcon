/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEgonPackage;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.utils.TimingUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Lorenzo on 9/24/2015.
 *
 */
public class PassVerifier {
    BalloonTip balloonTip;

    public PassVerifier(BalloonTip passBalloonTip) {
        this.balloonTip = passBalloonTip;
    }

    public boolean verifyLive(JComponent pass) {
        JPasswordField tf = (JPasswordField) pass;
        String in = String.valueOf(tf.getPassword());
        boolean length = in.length() >= 5 && in.length() <= 10;
        //boolean b = in.matches("\\w+");
        if (!length) {
            balloonTip.setAttachedComponent(tf);
            tf.setBackground(Color.red);
            balloonTip.setTextContents("Password needs to be between 5 - 10 characters long.");
            TimingUtils.showTimedBalloon(balloonTip, 2500);
            return false;
        }
        /*else if(!b){
            tf.setBackground(Color.red);
            balloonTip.setTextContents("Password can only contain number and letters.");
            balloonTip.setVisible(true);
        }*/
        else {
            balloonTip.setVisible(false);
            tf.setBackground(Color.green);
            return true;
        }
        //return length/*&&b*/;
    }

    public boolean verifyMatch(JComponent pass, JComponent confPass) {
        JPasswordField conField = (JPasswordField) confPass;
        JPasswordField passField = (JPasswordField) pass;
        balloonTip.setAttachedComponent(conField);
        String password = String.valueOf(passField.getPassword());
        String passwordConf = String.valueOf(conField.getPassword());
        if (!password.equals(passwordConf)) {
            conField.setBackground(Color.RED);
            balloonTip.setTextContents("Your passwords do not match.");
            TimingUtils.showTimedBalloon(balloonTip, 2500);
            return false;
        } else {
            balloonTip.setVisible(false);
            conField.setBackground(Color.green);
            return true;
        }
    }
}

/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */