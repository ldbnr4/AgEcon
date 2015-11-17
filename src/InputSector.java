import java.util.HashMap;

/**
 * Created by Lorenzo on 10/13/2015.
 *
 */
public class InputSector extends Sector {
    public char size;
    SeedTypes seedTypes;

    public InputSector(SeedTypes seeds, char size) {
        super(Consts.INPUT_SECTOR_NAME);
        setSeedTypes(seeds);
        setSize(size);
    }

    public InputSector() {
        super(Consts.INPUT_SECTOR_NAME);
        this.seedTypes = new SeedTypes();
    }

    public void setSize(char size) {
        this.size = size;
    }

    public void setSeedTypes(SeedTypes seedTypes) {
        this.seedTypes = seedTypes;
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

    public HashMap<Consts.Seed_Name, Double> getVarietyMarketShare() {
        HashMap<Consts.Seed_Name, Integer> db_ttls = Consts.DB.getSeedTotals(Consts.GAME_FLOW.currentYear);
        HashMap<Consts.Seed_Name, Double> sect_shares = new HashMap<>();

        sect_shares.put(Consts.Seed_Name.EARLY, Consts.round(((double) getEarlyAmnt() / (double) db_ttls.get(Consts.Seed_Name.EARLY)) * 100));
        sect_shares.put(Consts.Seed_Name.MID, Consts.round(((double) getMidAmnt() / (double) db_ttls.get(Consts.Seed_Name.MID)) * 100));
        sect_shares.put(Consts.Seed_Name.FULL, Consts.round(((double) getFullAmnt() / (double) db_ttls.get(Consts.Seed_Name.FULL)) * 100));
        sect_shares.put(Consts.Seed_Name.TOTAL, Consts.round(((double) getTtlSeedAmnt() / (double) db_ttls.get(Consts.Seed_Name.TOTAL)) * 100));

        return sect_shares;
    }

    @Override
    public boolean checkIfEmpty() {
        return seedTypes.getEarlyPrice() == 0 && seedTypes.getEarlySale() == 0 && seedTypes.getMidPrice() == 0
                && seedTypes.getMidSale() == 0 && seedTypes.getFullPrice() == 0 && seedTypes.getFullSale() == 0;
    }
}
