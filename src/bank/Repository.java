package bank;

import java.util.concurrent.atomic.AtomicInteger;

public class Repository {
    private AtomicInteger balance = new AtomicInteger(10_000);        // балан счета

    public synchronized void incrementBalance(int amountOfMoney) {
        int newBalance = this.balance.get() + amountOfMoney;
        this.balance.set(newBalance);
    }

    public int getBalance() {
        return balance.intValue();
    }

    public synchronized void decrementBalance(int amountOfMoney) {
        int newBalance = this.balance.get() - amountOfMoney;
        this.balance.set(newBalance);
    }
}
