package cn.sdu.edu.ejb.client.entityBean;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.BeforeClass;
import org.junit.Test;

import cn.sdu.edu.ejb.entityBean.module.Person;
import cn.sdu.edu.ejb.entityBean.service.PersonService;
import cn.sdu.edu.ejb.entityBean.service.PersonServiceBean;

/**
 * Test EntityBean
 * @author Yonggang Yuan
 *
 */

public class EntityTest {

    static PersonService personService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        final Context context = new InitialContext();

        /* appName指的是应用的名字，一般是ear的名字，当前的应用不存在ear，所以appName为空  */
        final String appName = "";
        /* moduleName指的是部署在server上的模块的名字，一般是jar的名字  */
        final String moduleName = "sdu-ejb-app";
        /* Jboass AS7 允许我们为每个部署的应用指定一个目标名称，当前应用没有指定，所以为空 */
        final String distinctName = "";
        /* EJB的名字为类的简单名称(简单名称 不包含包名，例如java.lang.String的简单名称就是String) */
        final String beanName = PersonServiceBean.class.getSimpleName();
        /* 远程接口的全类名(fully class name) */
        final String viewClassName = PersonService.class.getName();
        String ejbName = "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName;
        personService = (PersonService)context.lookup(ejbName);
    }

    @Test
    public void testSave() {
        for(int i=0; i<10; i++) {
            personService.save(new Person("Yonggan-" + i));
        }
    }

    @Test
    public void testFind() {
        
    }

    @Test
    public void testUpdate() {
        
    }

    @Test
    public void testDelete() {
        
    }

}
