/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.workflow.spi.hibernate3;

import com.opensymphony.workflow.StoreException;
import com.opensymphony.workflow.spi.WorkflowEntry;
import com.opensymphony.workflow.query.WorkflowExpressionQuery;
import com.opensymphony.workflow.query.NestedExpression;
import com.opensymphony.workflow.query.FieldExpression;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.sql.SQLException;
import java.util.*;


/**
 * @author masini
 *         <p/>
 *         New Refactored Spring Managed Hibernate Store.
 *         Look at @link NewSpringHibernateFunctionalWorkflowTestCase for a use case.
 */
public class SpringHibernateWorkflowStore extends AbstractHibernateWorkflowStore {
    //~ Instance fields ////////////////////////////////////////////////////////

    private SessionFactory sessionFactory;

    //~ Constructors ///////////////////////////////////////////////////////////

    public SpringHibernateWorkflowStore() {
        super();
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void init(Map props) throws StoreException {
    }

    protected Object execute(final InternalCallback action) throws StoreException {
        HibernateTemplate template = new HibernateTemplate(getSessionFactory());

        return template.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                try {

                    return action.doInHibernate(session);
                } catch (StoreException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    //spring hibernate no session so override it!
    public List query(final WorkflowExpressionQuery query) throws StoreException {
        HibernateTemplate template = new HibernateTemplate(getSessionFactory());
        return (List) template.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                com.opensymphony.workflow.query.Expression expression = query.getExpression();

                Criterion expr;

                Class entityClass = getQueryClass(expression, null);

                if (expression.isNested()) {
                    expr = buildNested((NestedExpression) expression);
                } else {
                    expr = queryComparison((FieldExpression) expression);
                }

                Criteria criteria = session.createCriteria(entityClass);
                criteria.setCacheable(isCacheable());

                if (isCacheable()) {
                    criteria.setCacheRegion(getCacheRegion());
                }

                criteria.add(expr);

                Set results = new HashSet();
                Iterator iter = criteria.list().iterator();

                while (iter.hasNext()) {
                    Object next = iter.next();
                    Object item;
                    if (next instanceof HibernateStep) {
                        HibernateStep step = (HibernateStep) next;
                        item = new Long(step.getEntryId());
                    } else {
                        WorkflowEntry entry = (WorkflowEntry) next;
                        item = new Long(entry.getId());
                    }

                    results.add(item);

                }

                return new ArrayList(results);
            }
        });
    }
}
