/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yy.base.dao;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.yy.base.context.UserContext;
import org.yy.base.context.UserContextHolder;
import org.yy.base.dao.test.BaseDaoTestCase;
import org.yy.base.model.Person;

/**
 *
 * @author yyi
 */
public class GenericDaoTest extends BaseDaoTestCase {

    private PersonDao personDao;

    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
        //org.hibernate.cache.EhCacheProvider a ;
    }

    public void testGetPersonByLastName() {
        List<Person> persons = personDao.findByLastName("wong");
        assertTrue(persons.size() > 0);
    }

    public void testGetPersonByLastNameWithPagination() {
        List<Person> persons = personDao.findByLastName("wong", 1, 1);
        assertTrue(persons.size() == 1);
        assertEquals("John2",persons.get(0).getFirstName());
    }

    public void testAddAndRemovePerson() {

        UserContext userCtx = new UserContext("yy");
        UserContextHolder.set(userCtx);

        Person p = new Person();
        p.setLastName("yee");
        p.setFirstName("win");

        p = personDao.save(p);

        assertNotNull(p.getId());
        assertEquals("yy", p.getCreatedBy());
        personDao.remove(p.getId());

        try {
            personDao.get(p.getId());
            fail("Person should not be found in db.");
        } catch (EntityNotFoundException ex) {
            System.out.println("Exception message: " + ex.getMessage());
            assertNotNull(ex);
        }
    }
}
