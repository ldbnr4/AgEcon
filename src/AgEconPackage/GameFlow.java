/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Lorenzo on 10/27/2015.
 *
 */
class GameFlow {
    private String name;
    private int startingYear;
    private int currentYear;
    private HashMap<Integer, LittleGameFlow> gameFlows;

    GameFlow(String name) {
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

    void nextYear() {
        currentYear += 1;
        gameFlows.putIfAbsent(currentYear, new LittleGameFlow());
        Consts.DB.saveGameFlow(this);
    }

    boolean prevYear() {
        if (currentYear > startingYear) {
            currentYear -= 1;
            Consts.DB.saveGameFlow(this);
            return true;
        }
        return false;
    }

    int getCurrentYear() {
        return currentYear;
    }

    int getStartingYear() {
        return startingYear;
    }

    boolean getCurrGameInput() {
        return gameFlows.get(currentYear).isInpuSect();
    }

    boolean getCurrGameMarket() {
        return gameFlows.get(currentYear).isMarketingSect();
    }

    void setGameFlowsInput(int year, boolean bool) {
        gameFlows.get(year).setInpuSect(bool);
    }

    void setGameFlowsMarket(int year, boolean bool) {
        gameFlows.get(year).setMarketingSect(bool);
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */