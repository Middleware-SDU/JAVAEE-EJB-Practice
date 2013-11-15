package cn.sdu.edu.ejb.remote.client;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import cn.sdu.edu.ejb.remote.stateful.CounterBean;
import cn.sdu.edu.ejb.remote.stateful.RemoteCounter;
import cn.sdu.edu.ejb.remote.stateless.CalculatorBean;
import cn.sdu.edu.ejb.remote.stateless.RemoteCalculator;

/**
 * 一个用于测试EJB的client(session bean with both stateful and stateless beans)
 * @author Yonggang Yuan
 *
 */

public class RemoteEJBClient {

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
        /* 调用另一个远程方法 */
        int num1 = 3434;
        int num2 = 2332;
        System.out.println("Subtracting " + num2 + " from " + num1 + " using the remote stateless calculator.");
        int difference = statelessRemoteCalculator.subtract(num1, num2);
        System.out.println("Remote calculator returned difference = " + difference);
        if (difference != num1 - num2) {
            throw new RuntimeException("Remote stateless calculator returned an incorrect difference " + difference + " ,expected difference was " + (num1 - num2));
        }
    }

    /**
     * 查找一个有状态的Bean并且调用之
     *
     * @throws NamingException
     */
    private static void invokeStatefulBean() throws NamingException {
        /* 查找一个远程的有状态的counter的Bean */
        final RemoteCounter statefulRemoteCounter = lookupRemoteStatefulCounter();
        System.out.println("已获得一个有状态的remote counter bean");
        /* 调用获取的bean，增加 */
        final int NUM_TIMES = 20;
        System.out.println("Counter will now be incremented " + NUM_TIMES + " times");
        for (int i = 0; i < NUM_TIMES; i++) {
            System.out.println("Incrementing counter");
            statefulRemoteCounter.increment();
            System.out.println("Count after increment is " + statefulRemoteCounter.getCount());
        }
        /* 减少 */
        System.out.println("Counter will now be decremented " + NUM_TIMES + " times");
        for (int i = NUM_TIMES; i > 0; i--) {
            System.out.println("Decrementing counter");
            statefulRemoteCounter.decrement();
            System.out.println("Count after decrement is " + statefulRemoteCounter.getCount());
        }
    }
 
    /**
     * 查找并返回一个远程无状态calculator bean的代理对象
     *
     * @return calculator bean的代理
     * 
     * @throws NamingException
     */
    private static RemoteCalculator lookupRemoteStatelessCalculator() throws NamingException {
        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);
        /* appName指的是应用的名字，一般是ear的名字，当前的应用不存在ear，所以appName为空  */
        final String appName = "";
        /* moduleName指的是部署在server上的模块的名字，一般是jar的名字  */
        final String moduleName = "sdu-ejb-remote-app";
        /* Jboass AS7 允许我们为每个部署的应用指定一个目标名称，当前应用没有指定，所以为空 */
        final String distinctName = "";
        /* EJB的名字为类的简单名称(简单名称 不包含包名，例如java.lang.String的简单名称就是String) */
        final String beanName = CalculatorBean.class.getSimpleName();
        /* 远程接口的全类名(fully class name) */
        final String viewClassName = RemoteCalculator.class.getName();
        /* 调用lookup开始查找,此处我用JNDI方式查找，还有一种方式就是DI or IOC */
        return (RemoteCalculator) context.lookup("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);
    }
 
    /**
     * 查找并返回一个remote stateful 的 counter bean
     *
     * @return ： remote stateful 的 counter bean
     * @throws NamingException
     */
    private static RemoteCounter lookupRemoteStatefulCounter() throws NamingException {
        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);
        final String appName = "";
        final String moduleName = "sdu-ejb-remote-app";
        final String distinctName = "";
        final String beanName = CounterBean.class.getSimpleName();
        final String viewClassName = RemoteCounter.class.getName();
        return (RemoteCounter) context.lookup("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName + "?stateful");
    }

}
