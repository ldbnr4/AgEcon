import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by Lorenzo on 11/12/2015.
 *
 */
public class Consts {
    public static final String INPUT_SECTOR_NAME = "Input Supply";
    public static final String FARM_SECTOR_NAME = "Farm Production";
    public static final String FOOD_SECTOR_NAME = "Food Marketing";
    public static final char SMALL_FARM = 'S';
    public static final char MED_FARM = 'M';
    public static final char LARGE_FARM = 'L';
    public static final char NO_FARM = 'X';
    public static final int S_FARM_CAP = 5;
    public static final int M_FARM_CAP = 10;
    public static final int L_FARM_CAP = 5;
    public static final double INFLATION = 1.05;
    public static final int FORWARD = -1;
    public static final int BACK = 1;
    public static final MongoDBConnection DB = MongoDBConnection.getInstance();
    public static GameFlow GAME_FLOW = DB.getGameFlow();

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

    public enum Seed_Name {
        EARLY, MID, FULL, TOTAL
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
