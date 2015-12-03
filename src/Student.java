import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.HashMap;

/**
 * Created by Lorenzo on 9/22/2015.
 *
 */

@Entity
public class Student {
    @Id
    public HashMap<String, Integer> id;
    public String uName;
    @Embedded
    public FarmTypes farm;
    protected String password;
    protected String salt;
    private int year;

    public Student() {
    }

    public Student(String name, String pass, FarmTypes farm) {
        this.uName = name;
        HashMap<String, String> passInfo = EncryptPassword.encrypt(pass);
        this.password = passInfo.get("password");
        this.salt = passInfo.get("salt");
        this.farm = farm;
        this.year = Consts.GAME_FLOW.currentYear;
        setId(this.year);
    }

    public int getYear(){
        return this.year;
    }

    public void setId(int year) {
        this.id = new HashMap<>();
        this.year = year;
        id.put(this.uName, year);
    }
}
