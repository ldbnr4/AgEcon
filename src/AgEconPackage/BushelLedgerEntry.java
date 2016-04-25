/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

/**
 * Created by Lorenzo on 12/20/2015.
 *
 */
class BushelLedgerEntry extends HarvestEntry implements Comparable {
    private double ppbndl;
    private String seller;

    BushelLedgerEntry(String date, int amount, double ppbndl, String seller) {
        super(date, amount);
        setDate(date);
        setAmount(amount);
        setPpbndl(ppbndl);
        setSeller(seller);
    }

    public BushelLedgerEntry() {
        super();
    }

    double getPpbndl() {
        return ppbndl;
    }

    private void setPpbndl(double ppbndl) {
        this.ppbndl = ppbndl;
    }

    String getSeller() {
        return seller;
    }

    private void setSeller(String seller) {
        this.seller = seller;
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */
