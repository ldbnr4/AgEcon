import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by Lorenzo on 10/13/2015.
 *
 */
@Embedded
public abstract class Sector {
    public String name;

    public Sector(String name) {
        this.name = name;
    }

    public Sector() {
    }

    abstract boolean checkIfEmpty();
}
