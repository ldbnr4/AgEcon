import org.mongodb.morphia.annotations.Embedded;

import java.io.Serializable;

/**
 * Created by Lorenzo on 10/13/2015.
 */
@Embedded
public class Sector implements Serializable {
    public String name;

    public Sector(String name) {
        this.name = name;
    }

    public Sector() {
    }
}
