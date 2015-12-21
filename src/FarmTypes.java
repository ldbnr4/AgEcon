import org.jetbrains.annotations.Contract;
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
public final class FarmTypes {
    private char size;
    private int acres, ttlYield, ttlBushels, seedsNeeded, ttlSeedsOwned;
    private Double totalCost = (double) 0;
    private HashMap<String, Double> staticCosts = new HashMap<>();
    private HashMap<String, Consts.SeedStat> seedCosts = new HashMap<>();
    private ArrayList<BushelLegerEntry> bshlLedger = new ArrayList<>();
    private HashMap<Consts.Seed_Name, Integer> seedsOwned;
    //HashMap<>;

    public FarmTypes(char size) {
        setSize(size);
        setAcres(size);
        setStaticCosts();
        setTtlYield(0);
        setTtlBushels(ttlYield / 56);
        setSeedsNeeded(10 * acres);
        setTtlSeedsOwned(0);
        setSeedsOwned(new HashMap<Consts.Seed_Name, Integer>());
        setTotalCost();
    }

    public FarmTypes() {
        size = Consts.NO_FARM;
        acres = 0;
        setSeedsOwned(new HashMap<Consts.Seed_Name, Integer>());
        setTtlSeedsOwned(0);
    }

    public int getSeedsNeeded() {
        return seedsNeeded;
    }

    public void setSeedsNeeded(int seedsNeeded) {
        this.seedsNeeded = seedsNeeded;
    }

    public int getTtlYield() {
        return ttlYield;
    }

    public void setTtlYield(int ttlYield) {
        this.ttlYield = ttlYield;
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

    public ArrayList<BushelLegerEntry> getBshlLedger() {
        return this.bshlLedger;
    }

    public void setStaticCosts() {
        if (size == Consts.SMALL_FARM) {
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
            staticCosts.put("Land Rent", Consts.round((175 * Consts.INFLATION) * acres));
        } else if (size == Consts.MED_FARM) {
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
            staticCosts.put("Land Rent", Consts.round((175 * Consts.INFLATION * 0.96) * acres));
        } else {
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
            staticCosts.put("Land Rent", Consts.round((175 * Consts.INFLATION * 0.94) * acres));
        }
    }

    public void addSeedCost(String who, Consts.Seed_Name seedVar, int amount, double price) {
        seedCosts.put(who, new Consts.SeedStat(seedVar, amount, price, Consts.round(price * amount)));
    }

    public void setTotalCost() {
        totalCost = 0d;
        for (Double staticCost : staticCosts.values()) {
            totalCost += staticCost;
        }

        for (Consts.SeedStat seedStat : seedCosts.values()) {
            totalCost += seedStat.getTtlCst();
        }

        totalCost = Consts.round(totalCost);
    }

    public char getSize() {
        return size;
    }

    public void setSize(char size) {
        this.size = size;
        setAcres(size);
    }

    public int getAcres() {
        return acres;
    }

    public void setAcres(char size) {
        switch (size) {
            case Consts.SMALL_FARM:
                acres = 100;
                break;
            case Consts.MED_FARM:
                acres = 250;
                break;
            default:
                acres = 500;
                break;
        }
        setSeedsNeeded(10 * acres);
    }

    public Double getTotalCost() {
        return totalCost;
    }

    @Contract(pure = true)
    boolean checkIfEmpty() {
        return size == Consts.NO_FARM && acres == 0;
    }

    public HashMap<Consts.Seed_Name, Integer> getSeedsOwned() {
        return seedsOwned;
    }

    public void setSeedsOwned(HashMap<Consts.Seed_Name, Integer> seedsOwned) {
        if (seedsOwned.isEmpty()) {
            seedsOwned.put(Consts.Seed_Name.EARLY, 0);
            seedsOwned.put(Consts.Seed_Name.MID, 0);
            seedsOwned.put(Consts.Seed_Name.FULL, 0);
        }
        this.seedsOwned = seedsOwned;
    }

    public void updateSeedsOwned(int early, int mid, int full) {
        seedsOwned.put(Consts.Seed_Name.EARLY, seedsOwned.get(Consts.Seed_Name.EARLY) + early);
        seedsOwned.put(Consts.Seed_Name.MID, seedsOwned.get(Consts.Seed_Name.MID) + mid);
        seedsOwned.put(Consts.Seed_Name.FULL, seedsOwned.get(Consts.Seed_Name.FULL) + full);

        updateTtlSeedsOwned();
    }

    public int getTtlSeedsOwned() {
        return ttlSeedsOwned;
    }

    public void setTtlSeedsOwned(int ttlSeedsOwned) {
        this.ttlSeedsOwned = ttlSeedsOwned;
    }

    public void updateTtlSeedsOwned() {
        ttlSeedsOwned = 0;
        for (int amount : seedsOwned.values()) {
            ttlSeedsOwned += amount;
        }
    }

    public void plantAction() {
        double earlyPerc = Consts.round((double) seedsOwned.get(Consts.Seed_Name.EARLY) / getTtlSeedsOwned());
        double midPerc = Consts.round((double) seedsOwned.get(Consts.Seed_Name.MID) / getTtlSeedsOwned());
        double fullPerc = Consts.round((double) seedsOwned.get(Consts.Seed_Name.FULL) / getTtlSeedsOwned());
        if (ttlSeedsOwned > seedsNeeded) {
            ttlSeedsOwned = seedsNeeded;
            seedsOwned.put(Consts.Seed_Name.EARLY, (int) Consts.round(earlyPerc * ttlSeedsOwned));
            seedsOwned.put(Consts.Seed_Name.MID, (int) Consts.round(midPerc * ttlSeedsOwned));
            seedsOwned.put(Consts.Seed_Name.FULL, (int) Consts.round(fullPerc * ttlSeedsOwned));
        }
        double earlyAcres = Consts.round((double) seedsOwned.get(Consts.Seed_Name.EARLY) / 10);
        double midAcres = Consts.round((double) seedsOwned.get(Consts.Seed_Name.MID) / 10);
        double fullAcres = Consts.round((double) seedsOwned.get(Consts.Seed_Name.FULL) / 10);

        BushelLegerEntry earlyEntry = new BushelLegerEntry(Consts.getEarlyHarvDt(), (int) ceil(earlyAcres * Consts.ACRE_YIELD));
        if (!bshlLedger.contains(earlyEntry)) {
            this.bshlLedger.add(earlyEntry);
        }
        BushelLegerEntry midEntry = new BushelLegerEntry(Consts.getMidHarvDt(), (int) ceil((midAcres * Consts.ACRE_YIELD)));
        if (!bshlLedger.contains(midEntry)) {
            this.bshlLedger.add(midEntry);
        }
        BushelLegerEntry fullEntry = new BushelLegerEntry(Consts.getFullHarvDt(), (int) ceil((fullAcres * Consts.ACRE_YIELD)));
        if (!bshlLedger.contains(fullEntry)) {
            this.bshlLedger.add(fullEntry);
        }

        setTtlBushels((int) ceil(earlyAcres * Consts.ACRE_YIELD) + (int) ceil(midAcres * Consts.ACRE_YIELD) +
                (int) ceil(fullAcres * Consts.ACRE_YIELD));

    }

    public void addToLedger(BushelLegerEntry entry) {
        bshlLedger.add(entry);
        sort(bshlLedger);
    }

    public void removeFromLedger(BushelLegerEntry entry) {
        bshlLedger.remove(entry);
    }
}
