/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEgonPackage;

import AgEgonPackage.Consts.Seed_Type;

import java.util.HashMap;

import static AgEgonPackage.Consts.Seed_Type.*;

/**
 * Created by Lorenzo on 10/13/2015.
 *
 */

public class InputSector {
    private String name;
    private int earlyAmnt = 0, midAmnt = 0, fullAmnt = 0;
    private double earlyPrice = 0, midPrice = 0, fullPrice = 0;

    public InputSector() {}

    public InputSector(String name, int earlyAmnt, double earlyCost, int midAmnt, double midCost, int fullAmnt,
                       double fullCost) {
        this.name = name;
        setPrice(new HashMap<Seed_Type, Double>() {{
            put(EARLY, earlyCost);
            put(MID, midCost);
            put(FULL, fullCost);
        }});

        setAmnts(new HashMap<Seed_Type, Integer>() {{
            put(EARLY, earlyAmnt);
            put(MID, midAmnt);
            put(FULL, fullAmnt);
        }});
    }

    public String getName() {
        return name;
    }

    private void setAmnt(Seed_Type type, int amnt) {
        switch (type) {
            case EARLY:
                earlyAmnt = amnt;
                break;
            case MID:
                midAmnt = amnt;
                break;
            case FULL:
                fullAmnt = amnt;
                break;
            default:
                break;
        }
    }

    public boolean updateAmnt(Seed_Type type, Integer amnt) {
        if (-amnt > getAmnt(type)) {
            return false;
        }
        setAmnt(type, amnt += getAmnt(type));
        Consts.DB.saveInput(this);
        return true;
    }

    public int getAmnt(Seed_Type type) {
        switch (type) {
            case EARLY:
                return earlyAmnt;
            case MID:
                return midAmnt;
            case FULL:
                return fullAmnt;
            default:
                return 0;
        }
    }

    public double getPrice(Seed_Type type) {
        switch (type) {
            case EARLY:
                return earlyPrice;
            case MID:
                return midPrice;
            case FULL:
                return fullPrice;
            default:
                return 0;
        }
    }

    public void setPrice(HashMap<Seed_Type, Double> seedPrices) {
        seedPrices.forEach((type, price) -> {
            switch (type) {
                case EARLY:
                    earlyPrice = price;
                    break;
                case MID:
                    midPrice = price;
                    break;
                case FULL:
                    fullPrice = price;
                    break;
                default:
                    break;
            }
        });
        Consts.DB.saveInput(this);
    }

    private void setAmnts(HashMap<Seed_Type, Integer> seedAmnts) {
        seedAmnts.forEach((type, amnt) -> {
            switch (type) {
                case EARLY:
                    earlyAmnt = amnt;
                    break;
                case MID:
                    midAmnt = amnt;
                    break;
                case FULL:
                    fullAmnt = amnt;
                    break;
                default:
                    break;
            }
        });
        Consts.DB.saveInput(this);
    }
}

/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */