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