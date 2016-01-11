import javax.swing.*;

/**
 * Created by Lorenzo on 12/19/2015.
 *
 */
public class CompanyThread implements Runnable {
    JLabel erlA, erlP, midA, midP, fullA, fullP;
    String name;
    InputSector DBinput;
    Boolean inputComp;

    JLabel amnt, price, dt;
    MarketingSector DBmarketingComp;


    public CompanyThread(String name, JLabel eAmnt, JLabel ePrice, JLabel mAmnt, JLabel mPrice, JLabel fAmnt, JLabel fPrice) {
        this.erlA = eAmnt;
        this.erlP = ePrice;
        this.midA = mAmnt;
        this.midP = mPrice;
        this.fullA = fAmnt;
        this.fullP = fPrice;
        this.name = name;
        this.inputComp = true;
        this.DBinput = Consts.DB.getInputSeller(name);
    }

    public CompanyThread(String name, JLabel numOfBushels, JLabel pricePerBushel, JLabel date) {
        this.inputComp = false;
        this.name = name;
        this.amnt = numOfBushels;
        this.price = pricePerBushel;
        this.dt = date;
        this.DBmarketingComp = Consts.DB.getMarketingComp(name);


    }

    @Override
    public void run() {
        //System.out.println(Consts.DB.getInputSeller(name, Consts.GAME_FLOW.currentYear));
        while (true) {
            if (inputComp) {
                try {
                    Consts.checkSetSoldOut(erlA, erlP, DBinput.getEarlyAmnt(), DBinput.getEarlyPrice());
                    Consts.checkSetSoldOut(midA, midP, DBinput.getMidAmnt(), DBinput.getMidPrice());
                    Consts.checkSetSoldOut(fullA, fullP, DBinput.getFullAmnt(), DBinput.getFullPrice());
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
