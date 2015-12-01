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
        //new WelcomePage();
        //new CreatePage();
        new HomePage(Consts.DB.getStudent("ldbnr4", 2015).uName, Consts.DB.getStudent("ldbnr4", 2015).sector);
        //Student student = DB.getStudent("InputIvy");
        //InputSector stuSect = (InputSector) student.sector;
        //System.out.println(DB.countFarmPpl());
        /*for (int i = 0; i < 3; i++) {
            Student student = new Student("Input"+ String.valueOf(i), "passpass", new InputSector());
            DB.addStudent(student);
        }*/
        /*for (int i = 0; i < 5; i++) {
            Student student = new Student("SmallFarmer"+ String.valueOf(i), "passpass", new FarmSector(Consts.SMALL_FARM));
            Consts.DB.addStudent(student);
        }
        for (int i = 0; i < 10; i++) {
            Student student = new Student("LargeFarmer"+ String.valueOf(i), "passpass", new FarmSector(Consts.LARGE_FARM));
            Consts.DB.addStudent(student);
        }
        for (int i = 0; i < 5; i++) {
            Student student = new Student("MedFarmers"+ String.valueOf(i), "passpass", new FarmSector(Consts.MED_FARM));
            Consts.DB.addStudent(student);
        }*/
        //new AdminDecisionPage(Consts.DB.getAdmin("admin"));
        //System.out.println(Consts.DB.getSeedsNeeded(Consts.GAME_FLOW.currentYear));
        //System.out.println();
/*
        int compMax = 0, early = 0, mid = 0, full = 0, rn = 0, var = 0;
        Double maxPrice = 3.50, minPrice = 1.50;
        for (int i = 1; i < 6; i++) {
            compMax = (int) ceil((double) Consts.DB.getSeedsNeeded(Consts.GAME_FLOW.currentYear) / 5);
            early = 0;
            mid = 0;
            full = 0;
            while (compMax > 0) {
                rn = new Random().nextInt(compMax) + 1;
                var = new Random().nextInt(3);
                switch (var) {
                    case 0:
                        early += rn;
                        break;
                    case 1:
                        mid += rn;
                        break;
                    case 2:
                        full += rn;
                        break;
                }
                compMax -= rn;
            }
            Consts.DB.addInputComp(new InputSector("Company" + i, early, Consts.round(minPrice + new Random().nextDouble() * (maxPrice - minPrice)),
                    mid, Consts.round(minPrice + new Random().nextDouble() * (maxPrice - minPrice)),
                    full, Consts.round(minPrice + new Random().nextDouble() * (maxPrice - minPrice))));
        }*/
    }
}
