package cn.sdu.edu.ejb.remote.stateful;

/**
 * 
 * @author Yonggang Yuan
 *
 */

public interface RemoteCounter {

    void increment();

    void decrement();
 
    int getCount();

}
