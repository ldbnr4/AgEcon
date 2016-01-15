/**
 * Created by Lorenzo on 9/18/2015.
 *
 */
public class GameDriver {

    public static void main(String[] args) {
        if (Consts.DB.getGameFlow() == null) {
            Consts.DB.addGameFlow(new GameFlow());
        }

        new WelcomePage();

        new EndofSeasonPage(Consts.DB.getStudent("ldbnr4"));
        //new MarketingDealsPage(Consts.DB.getStudent("farmerJoe"));
        //new SoftTestPage();
    }


}
