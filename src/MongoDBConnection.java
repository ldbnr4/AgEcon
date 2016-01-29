import com.mongodb.*;
import org.jetbrains.annotations.NotNull;
import org.mongodb.morphia.Morphia;

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
    private Morphia morphia = new Morphia();
    private DB db = openConnection();
    private DBCollection gameFlowColl = db.getCollection("gameFlow");
    private DBCollection adminsColl = db.getCollection("admins");
    private DBCollection usersColl = db.getCollection("users");
    private DBCollection inputColl = db.getCollection("inputSector");
    private DBCollection marketColl = db.getCollection("marketingSector");

    private MongoDBConnection() {
        morphia.map(Student.class).map(Farm.class).map(GameFlow.class).map(Admin.class).map(InputSector.class)
                .map(MarketingSector.class);
    }

    public static MongoDBConnection getInstance() {
        return ourInstance;
    }

    public void removeStudent(Student student){
        usersColl.remove(new BasicDBObject("_id", student.id));
        Consts.DB.getGameFlow().setTotalPlayers();
        saveGameFlow();
    }

    public void removeAdmin(String admin) {
        adminsColl.remove(new BasicDBObject("_id", admin));
    }

    public void removeInput(String input) {
        inputColl.remove(new BasicDBObject("_id", input));
    }

    public void removeMarketComp(String marketName) {
        marketColl.remove(new BasicDBObject("_id", marketName));
    }

    public void removeAllStudents() {
        try (DBCursor cursor = usersColl.find()) {
            while (cursor.hasNext()) {
                usersColl.remove(cursor.next());
                //usersColl.remove(new BasicDBObject("_id",cursor.next().get("_id")));
            }
        }
    }

    public void saveStudent(Student student) {
        removeStudent(student);
        addStudent(student);
    }

    public void saveGameFlow() {
        GameFlow gameFlow = getGameFlow();
        gameFlowColl.remove(new BasicDBObject("_id", gameFlow.name));
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
        id.put(username, getGameFlow().currentYear);
        DBObject person = usersColl.findOne(new BasicDBObject("_id", id));
        if (person == null) {
            return null;
        }
        return morphia.fromDBObject(Student.class, person);
    }

    //@NotNull
    public Student getStudent(String username, int year) {
        HashMap<String, Integer> id = new HashMap<>();
        id.put(username, year);
        DBObject person = usersColl.findOne(new BasicDBObject("_id", id));
        if (person == null) {
            return null;
        }
        return morphia.fromDBObject(Student.class, person);
    }

    //@NotNull
    public Admin getAdmin(String username) {
        DBObject person = adminsColl.findOne(new BasicDBObject("_id", username));
        if (person == null) {
            return null;
        }
        return morphia.fromDBObject(Admin.class, person);
    }

    public GameFlow getGameFlow() {
        DBObject person = gameFlowColl.findOne(new BasicDBObject("_id", "GameFlow"));
        if (person == null) {
            return null;
        }
        return morphia.fromDBObject(GameFlow.class, person);
    }

    public HashMap<String, InputSector> getInputSectorSellers() {
        HashMap<String, InputSector> list = new HashMap<>();
        try (DBCursor cursor = inputColl.find()){
            InputSector inputSector;
            while (cursor.hasNext()) {
                inputSector = morphia.fromDBObject(InputSector.class, cursor.next());
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
                marketingSector = morphia.fromDBObject(MarketingSector.class, cursor.next());
                list.put(marketingSector.getName(), marketingSector);
            }
        }
        return list;
    }

    @NotNull
    public InputSector getInputSeller(String name) {
        DBObject one = inputColl.findOne(new BasicDBObject("_id", name));
        while (one == null) {
            one = inputColl.findOne(new BasicDBObject("_id", name));
        }
        return morphia.fromDBObject(InputSector.class, one);
    }

    @NotNull
    public MarketingSector getMarketingComp(String name) {
        DBObject one = marketColl.findOne(new BasicDBObject("_id", name));
        while (one == null) {
            one = marketColl.findOne(new BasicDBObject("_id", name));
        }
        return morphia.fromDBObject(MarketingSector.class, one);
    }

    public int getTotalPlayers() {
        return (int) usersColl.count(new BasicDBObject("year", getGameFlow().currentYear));
    }

    public int getTotalPlayers(int year) {
        return (int) usersColl.count(new BasicDBObject("year", year));
    }

    public int getTotalAdmins() {
        return (int) adminsColl.count();
    }

   /* public HashMap<Consts.Seed_Type, Integer> getSeedTotals(int year) {

        HashMap<Consts.Seed_Type, Integer> seedTtls = new HashMap<>();
        seedTtls.put(Consts.Seed_Type.EARLY, 0);
        seedTtls.put(Consts.Seed_Type.MID, 0);
        seedTtls.put(Consts.Seed_Type.FULL, 0);
        seedTtls.put(Consts.Seed_Type.TOTAL, 0);
        Student student;
        InputSector studentSector;
        try (DBCursor cursor = openConnection().getCollection("users").find(querySector(Consts.INPUT_SECTOR_NAME, year))) {
            while (cursor.hasNext()) {
                student = morphia.fromDBObject(Student.class, cursor.next());
                studentSector = (InputSector) student.sector;
                seedTtls.put(Consts.Seed_Type.EARLY,
                        seedTtls.get(Consts.Seed_Type.EARLY) + studentSector.getEarlyAmnt());
                seedTtls.put(Consts.Seed_Type.MID,
                        seedTtls.get(Consts.Seed_Type.MID) + studentSector.getMidAmnt());
                seedTtls.put(Consts.Seed_Type.FULL,
                        seedTtls.get(Consts.Seed_Type.FULL) + studentSector.getFullAmnt());
            }
        }

        for(int x : seedTtls.values()){
            seedTtls.put(Consts.Seed_Type.TOTAL,
                    seedTtls.get(Consts.Seed_Type.TOTAL) + x);
        }
        return seedTtls;
    }*/

    public int getSeedsNeeded() {
        return (10 * 100) * 8 + (10 * 250) * 14 + (10 * 500) * 8;
        /*int seed_total = 0;
        try (DBCursor cursor = usersColl.find(new BasicDBObject("year", getGameFlow().currentYear), new BasicDBObject("farm.seedsNeeded", true))) {
            while (cursor.hasNext()) {
                seed_total += morphia.fromDBObject(Student.class, cursor.next()).farm.getSeedsNeeded();
            }
        }
        return seed_total;*/
    }

    public int getBshlsNeeded() {
        return (50 * 100) * 8 + (50 * 250) * 14 + (50 * 500) * 8;
        /*int bshl_ttl = 0;
        try (DBCursor cursor = usersColl.find(new BasicDBObject("year", getGameFlow().currentYear))) {
            while (cursor.hasNext()) {
                bshl_ttl += morphia.fromDBObject(Student.class, cursor.next()).farm.getTtlBushels();
            }
        }
        return bshl_ttl;*/
    }

    public HashMap<Consts.Farm_Size, Integer> numInEachFarm() {
        HashMap<Consts.Farm_Size, Integer> numRes = new HashMap<>();

        try {
            numRes.put(Consts.Farm_Size.SMALL_FARM,
                    (int) usersColl
                            .count(new BasicDBObject("year", getGameFlow().currentYear).append("farm.size", Consts.Farm_Size.SMALL_FARM.toValue())));
            numRes.put(Consts.Farm_Size.MED_FARM,
                    (int) usersColl
                            .count(new BasicDBObject("year", getGameFlow().currentYear).append("farm.size", Consts.Farm_Size.MED_FARM.toValue())));
            numRes.put(Consts.Farm_Size.LARGE_FARM, (int) usersColl
                    .count(new BasicDBObject("year", getGameFlow().currentYear).append("farm.size", Consts.Farm_Size.LARGE_FARM.toValue())));
            numRes.put(Consts.Farm_Size.NO_FARM,
                    (int) usersColl
                            .count(new BasicDBObject("year", getGameFlow().currentYear).append("farm.size", Consts.Farm_Size.NO_FARM.toValue())));
        } catch (NullPointerException e) {
            return numRes;
        }
        return numRes;
    }

    public void addStudent(Student student) {
        usersColl.insert(morphia.toDBObject(student));
        getGameFlow().setTotalPlayers();
        saveGameFlow();
    }

    public void addAdmin(Admin admin) {
        adminsColl.insert(morphia.toDBObject(admin));
    }

    public void addGameFlow(GameFlow gameFlow) {
        gameFlowColl.insert(morphia.toDBObject(gameFlow));
    }

    public void addInputComp(InputSector inputComp) {
        try {
            inputColl.insert(morphia.toDBObject(inputComp));
        } catch (MongoException ignored) {

        }
    }

    public void addMarketComp(MarketingSector marketingSector) {
        marketColl.insert(morphia.toDBObject(marketingSector));
    }

    public void yearChange(int dir) {
        int newYr = getGameFlow().currentYear;
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
        try (DBCursor cursor = usersColl.find(new BasicDBObject("year", getGameFlow().currentYear))) {
            while (cursor.hasNext()) {
                list.add(morphia.fromDBObject(Student.class, cursor.next()));
            }
        }
        return list;
    }

    ArrayList<Admin> getAllAdmins() {
        ArrayList<Admin> list = new ArrayList<>();
        try (DBCursor cursor = adminsColl.find()) {
            while (cursor.hasNext()) {
                list.add(morphia.fromDBObject(Admin.class, cursor.next()));
            }
        }
        return list;
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
