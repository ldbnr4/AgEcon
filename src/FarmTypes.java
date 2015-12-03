import org.jetbrains.annotations.Contract;
import org.mongodb.morphia.annotations.Embedded;

import java.util.HashMap;

/**
 * Created by Lorenzo on 10/22/2015.
 *
 */

@Embedded
public final class FarmTypes {
    public char size;
    public int acres;
    public Double totalCost = (double) 0;
    HashMap<String, Double> costs = new HashMap<>();
    int acreYield = 3500;
    int ttlYield;
    double ttlBushels;
    int seedsNeeded;
    HashMap<Consts.Seed_Name, Integer> seedsOwned;
    int ttlSeedsOwned;

    public FarmTypes(char size) {
        setSize(size);
        setAcres(size);
        setCosts();
        setTtlYield(acreYield * acres);
        setTtlBushels(ttlYield / 56);
        setSeedsNeeded(10 * acres);
        setSeedsOwned(new HashMap<Consts.Seed_Name, Integer>());
        setTtlSeedsOwned(0);
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

    public void setTtlBushels(double ttlBushels) {
        this.ttlBushels = ttlBushels;
    }

    public HashMap<String, Double> getCosts() {
        return costs;
    }

    public void setCosts() {
        if (size == Consts.SMALL_FARM) {
            costs.put("Nitrogen", Consts.round((0.68 * Consts.INFLATION) * acres * 130));
            costs.put("Phosphate", Consts.round((0.9 * Consts.INFLATION) * acres * 50));
            costs.put("Potash", Consts.round((0.72 * Consts.INFLATION) * acres * 30));
            costs.put("Lime", Consts.round((14 * Consts.INFLATION) * acres * 0.25));
            costs.put("Pesticides", Consts.round((20 * Consts.INFLATION) * acres));
            costs.put("Fuel Utils", Consts.round((20 * Consts.INFLATION) * acres));
            costs.put("Repairs", Consts.round((15.23 * Consts.INFLATION) * acres));
            costs.put("Misc", Consts.round((9 * Consts.INFLATION) * acres));
            costs.put("Interest", Consts.round((0.09 * Consts.INFLATION) * acres));
            costs.put("Labor", Consts.round((11 * Consts.INFLATION) * acres * 4));
            costs.put("Equipment", Consts.round((40 * Consts.INFLATION) * acres));
            costs.put("Land Rent", Consts.round((175 * Consts.INFLATION) * acres));
        } else if (size == Consts.MED_FARM) {
            costs.put("Nitrogen", Consts.round((0.68 * Consts.INFLATION * 0.96) * acres * 130));
            costs.put("Phosphate", Consts.round((0.9 * Consts.INFLATION * 0.96) * acres * 50));
            costs.put("Potash", Consts.round((0.72 * Consts.INFLATION * 0.96) * acres * 30));
            costs.put("Lime", Consts.round((14 * Consts.INFLATION * 0.96) * acres * 0.25));
            costs.put("Pesticides", Consts.round((20 * Consts.INFLATION * 0.96) * acres));
            costs.put("Fuel Utils", Consts.round((20 * Consts.INFLATION * 0.96) * acres));
            costs.put("Repairs", Consts.round((15.23 * Consts.INFLATION * 0.96) * acres));
            costs.put("Misc", Consts.round((9 * Consts.INFLATION * 0.96) * acres));
            costs.put("Interest", Consts.round((0.09 * Consts.INFLATION * 0.96) * acres));
            costs.put("Labor", Consts.round((11 * Consts.INFLATION * 0.96) * acres * 4));
            costs.put("Equipment", Consts.round((40 * Consts.INFLATION * 0.96) * acres));
            costs.put("Land Rent", Consts.round((175 * Consts.INFLATION * 0.96) * acres));
        } else {
            costs.put("Nitrogen", Consts.round((0.68 * Consts.INFLATION * 0.94) * acres * 130));
            costs.put("Phosphate", Consts.round((0.9 * Consts.INFLATION * 0.94) * acres * 50));
            costs.put("Potash", Consts.round((0.72 * Consts.INFLATION * 0.94) * acres * 30));
            costs.put("Lime", Consts.round((14 * Consts.INFLATION * 0.94) * acres * 0.25));
            costs.put("Pesticides", Consts.round((20 * Consts.INFLATION * 0.94) * acres));
            costs.put("Fuel Utils", Consts.round((20 * Consts.INFLATION * 0.94) * acres));
            costs.put("Repairs", Consts.round((15.23 * Consts.INFLATION * 0.94) * acres));
            costs.put("Misc", Consts.round((9 * Consts.INFLATION * 0.94) * acres));
            costs.put("Interest", Consts.round((0.09 * Consts.INFLATION * 0.94) * acres));
            costs.put("Labor", Consts.round((11 * Consts.INFLATION * 0.94) * acres * 4));
            costs.put("Equipment", Consts.round((40 * Consts.INFLATION * 0.94) * acres));
            costs.put("Land Rent", Consts.round((175 * Consts.INFLATION * 0.94) * acres));
        }
        for (Double cost : costs.values()) {
            totalCost += cost;
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
}
