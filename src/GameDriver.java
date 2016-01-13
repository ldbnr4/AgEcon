/**
 * Created by Lorenzo on 9/18/2015.
 *
 */
public class GameDriver {

    public static void main(String[] args) {
        if (Consts.DB.getGameFlow() == null) {
            Consts.DB.addGameFlow(new GameFlow());
        }

        new WelcomePage();

        //new MarketingDealsPage(Consts.DB.getStudent("farmerJoe"));
        //new SoftTestPage();

        //Student student =  Consts.DB.getStudent("farmerJoe");
        //student.setStage(Consts.Student_Stage.Sell_Yields);

        /*for(BushelLedgerEntry entry :student.farm.getBshlLedger()){
            entry.setAmount(entry.getAmount()*2);
        }*/
        //Consts.DB.saveStudent(student);
    }


}
