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

    public void updateSize(char size) {
        farm = new FarmTypes(size);
    }
}
