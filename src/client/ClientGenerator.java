package client;


import java.util.concurrent.ArrayBlockingQueue;

public class ClientGenerator implements Runnable{
    private final int MAX_MONEY = 1000_000;              // максимальное число денег, с которыми может оперировать клиент
    private final int MAX_SERVICE_TIME = 10_000;        // максимальное вермя обслуживания
    private final int MAX_GENERATE_INTERVAL = 2_000;    // максимальный интервал генерации нового клиента


    @Override
    public void run() {
        ArrayBlockingQueue arrayBlockingQueue = ClientQueue.getArrayBlockingQueue();
        while (true) {
            Client client = genNewClient();
            try {
                arrayBlockingQueue.put(client);
                synchronized (arrayBlockingQueue){
                    arrayBlockingQueue.notify();
                }
                System.out.println("New client was created: " + client.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep((long) (Math.random() * MAX_GENERATE_INTERVAL));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Client genNewClient(){
        int amountOfMoney = (int) (Math.random() * MAX_MONEY);
        int serviceTime = (int) (Math.random() * MAX_SERVICE_TIME);
        OperationWithMoney operationWithMoney =
                Math.random() > 0.5 ? OperationWithMoney.PULL : OperationWithMoney.PUSH;
        return new Client(amountOfMoney, serviceTime, operationWithMoney);
    }
}
