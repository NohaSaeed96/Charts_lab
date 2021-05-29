import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        TitanicPassenger passlist = new TitanicPassenger();
        List<TitanicPassenger> mylist =passlist.getPassengersFromJsonFile();
        passlist.graphPassengerAges(mylist);
        passlist.graphPassengerClass(mylist);
        passlist.graphPassengerSurvived(mylist);
        passlist.graphPassengerSurvivedbygender(mylist);


    }
}
