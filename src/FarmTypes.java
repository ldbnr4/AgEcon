import java.util.HashMap;

/**
 * Created by Lorenzo on 10/22/2015.
 *
 */
public final class FarmTypes {
    public char size;
    public int acres;
    public Double totalCost = (double) 0;
    HashMap<String, Double> costs = new HashMap<>();
    int acreYield = 3990;
    int ttlYield = acres * acreYield;
    double bushels = acreYield / 56;
    double ttlBushels = ttlYield / 56;
    int lostYield = 10 * acres;

    public FarmTypes(char size) {
        setSize(size);
        setAcres();
        setCosts();
    }

    public FarmTypes() {
        size = Consts.NO_FARM;
        acres = 0;
    }

    public void setAcres() {
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
    }

    public void setCosts() {
        if (size == Consts.SMALL_FARM) {
            costs.put("Chemicals", Consts.round((0.68 * Consts.INFLATION) * acres * 10));
            costs.put("Labor", Consts.round((0.9 * Consts.INFLATION) * acres * 10));
            costs.put("FuelUtils", Consts.round((0.72 * Consts.INFLATION) * acres * 10));
            costs.put("Cap Equipmnt", Consts.round((14 * Consts.INFLATION) * acres * 10));
            costs.put("GA", Consts.round((double) (acres * 20)));
            costs.put("R&D", Consts.round(acres * .1 * ttlYield * (2.5 / 10) * 1.05));
            costs.put("Marketing", Consts.round(acres * ttlYield * .2 * (2.5 / 10) * 1.05));
        } else if (size == Consts.MED_FARM) {
            costs.put("Chemicals", Consts.round((0.68 * Consts.INFLATION * 0.96) * acres * 10));
            costs.put("Labor", Consts.round((0.9 * Consts.INFLATION * 0.96) * acres * 10));
            costs.put("FuelUtils", Consts.round((0.72 * Consts.INFLATION * 0.96) * acres * 10));
            costs.put("Cap Equipmnt", Consts.round((14 * Consts.INFLATION * 0.96) * acres * 10));
            costs.put("GA", Consts.round((double) (acres * 20)));
            costs.put("R&D", Consts.round(acres * .1 * ttlYield * (2.5 / 10) * 1.05));
            costs.put("Marketing", Consts.round(acres * ttlYield * .2 * (2.5 / 10) * 1.05));
        } else {
            costs.put("Chemicals", Consts.round((0.68 * Consts.INFLATION * 0.94) * acres * 10));
            costs.put("Labor", Consts.round((0.9 * Consts.INFLATION * 0.94) * acres * 10));
            costs.put("FuelUtils", Consts.round((0.72 * Consts.INFLATION * 0.94) * acres * 10));
            costs.put("Cap Equipmnt", Consts.round((14 * Consts.INFLATION * 0.94) * acres * 10));
            costs.put("GA", Consts.round((double) (acres * 20)));
            costs.put("R&D", Consts.round(acres * .1 * ttlYield * (2.5 / 10) * 1.05));
            costs.put("Marketing", Consts.round(acres * ttlYield * .2 * (2.5 / 10) * 1.05));
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
    }

    public int getAcres() {
        return acres;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public int getYield() {
        return ttlYield;
    }
}
