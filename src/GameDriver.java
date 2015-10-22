/**
 * Created by Lorenzo on 9/18/2015.
 */
public class GameDriver {
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

    public static void main(String[] args) {
        new WelcomePage();
        //Student student = new Student();
        //new InputDecisionPage(student);
        //MongoDBConnection db = MongoDBConnection.getInstance( );
        /*for (int i = 0; i < 3; i++) {
            Student student = new Student("Input"+ String.valueOf(i), "passpass", "salt", new InputSector());
            db.addUser(student);
        }*/
        /*for (int i = 0; i < 7; i++) {
            Student student = new Student("Farm"+ String.valueOf(i), "passpass", "salt", new InputSector());
            db.addUser(student);
        }*/
    }

}
