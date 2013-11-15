package cn.sdu.edu.ejb.remote.stateless;

import javax.ejb.Remote;
import javax.ejb.Stateless;


/**
 * 
 * @author Yonggang Yuan
 *
 */

@Stateless
@Remote(RemoteCalculator.class)
public class CalculatorBean implements RemoteCalculator {

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int subtract(int a, int b) {
        return a - b;
    }

}
