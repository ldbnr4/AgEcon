/**
 * Created by Lorenzo on 12/20/2015.
 */
public class BushelLegerEntry {
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
}
