/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEgonPackage;

import javax.swing.*;

import static AgEgonPackage.Consts.Seed_Type.*;

/**
 * Created by Lorenzo on 12/19/2015.
 *
 */
public class CompanyThread implements Runnable {
    JLabel erlA, erlP, midA, midP, fullA, fullP;
    String name;
    InputSector DBinput;
    Boolean inputComp;
    JFrame page;

    JLabel amnt, price, dt;
    MarketingSector DBmarketingComp;


    public CompanyThread(BuySeedsPage page, String name, JLabel eAmnt, JLabel ePrice, JLabel mAmnt, JLabel mPrice, JLabel fAmnt, JLabel fPrice) {
        this.erlA = eAmnt;
        this.erlP = ePrice;
        this.midA = mAmnt;
        this.midP = mPrice;
        this.fullA = fAmnt;
        this.fullP = fPrice;
        this.name = name;
        this.inputComp = true;
        this.page = page;
        this.DBinput = Consts.DB.getInputSeller(name);
    }

    public CompanyThread(MarketingDealsPage page, String name, JLabel numOfBushels, JLabel pricePerBushel, JLabel date) {
        this.inputComp = false;
        this.name = name;
        this.amnt = numOfBushels;
        this.price = pricePerBushel;
        this.dt = date;
        this.page = page;
        this.DBmarketingComp = Consts.DB.getMarketingComp(name);


    }

    @Override
    public void run() {
        //System.out.println(Consts.DB.getInputSeller(name, Consts.GAME_FLOW.currentYear));
        while (page.isVisible()) {
            if (inputComp) {
                try {
                    Consts.checkSetSoldOut(erlA, erlP, DBinput.getAmnt(EARLY), DBinput.getPrice(EARLY));
                    Consts.checkSetSoldOut(midA, midP, DBinput.getAmnt(MID), DBinput.getPrice(MID));
                    Consts.checkSetSoldOut(fullA, fullP, DBinput.getAmnt(FULL), DBinput.getPrice(FULL));
                    DBinput = Consts.DB.getInputSeller(name);
                } catch (Exception e) {
                    //System.out.println("Trouble updating labels on Home Page.");
                    e.printStackTrace();

                }
            } else {
                try {
                    Consts.checkSetSoldOut(amnt, price, dt, DBmarketingComp.getBshls(), DBmarketingComp.getPricePerBush(),
                            DBmarketingComp.getNeededDate());
                    DBmarketingComp = Consts.DB.getMarketingComp(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */