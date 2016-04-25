/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.utils.TimingUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Lorenzo on 9/24/2015.
 *
 */
class UNameVerifier {
    private BalloonTip balloonTip;

    UNameVerifier(BalloonTip balloonTip) {
        this.balloonTip = balloonTip;
    }

    boolean verify(JComponent input, Object obj) {
        boolean adminFlag = false;
        if (obj instanceof Admin) {
            adminFlag = true;
        }
        JTextField tf = (JTextField) input;
        String in = tf.getText().trim();
        boolean length = in.length() >= 5 && in.length() <= 10;
        boolean b = in.matches("\\w+");
        balloonTip.setAttachedComponent(tf);
        if (!adminFlag) {
            Student student = Consts.DB.getStudent(in);
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
        } else {
            Admin admin = Consts.DB.getAdmin(in);
            if (!length || !b || admin != null) {
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
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */