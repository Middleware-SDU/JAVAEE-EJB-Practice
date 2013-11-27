package cn.sdu.edu.ejb.sessionBean.bean.stateless;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import cn.sdu.edu.ejb.sessionBean.local.stateless.LocalCalculator;
import cn.sdu.edu.ejb.sessionBean.remote.stateless.RemoteCalculator;


/**
 * 
 * @author Yonggang Yuan
 *
 */

@Stateless
@Remote(RemoteCalculator.class)
@Local(LocalCalculator.class)
public class CalculatorBean implements RemoteCalculator, LocalCalculator {

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int subtract(int a, int b) {
        return a - b;
    }

}
