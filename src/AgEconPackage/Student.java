/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

import java.util.HashMap;

import static AgEconPackage.Consts.DB;

/**
 * Created by Lorenzo on 9/22/2015.
 *
 */

public class Student {
    private HashMap<Integer, Farm> studentSeasons;
    String uName;
    String password;
    String salt;

    public Student(String name, String pass, Farm farm) {
        uName = name;
        HashMap<String, String> passInfo = EncryptPassword.encrypt(pass);
        password = passInfo.get("password");
        salt = passInfo.get("salt");
        studentSeasons = new HashMap<Integer, Farm>(){{
            put(Consts.DB.NNgetGameFlow().getCurrentYear(), farm);
        }};
    }

    public Farm getFarm(int year) {
        return studentSeasons.get(year);
    }

    public Farm getFarm() {
        return studentSeasons.get(Consts.DB.NNgetGameFlow().getCurrentYear());
    }

    int numOfSeasonsPlayed() {
        return studentSeasons.size();
    }

    void addReplaceFarm(Farm farm) {
        if (studentSeasons.containsKey(DB.NNgetGameFlow().getCurrentYear())) {
            studentSeasons.remove(DB.NNgetGameFlow().getCurrentYear());
        }
        studentSeasons.put(DB.NNgetGameFlow().getCurrentYear(), farm);
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */