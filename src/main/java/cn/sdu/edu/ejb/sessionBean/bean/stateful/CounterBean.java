package cn.sdu.edu.ejb.sessionBean.bean.stateful;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import cn.sdu.edu.ejb.sessionBean.local.stateful.LocalCounter;
import cn.sdu.edu.ejb.sessionBean.remote.stateful.RemoteCounter;

/**
 * 
 * @author Yonggang Yuan
 *
 */

@Stateful
@Remote(RemoteCounter.class)
@Local(LocalCounter.class)
public class CounterBean implements RemoteCounter, LocalCounter {

    private int count = 0;

    @Override
    public int increment() {
        return ++ this.count;
    }

    @Override
    public int decrement() {
        return -- count;
    }

}
