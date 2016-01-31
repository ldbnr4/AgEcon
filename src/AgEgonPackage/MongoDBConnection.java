package AgEgonPackage;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.jetbrains.annotations.NotNull;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
        usersColl.remove(new BasicDBObject("id", student.id));
        GameFlow gf = NNgetGameFlow();
        gf.setTotalPlayers();
        saveGameFlow(gf);
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
        gameFlowColl.remove(new BasicDBObject("name", gameFlow.name));
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

    //@NotNull
    public Student getStudent(String username) {
        HashMap<String, Integer> id = new HashMap<>();
        id.put(username, NNgetGameFlow().currentYear);
        DBObject person = usersColl.findOne(new BasicDBObject("id", id));
        if (person == null) {
            return null;
        }
        return gson.fromJson(person.toString(), Student.class);
    }

    //@NotNull
    public Student getStudent(String username, int year) {
        HashMap<String, Integer> id = new HashMap<>();
        id.put(username, year);
        DBObject person = usersColl.findOne(new BasicDBObject("id", id));
        if (person == null) {
            return null;
        }
        return gson.fromJson(person.toString(), Student.class);
    }

    //@NotNull
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

    public HashMap<String, InputSector> getInputSectorSellers() {
        HashMap<String, InputSector> list = new HashMap<>();
        try (DBCursor cursor = inputColl.find()){
            InputSector inputSector;
            while (cursor.hasNext()) {
                inputSector = gson.fromJson(cursor.next().toString(), InputSector.class);
                list.put(inputSector.getName(), inputSector);
            }
        }
        return list;
    }

    public HashMap<String, MarketingSector> getMarketingComps() {
        HashMap<String, MarketingSector> list = new HashMap<>();
        try (DBCursor cursor = marketColl.find()) {
            MarketingSector marketingSector;
            while (cursor.hasNext()) {
                marketingSector = gson.fromJson(cursor.next().toString(), MarketingSector.class);
                list.put(marketingSector.getName(), marketingSector);
            }
        }
        return list;
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

    public int getTotalPlayers() {
        return (int) usersColl.count(new BasicDBObject("year", NNgetGameFlow().currentYear));
    }

    public int getTotalPlayers(int year) {
        return (int) usersColl.count(new BasicDBObject("year", year));
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
            numRes.put(Consts.Farm_Size.SMALL_FARM,
                    (int) usersColl
                            .count(new BasicDBObject("year", NNgetGameFlow().currentYear).append("farm.size", Consts.Farm_Size.SMALL_FARM.toValue())));
            numRes.put(Consts.Farm_Size.MED_FARM,
                    (int) usersColl
                            .count(new BasicDBObject("year", NNgetGameFlow().currentYear).append("farm.size", Consts.Farm_Size.MED_FARM.toValue())));
            numRes.put(Consts.Farm_Size.LARGE_FARM, (int) usersColl
                    .count(new BasicDBObject("year", NNgetGameFlow().currentYear).append("farm.size", Consts.Farm_Size.LARGE_FARM.toValue())));
            numRes.put(Consts.Farm_Size.NO_FARM,
                    (int) usersColl
                            .count(new BasicDBObject("year", NNgetGameFlow().currentYear).append("farm.size", Consts.Farm_Size.NO_FARM.toValue())));
        } catch (NullPointerException e) {
            return numRes;
        }
        return numRes;
    }

    public void addStudent(Student student) {
        usersColl.insert((BasicDBObject) JSON.parse(gson.toJson(student)));
        GameFlow gf = NNgetGameFlow();
        gf.setTotalPlayers();
        saveGameFlow(gf);
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
        int newYr = NNgetGameFlow().currentYear;
        int oldYr = newYr + dir;
        int newTtl = getTotalPlayers(newYr);
        int oldTtl = getTotalPlayers(oldYr);

        if (oldTtl > newTtl) {
            try (DBCursor users = usersColl.find(new BasicDBObject("year", oldYr))) {
                Student student;
                while (users.hasNext()) {
                    if (getStudent((String) users.next().get("uName"), newYr) == null) {
                        student = getStudent((String) users.next().get("uName"), oldYr);
                        student.setId(newYr);
                        addStudent(student);
                    }
                }
            }
        } else if (oldTtl < newTtl) {
            try (DBCursor users = usersColl.find(new BasicDBObject("year", newYr))) {
                Student student;
                while (users.hasNext()) {
                    if (getStudent((String) users.next().get("uName"), oldYr) == null) {
                        student = getStudent((String) users.next().get("uName"), newYr);
                        student.setId(oldYr);
                        addStudent(student);
                    }
                }
            }
        }
    }

    ArrayList<Student> getAllStudents() {
        ArrayList<Student> list = new ArrayList<>();
        try (DBCursor cursor = usersColl.find(new BasicDBObject("year", NNgetGameFlow().currentYear))) {
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
        gameFlow.setInpuSect(true);
        saveGameFlow(gameFlow);
    }

    public void setGenMark() {
        GameFlow gameFlow = NNgetGameFlow();
        gameFlow.setMarketingSect(true);
        saveGameFlow(gameFlow);
    }

    private DB openConnection() {
        if(mongoClient == null) {
            String dbuname = "admin", database = "agdb", password = "password";
            MongoCredential credential = MongoCredential.createCredential(dbuname, database, password.toCharArray());
            try {
                this.mongoClient = new MongoClient(new ServerAddress("ds051543.mongolab.com", 51543), Collections.singletonList(credential));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return this.mongoClient.getDB("agdb");
    }
}
