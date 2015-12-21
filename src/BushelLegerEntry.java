import java.text.ParseException;
import java.util.Date;

/**
 * Created by Lorenzo on 12/20/2015.
 *
 */
public class BushelLegerEntry implements Comparable {
    private String date;
    private int amount;

    public BushelLegerEntry(String date, int amount) {
        setDate(date);
        setAmount(amount);
    }

    public BushelLegerEntry() {
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

    @Override
    public int compareTo(Object o) {
        Date compareDate = null;
        Date baseDate = null;
        try {
            compareDate = Consts.sd.parse(((BushelLegerEntry) o).getDate());
            baseDate = Consts.sd.parse(getDate());
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
