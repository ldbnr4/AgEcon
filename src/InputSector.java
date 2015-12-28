import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by Lorenzo on 10/13/2015.
 *
 */

@Entity
public class InputSector {
    @Id
    private String name;
    private int earlyAmnt = 0, midAmnt = 0, fullAmnt = 0;
    private double earlyPrice = 0, midPrice = 0, fullPrice = 0;

    public InputSector() {}

    public InputSector(String name, int earlyAmnt, double earlyCost, int midAmnt, double midCost, int fullAmnt,
                       double fullCost) {
        setName(name);
        updateEarlyAmnt(earlyAmnt);
        setEarlyPrice(earlyCost);
        updateMidAmnt(midAmnt);
        setMidPrice(midCost);
        updateFullAmnt(fullAmnt);
        setFullPrice(fullCost);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEarlyAmnt() {
        return earlyAmnt;
    }

    public boolean updateEarlyAmnt(int earlyAmnt) {
        if(-earlyAmnt > this.earlyAmnt){
            return false;
        }
        this.earlyAmnt += earlyAmnt;
        Consts.DB.saveInput(this);
        return true;
    }

    public int getMidAmnt() {
        return midAmnt;
    }

    public boolean updateMidAmnt(int midAmnt) {
        if(-midAmnt > this.midAmnt){
            return false;
        }
        this.midAmnt += midAmnt;
        Consts.DB.saveInput(this);
        return true;
    }

    public int getFullAmnt() {
        return fullAmnt;
    }

    public boolean updateFullAmnt(int fullAmnt) {
        if(-fullAmnt > this.fullAmnt){
            return false;
        }
        this.fullAmnt += fullAmnt;
        Consts.DB.saveInput(this);
        return true;
    }

    public double getEarlyPrice() {
        return earlyPrice;
    }

    public void setEarlyPrice(double earlyPrice) {
        this.earlyPrice = earlyPrice;
    }

    public double getMidPrice() {
        return midPrice;
    }

    public void setMidPrice(double midPrice) {
        this.midPrice = midPrice;
    }

    public double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(double fullPrice) {
        this.fullPrice = fullPrice;
    }
}
