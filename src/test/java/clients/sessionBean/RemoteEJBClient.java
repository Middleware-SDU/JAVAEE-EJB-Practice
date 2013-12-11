package clients.sessionBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import cn.sdu.edu.ejb.sessionBean.bean.stateful.CounterBean;
import cn.sdu.edu.ejb.sessionBean.bean.stateless.CalculatorBean;
import cn.sdu.edu.ejb.sessionBean.remote.stateful.RemoteCounter;
import cn.sdu.edu.ejb.sessionBean.remote.stateless.RemoteCalculator;

/**
 * 一个用于测试EJB的client(session bean with both stateful and stateless beans)
 * @author Yonggang Yuan
 *
 */

public class RemoteEJBClient {

    public static Context context = null;

    static {
        try {
            context = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        // 调用无状态Bean
        invokeStatelessBean();

        // 调用有状态Bean
        invokeStatefulBean();
    }

    /**
     * 查找一个无状态的Bean并且调用之
     *
     * @throws NamingException
     */
    private static void invokeStatelessBean() throws NamingException {
        /* 查找一个远程的无状态的calculator的Bean */
        final RemoteCalculator statelessRemoteCalculator = lookupRemoteStatelessCalculator();
        System.out.println("已获得一个远程的无状态的calculator bean");
        /* 调用remote calculator */
        int a = 204;
        int b = 340;
        System.out.println("Adding " + a + " and " + b + " using the remote stateless calculator.");
        int sum = statelessRemoteCalculator.add(a, b);
        System.out.println("Remote calculator returned sum = " + sum);
        if (sum != a + b) {
            throw new RuntimeException("Remote stateless calculator returned an incorrect sum " + sum + " ,expected sum was " + (a + b));
        }
    }

    /**
     * 查找一个有状态的Bean并且调用之
     *
     * @throws NamingException
     */
    private static void invokeStatefulBean() throws NamingException {
        /* 查找一个远程的有状态的counter的Bean */
        final RemoteCounter firstCounter = lookupRemoteStatefulCounter();
        final RemoteCounter secondCounter = lookupRemoteStatefulCounter();

        System.out.println("已获得2个有状态的remote counter bean");
        /* 调用获取的bean，增加 */
        final int COUNT_TIMES = 20;
        int result = 0;
        System.out.println("firstCounter will now be incremented " + COUNT_TIMES + " times based on 0");
        for(int i=0; i<COUNT_TIMES; i++) {
            result = firstCounter.increment();
        }
        System.out.println("firstCounter after increment is : " + result);

        // 调用另一个bean，证明有状态
        System.out.println("secondCounter will now be incremented " + COUNT_TIMES + " times based on 0");
        for(int i=0; i<COUNT_TIMES; i++) {
            result = secondCounter.increment();
        }
        System.out.println("secondCounter after increment is : " + result);
    }

    /**
     * 查找并返回一个远程无状态calculator bean的代理对象
     *
     * @return calculator bean的代理
     * 
     * @throws NamingException
     */
    private static RemoteCalculator lookupRemoteStatelessCalculator() throws NamingException {
        /* appName指的是应用的名字，一般是ear的名字，当前的应用不存在ear，所以appName为空  */
        final String appName = "";
        /* moduleName指的是部署在server上的模块的名字，一般是jar的名字  */
        final String moduleName = "sdu-ejb-app";
        /* Jboass AS7 允许我们为每个部署的应用指定一个目标名称，当前应用没有指定，所以为空 */
        final String distinctName = "";
        /* EJB的名字为类的简单名称(简单名称 不包含包名，例如java.lang.String的简单名称就是String) */
        final String beanName = CalculatorBean.class.getSimpleName();
        /* 远程接口的全类名(fully class name) */
        final String viewClassName = RemoteCalculator.class.getName();
        String ejbName = "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName;
        /* 调用lookup开始查找,此处我用JNDI方式查找，还有一种方式就是DI or IOC */
        return (RemoteCalculator) context.lookup(ejbName);
    }

    /**
     * 查找并返回一个remote stateful 的 counter bean
     *
     * @return ： remote stateful 的 counter bean
     * @throws NamingException
     */
    private static RemoteCounter lookupRemoteStatefulCounter() throws NamingException {

        final String appName = "";
        final String moduleName = "sdu-ejb-app";
        final String distinctName = "";
        final String beanName = CounterBean.class.getSimpleName();
        final String viewClassName = RemoteCounter.class.getName();
        return (RemoteCounter) context.lookup("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName + "?stateful");
    }

}
