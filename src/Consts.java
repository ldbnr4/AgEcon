import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Lorenzo on 11/12/2015.
 *
 */
public class Consts {
    static final String COMPANY_A_NAME = "CompanyA", COMPANY_B_NAME = "CompanyB", COMPANY_C_NAME = "CompanyC",
            COMPANY_D_NAME = "CompanyD", COMPANY_E_NAME = "CompanyE";
    static final int S_FARM_CAP = 8;
    static final int M_FARM_CAP = 14;
    static final int L_FARM_CAP = 8;
    static final int TOTAL_STUS = S_FARM_CAP + M_FARM_CAP + L_FARM_CAP;
    static final int S_ACRE = 100;
    static final int M_ACRE = 250;
    static final int L_ACRE = 500;
    static final double INFLATION = 1.05;
    static final int FORWARD = -1;
    static final int BACK = 1;
    static final MongoDBConnection DB = MongoDBConnection.getInstance();
    static final int ACRE_YIELD = 50;
    static SimpleDateFormat sd2 = new SimpleDateFormat("MMMM dd, yyyy");

    private Consts() {
        throw new AssertionError();
    }

    public static FocusListener greyFocusListener(JTextField textField) {
        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getForeground().equals(Color.GRAY)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        };
    }

    public static String htmlWrapper(String msg, int size) {
        return "<html><font size=\"" + size + "\">" + msg + "</font></html>";
    }

    public static Farm_Size randomFarmSize() {
        Farm_Size[] VALUES = Farm_Size.values();
        int SIZE = VALUES.length;
        Random RANDOM = new Random();
        Farm_Size farm_size = VALUES[(RANDOM.nextInt(SIZE))];
        if (farm_size.equals(Farm_Size.NO_FARM)) {
            return randomFarmSize();
        } else {
            return farm_size;
        }
    }

    static KeyListener customKeyListner(JTextField textField) {
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!String.valueOf(e.getKeyChar()).matches("\\d+")) {
                    e.setKeyChar('\0');
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (textField.getText().length() >= 3) {
                    textField.setText(NumberFormat.getNumberInstance().format(
                            Integer.valueOf(textField.getText().replaceAll(",", ""))
                    ));
                }
            }
        };
    }

    public static double round(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    static void checkSetSoldOut(JLabel amntlabel, JLabel priceLabel, int amount, double price) {
        if (amount > 0) {
            amntlabel.setForeground(Color.BLACK);
            amntlabel.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(amount)));
            priceLabel.setForeground(Color.BLACK);
            priceLabel.setText(String.valueOf(NumberFormat.getCurrencyInstance(Locale.US).format(price)));
        } else {
            amntlabel.setText("SOLD OUT");
            amntlabel.setForeground(Color.RED);
            priceLabel.setText("SOLD OUT");
            priceLabel.setForeground(Color.RED);
        }
    }

    static void checkSetSoldOut(JLabel amntlabel, JLabel pricelabel, JLabel datelabel, int amount, Double price, String date) {
        if (amount > 0) {
            amntlabel.setForeground(Color.BLACK);
            pricelabel.setForeground(Color.BLACK);
            datelabel.setForeground(Color.BLACK);
            amntlabel.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(amount)));
            pricelabel.setText(String.valueOf(NumberFormat.getCurrencyInstance(Locale.US).format(price)));
            datelabel.setText(date);
        } else {
            amntlabel.setText("SOLD OUT");
            amntlabel.setForeground(Color.RED);
            pricelabel.setText("SOLD OUT");
            pricelabel.setForeground(Color.RED);
            datelabel.setText("SOLD OUT");
            datelabel.setForeground(Color.RED);
        }
    }

    static String getEarlyHarvDt() {
        return "September 1, " + DB.getGameFlow().currentYear;
    }

    static String getMidHarvDt() {
        return "September 20, " + DB.getGameFlow().currentYear;
    }

    static String getFullHarvDt() {
        return "October 10, " + DB.getGameFlow().currentYear;
    }

    public enum Seed_Type {
        EARLY, MID, FULL
    }

    public enum Student_Stage {
        Select_Size, Buy_Seeds, Sell_Yields, End_of_Season
    }

    public enum Farm_Size {
        SMALL_FARM("SMALL_FARM"), MED_FARM("MED_FARM"), LARGE_FARM("LARGE_FARM"), NO_FARM("NO_FARM");

        private final String value;

        Farm_Size(String value) {
            this.value = value;
        }

        public static Farm_Size fromValue(String value) {
            if (value != null) {
                for (Farm_Size farm : values()) {
                    if (farm.value.equals(value)) {
                        return farm;
                    }
                }
            }

            throw new IllegalArgumentException("Invalid farm: " + value);
        }

        public String toValue() {
            return value;
        }

    }

    public static class RoundJTextField extends JTextField {
        private Shape shape;

        public RoundJTextField() {
            super(15);
            setOpaque(false); // As suggested by @AVD in comment.
        }

        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            super.paintComponent(g);
        }

        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        }

        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
            return shape.contains(x, y);
        }

    }

    public static class RoundPasswordField extends JPasswordField {
        private Shape shape;

        public RoundPasswordField() {
            super(15);
            setOpaque(false); // As suggested by @AVD in comment.
        }

        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            super.paintComponent(g);
        }

        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        }

        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
            return shape.contains(x, y);
        }
    }

}
