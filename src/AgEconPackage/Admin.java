/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

import java.util.HashMap;

/**
 * Created by Lorenzo on 10/27/2015.
 *
 */

public class Admin {
    public String name;
    String salt;
    String password;

    public Admin() {
    }

    public Admin(String name, String pass) {
        this.name = name;
        HashMap<String, String> strings = EncryptPassword.encrypt(pass);
        this.password = strings.get("password");
        this.salt = strings.get("salt");
    }
}


/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */