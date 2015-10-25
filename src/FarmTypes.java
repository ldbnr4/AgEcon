import java.util.HashMap;

/**
 * Created by Lorenzo on 10/22/2015.
 */
public class FarmTypes {
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
        if (size == GameDriver.SMALL_FARM) {
            acres = 100;
        } else if (size == GameDriver.MED_FARM) {
            acres = 250;
        } else {
            acres = 500;
        }
    }

    public void setCosts() {
        costs.put("Nitrogen", acres * 130 * 0.68 * 1.05);
        costs.put("Phosphate", acres * 50 * 0.9 * 1.05);
        costs.put("Potash", acres * 30 * 0.72 * 1.05);
        costs.put("Lime", acres * .25 * 14 * 1.05);
        costs.put("Pesticides", acres * 20 * 1.05);
        costs.put("Fuel", (double) (acres * 20));
        costs.put("Repairs", acres * 15.23 * 1.05);
        costs.put("Misc", acres * 9 * 1.05);
        costs.put("InterestOnOperCap", acres * 0.09 * 1.05);
        costs.put("OperLaborCharge", acres * 11 * 1.05 * 4);
        costs.put("Equipment", acres * 40 * 1.05);
        costs.put("LandRent", acres * 175 * 1.05);
        costs.put("GA", acres * .05 * (2.5 / 10) * 1.05 * yield);
        costs.put("R&D", acres * .1 * yield * (2.5 / 10) * 1.05);
        costs.put("Marketing", acres * yield * .2 * (2.5 / 10) * 1.05);

        for (Double cost : costs.values()) {
            totalCost += cost;
        }
    }
}
