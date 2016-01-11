/**
 * Created by Lorenzo on 9/18/2015.
 *
 */
public class GameDriver {

    public static void main(String[] args) {
        if (Consts.DB.getGameFlow() == null) {
            Consts.DB.saveGameFlow();
        }

        new WelcomePage();

        new SoftTestPage();
    }


}
