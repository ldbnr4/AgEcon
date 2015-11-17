/**
 * Created by Lorenzo on 10/13/2015.
 *
 */
public class SeedTypes {
    public int earlySale;
    public int earlyPrice;
    public int midSale;
    public int midPrice;
    public int fullSale;
    public int fullPrice;
    public int ttlSeeds;

    public SeedTypes() {
        this.earlySale = 0;
        this.earlyPrice = 0;
        this.midSale = 0;
        this.midPrice = 0;
        this.fullSale = 0;
        this.fullPrice = 0;
        setTtlSeeds();
    }

    public SeedTypes(int earlySale, int earlyPrice, int midSale, int midPrice, int fullSale, int fullPrice) {
        this.earlySale = earlySale;
        this.earlyPrice = earlyPrice;
        this.midSale = midSale;
        this.midPrice = midPrice;
        this.fullSale = fullSale;
        this.fullPrice = fullPrice;
        setTtlSeeds();
    }


    public int getEarlySale() {
        return earlySale;
    }

    public void setEarlySale(int earlySale) {
        this.earlySale = earlySale;
    }

    public int getEarlyPrice() {
        return earlyPrice;
    }

    public void setEarlyPrice(int earlyPrice) {
        this.earlyPrice = earlyPrice;
    }

    public int getMidSale() {
        return midSale;
    }

    public void setMidSale(int midSale) {
        this.midSale = midSale;
    }

    public int getMidPrice() {
        return midPrice;
    }

    public void setMidPrice(int midPrice) {
        this.midPrice = midPrice;
    }

    public int getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(int fullPrice) {
        this.fullPrice = fullPrice;
    }

    public int getFullSale() {
        return fullSale;
    }

    public void setFullSale(int fullSale) {
        this.fullSale = fullSale;
    }

    public void setTtlSeeds(){
        this.ttlSeeds = getEarlySale()+getMidSale()+getFullSale();
    }

    public int getTtlSeeds(){return this.ttlSeeds;}
}
