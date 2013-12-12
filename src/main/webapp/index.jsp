<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="cn.sdu.edu.ejb.sessionBean.bean.stateless.*" %>
<%@ page import="cn.sdu.edu.ejb.sessionBean.bean.stateful.*" %>
<%@ page import="cn.sdu.edu.ejb.sessionBean.local.stateless.*" %>
<%@ page import="cn.sdu.edu.ejb.sessionBean.local.stateful.*" %>
<%@ page import="javax.naming.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EJB Web Client</title>
    </head>
    <body>
        <h1> <font color="green"> Test EJB - Session Bean - Local Bean </font></h1>


        <%!
            /* 定义一个用于查找本地无状态session bean的方法  */
            private LocalCalculator lookupLocalStatelessCalculator() throws NamingException {

                final Context context = new InitialContext();

                final String appName = "";
                final String moduleName = "sdu-ejb-app";
                final String distinctName = "";
                final String beanName = CalculatorBean.class.getSimpleName();
                final String viewClassName = LocalCalculator.class.getName();
                String ejbName = "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName;
                return (LocalCalculator) context.lookup(ejbName);
            }

            /* 定义一个用于查找本地有状态session bean的方法  */
            private static LocalCounter lookupLocalStatefulCounter() throws NamingException {
    
                final Context context = new InitialContext();
    
                final String appName = "";
                final String moduleName = "sdu-ejb-app";
                final String distinctName = "";
                final String beanName = CounterBean.class.getSimpleName();
                final String viewClassName = LocalCounter.class.getName();
                return (LocalCounter) context.lookup("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName + "?stateful");
            }
        %>

        <%
            final LocalCalculator statelessLocalCalculator = lookupLocalStatelessCalculator();
            out.println("已获得一个本地的无状态的calculator bean<br/>");
            /* 调用local calculator */
            int a = 200;
            int b = 100;
            out.println("Subtract " + b + " from " + a + " using the local stateless calculator. <br />");
            int subtract = statelessLocalCalculator.subtract(a, b);
            out.println("Local calculator returned result = " + subtract + "<br />");
            if (subtract != a - b) {
                throw new RuntimeException("Local stateless calculator returned an incorrect result " + subtract + " ,expected result was " + (a - b) + "<br />");
            }

            out.println("###########################################################<br/>");
            out.println("###########################################################<br/>");
            final LocalCounter firstCounter = lookupLocalStatefulCounter();
            final LocalCounter secondCounter = lookupLocalStatefulCounter();

            out.println("已获得2个有状态的local counter bean<br/>");
            /* 调用获取的bean，增加 */
            final int COUNT_TIMES = 20;
            int result = 0;
            out.println("FirstCounter will now be decremented " + COUNT_TIMES + " times based on 0 <br/>");
            for(int i=0; i<COUNT_TIMES; i++) {
                result = firstCounter.decrement();
            }
            out.println("FirstCounter after decrementing is : " + result + "<br/>");

            // 调用另一个bean，证明有状态
            out.println("SecondCounter will now be decremented " + COUNT_TIMES + " times based on 0 <br/>");
            for(int i=0; i<COUNT_TIMES; i++) {
                result = secondCounter.decrement();
            }
            out.println("SecondCounter after decrementing is : " + result);
        %>


    </body>
</html>