import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Calendar;

/**
 * Created by Lorenzo on 10/27/2015.
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
    }


    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }


    public void setStartingYear(int startingYear) {
        this.startingYear = startingYear;
    }

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public void updateCurretPlayers() {
        setCurrentPlayers(GameDriver.DB.getCurrentPlayers());
    }

    public void nextYear() {
        setCurrentYear(GameDriver.GAME_FLOW.currentYear + 1);
        updateCurretPlayers();
    }

    public void prevYear() {
        if (GameDriver.GAME_FLOW.currentYear > GameDriver.GAME_FLOW.startingYear) {
            setCurrentYear(GameDriver.GAME_FLOW.currentYear - 1);
            updateCurretPlayers();
        }
    }

}
