import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Calendar;

/**
 * Created by Lorenzo on 10/27/2015.
 *
 */
@Entity
public class GameFlow {
    @Id
    public String name = "GameFlow";
    public int currentYear;
    public int startingYear;
    public int totalPlayers;

    public GameFlow() {
        setCurrentYear(Calendar.getInstance().get(Calendar.YEAR));
        setStartingYear(Calendar.getInstance().get(Calendar.YEAR));
        setTotalPlayers();
        GameDriver.DB.addGameFlow(this);
    }


    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public void setStartingYear(int startingYear) {
        this.startingYear = startingYear;
    }

    public void setTotalPlayers() {
        this.totalPlayers = GameDriver.DB.getTotalPlayers(currentYear);
    }

    public void nextYear() {
        setCurrentYear(this.currentYear + 1);
        GameDriver.DB.saveGameFlow();
    }

    public void prevYear() {
        if (this.currentYear > this.startingYear) {
            setCurrentYear(this.currentYear - 1);
        }
        GameDriver.DB.saveGameFlow();
    }

}
