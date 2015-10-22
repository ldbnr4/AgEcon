/**
 * Created by Lorenzo on 10/13/2015.
 */
public class InputSector extends Sector {
    SeedTypes seedTypes;

    public InputSector(int earlySale, int earlyPrice, int midSale, int midPrice, int fullSale, int fullPrice) {
        super(GameDriver.INPUT_SECTOR_NAME);
        this.seedTypes = new SeedTypes(earlySale, earlyPrice, midSale, midPrice, fullSale, fullPrice);
    }

    public InputSector() {
        super(GameDriver.INPUT_SECTOR_NAME);
        this.seedTypes = new SeedTypes();
    }

    public void updateEarly(int earlySale, int earlyPrice) {
        seedTypes.setEarlySale(earlySale);
        seedTypes.setEarlyPrice(earlyPrice);
    }

    public void updateMid(int midSale, int midPrice) {
        seedTypes.setMidSale(midSale);
        seedTypes.setMidPrice(midPrice);
    }

    public void updateFull(int fullSale, int fullPrice) {
        seedTypes.setFullSale(fullSale);
        seedTypes.setFullPrice(fullPrice);
    }

    public boolean checkIfEmpty() {
        return seedTypes.getEarlyPrice() == 0 && seedTypes.getEarlySale() == 0 && seedTypes.getMidPrice() == 0
                && seedTypes.getMidSale() == 0 && seedTypes.getFullPrice() == 0 && seedTypes.getFullSale() == 0;
    }
}
