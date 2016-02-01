package AgEgonPackage;

import java.util.ArrayList;
import java.util.HashMap;

import static AgEgonPackage.Consts.*;
import static AgEgonPackage.Consts.Farm_Size.NO_FARM;
import static AgEgonPackage.Consts.Seed_Type.*;
import static AgEgonPackage.Consts.Student_Stage.Buy_Seeds;
import static AgEgonPackage.Consts.Student_Stage.Select_Size;
import static java.lang.Math.ceil;
import static java.util.Collections.sort;

/**
 * Created by Lorenzo on 10/22/2015.
 *
 */

public class Farm {
    private Farm_Size size;
    private Student_Stage stage;
    private int acres, ttlBushels, seedsNeeded, ttlSeedsOwned;
    private HashMap<String, Double> staticCosts;
    private HashMap<Seed_Type, Integer> seedsOwned;
    private ArrayList<SeedLedgerEntry> seedLedger;

    private ArrayList<HarvestEntry> yieldRecords;
    private ArrayList<BushelLedgerEntry> saleRecords;

    public Farm(Farm_Size size) {
        setSizeAcreSeedsNeedStage(size);
        ttlBushels = 0;
        ttlSeedsOwned = 0;
        staticCosts = new HashMap<>();
        seedsOwned = new HashMap<Seed_Type, Integer>() {{
            put(EARLY, 0);
            put(MID, 0);
            put(FULL, 0);
        }};
        seedLedger = new ArrayList<>();
        yieldRecords = new ArrayList<>();
        saleRecords = new ArrayList<>();
    }

    public int getSeedsNeeded() {
        return seedsNeeded;
    }

    public HashMap<String, Double> getStaticCosts() {
        return staticCosts;
    }

    public void setStaticCosts(double acres) {
        switch (size) {
            case SMALL_FARM:
                staticCosts.put("Nitrogen", round((0.68 * INFLATION) * acres * 130));
                staticCosts.put("Phosphate", round((0.9 * INFLATION) * acres * 50));
                staticCosts.put("Potash", round((0.72 * INFLATION) * acres * 30));
                staticCosts.put("Lime", round((14 * INFLATION) * acres * 0.25));
                staticCosts.put("Pesticides", round((20 * INFLATION) * acres));
                staticCosts.put("Fuel Utils", round((20 * INFLATION) * acres));
                staticCosts.put("Repairs", round((15.23 * INFLATION) * acres));
                staticCosts.put("Misc", round((9 * INFLATION) * acres));
                staticCosts.put("Interest", round((0.09 * INFLATION) * acres));
                staticCosts.put("Labor", round((11 * INFLATION) * acres * 4));
                staticCosts.put("Equipment", round((40 * INFLATION) * acres));
                break;

            case MED_FARM:
                staticCosts.put("Nitrogen", round((0.68 * INFLATION * 0.96) * acres * 130));
                staticCosts.put("Phosphate", round((0.9 * INFLATION * 0.96) * acres * 50));
                staticCosts.put("Potash", round((0.72 * INFLATION * 0.96) * acres * 30));
                staticCosts.put("Lime", round((14 * INFLATION * 0.96) * acres * 0.25));
                staticCosts.put("Pesticides", round((20 * INFLATION * 0.96) * acres));
                staticCosts.put("Fuel Utils", round((20 * INFLATION * 0.96) * acres));
                staticCosts.put("Repairs", round((15.23 * INFLATION * 0.96) * acres));
                staticCosts.put("Misc", round((9 * INFLATION * 0.96) * acres));
                staticCosts.put("Interest", round((0.09 * INFLATION * 0.96) * acres));
                staticCosts.put("Labor", round((11 * INFLATION * 0.96) * acres * 4));
                staticCosts.put("Equipment", round((40 * INFLATION * 0.96) * acres));
                break;
            case LARGE_FARM:
                staticCosts.put("Nitrogen", round((0.68 * INFLATION * 0.94) * acres * 130));
                staticCosts.put("Phosphate", round((0.9 * INFLATION * 0.94) * acres * 50));
                staticCosts.put("Potash", round((0.72 * INFLATION * 0.94) * acres * 30));
                staticCosts.put("Lime", round((14 * INFLATION * 0.94) * acres * 0.25));
                staticCosts.put("Pesticides", round((20 * INFLATION * 0.94) * acres));
                staticCosts.put("Fuel Utils", round((20 * INFLATION * 0.94) * acres));
                staticCosts.put("Repairs", round((15.23 * INFLATION * 0.94) * acres));
                staticCosts.put("Misc", round((9 * INFLATION * 0.94) * acres));
                staticCosts.put("Interest", round((0.09 * INFLATION * 0.94) * acres));
                staticCosts.put("Labor", round((11 * INFLATION * 0.94) * acres * 4));
                staticCosts.put("Equipment", round((40 * INFLATION * 0.94) * acres));
                break;
        }
    }

