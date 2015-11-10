/**
 * Created by Lorenzo on 9/18/2015.
 *
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
    public enum Seed_Name {
        EARLY, MID, FULL, TOTAL
    }
    public static final MongoDBConnection DB = MongoDBConnection.getInstance();
    public static GameFlow GAME_FLOW = DB.getGameFlow();

    public static boolean checkEmpty(String field) {
        return field.isEmpty();
    }

    public static void main(String[] args) {
        //out.println(Arrays.toString(EncryptPassword.encrypt("pass")));
        //Admin admin = new Admin("admin", "pass");
        //DB.addAdmin(admin);
        if (GAME_FLOW == null) {
            GAME_FLOW = new GameFlow();
        }
        new AdminDecisionPage(DB.getAdmin("admin"));
        //new WelcomePage();
        //new CreatePage();
        //Student student = DB.getStudent("InputIvy");
        //InputSector stuSect = (InputSector) student.sector;
        //System.out.println(DB.countFarmPpl());
        /*for (int i = 0; i < 3; i++) {
            Student student = new Student("Input"+ String.valueOf(i), "passpass", new InputSector());
            DB.addStudent(student);
        }
        for (int i = 0; i < 7; i++) {
            Student student = new Student("Farm"+ String.valueOf(i), "passpass", new FarmSector(SMALL_FARM));
            DB.addStudent(student);
        }*/
    }

    public static double round(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
