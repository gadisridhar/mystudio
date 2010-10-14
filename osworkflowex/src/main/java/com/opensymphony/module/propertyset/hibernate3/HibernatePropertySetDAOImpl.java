/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.module.propertyset.hibernate3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.opensymphony.module.propertyset.PropertyException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public class HibernatePropertySetDAOImpl extends HibernateDaoSupport implements HibernatePropertySetDAO {
    private Log log = LogFactory.getLog(HibernatePropertySetDAOImpl.class);

    /**
     * Default construstor
     */
    public HibernatePropertySetDAOImpl()
    {
        super();
    }

    /**
     * @see com.tricision.maas.dao.IHibernatePropertySetDAO#setImpl(PropertySetItem, boolean)
     */
    public void setImpl(PropertySetItem item, boolean isUpdate)
    {
        this.log.debug("TOP of setImpl... "); //$NON-NLS-1$

        try
        {
            this.log.debug("(setImpl) Have an open sessionFactory"); //$NON-NLS-1$)

            if( isUpdate )
            {
                this.log.debug("(setImpl) Attempting update"); //$NON-NLS-1$)
                getHibernateTemplate().update(item);
            }
            else
            {
                this.log.debug("(setImpl) Attempting save"); //$NON-NLS-1$)
                getHibernateTemplate().save(item);
            }
            // flush to persist this data now
            getHibernateTemplate().flush();
            this.log.debug("(setImpl) Attempting flush"); //$NON-NLS-1$)
        }
        catch( HibernateException he )
        {
            throw new PropertyException("Could not save key '" + item.getKey() //$NON-NLS-1$
                    + "':" + he.getMessage()); //$NON-NLS-1$
        }
    }

    /**
     * @see com.tricision.maas.dao.IHibernatePropertySetDAO#getKeys(java.lang.String, java.lang.Long, java.lang.String, int)
     */
    public Collection getKeys(String entityName, Long entityId, String prefix,
            int type)
    {
        this.log.debug("Top of getKeys() with entityName: " + entityName); //$NON-NLS-1$
        List list = null;

        try
        {
            list = getKeysImpl(entityName, entityId, prefix, type);
        }
        catch( HibernateException e )
        {
            list = Collections.EMPTY_LIST;
        }
        return list;
    }

    /**
     * @see com.tricision.maas.dao.IHibernatePropertySetDAO#create(String, long, String)
     */
    public PropertySetItem create(String entityName, long entityId, String key)
    {
        return new PropertySetItem(entityName, entityId, key);
    }

    /**
     * @see com.tricision.maas.dao.IHibernatePropertySetDAO#findByKey(String, Long, String)
     */
    public PropertySetItem findByKey(String entityName, Long entityId, String key)
    {
        this.log.debug(this.getClass().getName() + " Top of findByKey with entityName: " + entityName + " entityId: " + entityId + " and Key: " + key); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        PropertySetItem item = null;

        try
        {
            this.log.debug("Looking for propertysetitem: " + entityName + " with entityId: " + entityId + " and key: " + key); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            item = getItem(entityName, entityId, key);
        }
        catch( HibernateException e )
        {
            this.log.error("Item not found in persistent storage. " + e.getMessage()); //$NON-NLS-1$
            return null;
        }
        return item;
    }

    /**
     * @see com.tricision.maas.dao.IHibernatePropertySetDAO#remove(String, Long)
     */
    public void remove(String entityName, Long entityId)
    {

        try
        {

            // REFACTOR
            // hani: TODO this needs to be optimised rather badly, but I have no
            // idea how
            Collection keys = getKeys(entityName, entityId, null, 0);
            Iterator iter = keys.iterator();

            while( iter.hasNext() )
            {
                String key = (String) iter.next();
                getHibernateTemplate().delete( getItem(entityName, entityId, key));
            }
        }
        catch( HibernateException e )
        {
            throw new PropertyException("Could not remove all keys: " + e.getMessage()); //$NON-NLS-1$
        }
    }

    /**
     * @see com.tricision.maas.dao.IHibernatePropertySetDAO#remove(String, Long, String)
     */
    public void remove(String entityName, Long entityId, String key)
    {

        try
        {
            getHibernateTemplate().delete(getItem(entityName, entityId, key));
        }
        catch( HibernateException e )
        {
            throw new PropertyException("Could not remove key '" + key + "': " //$NON-NLS-1$ //$NON-NLS-2$
                    + e.getMessage());
        }
    }

    /**
     * Get the propertyset item from persistent storage.
     * @param entityName - the name of the entity
     * @param entityId - the id of the entity
     * @param key - the string that is the key for the entity
     * @return - PropertySetItem
     * @throws HibernateException
     */
    public PropertySetItem getItem(final String entityName, final Long entityId, final String key) throws HibernateException
    {
        /**
         * Propertly construct the object before the find.
         */
        PropertySetItem findItem = new PropertySetItem();
        findItem.setEntityId(entityId.longValue());
        findItem.setKey(key);
        findItem.setEntityName(entityName);

        // getHibernateTemplate().load will throw an ObjectNotFound exception, get() will
        // just return null for the object.
        return (PropertySetItem) getHibernateTemplate().get(PropertySetItem.class,findItem);
    }

    /**
     * This is the body of the getKeys() method, so that you can reuse it
     * wrapped by your own session management.
     * @param entityName
     * @param entityId
     * @param prefix
     * @param type
     * @return
     * @throws HibernateException
     */
    public List getKeysImpl(String entityName, Long entityId, String prefix, int type) throws HibernateException
    {
        Query query;

        if((prefix != null) && (type > 0))
        {
            query = getHibernateTemplate().getSessionFactory().openSession().getNamedQuery("all_keys_with_type_like"); //$NON-NLS-1$
            query.setString("like", prefix + '%'); //$NON-NLS-1$
            query.setInteger("type", type); //$NON-NLS-1$
        }
        else if(prefix != null)
        {
            query = getHibernateTemplate().getSessionFactory().openSession().getNamedQuery("all_keys_like"); //$NON-NLS-1$
            query.setString("like", prefix + '%'); //$NON-NLS-1$
        }
        else if(type > 0)
        {
            query = getHibernateTemplate().getSessionFactory().openSession().getNamedQuery("all_keys_with_type"); //$NON-NLS-1$
            query.setInteger("type", type); //$NON-NLS-1$
        }
        else
        {
            query = getHibernateTemplate().getSessionFactory().openSession().getNamedQuery("all_keys"); //$NON-NLS-1$
        }

        query.setString("entityName", entityName); //$NON-NLS-1$
        query.setLong("entityId", entityId.longValue()); //$NON-NLS-1$
        return query.list();
    }
}
