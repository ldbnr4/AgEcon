/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEgonPackage;

import java.util.HashMap;

import static AgEgonPackage.Consts.DB;

/**
 * Created by Lorenzo on 9/22/2015.
 *
 */

public class Student {
    public HashMap<Integer, Farm> studentSeasons;
    public String uName;
    protected String password;
    protected String salt;

    public Student(String name, String pass, Farm farm) {
        uName = name;
        HashMap<String, String> passInfo = EncryptPassword.encrypt(pass);
        password = passInfo.get("password");
        salt = passInfo.get("salt");
        studentSeasons = new HashMap<>();
        studentSeasons.put(Consts.DB.NNgetGameFlow().getCurrentYear(), farm);
    }

    public Farm getFarm(int year) {
        return studentSeasons.get(year);
    }

    public Farm getFarm() {
        return studentSeasons.get(Consts.DB.NNgetGameFlow().getCurrentYear());
    }

    public int numOfSeasonsPlayed() {
        return studentSeasons.size();
    }

    public void addReplaceFarm(Farm farm) {
        if (studentSeasons.containsKey(DB.NNgetGameFlow().getCurrentYear())) {
            studentSeasons.remove(DB.NNgetGameFlow().getCurrentYear());
        }
        studentSeasons.put(DB.NNgetGameFlow().getCurrentYear(), farm);
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */