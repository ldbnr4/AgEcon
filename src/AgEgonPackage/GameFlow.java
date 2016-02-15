/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEgonPackage;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Lorenzo on 10/27/2015.
 *
 */
public class GameFlow {
    private String name;
    private int startingYear;
    private int currentYear;
    private HashMap<Integer, LittleGameFlow> gameFlows;

    public GameFlow(String name) {
        this.name = name;
        startingYear = Calendar.getInstance().get(Calendar.YEAR);
        gameFlows = new HashMap<Integer, LittleGameFlow>() {{
            put(startingYear, new LittleGameFlow());
        }};
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
    }

    public String getName() {
        return name;
    }

    public void nextYear() {
        currentYear += 1;
        if (gameFlows.get(currentYear) == null) {
            gameFlows.put(currentYear, new LittleGameFlow());
        }
        Consts.DB.saveGameFlow(this);
    }

    public boolean prevYear() {
        if (currentYear > startingYear) {
            currentYear -= 1;
            Consts.DB.saveGameFlow(this);
            return true;
        }
        return false;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public int getStartingYear() {
        return startingYear;
    }

    public boolean getCurrGameInput() {
        return gameFlows.get(currentYear).isInpuSect();
    }

    public boolean getCurrGameMarket() {
        return gameFlows.get(currentYear).isMarketingSect();
    }

    public void setGameFlowsInput(int year, boolean bool) {
        gameFlows.get(year).setInpuSect(bool);
    }

    public void setGameFlowsMarket(int year, boolean bool) {
        gameFlows.get(year).setMarketingSect(bool);
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */