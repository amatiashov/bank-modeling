package client;


import java.util.concurrent.ArrayBlockingQueue;

public class ClientQueue {
    private final static ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(50);

    public static ArrayBlockingQueue getArrayBlockingQueue(){
        return arrayBlockingQueue;
    }
}
