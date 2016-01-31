package AgEgonPackage;

import java.util.Calendar;

/**
 * Created by Lorenzo on 10/27/2015.
 *
 */
public class GameFlow {
    public String name = "GameFlow";
    public int currentYear;
    public int startingYear;
    public int totalPlayers;

    public GameFlow() {
        setCurrentYear(Calendar.getInstance().get(Calendar.YEAR));
        setStartingYear(Calendar.getInstance().get(Calendar.YEAR));
        setTotalPlayers();
    }


    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public void setStartingYear(int startingYear) {
        this.startingYear = startingYear;
    }

    public void setTotalPlayers() {
        this.totalPlayers = Consts.DB.getTotalPlayers(currentYear);
    }

    public void nextYear() {
        setCurrentYear(this.currentYear + 1);
        Consts.DB.saveGameFlow();
    }

    public void prevYear() {
        if (this.currentYear > this.startingYear) {
            setCurrentYear(this.currentYear - 1);
        }
        Consts.DB.saveGameFlow();
    }

}
