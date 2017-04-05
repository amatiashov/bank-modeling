package operator;

import bank.Repository;
import client.Client;
import client.ClientQueue;
import client.OperationWithMoney;

import java.util.concurrent.ArrayBlockingQueue;

public class Operator implements Runnable{
    Repository repository;
    ArrayBlockingQueue clientQueue = ClientQueue.getArrayBlockingQueue();

    public Operator(Repository repository){
        this.repository = repository;
    }

    @Override
    public void run() {
        while (true){
            if (clientQueue.isEmpty())
                synchronized (clientQueue){
                    try {
                        clientQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            Client client = (Client) clientQueue.remove();
            System.out.println("Оператор: " + this.hashCode() + " Обслуживается клиент " + client.toString());
            if (client.getOperationWithMoney() == OperationWithMoney.PUSH) {
                repository.incrementBalance(client.getAmountOfMoney());
                System.out.println("Баланс счета после пополнения = " + repository.getBalance());
            }
            else if (client.getOperationWithMoney() == OperationWithMoney.PULL){
                if (repository.getBalance() >= client.getAmountOfMoney()) {
                    repository.decrementBalance(client.getAmountOfMoney());
                    System.out.println("Баланс счета после списания = " + repository.getBalance());
                }
                else {
                    System.err.println("Недостаточно средств на счете! Баланс - " +
                    repository.getBalance() + " Требуется - " + client.getAmountOfMoney() +
                    " Денег не будет :-)");
                }

            }
            try {
                Thread.sleep(client.getServiceTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
