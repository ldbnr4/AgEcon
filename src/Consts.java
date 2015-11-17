/**
 * Created by Lorenzo on 11/12/2015.
 */
public class Consts {
    public static final String INPUT_SECTOR_NAME = "Input Supply";
    public static final String FARM_SECTOR_NAME = "Farm Production";
    public static final String FOOD_SECTOR_NAME = "Food Marketing";
    public static final int SUPPLY_CAP = 5;
    public static final int FARM_CAP = 20;
    public static final int FOOD_CAP = 5;
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

    public enum Seed_Name {
        EARLY, MID, FULL, TOTAL
    }
}
