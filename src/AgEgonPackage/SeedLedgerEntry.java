package AgEgonPackage;

/**
 * Created by Lorenzo on 12/27/2015.
 *
 */

public class SeedLedgerEntry {
    private String seller;
    private Consts.Seed_Type seedType;
    private int amount;
    private double price;

    SeedLedgerEntry(){}

    public SeedLedgerEntry(String seller, Consts.Seed_Type seedType, int amount, double price){
        setSeller(seller);
        setSeedType(seedType);
        setAmount(amount);
        setPrice(price);
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Consts.Seed_Type getSeedType() {
        return seedType;
    }

    public void setSeedType(Consts.Seed_Type seedType) {
        this.seedType = seedType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
