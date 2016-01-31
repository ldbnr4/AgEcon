package AgEgonPackage;

import java.util.HashMap;

/**
 * Created by Lorenzo on 9/22/2015.
 *
 */

public class Student {
    public HashMap<String, Integer> id;
    public String uName;
    public Farm farm;
    protected String password;
    protected String salt;
    private int year;
    private Consts.Student_Stage stage;

    public Student() {}

    public Student(String name, String pass, Farm farm) {
        this.uName = name;
        HashMap<String, String> passInfo = EncryptPassword.encrypt(pass);
        this.password = passInfo.get("password");
        this.salt = passInfo.get("salt");
        this.farm = farm;
        this.year = Consts.DB.getGameFlow().currentYear;
        setId(this.year);
        setStage(Consts.Student_Stage.Select_Size);
    }

    public void setId(int year) {
        this.id = new HashMap<>();
        this.year = year;
        id.put(this.uName, year);
    }

    public Consts.Student_Stage getStage() {
        return stage;
    }

    public void setStage(Consts.Student_Stage stage) {
        this.stage = stage;
    }
}
