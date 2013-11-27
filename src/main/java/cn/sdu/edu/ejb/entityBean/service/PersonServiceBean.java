package cn.sdu.edu.ejb.entityBean.service;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import cn.sdu.edu.ejb.entityBean.dao.DAOSupport;
import cn.sdu.edu.ejb.entityBean.module.Person;


/**
 * 
 * @author Yonggang Yuan
 *
 */

@Stateless
@Remote(PersonService.class)
public class PersonServiceBean extends DAOSupport<Person> implements PersonService {

}
