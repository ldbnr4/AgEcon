import com.mongodb.*;
import org.mongodb.morphia.Morphia;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Lorenzo on 10/6/2015.
 */
public class MongoDBConnection {
    private static MongoDBConnection ourInstance = new MongoDBConnection();
    MongoClient mongoClient = null;

    private MongoDBConnection() {
    }

    public static MongoDBConnection getInstance() {
        return ourInstance;
    }

    public void saveStudent(Student student) {
        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(Sector.class);
        DB db = openConnection();
        DBCollection coll = db.getCollection("users");
        coll.remove(new BasicDBObject("_id", student.uName));
        DBObject studentObj = morphia.toDBObject(student);
        coll.insert(studentObj);
        closeConnection();

    }

    public void saveGameFlow() {
        Morphia morphia = new Morphia();
        morphia.map(GameFlow.class);
        DB db = openConnection();
        DBCollection coll = db.getCollection("gameFlow");
        coll.remove(new BasicDBObject("_id", GameDriver.GAME_FLOW.name));
        DBObject gameFlowObj = morphia.toDBObject(GameDriver.GAME_FLOW);
        coll.insert(gameFlowObj);
        closeConnection();
    }

    public Student getStudent(String username) {
        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(Sector.class);

        DB db = openConnection();
        DBCollection coll = db.getCollection("users");
        BasicDBObject query = new BasicDBObject("_id", username);
        DBObject person = coll.findOne(query);
        //Map persMap = person.toMap();
        closeConnection();
        if (person == null) {
            return null;
        }
        //Student student = new Student(persMap.get("User Name").toString(),persMap.get("Password").toString(),persMap.get("Salt").toString(), (Sector) persMap.get("Sector"));
        //morphia.fromDBObject()
        //System.out.println(student);
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
        closeConnection();
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
        closeConnection();
        //Student student = new Student(persMap.get("User Name").toString(),persMap.get("Password").toString(),persMap.get("Salt").toString(), (Sector) persMap.get("Sector"));
        //morphia.fromDBObject()
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

    public int getTotalPlayers(int startingYear) {
        DB db = openConnection();
        int count = (int) db.getCollection("users").count(new BasicDBObject("startingYear", startingYear));
        closeConnection();

        return count;
    }

    public int getCurrentPlayers(int currentYear) {
        DB db = openConnection();
        int count = (int) db.getCollection("users").count(new BasicDBObject("currentYear", currentYear));
        closeConnection();

        return count;
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
        closeConnection();
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
        FarmSector studentSector;
        Student student;
        try (DBCursor cursor = coll.find()) {
            while (cursor.hasNext()) {
                student = morphia.fromDBObject(Student.class, cursor.next());
                //System.out.println(student.uName.matches("[a-zA-Z]+"));
                if (student.sector.name.equals(GameDriver.FARM_SECTOR_NAME)) {
                    studentSector = (FarmSector) student.sector;
                    numRes.put(studentSector.farm.size, numRes.get(studentSector.farm.size) + 1);
                }
                //numRes.put(student.sector, numRes.get(student.sector.name) + 1);
            }
        }
        //System.out.println(numRes);
        closeConnection();
        return numRes;
    }

    public void addUser(Student student) {
        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(Sector.class);
        //System.out.println(student);
        DB db = openConnection();
        //Datastore ds = morphia.createDatastore(this.mongoClient, "users");
        //ds.save(student);
        DBCollection coll = db.getCollection("users");
        //BasicDBObject doc = new BasicDBObject("Student Name", morphia.toString());
        DBObject studentObj = morphia.toDBObject(student);
        //morphia.fromDBObject(Student.class,studentObj);
        //db.getCollection("users").save(studentObj);
        coll.insert(studentObj);
        closeConnection();
    }

    public void addAdmin(Admin admin) {
        Morphia morphia = new Morphia();
        morphia.map(Admin.class);
        //System.out.println(student);
        DB db = openConnection();
        //Datastore ds = morphia.createDatastore(this.mongoClient, "admins");
        //ds.save(student);
        DBCollection coll = db.getCollection("admins");
        //BasicDBObject doc = new BasicDBObject("Student Name", morphia.toString());
        DBObject adminObj = morphia.toDBObject(admin);
        //morphia.fromDBObject(Student.class,studentObj);
        //db.getCollection("users").save(studentObj);
        coll.insert(adminObj);
        closeConnection();
    }

    public void addGameFlow(GameFlow gameFlow) {
        Morphia morphia = new Morphia();
        morphia.map(GameFlow.class);
        //System.out.println(student);
        DB db = openConnection();
        //Datastore ds = morphia.createDatastore(this.mongoClient, "admins");
        //ds.save(student);
        DBCollection coll = db.getCollection("gameFlow");
        //BasicDBObject doc = new BasicDBObject("Student Name", morphia.toString());
        DBObject gameFlowObj = morphia.toDBObject(gameFlow);
        //morphia.fromDBObject(Student.class,studentObj);
        //db.getCollection("users").save(studentObj);
        coll.insert(gameFlowObj);
        closeConnection();
    }

    private DB openConnection() {
        String dbuname = "admin";
        String database = "agdb";
        String password = "password";
        MongoCredential credential = MongoCredential.createCredential(dbuname, database, password.toCharArray());
        try {
            this.mongoClient = new MongoClient(new ServerAddress("ds051543.mongolab.com", 51543), Collections.singletonList(credential));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return this.mongoClient.getDB("agdb");

    }

    private void closeConnection() {
        this.mongoClient.close();
    }
}
