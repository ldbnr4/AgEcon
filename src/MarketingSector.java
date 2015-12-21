import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by Lorenzo on 12/15/2015.
 *
 */

@Entity
public class MarketingSector {
    @Id
    private String name;
    private String neededDate;
    private Double pricePerBush;
    private int year;

    private int bshls;

    public MarketingSector() {
    }

    public MarketingSector(String name, String neededDate, Double pricePerBush, int bshls) {
        this.name = name;
        this.neededDate = neededDate;
        this.pricePerBush = pricePerBush;
        this.bshls = bshls;
        this.year = Consts.GAME_FLOW.currentYear;
    }

    public String getName() {
        return this.name;
    }

    public String getNeededDate() {
        return neededDate;
    }

    public Double getPricePerBush() {
        return pricePerBush;
    }

    public int getBshls() {
        return this.bshls;
    }

    public boolean updateBshls(int adjust) {
        if (adjust > bshls) {
            return false;
        }
        this.bshls -= adjust;
        Consts.DB.saveMarketing(this);
        return true;
    }
}