    public Student_Stage getStage() {
        return stage;
    }

    public void setStage(Student_Stage stage) {
        this.stage = stage;
    }

    public Farm_Size getSize(){
        return size;
    }

    public ArrayList<BushelLedgerEntry> getSaleRecords() {
        return saleRecords;
    }

    public ArrayList<HarvestEntry> getYieldRecords() {
        return yieldRecords;
    }

    public void setYieldRecords(ArrayList<HarvestEntry> yieldRecords) {
        this.yieldRecords = yieldRecords;
    }

    public void setSizeAcreSeedsNeedStage(Farm_Size size) {
        this.size = size;
        setAcres(size);
        if (size.equals(NO_FARM)) stage = Select_Size;
        else stage = Buy_Seeds;
    }

    public int getAcres() {
        return acres;
    }

    public void setAcres(Farm_Size size) {
        switch (size) {
            case SMALL_FARM:
                acres = S_ACRE;
                break;
            case MED_FARM:
                acres = M_ACRE;
                break;
            case LARGE_FARM:
                acres = L_ACRE;
                break;
            case NO_FARM:
                acres = 0;
        }
        seedsNeeded = 10 * acres;
    }

    public HashMap<Consts.Seed_Type, Integer> getSeedsOwned() {
        return seedsOwned;
    }

    public void updateSeedsOwned(Seed_Type type, int amnt) {
        seedsOwned.put(type, seedsOwned.get(type) + amnt);

        updateTtlSeedsOwned();
    }

    public int getTtlSeedsOwned() {
        return ttlSeedsOwned;
    }

    public void updateTtlSeedsOwned() {
        ttlSeedsOwned = 0;
        for (int amount : seedsOwned.values()) {
            ttlSeedsOwned += amount;
        }
    }

    public void plantAction() {
        double earlyPerc = round((double) seedsOwned.get(EARLY) / getTtlSeedsOwned());
        double midPerc = round((double) seedsOwned.get(MID) / getTtlSeedsOwned());
        double fullPerc = round((double) seedsOwned.get(FULL) / getTtlSeedsOwned());
        if (ttlSeedsOwned > seedsNeeded) {
            ttlSeedsOwned = seedsNeeded;
            seedsOwned.put(EARLY, (int) round(earlyPerc * ttlSeedsOwned));
            seedsOwned.put(MID, (int) round(midPerc * ttlSeedsOwned));
            seedsOwned.put(FULL, (int) round(fullPerc * ttlSeedsOwned));
        }
        double earlyAcres = round((double) seedsOwned.get(EARLY) / 10);
        double midAcres = round((double) seedsOwned.get(MID) / 10);
        double fullAcres = round((double) seedsOwned.get(FULL) / 10);

        setStaticCosts(earlyAcres + midAcres + fullAcres);

        HarvestEntry earlyEntry = new HarvestEntry(getEarlyHarvDt(), (int) ceil(earlyAcres * ACRE_YIELD));
        if (!yieldRecords.contains(earlyEntry)) {
            addToYieldRecord(earlyEntry);
        }
        HarvestEntry midEntry = new HarvestEntry(getMidHarvDt(), (int) ceil((midAcres * ACRE_YIELD)));
        if (!yieldRecords.contains(midEntry)) {
            addToYieldRecord(midEntry);
        }
        HarvestEntry fullEntry = new HarvestEntry(getFullHarvDt(), (int) ceil((fullAcres * ACRE_YIELD)));
        if (!yieldRecords.contains(fullEntry)) {
            addToYieldRecord(fullEntry);
        }

        ttlBushels = (int) ceil(earlyAcres * ACRE_YIELD) + (int) ceil(midAcres * ACRE_YIELD) +
                (int) ceil(fullAcres * ACRE_YIELD);

    }

    public void addToSaleRecords(BushelLedgerEntry entry) {
        saleRecords.add(entry);
        sort(saleRecords);
    }

    public void addToYieldRecord(HarvestEntry entry) {
        yieldRecords.add(entry);
        sort(yieldRecords);
    }

    public void removeFromYieldRecord(HarvestEntry entry) {
        yieldRecords.remove(entry);
    }

    public void addToSeedLedger(SeedLedgerEntry entry){
        seedLedger.add(entry);
        //updateSeedsOwned();
    }

    public ArrayList<SeedLedgerEntry> getSeedLedger() {
        return seedLedger;
    }
}