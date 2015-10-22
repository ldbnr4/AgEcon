/**
 * Created by Lorenzo on 9/24/2015.
 */
public class EncryptPassword {
    public static String[] encrypt(String password) {
        String ret[] = new String[10];
        CSPRNG salt = new CSPRNG();
        String saltString = String.valueOf(salt.getAll());
        String hash = String.valueOf((password + saltString).hashCode());
        ret[0] = saltString;
        ret[1] = hash;

        return ret;
    }

    public static String encrpyt(String password, String salt) {
        String combo = password + salt;
        String hash = String.valueOf(combo.hashCode());

        return hash;
    }

}
