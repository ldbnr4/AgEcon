import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Lorenzo on 10/6/2015.
 */
public class MongoDBConnection {
    private static MongoDBConnection ourInstance = new MongoDBConnection();
    MongoClient mongoClient = null;
    private String dbuname = "admin";
    private String database = "agdb";
    private String password = "password";

    private MongoDBConnection() {
    }

    public static MongoDBConnection getInstance() {
        return ourInstance;
    }

    public void updateStudent(Student student) {
        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(Sector.class);
        DB db = openConnection();
        DBCollection coll = db.getCollection("users");
        coll.remove(new BasicDBObject("_id", student.uName));
        DBObject studentObj = morphia.toDBObject(student);
        coll.insert(studentObj);
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
        //Student student = new Student(persMap.get("User Name").toString(),persMap.get("Password").toString(),persMap.get("Salt").toString(), (Sector) persMap.get("Sector"));
        Student student = morphia.fromDBObject(Student.class, person);
        //morphia.fromDBObject()
        //System.out.println(student);
        return student;
    }

    public HashMap<String, Integer> numInSectors() {
        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(Sector.class);

        DB db = openConnection();
        DBCollection coll = db.getCollection("users");
        DBCursor cursor = coll.find();
        HashMap<String, Integer> numRes = new HashMap<>();
        numRes.put(GameDriver.INPUT_SECTOR_NAME, 0);
        numRes.put(GameDriver.FARM_SECTOR_NAME, 0);
        numRes.put(GameDriver.FOOD_SECTOR_NAME, 0);
        try {
            while (cursor.hasNext()) {
                Student student = morphia.fromDBObject(Student.class, cursor.next());
                //System.out.println(student.uName.matches("[a-zA-Z]+"));
                numRes.put(student.sector.name, numRes.get(student.sector.name) + 1);
            }
        } finally {
            cursor.close();
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
        DBCursor cursor = coll.find();
        HashMap<Character, Integer> numRes = new HashMap<>();
        numRes.put(GameDriver.SMALL_FARM, 0);
        numRes.put(GameDriver.MED_FARM, 0);
        numRes.put(GameDriver.LARGE_FARM, 0);
        FarmSector studentSector;
        Student student;
        try {
            while (cursor.hasNext()) {
                student = morphia.fromDBObject(Student.class, cursor.next());
                //System.out.println(student.uName.matches("[a-zA-Z]+"));
                if (student.sector.name.equals(GameDriver.FARM_SECTOR_NAME)) {
                    studentSector = (FarmSector) student.sector;
                    numRes.put(studentSector.farm.size, numRes.get(studentSector.farm.size) + 1);
                }
                //numRes.put(student.sector, numRes.get(student.sector.name) + 1);
            }
        } finally {
            cursor.close();
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
        Datastore ds = morphia.createDatastore(this.mongoClient, "users");
        //ds.save(student);
        DBCollection coll = db.getCollection("users");
        //BasicDBObject doc = new BasicDBObject("Student Name", morphia.toString());
        DBObject studentObj = morphia.toDBObject(student);
        //morphia.fromDBObject(Student.class,studentObj);
        //db.getCollection("users").save(studentObj);
        coll.insert(studentObj);
        closeConnection();
    }

    public boolean userInDB(String name) {
        DB db = openConnection();

        BasicDBObject query = new BasicDBObject("_id", name);
        DBCollection coll = db.getCollection("users");
        DBCursor cursor = coll.find(query);
        try {
            if (cursor.hasNext()) {
                return true;
            }
        } finally {
            cursor.close();
        }
        closeConnection();

        return false;
    }

    private DB openConnection() {
        MongoCredential credential = MongoCredential.createCredential(this.dbuname, this.database, this.password.toCharArray());
        try {
            this.mongoClient = new MongoClient(new ServerAddress("ds051543.mongolab.com", 51543), Arrays.asList(credential));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return this.mongoClient.getDB("agdb");

    }

    private void closeConnection() {
        this.mongoClient.close();
    }
}
