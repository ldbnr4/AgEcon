import java.util.HashMap;

/**
 * Created by Lorenzo on 10/13/2015.
 *
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

    public void setEarlySeeds(int earlySale, int earlyPrice) {
        seedTypes.setEarlySale(earlySale);
        seedTypes.setEarlyPrice(earlyPrice);
    }

    public int getEarlyAmnt(){
        return seedTypes.getEarlySale();
    }

    public void setMidSeeds(int midSale, int midPrice) {
        seedTypes.setMidSale(midSale);
        seedTypes.setMidPrice(midPrice);
    }

    public int getMidAmnt(){
        return seedTypes.getMidSale();
    }

    public void setFullSeeds(int fullSale, int fullPrice) {
        seedTypes.setFullSale(fullSale);
        seedTypes.setFullPrice(fullPrice);
    }

    public int getFullAmnt(){
        return seedTypes.getFullSale();
    }

    public int getTtlSeedAmnt(){
        return seedTypes.getTtlSeeds();
    }

    public HashMap<GameDriver.Seed_Name, Double> getVarietyMarketShare(){
        HashMap<GameDriver.Seed_Name, Integer> db_ttls = GameDriver.DB.getSeedTotals();
        HashMap<GameDriver.Seed_Name, Double> sect_shares = new HashMap<>();

        sect_shares.put(GameDriver.Seed_Name.EARLY, GameDriver.round(((double)getEarlyAmnt()/(double)db_ttls.get(GameDriver.Seed_Name.EARLY))*100));
        sect_shares.put(GameDriver.Seed_Name.MID, GameDriver.round(((double)getMidAmnt()/(double)db_ttls.get(GameDriver.Seed_Name.MID))*100));
        sect_shares.put(GameDriver.Seed_Name.FULL, GameDriver.round(((double)getFullAmnt()/(double)db_ttls.get(GameDriver.Seed_Name.FULL))*100));
        sect_shares.put(GameDriver.Seed_Name.TOTAL, GameDriver.round(((double)getTtlSeedAmnt()/(double)db_ttls.get(GameDriver.Seed_Name.TOTAL))*100));

        return sect_shares;
    }

    @Override
    public boolean checkIfEmpty() {
        return seedTypes.getEarlyPrice() == 0 && seedTypes.getEarlySale() == 0 && seedTypes.getMidPrice() == 0
                && seedTypes.getMidSale() == 0 && seedTypes.getFullPrice() == 0 && seedTypes.getFullSale() == 0;
    }
}
