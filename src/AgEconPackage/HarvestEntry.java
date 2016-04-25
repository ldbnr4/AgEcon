/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

import com.sun.istack.internal.NotNull;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Lorenzo on 1/25/2016.
 *
 */
class HarvestEntry implements Comparable {
    private String date;
    private int amount;

    HarvestEntry() {}

    HarvestEntry(String date, int amount) {
        this.date = date;
        this.amount = amount;
    }

    String getDate() {
        return date;
    }

    void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        Date compareDate = null;
        Date baseDate = null;
        try {
            compareDate = Consts.sd2.parse(((HarvestEntry) o).getDate());
            baseDate = Consts.sd2.parse(getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (baseDate != null && baseDate.before(compareDate)) {
            return -1;
        } else if (baseDate != null && baseDate.equals(compareDate)) {
            return 0;
        }

        return 1;
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */