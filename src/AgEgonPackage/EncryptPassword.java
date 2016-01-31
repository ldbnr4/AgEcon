package AgEgonPackage;

import java.util.HashMap;

/**
 * Created by Lorenzo on 9/24/2015.
 *
 */
public class EncryptPassword {
    public static HashMap<String, String> encrypt(String password) {
        HashMap<String, String> ret = new HashMap<>();
        CSPRNG salt = new CSPRNG();
        String saltString = String.valueOf(salt.getAll());
        String hash = String.valueOf((password + saltString).hashCode());
        ret.put("salt", saltString);
        ret.put("password", hash);
        return ret;
    }

    public static String encrpyt(String password, String salt) {
        String combo = password + salt;

        return String.valueOf(combo.hashCode());
    }

}
