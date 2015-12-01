import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by Lorenzo on 10/13/2015.
 *
 */

@Entity
public class InputSector {
    @Embedded
    SeedTypes seedTypes;
    @Id
    String name;
    int year;

    public InputSector() {
        setSeedTypes(new SeedTypes());
        setYear(Consts.GAME_FLOW.currentYear);
        setName("");
    }

    public InputSector(String name, int earlyAmnt, double earlyCost, int midAmnt, double midCost, int fullAmnt,
                       double fullCost) {
        setName(name);
        setYear(Consts.GAME_FLOW.currentYear);
        setSeedTypes(new SeedTypes(earlyAmnt, earlyCost, midAmnt, midCost, fullAmnt, fullCost));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeedTypes(SeedTypes seedTypes) {
        this.seedTypes = seedTypes;
    }

    public void setEarlySeeds(int earlySale, double earlyPrice) {
        seedTypes.setEarlySale(earlySale);
        seedTypes.setEarlyPrice(earlyPrice);
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getEarlyAmnt(){
        return seedTypes.getEarlySale();
    }

    public double getEarlyPrice() {
        return seedTypes.getEarlyPrice();
    }

    public double getMidPrice() {
        return seedTypes.getMidPrice();
    }

    public double getFullPrice() {
        return seedTypes.getFullPrice();
    }

    public void setMidSeeds(int midSale, double midPrice) {
        seedTypes.setMidSale(midSale);
        seedTypes.setMidPrice(midPrice);
    }

    public int getMidAmnt(){
        return seedTypes.getMidSale();
    }

    public void setFullSeeds(int fullSale, double fullPrice) {
        seedTypes.setFullSale(fullSale);
        seedTypes.setFullPrice(fullPrice);
    }

    public int getFullAmnt(){
        return seedTypes.getFullSale();
    }

    public int getTtlSeedAmnt(){
        return seedTypes.getTtlSeeds();
    }

    /*public HashMap<Consts.Seed_Name, Double> getVarietyMarketShare() {
        HashMap<Consts.Seed_Name, Integer> db_ttls = Consts.DB.getSeedTotals(Consts.GAME_FLOW.currentYear);
        HashMap<Consts.Seed_Name, Double> sect_shares = new HashMap<>();

        sect_shares.put(Consts.Seed_Name.EARLY, Consts.round(((double) getEarlyAmnt() / (double) db_ttls.get(Consts.Seed_Name.EARLY)) * 100));
        sect_shares.put(Consts.Seed_Name.MID, Consts.round(((double) getMidAmnt() / (double) db_ttls.get(Consts.Seed_Name.MID)) * 100));
        sect_shares.put(Consts.Seed_Name.FULL, Consts.round(((double) getFullAmnt() / (double) db_ttls.get(Consts.Seed_Name.FULL)) * 100));
        sect_shares.put(Consts.Seed_Name.TOTAL, Consts.round(((double) getTtlSeedAmnt() / (double) db_ttls.get(Consts.Seed_Name.TOTAL)) * 100));

        return sect_shares;
    }*/

    public boolean checkIfEmpty() {
        return seedTypes.getEarlyPrice() == 0 && seedTypes.getEarlySale() == 0 && seedTypes.getMidPrice() == 0
                && seedTypes.getMidSale() == 0 && seedTypes.getFullPrice() == 0 && seedTypes.getFullSale() == 0;
    }
}
