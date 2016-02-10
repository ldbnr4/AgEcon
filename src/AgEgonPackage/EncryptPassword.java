/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEgonPackage;

import java.util.HashMap;

import static java.lang.String.valueOf;

/**
 * Created by Lorenzo on 9/24/2015.
 *
 */
public class EncryptPassword {
    public static HashMap<String, String> encrypt(String password) {
        HashMap<String, String> ret = new HashMap<>();
        String saltString = valueOf(new CSPRNG().getAll());
        String hash = valueOf((password + saltString).hashCode());
        ret.put("salt", saltString);
        ret.put("password", hash);
        return ret;
    }

    public static String encrpyt(String password, String salt) {
        String combo = password + salt;

        return valueOf(combo.hashCode());
    }

}

/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */