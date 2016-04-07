/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.sun.istack.internal.NotNull;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static AgEconPackage.Consts.Farm_Size.*;
import static AgEconPackage.Consts.GSON;

/**
 * Created by Lorenzo on 10/6/2015.
 *
 */
public class MongoDBConnection{
    private static MongoDBConnection ourInstance = new MongoDBConnection();
    private MongoClient mongoClient = null;
    private DB db = openConnection();
    private DBCollection gameFlowColl = db.getCollection("gameFlow");
    private DBCollection adminsColl = db.getCollection("admins");
    private DBCollection usersColl = db.getCollection("users");
    private DBCollection inputColl = db.getCollection("inputSector");
    private DBCollection marketColl = db.getCollection("marketingSector");

    private MongoDBConnection() {}

    static MongoDBConnection getInstance() {
        return ourInstance;
    }

    public void removeStudent(Student student){
        usersColl.remove(new BasicDBObject("uName", student.uName));
    }

    public void removeAdmin(String admin) {
        adminsColl.remove(new BasicDBObject("name", admin));
    }

    private void removeInput(String input) {
        inputColl.remove(new BasicDBObject("name", input));
    }

    private void removeMarketComp(String marketName) {
        marketColl.remove(new BasicDBObject("name", marketName));
    }

    void removeAllStudents() {
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

    void saveGameFlow(GameFlow gameFlow) {
        gameFlowColl.remove(new BasicDBObject("name", gameFlow.getName()));
        addGameFlow(gameFlow);
    }

    void saveInput(InputSector inputSector) {
        removeInput(inputSector.getName());
        addInputComp(inputSector);
    }

    void saveMarketing(MarketingSector marketingSector) {
        removeMarketComp(marketingSector.getName());
        addMarketComp(marketingSector);
    }

    public Student getStudent(String username) {
        DBObject person = usersColl.findOne(new BasicDBObject("uName", username));
        if (person == null) {
            return null;
        }
        return GSON.fromJson(person.toString(), Student.class);
    }

    Admin getAdmin(String username) {
        DBObject person = adminsColl.findOne(new BasicDBObject("name", username));
        if (person == null) {
            return null;
        }
        return GSON.fromJson(person.toString(), Admin.class);
    }

    GameFlow getGameFlow() {
        DBObject person = gameFlowColl.findOne(new BasicDBObject("name", "GameFlow"));
        if (person == null) {
            return null;
        }
        return GSON.fromJson(person.toString(), GameFlow.class);
    }

    @NotNull
    public GameFlow NNgetGameFlow() {
        DBObject gf = gameFlowColl.findOne(new BasicDBObject("name", "GameFlow"));
        while (gf == null) {
            gf = gameFlowColl.findOne(new BasicDBObject("name", "GameFlow"));
        }
        return GSON.fromJson(gf.toString(), GameFlow.class);
    }

    @NotNull
    public InputSector getInputSeller(String name) {
        DBObject one = inputColl.findOne(new BasicDBObject("name", name));
        while (one == null) {
            one = inputColl.findOne(new BasicDBObject("name", name));
        }
        return GSON.fromJson(one.toString(), InputSector.class);
    }

    @NotNull
    MarketingSector getMarketingComp(String name) {
        DBObject one = marketColl.findOne(new BasicDBObject("name", name));
        while (one == null) {
            one = marketColl.findOne(new BasicDBObject("name", name));
        }
        return GSON.fromJson(one.toString(), MarketingSector.class);
    }

    int getTotalPlayers(int year) {
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

    int getTotalAdmins() {
        return (int) adminsColl.count();
    }

    int getSeedsNeeded() {
        return (10 * 100) * 8 + (10 * 250) * 14 + (10 * 500) * 8;
    }

    int getBshlsNeeded() {
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

    void addStudent(Student student) {
        if (getStudent(student.uName) == null) usersColl.insert((BasicDBObject) JSON.parse(GSON.toJson(student)));
    }

    void addAdmin(Admin admin) {
        adminsColl.insert((BasicDBObject) JSON.parse(GSON.toJson(admin)));
    }

    void addGameFlow(GameFlow gameFlow) {
        gameFlowColl.insert((BasicDBObject) JSON.parse(GSON.toJson(gameFlow)));
    }

    private void addInputComp(InputSector inputComp) {
        try {
            inputColl.insert((BasicDBObject) JSON.parse(GSON.toJson(inputComp)));
        } catch (MongoException ignored) {

        }
    }

    private void addMarketComp(MarketingSector marketingSector) {
        marketColl.insert((BasicDBObject) JSON.parse(GSON.toJson(marketingSector)));
    }

    void yearChange(int dir) {
        int newYr = NNgetGameFlow().getCurrentYear();
        Student student;

        try (DBCursor users = usersColl.find()) {
            while (users.hasNext()) {
                student = GSON.fromJson(users.next().toString(), Student.class);
                if (student.getSector(newYr) == null) {
                    student.addReplaceSector(new Farm(NO_FARM));
                    saveStudent(student);
                }
            }
        }
    }

    ArrayList<Student> getAllStudents() {
        ArrayList<Student> list = new ArrayList<>();
        try (DBCursor cursor = usersColl.find()) {
            while (cursor.hasNext()) {
                list.add(GSON.fromJson(cursor.next().toString(), Student.class));
            }
        }
        return list;
    }

    ArrayList<Admin> getAllAdmins() {
        ArrayList<Admin> list = new ArrayList<>();
        try (DBCursor cursor = adminsColl.find()) {
            while (cursor.hasNext()) {
                list.add(GSON.fromJson(cursor.next().toString(), Admin.class));
            }
        }
        return list;
    }

    public int getAllUserCount() {
        return (int) usersColl.count();
    }

    void setGenInput() {
        GameFlow gameFlow = NNgetGameFlow();
        gameFlow.setGameFlowsInput(gameFlow.getCurrentYear(), true);
        saveGameFlow(gameFlow);
    }

    void setGenMark() {
        GameFlow gameFlow = NNgetGameFlow();
        gameFlow.setGameFlowsMarket(gameFlow.getCurrentYear(), true);
        saveGameFlow(gameFlow);
    }

    private DB openConnection() {
        String dbuname = "admin", database = "agdb", password = "password";
        //String dbuname = "mizzouUser", database = "MizzouAMUMC", password = "password";
        if(mongoClient == null) {
            MongoCredential credential = MongoCredential.createCredential(dbuname, database, password.toCharArray());
            try {
                this.mongoClient = new MongoClient(new ServerAddress("ds051543.mongolab.com", 51543), Collections.singletonList(credential));
                //this.mongoClient = new MongoClient(new ServerAddress("ldbnr4.ddns.net", 10000), Collections.singletonList(credential));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return this.mongoClient.getDB(database);
    }
}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */