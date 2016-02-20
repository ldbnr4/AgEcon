/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEgonPackage;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.jetbrains.annotations.NotNull;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static AgEgonPackage.Consts.Farm_Size.*;

/**
 * Created by Lorenzo on 10/6/2015.
 *
 */
public class MongoDBConnection{
    private static MongoDBConnection ourInstance = new MongoDBConnection();
    private MongoClient mongoClient = null;
    private Gson gson = new Gson();
    private DB db = openConnection();
    private DBCollection gameFlowColl = db.getCollection("gameFlow");
    private DBCollection adminsColl = db.getCollection("admins");
    private DBCollection usersColl = db.getCollection("users");
    private DBCollection inputColl = db.getCollection("inputSector");
    private DBCollection marketColl = db.getCollection("marketingSector");

    private MongoDBConnection() {
        //morphia.mapPackage("AgEgonPackage");
    }

    public static MongoDBConnection getInstance() {
        return ourInstance;
    }

    public void removeStudent(Student student){
        usersColl.remove(new BasicDBObject("uName", student.uName));
    }

    public void removeAdmin(String admin) {
        adminsColl.remove(new BasicDBObject("name", admin));
    }

    public void removeInput(String input) {
        inputColl.remove(new BasicDBObject("name", input));
    }

    public void removeMarketComp(String marketName) {
        marketColl.remove(new BasicDBObject("name", marketName));
    }

    public void removeAllStudents() {
        try (DBCursor cursor = usersColl.find()) {
            while (cursor.hasNext()) {
                usersColl.remove(cursor.next());
            }
        }
    }

    public void saveStudent(Student student) {
        removeStudent(student);
        addStudent(student);
    }

    public void saveGameFlow(GameFlow gameFlow) {
        gameFlowColl.remove(new BasicDBObject("name", gameFlow.getName()));
        addGameFlow(gameFlow);
    }

    public void saveInput(InputSector inputSector) {
        removeInput(inputSector.getName());
        addInputComp(inputSector);
    }

    public void saveMarketing(MarketingSector marketingSector) {
        removeMarketComp(marketingSector.getName());
        addMarketComp(marketingSector);
    }

    public Student getStudent(String username) {
        DBObject person = usersColl.findOne(new BasicDBObject("uName", username));
        if (person == null) {
            return null;
        }
        return gson.fromJson(person.toString(), Student.class);
    }

    public Admin getAdmin(String username) {
        DBObject person = adminsColl.findOne(new BasicDBObject("name", username));
        if (person == null) {
            return null;
        }
        return gson.fromJson(person.toString(), Admin.class);
    }

    public GameFlow getGameFlow() {
        DBObject person = gameFlowColl.findOne(new BasicDBObject("name", "GameFlow"));
        if (person == null) {
            return null;
        }
        return gson.fromJson(person.toString(), GameFlow.class);
    }

    @NotNull
    public GameFlow NNgetGameFlow() {
        DBObject gf = gameFlowColl.findOne(new BasicDBObject("name", "GameFlow"));
        while (gf == null) {
            gf = gameFlowColl.findOne(new BasicDBObject("name", "GameFlow"));
        }
        return gson.fromJson(gf.toString(), GameFlow.class);
    }

    @NotNull
    public InputSector getInputSeller(String name) {
        DBObject one = inputColl.findOne(new BasicDBObject("name", name));
        while (one == null) {
            one = inputColl.findOne(new BasicDBObject("name", name));
        }
        return gson.fromJson(one.toString(), InputSector.class);
    }

    @NotNull
    public MarketingSector getMarketingComp(String name) {
        DBObject one = marketColl.findOne(new BasicDBObject("name", name));
        while (one == null) {
            one = marketColl.findOne(new BasicDBObject("name", name));
        }
        return gson.fromJson(one.toString(), MarketingSector.class);
    }

    public int getTotalPlayers(int year) {
        HashMap<String, Integer> farmHash = new HashMap<>();
        int count = 0;
        try (DBCursor cursor = usersColl.find()) {
            while (cursor.hasNext()) {
                farmHash = (HashMap<String, Integer>) cursor.next().get("studentSeasons");
                if (farmHash.get(String.valueOf(year)) != null) count++;
            }
        }
        return count;
    }

    public int getTotalAdmins() {
        return (int) adminsColl.count();
    }

    public int getSeedsNeeded() {
        return (10 * 100) * 8 + (10 * 250) * 14 + (10 * 500) * 8;
    }

    public int getBshlsNeeded() {
        return (Consts.ACRE_YIELD * Consts.S_ACRE) * Consts.S_FARM_CAP + (Consts.ACRE_YIELD * Consts.M_ACRE) * Consts.M_FARM_CAP + (Consts.ACRE_YIELD * Consts.L_ACRE) * Consts.L_FARM_CAP;
    }

    public HashMap<Consts.Farm_Size, Integer> numInEachFarm() {
        HashMap<Consts.Farm_Size, Integer> numRes = new HashMap<>();
        try {
            numRes.put(SMALL_FARM, (int) usersColl
                    .count(new BasicDBObject("studentSeasons." + NNgetGameFlow().getCurrentYear() + ".size", SMALL_FARM.toValue())));
            numRes.put(MED_FARM, (int) usersColl
                    .count(new BasicDBObject("studentSeasons." + NNgetGameFlow().getCurrentYear() + ".size", MED_FARM.toValue())));
            numRes.put(LARGE_FARM, (int) usersColl
                    .count(new BasicDBObject("studentSeasons." + NNgetGameFlow().getCurrentYear() + ".size", LARGE_FARM.toValue())));
            numRes.put(NO_FARM, (int) usersColl
                    .count(new BasicDBObject("studentSeasons." + NNgetGameFlow().getCurrentYear() + ".size", NO_FARM.toValue())));
        } catch (NullPointerException e) {
            return null;
        }
        return numRes;
    }

    public void addStudent(Student student) {
        usersColl.insert((BasicDBObject) JSON.parse(gson.toJson(student)));
        GameFlow gf = NNgetGameFlow();
    }

    public void addAdmin(Admin admin) {
        adminsColl.insert((BasicDBObject) JSON.parse(gson.toJson(admin)));
    }

    public void addGameFlow(GameFlow gameFlow) {
        gameFlowColl.insert((BasicDBObject) JSON.parse(gson.toJson(gameFlow)));
    }

    public void addInputComp(InputSector inputComp) {
        try {
            inputColl.insert((BasicDBObject) JSON.parse(gson.toJson(inputComp)));
        } catch (MongoException ignored) {

        }
    }

    public void addMarketComp(MarketingSector marketingSector) {
        marketColl.insert((BasicDBObject) JSON.parse(gson.toJson(marketingSector)));
    }

    public void yearChange(int dir) {
        int newYr = NNgetGameFlow().getCurrentYear();
        Student student;

        try (DBCursor users = usersColl.find()) {
            while (users.hasNext()) {
                student = gson.fromJson(users.next().toString(), Student.class);
                if (student.getFarm(newYr) == null) {
                    student.addReplaceFarm(new Farm(NO_FARM));
                    saveStudent(student);
                }
            }
        }
    }

    ArrayList<Student> getAllStudents() {
        ArrayList<Student> list = new ArrayList<>();
        try (DBCursor cursor = usersColl.find()) {
            while (cursor.hasNext()) {
                list.add(gson.fromJson(cursor.next().toString(), Student.class));
            }
        }
        return list;
    }

    ArrayList<Admin> getAllAdmins() {
        ArrayList<Admin> list = new ArrayList<>();
        try (DBCursor cursor = adminsColl.find()) {
            while (cursor.hasNext()) {
                list.add(gson.fromJson(cursor.next().toString(), Admin.class));
            }
        }
        return list;
    }

    public void setGenInput() {
        GameFlow gameFlow = NNgetGameFlow();
        gameFlow.setGameFlowsInput(gameFlow.getCurrentYear(), true);
        saveGameFlow(gameFlow);
    }

    public void setGenMark() {
        GameFlow gameFlow = NNgetGameFlow();
        gameFlow.setGameFlowsMarket(gameFlow.getCurrentYear(), true);
        saveGameFlow(gameFlow);
    }

    private DB openConnection() {
        if(mongoClient == null) {
            String dbuname = "mizzouUser", database = "MizzouAMUMC", password = "password";
            MongoCredential credential = MongoCredential.createCredential(dbuname, database, password.toCharArray());
            try {
                this.mongoClient = new MongoClient(new ServerAddress("ldbnr4.ddns.net", 27017), Collections.singletonList(credential));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return this.mongoClient.getDB("MizzouAMUMC");
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */