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
        //new AdminDecisionPage(DB.getAdmin("admin"));
        //new WelcomePage();
        new CreatePage();
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
}
