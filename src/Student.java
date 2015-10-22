import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.io.Serializable;

/**
 * Created by Lorenzo on 9/22/2015.
 */

@Entity
public class Student implements Serializable {
    @Id
    public String uName;
    @Embedded
    public Sector sector;
    protected String password;
    protected String salt;

    public Student() {
    }

    public Student(String name, String pass, String salt, Sector sector) {
        this.uName = name;
        this.password = pass;
        this.salt = salt;
        this.sector = sector;
    }
}
