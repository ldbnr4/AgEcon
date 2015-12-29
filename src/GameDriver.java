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

        new WelcomePage();
    }
}
