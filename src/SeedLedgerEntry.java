import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by Lorenzo on 12/27/2015.
 */

@Embedded
public class SeedLedgerEntry {
    Consts.Seed_Type seedType;
    int amount;
    double price;
}
