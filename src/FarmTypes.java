/**
 * Created by Lorenzo on 10/22/2015.
 */
public class FarmTypes {
    public char size;
    public int acres;

    public FarmTypes(char size) {
        setSize(size);
    }

    public FarmTypes() {
        size = GameDriver.NO_FARM;
        acres = 0;
    }

    public void setSize(char size) {
        this.size = size;
        if (size == GameDriver.SMALL_FARM) {
            acres = 100;
        } else if (size == GameDriver.MED_FARM) {
            acres = 250;
        } else {
            acres = 500;
        }
    }
}
