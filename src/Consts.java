import org.mongodb.morphia.annotations.Embedded;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Lorenzo on 11/12/2015.
 *
 */
public class Consts {
    static final String COMPANY_A_NAME = "CompanyA", COMPANY_B_NAME = "CompanyB", COMPANY_C_NAME = "CompanyC",
            COMPANY_D_NAME = "CompanyD", COMPANY_E_NAME = "CompanyE";
    static final char SMALL_FARM = 'S', MED_FARM = 'M', LARGE_FARM = 'L', NO_FARM = 'X';
    static final int S_FARM_CAP = 5;
    static final int M_FARM_CAP = 10;
    static final int L_FARM_CAP = 5;
    static final double INFLATION = 1.05;
    static final int FORWARD = -1;
    static final int BACK = 1;
    static final MongoDBConnection DB = MongoDBConnection.getInstance();
    static GameFlow GAME_FLOW = DB.getGameFlow();
    static Double ACRE_YIELD = 62.5;
    static SimpleDateFormat sd = new SimpleDateFormat("MMM dd yyyy");

    private Consts() {
        throw new AssertionError();
    }

    public static boolean checkEmpty(String field) {
        return field.isEmpty();
    }

    public static double round(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static ListCellRenderer<? super String> getRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
                return listCellRendererComponent;
            }
        };
    }

    static void checkSetSoldOut(JLabel amntlabel, JLabel priceLabel, int amount, double price) {
        if (amount > 0) {
            amntlabel.setForeground(Color.BLACK);
            amntlabel.setText(String.valueOf(amount));
            priceLabel.setForeground(Color.BLACK);
            priceLabel.setText(String.valueOf(price));
        } else {
            amntlabel.setText("SOLD OUT");
            amntlabel.setForeground(Color.RED);
            priceLabel.setText("SOLD OUT");
            priceLabel.setForeground(Color.RED);
        }
    }

    static String getEarlyHarvDt() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 100);
        return sd.format(cal.getTime());
    }

    static String getMidHarvDt() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 107);
        return sd.format(cal.getTime());
    }

    static String getFullHarvDt() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 112);
        return sd.format(cal.getTime());
    }

    public enum Seed_Name {
        EARLY, MID, FULL
    }

    @Embedded
    static class SeedStat {
        Seed_Name seedType;
        int amount;
        double price;
        double ttlCst;

        public SeedStat(Seed_Name seedType, int amount, double price, double totalCst) {
            setSeedType(seedType);
            setAmount(amount);
            setPrice(price);
            setTtlCst(totalCst);
        }

        public SeedStat() {

        }

        public Seed_Name getSeedType() {
            return seedType;
        }

        public void setSeedType(Seed_Name seedType) {
            this.seedType = seedType;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getTtlCst() {
            return ttlCst;
        }

        public void setTtlCst(double ttlCst) {
            this.ttlCst = ttlCst;
        }
    }

    public static class RoundJTextField extends JTextField {
        private Shape shape;

        public RoundJTextField(int size) {
            super(size);
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

        public RoundPasswordField(int size) {
            super(size);
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
