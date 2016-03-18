/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

import com.google.gson.Gson;

import java.util.HashMap;

import static AgEconPackage.Consts.DB;

/**
 * Created by Lorenzo on 9/22/2015.
 *
 */

public class Student {
    public HashMap<Integer, Object> studentSeasons = new HashMap<>();
    public String uName;
    protected String password;
    protected String salt;

    public Student(String name, String pass) {
        uName = name;
        HashMap<String, String> passInfo = EncryptPassword.encrypt(pass);
        password = passInfo.get("password");
        salt = passInfo.get("salt");
        DB.addStudent(this);
    }

    public Sector getSector(int year) {
        Sector farm = (Farm) studentSeasons.get(year);
        if(farm instanceof Farm) return (Farm) farm;
        return null;
    }

    public Sector getSector() {
        Gson gson = new Gson();
        String toJson = gson.toJson(studentSeasons.get(Consts.DB.NNgetGameFlow().getCurrentYear()));
        Farm fromJsonFarm = gson.fromJson(toJson, Farm.class);
        MarketingSector fromJsonMarketing = gson.fromJson(toJson, MarketingSector.class);

        if(fromJsonFarm.isComplete()){

        }
        //return fromJsonFarm;
        return new Farm();
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