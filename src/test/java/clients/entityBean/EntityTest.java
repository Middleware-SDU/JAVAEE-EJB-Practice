package clients.entityBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import cn.sdu.edu.ejb.entityBean.module.Person;
import cn.sdu.edu.ejb.entityBean.service.PersonService;
import cn.sdu.edu.ejb.entityBean.service.PersonServiceBean;

/**
 * Test EntityBean
 * @author Yonggang Yuan
 *
 */

public class EntityTest {

    public static PersonService personService;
    public static Context context;
    // FIXME : 此处的entityId直接指定有点不科学，有待完善
    public static Integer entityId = 1;

    static {
        try {
            context = new InitialContext();
            final String appName = "";
            final String moduleName = "sdu-ejb-app";
            final String distinctName = "";
            final String beanName = PersonServiceBean.class.getSimpleName();
            final String viewClassName = PersonService.class.getName();
            String ejbName = "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName;
            personService = (PersonService)context.lookup(ejbName);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testSave();
        testFind();
        testUpdate();
        testDelete();
    }


    public static void testSave() {
        System.out.println("#################TEST SAVE##################\n");
        for(int i=0; i<10; i++) {
            personService.save(new Person("Yonggang Yuan - " + i));
            System.out.println("Yonggang Yuan - " + i + " has been saved successfully.");
        }
        System.out.println("\n#############################################\n\n");
    }

    public static void testFind() {
        System.out.println("#################TEST FIND##################\n");
        Person person = null;
        // FIXME : 此处循环是为了防止多次试验，在hibernate控制mysql主键生成的情况下的问题，有待完善。
        for(int i=0; i<=20; i++) {
            person = personService.find(entityId + 10*i);
            if(person != null){
                break;
            }
        }
        if(person == null) {
            System.out.println("Please check your entityId[" + entityId + "] in your database.");
            System.out.println("\n###########################################\n\n");
            return;
        }
        System.out.println("Find a person from database, he/she is " + person.getName());
        System.out.println("\n###########################################\n\n");
    }

    public static void testUpdate() {
        // TODO ：To test it
        System.out.println("################TEST UPDATE####################\n");
        System.out.println("Do nothing yet. You can test this method by yourself.");
        System.out.println("\n##############################################\n\n");
    }

    public static void testDelete() {
        // TODO ： To test it
        System.out.println("################TEST DELETE####################\n");
        System.out.println("Do nothing yet. You can test this method by yourself.");
        System.out.println("\n##############################################\n\n");
    }

}
