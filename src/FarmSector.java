/**
 * Created by Lorenzo on 10/22/2015.
 */
public class FarmSector extends Sector {
    FarmTypes farm;

    public FarmSector(char farmSize) {
        super(GameDriver.FARM_SECTOR_NAME);
        updateSize(farmSize);
    }

    public FarmSector() {
        super(GameDriver.FARM_SECTOR_NAME);
        //farm = new FarmTypes();
    }

    @Override
    boolean checkIfEmpty() {
        return farm.size == GameDriver.NO_FARM && farm.acres == 0;
    }

    public void updateSize(char size) {
        farm = new FarmTypes(size);
    }


}
