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
    int yield = 4000;

    public FarmTypes(char size) {
        setSize(size);
        setAcres();
        setCosts();
    }

    public FarmTypes() {
        size = GameDriver.NO_FARM;
        acres = 0;
    }

    public void setSize(char size) {
        this.size = size;
    }

    public void setAcres() {
        switch (size) {
            case GameDriver.SMALL_FARM:
                acres = 100;
                break;
            case GameDriver.MED_FARM:
                acres = 250;
                break;
            default:
                acres = 500;
                break;
        }
    }

    public void setCosts() {
        costs.put("Nitrogen", GameDriver.round(acres * 130 * 0.68 * 1.05));
        costs.put("Phosphate", GameDriver.round(acres * 50 * 0.9 * 1.05));
        costs.put("Potash", GameDriver.round(acres * 30 * 0.72 * 1.05));
        costs.put("Lime", GameDriver.round(acres * .25 * 14 * 1.05));
        costs.put("Pesticides", GameDriver.round(acres * 20 * 1.05));
        costs.put("Fuel", GameDriver.round((double) (acres * 20)));
        costs.put("Repairs", GameDriver.round(acres * 15.23 * 1.05));
        costs.put("Misc", GameDriver.round(acres * 9 * 1.05));
        costs.put("InterestOnOperCap", GameDriver.round(acres * 0.09 * 1.05));
        costs.put("OperLaborCharge", GameDriver.round(acres * 11 * 1.05 * 4));
        costs.put("Equipment", GameDriver.round(acres * 40 * 1.05));
        costs.put("LandRent", GameDriver.round(acres * 175 * 1.05));
        costs.put("GA", GameDriver.round(acres * .05 * (2.5 / 10) * 1.05 * yield));
        costs.put("R&D", GameDriver.round(acres * .1 * yield * (2.5 / 10) * 1.05));
        costs.put("Marketing", GameDriver.round(acres * yield * .2 * (2.5 / 10) * 1.05));

        for (Double cost : costs.values()) {
            totalCost += cost;
        }

        totalCost = GameDriver.round(totalCost);
    }
}
