/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

import AgEconPackage.Consts.Student_Stage;

import java.util.HashMap;

import static AgEconPackage.Consts.DB;
import static AgEconPackage.Consts.GSON;

/**
 * Created by Lorenzo on 9/22/2015.
 *
 */

public class Student {
    String uName;
    String password;
    String salt;
    private HashMap<Integer, Object> studentSeasons = new HashMap<>();

    public Student(String name, String pass) {
        uName = name;
        HashMap<String, String> passInfo = EncryptPassword.encrypt(pass);
        password = passInfo.get("password");
        salt = passInfo.get("salt");
        DB.addStudent(this);
    }

    public Sector getSector(int year) {
        String toJson = GSON.toJson(studentSeasons.get(year));

        Farm fromJsonFarm = GSON.fromJson(toJson, Farm.class);
        MarketingSector fromJsonMarketing = GSON.fromJson(toJson, MarketingSector.class);

        if (fromJsonFarm.isComplete()) {
            return fromJsonFarm;
        }
        return fromJsonMarketing;
    }

    public Sector getSector() {
        String toJson = GSON.toJson(studentSeasons.get(Consts.DB.NNgetGameFlow().getCurrentYear()));

        Farm fromJsonFarm = GSON.fromJson(toJson, Farm.class);
        MarketingSector fromJsonMarketing = GSON.fromJson(toJson, MarketingSector.class);

        if (fromJsonFarm.isComplete()) {
            return fromJsonFarm;
        }
        return fromJsonMarketing;
    }

    public int numOfSeasonsPlayed() {
        return studentSeasons.size();
    }

    public void addReplaceSector(Sector sector) {
        if (studentSeasons.containsKey(DB.NNgetGameFlow().getCurrentYear())) {
            studentSeasons.remove(DB.NNgetGameFlow().getCurrentYear());
        }
        studentSeasons.put(DB.NNgetGameFlow().getCurrentYear(), sector);
    }

    Student_Stage getStage() {
        return getSector().stage;
    }

    public void setStage(Student_Stage newStage) {
        Farm farm = (Farm) getSector();
        farm.stage = newStage;
        addReplaceSector(farm);
    }

    Student_Stage getStage(int year) {
        return getSector(year).stage;
    }

    public String getuName() {
        return uName;
    }

    public String getPassword() {
        return password;
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */