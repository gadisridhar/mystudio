package org.yy.base.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.lang.reflect.Method;
import javax.persistence.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yy.base.AppException;
import org.yy.base.dao.FinderArgumentTypeFactory;
import org.yy.base.dao.FinderExecutor;
import org.yy.base.dao.FinderNamingStrategy;
import org.yy.base.dao.GenericDao;
import org.yy.base.dao.annotation.FirstResult;
import org.yy.base.dao.annotation.MaxResult;
import org.yy.base.dao.annotation.Param;

/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 *
 * <p>To register this class in your Spring context file, use the following XML.
 * <pre>
 *      &lt;bean id="fooDao" class="org.yy.dao.hibernate.GenericDaoJpaHibernate"&gt;
 *          &lt;constructor-arg value="org.yy.model.Foo"/&gt;
 *          &lt;property name="sessionFactory" ref="sessionFactory"/&gt;
 *      &lt;/bean&gt;
 * </pre>
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public class GenericDaoJpa<T, PK extends Serializable> implements GenericDao<T, PK> {

    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());
    /**
     * Entity manager, injected by Spring using @PersistenceContext annotation on setEntityManager()
     */
    @PersistenceContext
    protected EntityManager entityManager;
    private FinderNamingStrategy namingStrategy = new SimpleFinderNamingStrategy(); // Default. Can override in config
    //  private FinderArgumentTypeFactory argumentTypeFactory = new SimpleFinderArgumentTypeFactory(); // Default. Can override in config
    private Class<T> persistentClass;

    protected Session getSession() {
        return (Session) this.entityManager.getDelegate();
    }

    /**
     * Constructor that takes in a class to see which type of entity to persist
     * @param persistentClass the class type you'd like to persist
     */
    public GenericDaoJpa() {
        ParameterizedType pc = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.persistentClass = (Class<T>) pc.getActualTypeArguments()[0];

    }

    public GenericDaoJpa(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return this.entityManager.createQuery(
                "select obj from " + this.persistentClass.getName() + " obj").getResultList();
    }

    /**
     * {@inheritDoc}
     */
    public T get(PK id) {
        T entity = this.entityManager.find(this.persistentClass, id);

        if (entity == null) {
            String msg = "Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...";
            log.warn(msg);
            throw new EntityNotFoundException(msg);
        }

        return entity;
    }

    public T getReference(PK id) {
        T entity = this.entityManager.getReference(persistentClass, id);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    public boolean exists(PK id) {
        T entity = this.entityManager.find(this.persistentClass, id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    public T save(T object) {

        return this.entityManager.merge(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        this.entityManager.remove(this.get(id));
    }

    public void remove(T object) {
        this.entityManager.remove(object);
    }

    public void flush() {
        this.entityManager.flush();
    }

    public void Detach(T object) {
        //this.entityManager.
    }

    public T GetAndDetach(PK id) {
        T entity = get(id);
        return entity;
    }

    public List<T> executeFinder(Method method, final Object[] queryArgs) {
        return executeNamedQuery(method, queryArgs);
    }

    private List<T> executeNamedQuery(Method method, final Object[] queryArgs) {
        final Query namedQuery = prepareQuery(method, queryArgs);
        return (List<T>) namedQuery.getResultList();
    }

    private Query prepareQuery(Method method, Object[] queryArgs) {
        final String queryName = getNamingStrategy().queryNameFromMethod(persistentClass, method);
        final Query namedQuery = entityManager.createNamedQuery(queryName);

        Annotation[][] parameterTypes = method.getParameterAnnotations();

        for (int i = 0; i < parameterTypes.length; i++) {
            Annotation[] paramType = parameterTypes[i];

            boolean found = false;
            for (int j = 0; j < paramType.length; j++) {

                if (paramType[j] instanceof Param) {
                    Param param = (Param) paramType[j];
                    Object arg = queryArgs[i];
                    namedQuery.setParameter(param.value(), arg);
                    found = true;
                } else if (paramType[j] instanceof FirstResult) {
                    // FirstResult firstResult = (FirstResult) paramType[j];
                    found = true;
                    int startPosition = ((Integer) queryArgs[i]).intValue();
                    if (startPosition > 0) {
                        namedQuery.setFirstResult(startPosition);
                    }
                }
                if (paramType[j] instanceof MaxResult) {
                    // MaxResult maxResult = (MaxResult) paramType[j];
                    found = true;
                    int maxResults = ((Integer) queryArgs[i]).intValue();
                    if (maxResults > 0) {
                        namedQuery.setMaxResults(maxResults);
                    }

                }

            } // second for
            if (found == false) {
                throw new AppException("Using NamedQuery must annotate all parameters with Param or FirstResult or MaxResult");
            }
        }
//            if (queryArgs != null) {
//                for (int i = 0; i < queryArgs.length; i++) {
//                    Object arg = queryArgs[i];
//                    namedQuery.setParameter(i, arg);
//                }
//            }
        return namedQuery;
    }

//    public Iterator<T> iterateFinder(Method method, final Object[] queryArgs) {
//        final Query namedQuery = prepareQuery(method, queryArgs);
//        return (Iterator<T>) namedQuery.iterate();
//    }
//    public ScrollableResults scrollFinder(Method method, final Object[] queryArgs)
//    {
//        final Query namedQuery = prepareQuery(method, queryArgs);
//        return (ScrollableResults) namedQuery.scroll();
//    }
//    private Query prepareQuery(Method method, Object[] queryArgs) {
//        final String queryName = getNamingStrategy().queryNameFromMethod(persistentClass, method);
//        final Query namedQuery = entityManager.createNamedQuery(queryName);
//        String[] namedParameters = namedQuery.getNamedParameters();
//        if (namedParameters.length == 0) {
//            setPositionalParams(queryArgs, namedQuery);
//        } else {
//            setNamedParams(namedParameters, queryArgs, namedQuery);
//        }
//        return namedQuery;
//    }
//    private void setPositionalParams(Object[] queryArgs, Query namedQuery) {
//        // Set parameter. Use custom Hibernate Type if necessary
//        if (queryArgs != null) {
//            for (int i = 0; i < queryArgs.length; i++) {
//                Object arg = queryArgs[i];
//               // Type argType = getArgumentTypeFactory().getArgumentType(arg);
//                if (argType != null) {
//                    namedQuery.set.setParameter(i, arg, argType);
//                } else {
//                    namedQuery.setParameter(i, arg);
//                }
//            }
//        }
//    }
//
//    private void setNamedParams(String[] namedParameters, Object[] queryArgs, Query namedQuery) {
//        // Set parameter. Use custom Hibernate Type if necessary
//        if (queryArgs != null) {
//            for (int i = 0; i < queryArgs.length; i++) {
//                Object arg = queryArgs[i];
//                Type argType = getArgumentTypeFactory().getArgumentType(arg);
//                if (argType != null) {
//                    namedQuery.setParameter(namedParameters[i], arg, argType);
//                } else {
//                    if (arg instanceof Collection) {
//                        namedQuery.setParameterList(namedParameters[i], (Collection) arg);
//                    } else {
//                        namedQuery.setParameter(namedParameters[i], arg);
//                    }
//                }
//            }
//        }
//    }
    public FinderNamingStrategy getNamingStrategy() {
        return namingStrategy;
    }

    public void setNamingStrategy(FinderNamingStrategy namingStrategy) {
        this.namingStrategy = namingStrategy;
    }
}
