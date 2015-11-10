import com.mongodb.*;
import org.mongodb.morphia.Morphia;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

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
        coll.remove(new BasicDBObject("_id", GameDriver.GAME_FLOW.name));
        DBObject gameFlowObj = morphia.toDBObject(GameDriver.GAME_FLOW);
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

    public HashMap<String, Student> getInputSector() {
        HashMap<String, Student> list = new HashMap<>();
        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(Sector.class);

        DB db = openConnection();
        DBCollection coll = db.getCollection("users");
        try (DBCursor cursor = coll.find()) {
            while (cursor.hasNext()) {
                Student student = morphia.fromDBObject(Student.class, cursor.next());
                if (Objects.equals(student.sector.name, GameDriver.INPUT_SECTOR_NAME)) {
                    list.put(student.uName, student);
                }
            }
        }
        return list;
    }

    public int getTotalPlayers(int year) {
        DB db = openConnection();

        return (int) db.getCollection("users").count(new BasicDBObject("year", year));
    }

    public HashMap<GameDriver.Seed_Name, Integer> getSeedTotals(){
        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(Sector.class);
        DB db = openConnection();
        DBCollection coll = db.getCollection("users");
        HashMap<GameDriver.Seed_Name, Integer> seedTtls = new HashMap<>();
        seedTtls.put(GameDriver.Seed_Name.EARLY, 0);
        seedTtls.put(GameDriver.Seed_Name.MID, 0);
        seedTtls.put(GameDriver.Seed_Name.FULL, 0);
        seedTtls.put(GameDriver.Seed_Name.TOTAL, 0);
        Student student;
        InputSector studentSector;
        try (DBCursor cursor = coll.find()) {
            while (cursor.hasNext()) {
                student = morphia.fromDBObject(Student.class, cursor.next());
                if(student.sector instanceof InputSector) {
                    studentSector = (InputSector) student.sector;
                    seedTtls.put(GameDriver.Seed_Name.EARLY,
                            seedTtls.get(GameDriver.Seed_Name.EARLY) + studentSector.getEarlyAmnt());
                    seedTtls.put(GameDriver.Seed_Name.MID,
                            seedTtls.get(GameDriver.Seed_Name.MID) + studentSector.getMidAmnt());
                    seedTtls.put(GameDriver.Seed_Name.FULL,
                            seedTtls.get(GameDriver.Seed_Name.FULL) + studentSector.getFullAmnt());
                }
            }
        }

        for(int x : seedTtls.values()){
            seedTtls.put(GameDriver.Seed_Name.TOTAL,
                    seedTtls.get(GameDriver.Seed_Name.TOTAL) + x);
        }
        return seedTtls;
    }

    public HashMap<String, Integer> numInSectors() {
        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(Sector.class);

        DB db = openConnection();
        DBCollection coll = db.getCollection("users");
        HashMap<String, Integer> numRes = new HashMap<>();
        numRes.put(GameDriver.INPUT_SECTOR_NAME, 0);
        numRes.put(GameDriver.FARM_SECTOR_NAME, 0);
        numRes.put(GameDriver.FOOD_SECTOR_NAME, 0);
        try (DBCursor cursor = coll.find()) {
            while (cursor.hasNext()) {
                Student student = morphia.fromDBObject(Student.class, cursor.next());
                //System.out.println(student.uName.matches("[a-zA-Z]+"));
                numRes.put(student.sector.name, numRes.get(student.sector.name) + 1);
            }
        }
        //System.out.println(numRes);
        return numRes;
    }

    public HashMap<Character, Integer> numInFarms() {
        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(Sector.class);

        DB db = openConnection();
        DBCollection coll = db.getCollection("users");
        HashMap<Character, Integer> numRes = new HashMap<>();
        numRes.put(GameDriver.SMALL_FARM, 0);
        numRes.put(GameDriver.MED_FARM, 0);
        numRes.put(GameDriver.LARGE_FARM, 0);
        numRes.put(GameDriver.NO_FARM, 0);
        FarmSector studentSector;
        Student student;
        try (DBCursor cursor = coll.find()) {
            while (cursor.hasNext()) {
                student = morphia.fromDBObject(Student.class, cursor.next());
                //System.out.println(student.uName.matches("[a-zA-Z]+"));
                if (student.sector.name.equals(GameDriver.FARM_SECTOR_NAME)) {
                    studentSector = (FarmSector) student.sector;
                    if(studentSector.farm != null) {
                        numRes.put(studentSector.farm.size, numRes.get(studentSector.farm.size) + 1);
                    }
                    else {
                        System.out.println(student.uName);
                    }
                }
                //numRes.put(student.sector, numRes.get(student.sector.name) + 1);
            }
        }
        //System.out.println(numRes);
        return numRes;
    }

    public void addStudent(Student student) {
        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(Sector.class);
        DB db = openConnection();
        DBCollection coll = db.getCollection("users");
        DBObject studentObj = morphia.toDBObject(student);
        coll.insert(studentObj);
        GameDriver.GAME_FLOW.setTotalPlayers();
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

    public void newYearStudents(int year){

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
