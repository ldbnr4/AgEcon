/**
 * Created by Lorenzo on 10/22/2015.
 */
public class FarmTypes {
    public char size;
    public int acres;

    public FarmTypes(char size) {
        this.size = size;
        if (size == 'S') {
            acres = 100;
        } else if (size == 'M') {
            acres = 250;
        } else {
            acres = 500;
        }
    }

    public FarmTypes() {
        size = 'X';
        acres = 0;
    }
}
