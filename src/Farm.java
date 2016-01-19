import org.mongodb.morphia.annotations.Embedded;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.ceil;
import static java.util.Collections.sort;

/**
 * Created by Lorenzo on 10/22/2015.
 *
 */

@Embedded
public final class Farm {
    private Consts.Farm_Size size;
    private int acres, ttlBushels, seedsNeeded, ttlSeedsOwned;
    private HashMap<String, Double> staticCosts = new HashMap<>();
    private ArrayList<BushelLedgerEntry> bshlLedger = new ArrayList<>();
    private HashMap<Consts.Seed_Type, Integer> seedsOwned;
    private ArrayList<SeedLedgerEntry> seedLedger = new ArrayList<>();

    public Farm(Consts.Farm_Size size) {
        setSize(size);
        setAcres(size);
        setStaticCosts();
        setTtlBushels(0);
        setSeedsNeeded(10 * acres);
        setTtlSeedsOwned();
        setSeedsOwned(new HashMap<>());
    }

    public Farm() {
        size = Consts.Farm_Size.NO_FARM;
        acres = 0;
        setSeedsOwned(new HashMap<>());
        setTtlSeedsOwned();
    }

    public int getSeedsNeeded() {
        return seedsNeeded;
    }

    public void setSeedsNeeded(int seedsNeeded) {
        this.seedsNeeded = seedsNeeded;
    }

    public double getTtlBushels() {
        return ttlBushels;
    }

    public void setTtlBushels(int ttlBushels) {
        this.ttlBushels = ttlBushels;
    }

    public HashMap<String, Double> getStaticCosts() {
        return staticCosts;
    }

    public ArrayList<BushelLedgerEntry> getBshlLedger() {
        return this.bshlLedger;
    }

    public void setStaticCosts() {
        switch (size) {
            case SMALL_FARM:
                staticCosts.put("Nitrogen", Consts.round((0.68 * Consts.INFLATION) * acres * 130));
                staticCosts.put("Phosphate", Consts.round((0.9 * Consts.INFLATION) * acres * 50));
                staticCosts.put("Potash", Consts.round((0.72 * Consts.INFLATION) * acres * 30));
                staticCosts.put("Lime", Consts.round((14 * Consts.INFLATION) * acres * 0.25));
                staticCosts.put("Pesticides", Consts.round((20 * Consts.INFLATION) * acres));
                staticCosts.put("Fuel Utils", Consts.round((20 * Consts.INFLATION) * acres));
                staticCosts.put("Repairs", Consts.round((15.23 * Consts.INFLATION) * acres));
                staticCosts.put("Misc", Consts.round((9 * Consts.INFLATION) * acres));
                staticCosts.put("Interest", Consts.round((0.09 * Consts.INFLATION) * acres));
                staticCosts.put("Labor", Consts.round((11 * Consts.INFLATION) * acres * 4));
                staticCosts.put("Equipment", Consts.round((40 * Consts.INFLATION) * acres));
                break;

            case MED_FARM:
                staticCosts.put("Nitrogen", Consts.round((0.68 * Consts.INFLATION * 0.96) * acres * 130));
                staticCosts.put("Phosphate", Consts.round((0.9 * Consts.INFLATION * 0.96) * acres * 50));
                staticCosts.put("Potash", Consts.round((0.72 * Consts.INFLATION * 0.96) * acres * 30));
                staticCosts.put("Lime", Consts.round((14 * Consts.INFLATION * 0.96) * acres * 0.25));
                staticCosts.put("Pesticides", Consts.round((20 * Consts.INFLATION * 0.96) * acres));
                staticCosts.put("Fuel Utils", Consts.round((20 * Consts.INFLATION * 0.96) * acres));
                staticCosts.put("Repairs", Consts.round((15.23 * Consts.INFLATION * 0.96) * acres));
                staticCosts.put("Misc", Consts.round((9 * Consts.INFLATION * 0.96) * acres));
                staticCosts.put("Interest", Consts.round((0.09 * Consts.INFLATION * 0.96) * acres));
                staticCosts.put("Labor", Consts.round((11 * Consts.INFLATION * 0.96) * acres * 4));
                staticCosts.put("Equipment", Consts.round((40 * Consts.INFLATION * 0.96) * acres));
                break;
            case LARGE_FARM:
                staticCosts.put("Nitrogen", Consts.round((0.68 * Consts.INFLATION * 0.94) * acres * 130));
                staticCosts.put("Phosphate", Consts.round((0.9 * Consts.INFLATION * 0.94) * acres * 50));
                staticCosts.put("Potash", Consts.round((0.72 * Consts.INFLATION * 0.94) * acres * 30));
                staticCosts.put("Lime", Consts.round((14 * Consts.INFLATION * 0.94) * acres * 0.25));
                staticCosts.put("Pesticides", Consts.round((20 * Consts.INFLATION * 0.94) * acres));
                staticCosts.put("Fuel Utils", Consts.round((20 * Consts.INFLATION * 0.94) * acres));
                staticCosts.put("Repairs", Consts.round((15.23 * Consts.INFLATION * 0.94) * acres));
                staticCosts.put("Misc", Consts.round((9 * Consts.INFLATION * 0.94) * acres));
                staticCosts.put("Interest", Consts.round((0.09 * Consts.INFLATION * 0.94) * acres));
                staticCosts.put("Labor", Consts.round((11 * Consts.INFLATION * 0.94) * acres * 4));
                staticCosts.put("Equipment", Consts.round((40 * Consts.INFLATION * 0.94) * acres));
                break;
        }
    }

