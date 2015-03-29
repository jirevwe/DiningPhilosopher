package diningphilosopher;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Chopstick class: Represents a Chopstick
 * Each Chopstick is a lock.
 * 
 * @author Steel
 */
public class Chopstick {
    private final Lock lock;   //the lock on the Chopstick
    private final int id;      //the Chopstick's id number for referential purposes

    /**
     * creates a new Chopstick
     * @param id the Chopstick id
     */
    public Chopstick(int id) {
        this.id = id;
        lock = new ReentrantLock(true);
    }
    
    /**
     * gets the current Chopstick object id
     * @return the Chopstick id
     */
    public int getId(){
        return id;
    }
    
    /**
     * try to pick up a Chopstick, by locking the Chopstick object
     * @return returns true if the lock was acquired and false if the lock wasn't acquired
     */
    public boolean carry(){
        return lock.tryLock();
    }
    
    /**
     * drops a Chopstick, by releasing the lock on the Chopstick object
     */
    public void drop(){
        try{
            lock.unlock();
        }catch(IllegalMonitorStateException e){
        }
    }
}
