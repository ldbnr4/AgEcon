import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.HashMap;

/**
 * Created by Lorenzo on 10/27/2015.
 * 
 */

@Entity
public class Admin {
    @Id
    public String name;
    protected String password;
    protected String salt;

    public Admin() {
    }

    public Admin(String name, String pass) {
        this.name = name;
        HashMap<String, String> strings = EncryptPassword.encrypt(pass);
        this.password = strings.get("password");
        this.salt = strings.get("salt");
    }
}
