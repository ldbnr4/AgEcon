/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEgonPackage;

import com.mongodb.BasicDBObject;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Created by Lorenzo on 9/22/2015.
 *
 */
public class CSPRNG extends BasicDBObject {
    byte[] randomBytes;
    private boolean randomBoolean;
    private int randomInt;

    public CSPRNG() {
        try {
            //Locate a SHA1PRNG provider
            SecureRandom csprng = SecureRandom.getInstance("SHA1PRNG");

            //Generate a random boolean value
            this.randomBoolean = csprng.nextBoolean();
            //System.out.println(randomBoolean);

            //Generate a random int value
            this.randomInt = csprng.nextInt();

            //Generate 3 random bytes
            this.randomBytes = new byte[3];
            csprng.nextBytes(randomBytes);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getAll() {
        return (Arrays.toString(this.randomBytes) + this.randomInt + this.randomBoolean);
    }
}

/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */