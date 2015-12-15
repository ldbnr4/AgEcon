import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lorenzo on 10/13/2015.
 *
 */
public class SeedTypes {
    int earlySale, midSale, ttlSeeds, fullSale;
    double earlyPrice, midPrice, fullPrice;
    Date earlyHarvDt = null, midHarvDt = null, fullHarvDt = null;

    public SeedTypes() {
        this.earlySale = 0;
        this.earlyPrice = 0;
        this.midSale = 0;
        this.midPrice = 0;
        this.fullSale = 0;
        this.fullPrice = 0;
        setTtlSeeds();
    }

    public SeedTypes(int earlySale, double earlyPrice, int midSale, double midPrice, int fullSale, double fullPrice) {
        this.earlySale = earlySale;
        this.earlyPrice = earlyPrice;
        this.midSale = midSale;
        this.midPrice = midPrice;
        this.fullSale = fullSale;
        this.fullPrice = fullPrice;
        setTtlSeeds();
    }

    public void setAllHarvDts() {
        setEarlyHarvDt();
        setMidHarvDt();
        setFullHarvDt();
    }

    public void setEarlyHarvDt() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 100);
        earlyHarvDt = cal.getTime();
    }

    public Date getEarlyHarvDt() {
        return earlyHarvDt;
    }

    public void setMidHarvDt() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 107);
        midHarvDt = cal.getTime();
    }

    public Date getMidHarvDt() {
        return midHarvDt;
    }

    public void setFullHarvDt() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 112);
        fullHarvDt = cal.getTime();
    }

    public Date getFullHarvDt() {
        return fullHarvDt;
    }

    public int getEarlySale() {
        return earlySale;
    }

    public void setEarlySale(int earlySale) {
        if (ttlSeeds > 0)
            ttlSeeds -= this.earlySale;
        this.earlySale = earlySale;
        ttlSeeds += earlySale;

    }

    public double getEarlyPrice() {
        return earlyPrice;
    }

    public void setEarlyPrice(double earlyPrice) {
        this.earlyPrice = earlyPrice;
    }

    public int getMidSale() {
        return midSale;
    }

    public void setMidSale(int midSale) {
        if (ttlSeeds > 0)
            ttlSeeds -= this.midSale;
        this.midSale = midSale;
        ttlSeeds += midSale;
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

    public int getFullSale() {
        return fullSale;
    }

    public void setFullSale(int fullSale) {
        if (ttlSeeds > 0)
            ttlSeeds -= this.fullSale;
        this.fullSale = fullSale;
        ttlSeeds += fullSale;
    }

    public void setTtlSeeds(){
        this.ttlSeeds = getEarlySale()+getMidSale()+getFullSale();
    }

    public int getTtlSeeds(){return this.ttlSeeds;}
}
