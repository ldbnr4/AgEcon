/**
 * Created by Lorenzo on 9/18/2015.
 *
 */
public class GameDriver {

    public static void main(String[] args) {
        if (Consts.GAME_FLOW == null) {
            Consts.GAME_FLOW = new GameFlow();
            Consts.DB.addGameFlow(Consts.GAME_FLOW);
        }
        /*FarmTypes test = new FarmTypes('M');
        test.updateSeedsOwned(1000, 2000, 4000);

        test.plantAction();*/

        //new WelcomePage();
        new CreatePage();
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
        new AdminDecisionPage(Consts.DB.getAdmin("admin"));
        new MarketingDealsPage(Consts.DB.getStudent("ldbnr4"));
        //new HomePage(Consts.DB.getStudent("ldbnr4"));
        //System.out.println(Consts.DB.getSeedsNeeded(Consts.GAME_FLOW.currentYear));
        //System.out.println();
    }
}
