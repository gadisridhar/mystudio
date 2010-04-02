package org.yy.base.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yy.base.dao.UniversalDao;

public class UniversalDaoJpa implements UniversalDao {

    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());
    /**
     * Entity manager, injected by Spring using @PersistenceContext annotation on setEntityManager()
     */
    @PersistenceContext
    protected EntityManager entityManager;

    //@PersistenceContext(unitName="ApplicationEntityManager")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    public Object save(Object o) {
        return this.entityManager.merge(o);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Object get(Class clazz, Serializable id) {
        Object o = this.entityManager.find(clazz, id);

        if (o == null) {
            String msg = "Uh oh, '" + clazz + "' object with id '" + id + "' not found...";
            log.warn(msg);
            throw new EntityNotFoundException(msg);
        }

        return o;
    }

    /**
     * {@inheritDoc}
     */
    public List getAll(Class clazz) {
        return this.entityManager.createQuery("select obj from " + clazz.getSimpleName() + " obj").getResultList();
    }

    /**
     * {@inheritDoc}
     */
    public void remove(Class clazz, Serializable id) {
        this.entityManager.remove(this.get(clazz, id));
    }

    public void remove(Object o) {
        this.entityManager.remove(o);
    }
}
