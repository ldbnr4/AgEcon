/**
 * Created by Lorenzo on 9/18/2015.
 *
 */
public class GameDriver {

    public static void main(String[] args) {
        //out.println(Arrays.toString(EncryptPassword.encrypt("pass")));
        //Admin admin = new Admin("admin", "pass");
        //DB.addAdmin(admin);
        if (Consts.GAME_FLOW == null) {
            Consts.GAME_FLOW = new GameFlow();
            Consts.DB.addGameFlow(Consts.GAME_FLOW);
        }
        //DB.yearChange(1);
        new WelcomePage();
        //new CreatePage();
        //new HomePage(Consts.DB.getStudent("ldbnr4"));
        //Student student = DB.getStudent("InputIvy");
        //InputSector stuSect = (InputSector) student.sector;
        //System.out.println(DB.countFarmPpl());
        /*for (int i = 0; i < 3; i++) {
            Student student = new Student("Input"+ String.valueOf(i), "passpass", new InputSector());
            DB.addStudent(student);
        }*/
       /* for (int i = 0; i < 5; i++) {
            Student student = new Student("SmallFarmer"+ String.valueOf(i), "passpass", new FarmTypes(Consts.SMALL_FARM));
            Consts.DB.addStudent(student);
        }
        for (int i = 0; i < 4; i++) {
            Student student = new Student("LargeFarmer"+ String.valueOf(i), "passpass", new FarmTypes(Consts.LARGE_FARM));
            Consts.DB.addStudent(student);
        }
        for (int i = 0; i < 10; i++) {
            Student student = new Student("MedFarmers"+ String.valueOf(i), "passpass", new FarmTypes(Consts.MED_FARM));
            Consts.DB.addStudent(student);
        }*/
        //new AdminDecisionPage(Consts.DB.getAdmin("admin"));
        //System.out.println(Consts.DB.getSeedsNeeded(Consts.GAME_FLOW.currentYear));
        //System.out.println();
    }
}
