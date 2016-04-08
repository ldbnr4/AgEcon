/*
 * © 2015, by The Curators of University of Missouri, All Rights Reserved
 */

package AgEconPackage;

/**
 * Created by Lorenzo on 9/18/2015.
 *
 */
public class GameDriver {

    public static void main(String[] args) {
        if (Consts.DB.getGameFlow() == null) {
            Consts.DB.addGameFlow(new GameFlow("GameFlow"));
        }

        //new WaitPage();
        new WelcomePage();
        new SoftTestPage();
        new AdminDecisionPage(Consts.DB.getAdmin("ldbnr4"));
        /*Student realFarmer = Consts.DB.getStudent("realFarmer");
        Farm realFarmerSector = (Farm) realFarmer.getSector();
        realFarmerSector.plantAction();
        realFarmer.addReplaceSector(realFarmerSector);
        Consts.DB.saveStudent(realFarmer);*/
        /*Student student = Consts.DB.getStudent("tester2");
        Student student1 = new Student("name", "pass");
        student1.addReplaceSector(new MarketingSector("name", getEarlyHarvDt(), 5.5, 1000));
        DB.saveStudent(student1);
        student1 = Consts.DB.getStudent("name");
        Sector sector = student.getSector();
        Sector sector1 = student1.getSector();

        System.out.println(sector+"\n"+sector1);*/

        //Consts.DB.addAdmin(new Admin("admin", "password"));
        //Consts.DB.addStudent(new Student("ldbnr4","password", new Farm()));
        //System.out.println(Consts.DB.getTotalPlayers(2016));

        //Student student = Consts.DB.getStudent("tester");
        //Admin admin = Consts.DB.getAdmin("ldbnr4");


        //for(int i=0;i<40;i++){
        //new BuySeedsPage(student);
        //}
        //System.out.println("Done");
        //new MarketingDealsPage(student);
        //new ViewSeedOrdersPage(student);
        //new EndofSeasonPage(student);

        //new SoftTestPage();
        //new AdminDecisionPage(admin);
    }


}

/*
 * Copyright (c) 2015, by The Curators of University of Missouri, All Rights Reserved
 */