    public Consts.Farm_Size getSize() {
        return size;
    }

    public void setSize(Consts.Farm_Size size) {
        this.size = size;
        setAcres(size);
    }

    public int getAcres() {
        return acres;
    }

    public void setAcres(Consts.Farm_Size size) {
        switch (size) {
            case SMALL_FARM:
                acres = Consts.S_ACRE;
                break;
            case MED_FARM:
                acres = Consts.M_ACRE;
                break;
            case LARGE_FARM:
                acres = Consts.L_ACRE;
                break;
        }
        setSeedsNeeded(10 * acres);
    }

    public HashMap<Consts.Seed_Type, Integer> getSeedsOwned() {
        return seedsOwned;
    }

    public void setSeedsOwned(HashMap<Consts.Seed_Type, Integer> seedsOwned) {
        if (seedsOwned.isEmpty()) {
            seedsOwned.put(Consts.Seed_Type.EARLY, 0);
            seedsOwned.put(Consts.Seed_Type.MID, 0);
            seedsOwned.put(Consts.Seed_Type.FULL, 0);
        }
        this.seedsOwned = seedsOwned;
    }

    public void updateSeedsOwned(int early, int mid, int full) {
        seedsOwned.put(Consts.Seed_Type.EARLY, seedsOwned.get(Consts.Seed_Type.EARLY) + early);
        seedsOwned.put(Consts.Seed_Type.MID, seedsOwned.get(Consts.Seed_Type.MID) + mid);
        seedsOwned.put(Consts.Seed_Type.FULL, seedsOwned.get(Consts.Seed_Type.FULL) + full);

        updateTtlSeedsOwned();
    }

    public int getTtlSeedsOwned() {
        return ttlSeedsOwned;
    }

    public void setTtlSeedsOwned() {
        this.ttlSeedsOwned = 0;
    }

    public void updateTtlSeedsOwned() {
        ttlSeedsOwned = 0;
        for (int amount : seedsOwned.values()) {
            ttlSeedsOwned += amount;
        }
    }

    public void plantAction() {
        double earlyPerc = Consts.round((double) seedsOwned.get(Consts.Seed_Type.EARLY) / getTtlSeedsOwned());
        double midPerc = Consts.round((double) seedsOwned.get(Consts.Seed_Type.MID) / getTtlSeedsOwned());
        double fullPerc = Consts.round((double) seedsOwned.get(Consts.Seed_Type.FULL) / getTtlSeedsOwned());
        if (ttlSeedsOwned > seedsNeeded) {
            ttlSeedsOwned = seedsNeeded;
            seedsOwned.put(Consts.Seed_Type.EARLY, (int) Consts.round(earlyPerc * ttlSeedsOwned));
            seedsOwned.put(Consts.Seed_Type.MID, (int) Consts.round(midPerc * ttlSeedsOwned));
            seedsOwned.put(Consts.Seed_Type.FULL, (int) Consts.round(fullPerc * ttlSeedsOwned));
        }
        double earlyAcres = Consts.round((double) seedsOwned.get(Consts.Seed_Type.EARLY) / 10);
        double midAcres = Consts.round((double) seedsOwned.get(Consts.Seed_Type.MID) / 10);
        double fullAcres = Consts.round((double) seedsOwned.get(Consts.Seed_Type.FULL) / 10);

        BushelLedgerEntry earlyEntry = new BushelLedgerEntry(Consts.getEarlyHarvDt(), (int) ceil(earlyAcres * Consts.ACRE_YIELD), 0, null);
        if (!bshlLedger.contains(earlyEntry)) {
            this.bshlLedger.add(earlyEntry);
        }
        BushelLedgerEntry midEntry = new BushelLedgerEntry(Consts.getMidHarvDt(), (int) ceil((midAcres * Consts.ACRE_YIELD)), 0, null);
        if (!bshlLedger.contains(midEntry)) {
            this.bshlLedger.add(midEntry);
        }
        BushelLedgerEntry fullEntry = new BushelLedgerEntry(Consts.getFullHarvDt(), (int) ceil((fullAcres * Consts.ACRE_YIELD)), 0, null);
        if (!bshlLedger.contains(fullEntry)) {
            this.bshlLedger.add(fullEntry);
        }

        setTtlBushels((int) ceil(earlyAcres * Consts.ACRE_YIELD) + (int) ceil(midAcres * Consts.ACRE_YIELD) +
                (int) ceil(fullAcres * Consts.ACRE_YIELD));

    }

    public void addToBshlLedger(BushelLedgerEntry entry) {
        bshlLedger.add(entry);
        //noinspection unchecked
        sort(bshlLedger);
    }

    public void removeFromBshlLedger(BushelLedgerEntry entry) {
        bshlLedger.remove(entry);
    }

    public void addToSeedLedger(SeedLedgerEntry entry){
        seedLedger.add(entry);
    }

    public ArrayList<SeedLedgerEntry> getSeedLedger() {
        return seedLedger;
    }
}
