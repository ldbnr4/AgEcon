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

    private MongoDBConnection() {
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
        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(Sector.class);
        openConnection().getCollection("users").remove(new BasicDBObject("_id", student.uName).append("year", student.getYear()));
    }

    public void removeAdmin(String admin) {
        openConnection().getCollection("admins").remove(new BasicDBObject("_id", admin));
    }

    public void saveStudent(Student student) {
        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(Sector.class);
        removeStudent(student);
        addStudent(student);
    }

    public void saveGameFlow() {
        Morphia morphia = new Morphia();
        morphia.map(GameFlow.class);
        DB db = openConnection();
        DBCollection coll = db.getCollection("gameFlow");
        coll.remove(new BasicDBObject("_id", Consts.GAME_FLOW.name));
        DBObject gameFlowObj = morphia.toDBObject(Consts.GAME_FLOW);
        coll.insert(gameFlowObj);
    }

    public Student getStudent(String username, int year) {
        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(Sector.class);

        DBObject person = openConnection().getCollection("users").findOne(new BasicDBObject("_id", username).append("year", year));
        if (person == null) {
            return null;
        }
        return morphia.fromDBObject(Student.class, person);
    }

    public Admin getAdmin(String username) {
        Morphia morphia = new Morphia();
        morphia.map(Admin.class);

        DB db = openConnection();
        DBCollection coll = db.getCollection("admins");
        BasicDBObject query = new BasicDBObject("_id", username);
        DBObject person = coll.findOne(query);
        //Map persMap = person.toMap();
        if (person == null) {
            return null;
        }
        //Student student = new Student(persMap.get("User Name").toString(),persMap.get("Password").toString(),persMap.get("Salt").toString(), (Sector) persMap.get("Sector"));
        //morphia.fromDBObject()
        //System.out.println(person.toString());

        return morphia.fromDBObject(Admin.class, person);
    }

    public GameFlow getGameFlow() {
        Morphia morphia = new Morphia();
        morphia.map(GameFlow.class);
        DB db = openConnection();
        DBCollection coll = db.getCollection("gameFlow");
        BasicDBObject query = new BasicDBObject("_id", "GameFlow");
        DBObject person = coll.findOne(query);
        //Map persMap = person.toMap();
        if (person == null) {
            return null;
        }
        return morphia.fromDBObject(GameFlow.class, person);
    }

    public HashMap<String, Student> getInputSectorStudents(int year) {
        HashMap<String, Student> list = new HashMap<>();
        Morphia morphia = new Morphia().map(Student.class).map(Sector.class);
        Student student;

        try (DBCursor cursor = openConnection().getCollection("users").find(querySector(Consts.INPUT_SECTOR_NAME, year))) {
            while (cursor.hasNext()) {
                student = morphia.fromDBObject(Student.class, cursor.next());
                    list.put(student.uName, student);
            }
        }
        return list;
    }

    public int getTotalPlayers(int year) {
        return (int) openConnection().getCollection("users").count(new BasicDBObject("year", year));
    }

    public int getTotalAdmins() {
        return (int) openConnection().getCollection("admins").count();
    }

    public HashMap<Consts.Seed_Name, Integer> getSeedTotals(int year) {
        Morphia morphia = new Morphia().map(Student.class).map(Sector.class);
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
    }

    public HashMap<Character, Integer> numInEachFarm(int year) {
        HashMap<Character, Integer> numRes = new HashMap<>();

        numRes.put(Consts.SMALL_FARM,
                (int) openConnection().getCollection("users")
                        .count(querySector(Consts.FARM_SECTOR_NAME, year).append("sector.farm.size", Consts.SMALL_FARM)));
        numRes.put(Consts.MED_FARM,
                (int) openConnection().getCollection("users")
                        .count(querySector(Consts.FARM_SECTOR_NAME, year).append("sector.farm.size", Consts.MED_FARM)));
        numRes.put(Consts.LARGE_FARM,
                (int) openConnection().getCollection("users")
                        .count(querySector(Consts.FARM_SECTOR_NAME, year).append("sector.farm.size", Consts.LARGE_FARM)));
        numRes.put(Consts.NO_FARM,
                (int) openConnection().getCollection("users")
                        .count(querySector(Consts.FARM_SECTOR_NAME, year).append("sector.farm.size", Consts.NO_FARM)));
        return numRes;
    }

    public void addStudent(Student student) {
        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(Sector.class);
        DB db = openConnection();
        DBCollection coll = db.getCollection("users");
        DBObject studentObj = morphia.toDBObject(student);
        coll.insert(studentObj);
        Consts.GAME_FLOW.setTotalPlayers();
        saveGameFlow();
    }

    public void addAdmin(Admin admin) {
        DBObject adminObj = new Morphia().map(Admin.class).toDBObject(admin);
        openConnection().getCollection("admins").insert(adminObj);
    }

    public void addGameFlow(GameFlow gameFlow) {
        DBObject gameFlowObj = new Morphia().map(GameFlow.class).toDBObject(gameFlow);
        openConnection().getCollection("gameFlow").insert(gameFlowObj);
    }

    public void yearChange(int dir) {
        int newYr = Consts.GAME_FLOW.currentYear;
        int oldYr = newYr + dir;
        int newTtl = getTotalPlayers(newYr);
        int oldTtl = getTotalPlayers(oldYr);

        if (oldTtl > newTtl) {
            try (DBCursor users = openConnection().getCollection("users").find(new BasicDBObject("year", oldYr))) {
                Student student;
                while (users.hasNext()) {
                    if (getStudent((String) users.next().get("_id"), newYr) == null) {
                        student = getStudent((String) users.next().get("_id"), oldYr);
                        student.setYear(newYr);
                        addStudent(student);
                    }
                }
            }
        } else if (oldTtl < newTtl) {
            try (DBCursor users = openConnection().getCollection("users").find(new BasicDBObject("year", newYr))) {
                Student student;
                while (users.hasNext()) {
                    if (getStudent((String) users.next().get("_id"), oldYr) == null) {
                        student = getStudent((String) users.next().get("_id"), newYr);
                        student.setYear(oldYr);
                        addStudent(student);
                    }
                }
            }
        }
    }

    ArrayList<Student> getAllStudents(int year) {
        ArrayList<Student> list = new ArrayList<>();
        Morphia morphia = new Morphia().map(Student.class).map(Sector.class);
        Student student;

        try (DBCursor cursor = openConnection().getCollection("users").find(new BasicDBObject("year", year))) {
            while (cursor.hasNext()) {
                student = morphia.fromDBObject(Student.class, cursor.next());
                list.add(student);
            }
        }
        return list;
    }

    ArrayList<Admin> getAllAdmins() {
        ArrayList<Admin> list = new ArrayList<>();
        Morphia morphia = new Morphia().map(Admin.class);
        Admin admin;

        try (DBCursor cursor = openConnection().getCollection("admins").find()) {
            while (cursor.hasNext()) {
                admin = morphia.fromDBObject(Admin.class, cursor.next());
                list.add(admin);
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
