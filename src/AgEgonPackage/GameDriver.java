package AgEgonPackage;

/**
 * Created by Lorenzo on 9/18/2015.
 *
 */
public class GameDriver {

    public static void main(String[] args) {
        if (Consts.DB.getGameFlow() == null) {
            Consts.DB.addGameFlow(new GameFlow());
        }

        //new WaitPage();
        //new WelcomePage();
        //Consts.DB.addAdmin(new Admin("ldbnr4", "password"));
        //Consts.DB.addStudent(new Student("ldbnr4","password", new Farm()));
        //System.out.println(Consts.DB.getTotalPlayers(2016));

        //Student student = Consts.DB.getStudent("ldbnr4");
        Admin admin = Consts.DB.getAdmin("ldbnr4");


        //new BuySeedsPage(student);
        //new MarketingDealsPage(student);
        //new ViewSeedOrdersPage(student);
        //new EndofSeasonPage(student);

        new SoftTestPage();
        //new AdminDecisionPage(admin);
    }


}
