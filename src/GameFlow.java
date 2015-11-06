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
    public int currentPlayers;

    public GameFlow() {
        setCurrentYear(Calendar.getInstance().get(Calendar.YEAR));
        setStartingYear(Calendar.getInstance().get(Calendar.YEAR));
        setTotalPlayers();
        setCurrentPlayers(this.currentYear);
    }


    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public void setStartingYear(int startingYear) {
        this.startingYear = startingYear;
    }

    public void setTotalPlayers() {
        this.totalPlayers = GameDriver.DB.getTotalPlayers(this.startingYear);
    }

    public void setCurrentPlayers(int currentYear) {
        this.currentPlayers = GameDriver.DB.getCurrentPlayers(currentYear);
    }

    public void nextYear() {
        setCurrentYear(this.currentYear + 1);
        setCurrentPlayers(this.currentYear);
    }

    public void prevYear() {
        if (this.currentYear > this.startingYear) {
            setCurrentYear(this.currentYear - 1);
            setCurrentPlayers(this.currentYear);
        }
    }

}
