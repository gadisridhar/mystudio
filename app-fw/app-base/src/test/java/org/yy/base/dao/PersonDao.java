/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yy.base.dao;

import java.util.List;
import org.yy.base.dao.annotation.FirstResult;
import org.yy.base.dao.annotation.MaxResult;
import org.yy.base.dao.annotation.Param;
import org.yy.base.model.Person;

/**
 *
 * @author yyi
 */

public interface PersonDao extends GenericDao<Person, Long> {

    List<Person> findByLastName(@Param("lastName")String lastName);
    List<Person> findByLastName(@Param("lastName")String lastNam,@FirstResult int firstResult, @MaxResult int maxResults);
}
