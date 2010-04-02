/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yy.base.dao;

import java.util.List;
import org.yy.base.dao.impl.GenericDaoJpa;
import org.yy.base.model.Person;

/**
 *
 * @author yyi
 */
public class PersonDaoJpa extends GenericDaoJpa<Person,Long> implements PersonDao {

    public List<Person> findByLastName(String lastName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Person> findByLastName(String lastNam, int firstResult, int maxResults) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
