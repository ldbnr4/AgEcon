import com.mongodb.*;
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
    MongoClient mongoClient = null;
    Morphia morphia = new Morphia();
    DB db = openConnection();
    DBCollection gameFlowColl = db.getCollection("gameFlow");
    DBCollection adminsColl = db.getCollection("admins");
    DBCollection usersColl = db.getCollection("users");
    DBCollection inputColl = db.getCollection("inputSector");

    private MongoDBConnection() {
        morphia.map(Student.class).map(FarmSector.class).map(GameFlow.class).map(Admin.class).map(InputSector.class);
    }

    public static MongoDBConnection getInstance() {
        return ourInstance;
    }

    /*public int countFarmPpl(){
        DB db = openConnection();
        int count = (int) db.getCollection("users").count(new BasicDBObject("sector.className", FarmSector.class.getName()));

        return count;
    }*/

    public void removeStudent(Student student){
        usersColl.remove(new BasicDBObject("_id", student.id));
        Consts.GAME_FLOW.setTotalPlayers();
        saveGameFlow();
    }

    public void removeAdmin(String admin) {
        adminsColl.remove(new BasicDBObject("_id", admin));
    }

    public void saveStudent(Student student) {
        removeStudent(student);
        addStudent(student);
    }

    public void saveGameFlow() {
        gameFlowColl.remove(new BasicDBObject("_id", Consts.GAME_FLOW.name));
        addGameFlow(Consts.GAME_FLOW);
    }

    public Student getStudent(String username, int year) {
        HashMap<String, Integer> id = new HashMap<>();
        id.put(username, year);
        DBObject person = usersColl.findOne(new BasicDBObject("_id", id));
        if (person == null) {
            return null;
        }
        return morphia.fromDBObject(Student.class, person);
    }

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

    public HashMap<String, InputSector> getInputSectorSellers(int year) {
        HashMap<String, InputSector> list = new HashMap<>();
        try (DBCursor cursor = inputColl.find(new BasicDBObject("year", year))) {
            InputSector inputSector;
            while (cursor.hasNext()) {
                inputSector = morphia.fromDBObject(InputSector.class, cursor.next());
                list.put(inputSector.name, inputSector);
            }
        }
        return list;
    }

    public int getTotalPlayers(int year) {
        return (int) usersColl.count(new BasicDBObject("year", year));
    }

    public int getTotalAdmins() {
        return (int) adminsColl.count();
    }

   /* public HashMap<Consts.Seed_Name, Integer> getSeedTotals(int year) {

        HashMap<Consts.Seed_Name, Integer> seedTtls = new HashMap<>();
        seedTtls.put(Consts.Seed_Name.EARLY, 0);
        seedTtls.put(Consts.Seed_Name.MID, 0);
        seedTtls.put(Consts.Seed_Name.FULL, 0);
        seedTtls.put(Consts.Seed_Name.TOTAL, 0);
        Student student;
        InputSector studentSector;
        try (DBCursor cursor = openConnection().getCollection("users").find(querySector(Consts.INPUT_SECTOR_NAME, year))) {
            while (cursor.hasNext()) {
                student = morphia.fromDBObject(Student.class, cursor.next());
                studentSector = (InputSector) student.sector;
                seedTtls.put(Consts.Seed_Name.EARLY,
                        seedTtls.get(Consts.Seed_Name.EARLY) + studentSector.getEarlyAmnt());
                seedTtls.put(Consts.Seed_Name.MID,
                        seedTtls.get(Consts.Seed_Name.MID) + studentSector.getMidAmnt());
                seedTtls.put(Consts.Seed_Name.FULL,
                        seedTtls.get(Consts.Seed_Name.FULL) + studentSector.getFullAmnt());
            }
        }

        for(int x : seedTtls.values()){
            seedTtls.put(Consts.Seed_Name.TOTAL,
                    seedTtls.get(Consts.Seed_Name.TOTAL) + x);
        }
        return seedTtls;
    }*/

    public int getSeedsNeeded(int year) {
        int seed_total = 0;
        try (DBCursor cursor = usersColl.find(new BasicDBObject("year", year), new BasicDBObject("sector.farm.seedsNeeded", true))) {
            while (cursor.hasNext()) {
                seed_total += morphia.fromDBObject(Student.class, cursor.next()).sector.getSeedsNeeded();
            }
        }
        return seed_total;
    }

    public HashMap<Character, Integer> numInEachFarm(int year) {
        HashMap<Character, Integer> numRes = new HashMap<>();

        numRes.put(Consts.SMALL_FARM,
                (int) usersColl
                        .count(new BasicDBObject("year", year).append("sector.farm.size", Consts.SMALL_FARM)));
        numRes.put(Consts.MED_FARM,
                (int) usersColl
                        .count(new BasicDBObject("year", year).append("sector.farm.size", Consts.MED_FARM)));
        numRes.put(Consts.LARGE_FARM,
                (int) usersColl
                        .count(new BasicDBObject("year", year).append("sector.farm.size", Consts.LARGE_FARM)));
        numRes.put(Consts.NO_FARM,
                (int) usersColl
                        .count(new BasicDBObject("year", year).append("sector.farm.size", Consts.NO_FARM)));
        return numRes;
    }

    public void addStudent(Student student) {
        usersColl.insert(morphia.toDBObject(student));
        Consts.GAME_FLOW.setTotalPlayers();
        saveGameFlow();
    }

    public void addAdmin(Admin admin) {
        adminsColl.insert(morphia.toDBObject(admin));
    }

    public void addGameFlow(GameFlow gameFlow) {
        gameFlowColl.insert(morphia.toDBObject(gameFlow));
    }

    public void addInputComp(InputSector inputComp) {
        inputColl.insert(morphia.toDBObject(inputComp));
    }

    public void yearChange(int dir) {
        int newYr = Consts.GAME_FLOW.currentYear;
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

    ArrayList<Student> getAllStudents(int year) {
        ArrayList<Student> list = new ArrayList<>();
        Morphia morphia = new Morphia().map(Student.class).map(FarmSector.class);
        Student student;

        try (DBCursor cursor = usersColl.find(new BasicDBObject("year", year))) {
            while (cursor.hasNext()) {
                student = morphia.fromDBObject(Student.class, cursor.next());
                list.add(student);
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

    private BasicDBObject querySector(String sector, int year) {
        return new BasicDBObject("year", year).append("sector.name", sector);
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
