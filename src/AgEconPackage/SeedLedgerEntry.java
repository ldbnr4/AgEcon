/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

/**
 * Created by Lorenzo on 12/27/2015.
 *
 */

class SeedLedgerEntry {
    private String seller;
    private Consts.Seed_Type seedType;
    private int amount;
    private double price;

    SeedLedgerEntry(){}

    SeedLedgerEntry(String seller, Consts.Seed_Type seedType, int amount, double price){
        setSeller(seller);
        setSeedType(seedType);
        setAmount(amount);
        setPrice(price);
    }

    String getSeller() {
        return seller;
    }

    private void setSeller(String seller) {
        this.seller = seller;
    }

    Consts.Seed_Type getSeedType() {
        return seedType;
    }

    private void setSeedType(Consts.Seed_Type seedType) {
        this.seedType = seedType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    double getPrice() {
        return price;
    }

    private void setPrice(double price) {
        this.price = price;
    }
}


/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */