/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

/**
 * Created by Lorenzo on 12/15/2015.
 *
 */

class MarketingSector {
    private String name;
    private String neededDate;
    private Double pricePerBush;

    private int bshls;

    public MarketingSector() {
    }

    MarketingSector(String name, String neededDate, Double pricePerBush, int bshls) {
        this.name = name;
        this.neededDate = neededDate;
        this.pricePerBush = pricePerBush;
        this.bshls = bshls;
    }

    public String getName() {
        return this.name;
    }

    String getNeededDate() {
        return neededDate;
    }

    Double getPricePerBush() {
        return pricePerBush;
    }

    int getBshls() {
        return this.bshls;
    }

    boolean subtractBshls(int adjust) {
        if (adjust > bshls) {
            return false;
        }
        bshls -= adjust;
        Consts.DB.saveMarketing(this);
        return true;
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */