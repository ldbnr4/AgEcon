/**
 * Created by Lorenzo on 10/22/2015.
 *
 */
public class FarmSector extends Sector {
    FarmTypes farm;

    public FarmSector(char farmSize) {
        super(Consts.FARM_SECTOR_NAME);
        farm = new FarmTypes(farmSize);
    }

    public FarmSector() {
        super(Consts.FARM_SECTOR_NAME);
        farm = new FarmTypes();
    }

    @Override
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

    public int getFarmYld(){
        return farm.getYield();
    }
}
