import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.HashMap;

/**
 * Created by Lorenzo on 9/22/2015.
 */

@Entity
public class Student {
    @Id
    public String uName;
    @Embedded
    public Sector sector;
    private int year;
    protected String password;
    protected String salt;

    public Student() {
    }

    public Student(String name, String pass, Sector sector) {
        this.uName = name;
        HashMap<String, String> passInfo = EncryptPassword.encrypt(pass);
        this.password = passInfo.get("password");
        this.salt = passInfo.get("salt");
        this.sector = sector;
        this.year = GameDriver.GAME_FLOW.currentYear;
    }

    public int getYear(){
        return this.year;
    }
}
