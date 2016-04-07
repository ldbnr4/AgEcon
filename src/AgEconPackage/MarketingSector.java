/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lorenzo on 12/15/2015.
 *
 */

class MarketingSector extends Sector {
    private String name;
    private String neededDate;
    private Double pricePerBush;

    private int bshls;

    MarketingSector(String name, String neededDate, Double pricePerBush, int bshls) {
        this.name = name;
        this.neededDate = neededDate;
        this.pricePerBush = pricePerBush;
        this.bshls = bshls;
    }

    @Override
    public boolean isComplete() {
        List<Field> fields = Arrays.asList(getClass().getDeclaredFields());
        for (Field field : fields) {
            try {
                Object fieldVal = field.get(this);

                if (fieldVal == null) continue;
                else if (fieldVal.equals(0)) continue;
                else if (fieldVal.equals(false)) continue;

                return true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    String getNeededDate() {
        return neededDate;
    }

    Double getPricePerBush() {
        return pricePerBush;
    }

    int getBshls() {
        return this.bshls;
    }

    boolean subtractBshls(int adjust) {
        if (adjust > bshls) {
            return false;
        }
        bshls -= adjust;
        Consts.DB.saveMarketing(this);
        return true;
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */