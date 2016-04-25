/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

class LittleGameFlow {
    private boolean inpuSect;
    private boolean marketingSect;

    LittleGameFlow() {
        inpuSect = false;
        marketingSect = false;
    }

    boolean isMarketingSect() {
        return marketingSect;
    }

    void setMarketingSect(boolean marketingSect) {
        this.marketingSect = marketingSect;
    }

    boolean isInpuSect() {
        return inpuSect;
    }

    void setInpuSect(boolean inpuSect) {
        this.inpuSect = inpuSect;
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */