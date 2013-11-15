package cn.sdu.edu.ejb.remote.stateful;

import javax.ejb.Remote;
import javax.ejb.Stateful;

/**
 * 
 * @author Yonggang Yuan
 *
 */

@Stateful
@Remote(RemoteCounter.class)
public class CounterBean implements RemoteCounter {

    private int count = 0;

    @Override
    public void increment() {
        this.count ++;
    }

    @Override
    public void decrement() {
        this.count --;
    }

    @Override
    public int getCount() {
        return this.count;
    }

}
