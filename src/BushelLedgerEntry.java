/**
 * Created by Lorenzo on 12/20/2015.
 *
 */
public class BushelLedgerEntry extends HarvestEntry implements Comparable {
    private double ppbndl;
    private String seller;

    public BushelLedgerEntry(String date, int amount, double ppbndl, String seller) {
        super(date, amount);
        setDate(date);
        setAmount(amount);
        setPpbndl(ppbndl);
        setSeller(seller);
    }

    public BushelLedgerEntry() {
        super();
    }

    public double getPpbndl() {
        return ppbndl;
    }

    public void setPpbndl(double ppbndl) {
        this.ppbndl = ppbndl;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
