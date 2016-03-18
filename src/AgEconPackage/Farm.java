/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static AgEconPackage.Consts.*;
import static AgEconPackage.Consts.Farm_Size.NO_FARM;
import static AgEconPackage.Consts.Seed_Type.*;
import static AgEconPackage.Consts.Student_Stage.Buy_Seeds;
import static AgEconPackage.Consts.Student_Stage.Select_Size;
import static java.lang.Math.ceil;
import static java.util.Collections.sort;

/**
 * Created by Lorenzo on 10/22/2015.
 *
 */

public class Farm extends Sector{
    private Farm_Size size;
    private Student_Stage stage;
    private int acres, ttlCwt, seedsNeeded, ttlSeedsOwned;
    private HashMap<String, Double> staticCosts;
    private HashMap<Seed_Type, Integer> seedsOwned;
    private ArrayList<SeedLedgerEntry> seedLedger;

    private ArrayList<HarvestEntry> yieldRecords;
    private ArrayList<BushelLedgerEntry> saleRecords;
    private boolean irrigated;

    public Farm(){}

    @Override
    public boolean isComplete() {
        List<Field> fields = Arrays.asList(getClass().getFields());
        fields.stream().forEach(field -> {
            try {
                Object o = field.get(this);
                System.out.println(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return false;
    }

    public Farm(Farm_Size size) {
        setSizeAcreSeedsNeedStage(size);
        ttlCwt = 0;
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

    public void setIrrigated(boolean flag){
        irrigated = flag;
    }

    public int getSeedsNeeded() {
        return seedsNeeded;
    }

    public HashMap<String, Double> getStaticCosts() {
        return staticCosts;
    }

    public void setStaticCosts(double acres) {
        double econOfScale;
        switch (size) {
            case SMALL_FARM:
                econOfScale = 1;
                break;

            case MED_FARM:
               econOfScale = 0.96;
                break;

            case LARGE_FARM:
                econOfScale = 0.94;
                break;

            default:
                econOfScale = 1;
                break;
        }

        staticCosts.put("Nitrogen", round((0.45 * INFLATION * econOfScale) * acres * 185));
        staticCosts.put("Phosphate", round((0.4 * INFLATION * econOfScale) * acres * 80));
        staticCosts.put("Potash", round((0.35 * INFLATION * econOfScale) * acres * 60));
        staticCosts.put("LimeStone", round((29 * INFLATION * econOfScale) * acres * 0.5));
        staticCosts.put("Insecticide", round((15 * INFLATION * econOfScale) * acres));
        staticCosts.put("Fungicide", round((15 * INFLATION * econOfScale) * acres));
        staticCosts.put("Machinery Fuel", round((17 * INFLATION * econOfScale) * acres));
        staticCosts.put("Machinery Repairs", round((17 * INFLATION * econOfScale) * acres));
        staticCosts.put("Miscellaneous Overhead", round((12 * INFLATION * econOfScale) * acres));
        staticCosts.put("Interest", round((0.09 * INFLATION * econOfScale) * acres));
        staticCosts.put("Labor", round((35 * INFLATION * econOfScale) * acres));
        staticCosts.put("Equipment", round((40 * INFLATION * econOfScale) * acres));
        staticCosts.put("Hauling & Transportation", round((5000 * INFLATION * econOfScale) * acres * 0.0015));
        staticCosts.put("Custom Application", round((6.50 * INFLATION * econOfScale) * acres));
        staticCosts.put("Crop Insurance", round((10 * INFLATION * econOfScale) * acres));

        if (irrigated){
            staticCosts.put("Irrigation Fuel", round((12 * INFLATION * econOfScale) * acres * 2));
            staticCosts.put("Irrigation Repairs", round((15 * INFLATION * econOfScale) * acres));
            staticCosts.put("Irrigation Labor", round((4 * INFLATION * econOfScale) * acres));
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

    public HashMap<Seed_Type, Integer> getSeedsOwned() {
        return seedsOwned;
    }

    private void updateSeedsOwned(Seed_Type type, int amnt) {
        seedsOwned.put(type, seedsOwned.get(type) + amnt);

        updateTtlSeedsOwned();
    }

    public int getTtlSeedsOwned() {
        return ttlSeedsOwned;
    }

    private void updateTtlSeedsOwned() {
        ttlSeedsOwned = 0;
        for (int amount : seedsOwned.values()) {
            ttlSeedsOwned += amount;
        }
    }

    public void plantAction() {
        if (ttlSeedsOwned > seedsNeeded) {
            double earlyPerc = round((double) seedsOwned.get(EARLY) / ttlSeedsOwned);
            double midPerc = round((double) seedsOwned.get(MID) / ttlSeedsOwned);
            double fullPerc = round((double) seedsOwned.get(FULL) / ttlSeedsOwned);
            ttlSeedsOwned = seedsNeeded;
            seedsOwned.put(EARLY, (int) round(earlyPerc * ttlSeedsOwned));
            seedsOwned.put(MID, (int) round(midPerc * ttlSeedsOwned));
            seedsOwned.put(FULL, (int) round(fullPerc * ttlSeedsOwned));
        }
        double earlyAcres = round((double) seedsOwned.get(EARLY) / 10);
        double midAcres = round((double) seedsOwned.get(MID) / 10);
        double fullAcres = round((double) seedsOwned.get(FULL) / 10);

        setStaticCosts(earlyAcres + midAcres + fullAcres);

        double earlyYield = earlyAcres * ACRE_YIELD;
        if(irrigated){
            earlyYield = earlyAcres * I_ACRE_YIELD;
        }
        HarvestEntry earlyEntry = new HarvestEntry(getEarlyHarvDt(), (int) ceil(earlyYield));
        if (!yieldRecords.contains(earlyEntry)) {
            addToYieldRecord(earlyEntry);
        }

        double midYield = midAcres * ACRE_YIELD * 0.9;
        if(irrigated){
            midYield = midAcres * I_ACRE_YIELD * 0.9;
        }

        HarvestEntry midEntry = new HarvestEntry(getMidHarvDt(), (int) ceil(midYield));
        if (!yieldRecords.contains(midEntry)) {
            addToYieldRecord(midEntry);
        }

        double fullYield = fullAcres * ACRE_YIELD * 0.75;
        if(irrigated){
            fullYield = fullAcres * I_ACRE_YIELD * 0.75;
        }
        HarvestEntry fullEntry = new HarvestEntry(getFullHarvDt(), (int) ceil(fullYield));
        if (!yieldRecords.contains(fullEntry)) {
            addToYieldRecord(fullEntry);
        }

        ttlCwt = (int) ceil(earlyYield + midYield + fullYield);

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
        updateSeedsOwned(entry.getSeedType(), entry.getAmount());
        seedLedger.add(entry);
    }

    public ArrayList<SeedLedgerEntry> getSeedLedger() {
        return seedLedger;
    }

}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */