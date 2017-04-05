import bank.Repository;
import client.ClientGenerator;
import operator.Operator;

public class App {
    private static int COUNT_OPERATOR = 5;

    public static void main(String[] args) {
        Repository repository = new Repository();

        for (int i = 0; i < COUNT_OPERATOR; i++)
            (new Thread(new Operator(repository))).start();

        (new Thread(new ClientGenerator())).start();
    }
}
