import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by Lorenzo on 10/22/2015.
 *
 */

@Embedded
public class FarmSector {
    FarmTypes farm;

    public FarmSector(char farmSize) {
        farm = new FarmTypes(farmSize);
    }

    public FarmSector() {
        farm = new FarmTypes();
    }

    boolean checkIfEmpty() {
        return farm.size == Consts.NO_FARM && farm.acres == 0;
    }

    public int getFarmAcres() {
        return farm.getAcres();
    }

    public char getFarmSize(){
        return farm.getSize();
    }

    public void setFarmSize(char size) {
        farm.setSize(size);
    }

    public double getFarmTtlCost(){
        return farm.getTotalCost();
    }

    public int getTTLFarmYld() {
        return farm.getTtlYield();
    }

    public int getSeedsNeeded() {
        return farm.getSeedsNeeded();
    }
}
