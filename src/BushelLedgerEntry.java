import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Lorenzo on 12/20/2015.
 *
 */
public class BushelLedgerEntry implements Comparable {
    private String date;
    private int amount;
    private double ppbndl;
    private String seller;

    public BushelLedgerEntry(String date, int amount, double ppbndl, String seller) {
        setDate(date);
        setAmount(amount);
        setPpbndl(ppbndl);
        setSeller(seller);
    }

    public BushelLedgerEntry() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    @Override
    public int compareTo(@NotNull Object o) {
        Date compareDate = null;
        Date baseDate = null;
        try {
            compareDate = Consts.sd2.parse(((BushelLedgerEntry) o).getDate());
            baseDate = Consts.sd2.parse(getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (baseDate != null && baseDate.before(compareDate)) {
            return -1;
        } else if (baseDate != null && baseDate.equals(compareDate)) {
            return 0;
        }

        return 1;
    }
}
