/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.workflow.spi.hibernate3;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.QueryNotSupportedException;
import com.opensymphony.workflow.StoreException;
import com.opensymphony.workflow.query.FieldExpression;
import com.opensymphony.workflow.query.NestedExpression;
import com.opensymphony.workflow.query.WorkflowExpressionQuery;
import com.opensymphony.workflow.query.WorkflowQuery;
import com.opensymphony.workflow.spi.Step;
import com.opensymphony.workflow.spi.WorkflowEntry;
import com.opensymphony.workflow.spi.WorkflowStore;
import com.opensymphony.workflow.util.PropertySetDelegate;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;

import java.util.*;


/**
 * @author <a href="mailto:cucuchen520@yahoo.com.cn">Chris Chen</a>
 */
public abstract class AbstractHibernateWorkflowStore implements WorkflowStore {
    //~ Instance fields ////////////////////////////////////////////////////////

    private PropertySetDelegate propertySetDelegate;
    private String cacheRegion = null;
    private boolean cacheable = false;
    private final String HQL_CURRENT_STEP_BY_ENTRYID = "from HibernateCurrentStep pojo where pojo.entry.id=?";
    private final String HQL_CURRENT_STEP_PREVID_BY_STEPID = "select pojo.previousId from HibernateCurrentStepPrev pojo where pojo.id=?";
    private final String HQL_HISTORY_STEP_BY_ENTRYID = "from HibernateHistoryStep pojo where pojo.entry.id=?";
    private final String HQL_HISTORY_STEP_PREVID_BY_STEPID = "select pojo.previousId from HibernateHistoryStepPrev pojo where pojo.id=?";
    private final String HQL_CURRENT_STEP_PREV_BY_STEPID = "delete from HibernateCurrentStepPrev pojo where pojo.id=?";
    private final String HQL_HISTORY_STEP_PREV_BY_STEPID = "delete from HibernateHistoryStepPrev pojo where pojo.id=?";
    //~ Methods ////////////////////////////////////////////////////////////////

    // ~ Getter/Setter ////////////////////////////////////////////////////////////////
    public void setCacheRegion(String cacheRegion) {
        this.cacheRegion = cacheRegion;
    }

    public void setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
    }

    public void setEntryState(final long entryId, final int state) throws StoreException {
        HibernateWorkflowEntry entry = loadEntry(entryId);
        entry.setState(state);
        update(entry);
    }

    public PropertySet getPropertySet(long entryId) throws StoreException {
        if (getPropertySetDelegate() == null) {
            throw new StoreException("PropertySetDelegate is not properly configured");
        }

        return getPropertySetDelegate().getPropertySet(entryId);
    }

    public void setPropertySetDelegate(PropertySetDelegate propertySetDelegate) {
        this.propertySetDelegate = propertySetDelegate;
    }

    public PropertySetDelegate getPropertySetDelegate() {
        return propertySetDelegate;
    }

    public Step createCurrentStep(final long entryId, final int stepId, final String owner, final Date startDate, final Date dueDate, final String status, final long[] previousIds) throws StoreException {
        final HibernateWorkflowEntry entry = loadEntry(entryId);
        final HibernateCurrentStep step = new HibernateCurrentStep();

        step.setStepId(stepId);
        step.setOwner(owner);
        step.setStartDate(startDate);
        step.setDueDate(dueDate);
        step.setStatus(status);

        step.setPreviousStepIds(previousIds);
        entry.addCurrentSteps(step);

        saveOrUpdate(step);
        for (int i = 0; i < previousIds.length; i++) {
            HibernateCurrentStepPrev prev = new HibernateCurrentStepPrev();
            prev.setId(step.getId());
            prev.setPreviousId(previousIds[i]);
            save(prev);

        }
        return step;
    }

    public WorkflowEntry createEntry(String workflowName) throws StoreException {
        final HibernateWorkflowEntry entry = new HibernateWorkflowEntry();
        entry.setState(WorkflowEntry.CREATED);
        entry.setWorkflowName(workflowName);
        save(entry);

        return entry;
    }

    public List findCurrentSteps(final long entryId) throws StoreException {
        List list = getHibernateCurrentStepList(entryId);
        ArrayList currentSteps = new ArrayList();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                HibernateCurrentStep hcs = (HibernateCurrentStep) list.get(i);
                long[] prevIds = getCurrentPreviousIdList(hcs.getId());
                hcs.setPreviousStepIds(prevIds);
                currentSteps.add(hcs);
            }
        }
        return currentSteps;
    }

    public WorkflowEntry findEntry(long entryId) throws StoreException {
        return loadEntry(entryId);
    }

    public List findHistorySteps(final long entryId) throws StoreException {
        List list = getHibernateHistoryStepList(entryId);
        ArrayList currentSteps = new ArrayList();

        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                HibernateHistoryStep hhs = (HibernateHistoryStep) list.get(i);
                long []prevIds = getHistoryPreviousIdList(hhs.getId());
                hhs.setPreviousStepIds(prevIds);
                currentSteps.add(hhs);
            }
        }
        return currentSteps;
    }

    public Step markFinished(Step step, int actionId, Date finishDate, String status, String caller) throws StoreException {
        final HibernateCurrentStep currentStep = (HibernateCurrentStep) step;
        currentStep.setActionId(actionId);
        currentStep.setFinishDate(finishDate);
        currentStep.setStatus(status);
        currentStep.setCaller(caller);

        update(currentStep);
        return currentStep;
    }

    public void moveToHistory(final Step step) throws StoreException {

        final HibernateCurrentStep currentStep = (HibernateCurrentStep) step;
        final HibernateHistoryStep historyStep = new HibernateHistoryStep(currentStep);

        // set the same id as current table
        historyStep.setId(currentStep.getId());
        historyStep.setPreviousStepIds(currentStep.getPreviousStepIds());

        save(historyStep);

        long[] previousIds = historyStep.getPreviousStepIds();

        if (previousIds != null && previousIds.length > 0) {
            for (int i = 0; i < previousIds.length; i++) {
                HibernateHistoryStepPrev p = new HibernateHistoryStepPrev();
                p.setId(historyStep.getId());
                p.setPreviousId(previousIds[i]);
                save(p);
            }
        }
        deleteCurrentStepPrev(historyStep.getId());

        delete(currentStep);


    }

    public List query(final WorkflowQuery query) throws StoreException {
        return (List) execute(new InternalCallback() {
            public Object doInHibernate(Session session) throws HibernateException, StoreException {
                Class entityClass;

                int qtype = query.getType();

                if (qtype == 0) { // then not set, so look in sub queries

                    if (query.getLeft() != null) {
                        qtype = query.getLeft().getType();
                    }
                }

                if (qtype == WorkflowQuery.CURRENT) {
                    entityClass = HibernateCurrentStep.class;
                } else {
                    entityClass = HibernateHistoryStep.class;
                }

                Criteria criteria = session.createCriteria(entityClass);
                Criterion expression = buildExpression(query);
                criteria.setCacheable(isCacheable());

                if (isCacheable()) {
                    criteria.setCacheRegion(getCacheRegion());
                }

                criteria.add(expression);

                Set results = new HashSet();
                Iterator iter = criteria.list().iterator();

                while (iter.hasNext()) {
                    HibernateStep step = (HibernateStep) iter.next();
                    results.add(new Long(step.getEntryId()));
                }

                return new ArrayList(results);
            }
        });
    }

    /*
     * (non-Javadoc)
     *
     * @see com.opensymphony.workflow.spi.WorkflowStore#query(com.opensymphony.workflow.query.WorkflowExpressionQuery)
     */
    public List query(final WorkflowExpressionQuery query) throws StoreException {
        return (List) execute(new InternalCallback() {
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

    // Companion method of InternalCallback class
    protected abstract Object execute(InternalCallback action) throws StoreException;

    protected String getCacheRegion() {
        return cacheRegion;
    }

    protected boolean isCacheable() {
        return cacheable;
    }

    protected Criterion getExpression(final WorkflowQuery query) throws StoreException {
        return (Criterion) execute(new InternalCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                int operator = query.getOperator();

                switch (operator) {
                    case WorkflowQuery.EQUALS:
                        return Expression.eq(getFieldName(query.getField()), query.getValue());

                    case WorkflowQuery.NOT_EQUALS:
                        return Expression.not(Expression.like(getFieldName(query.getField()), query.getValue()));

                    case WorkflowQuery.GT:
                        return Expression.gt(getFieldName(query.getField()), query.getValue());

                    case WorkflowQuery.LT:
                        return Expression.lt(getFieldName(query.getField()), query.getValue());

                    default:
                        return Expression.eq(getFieldName(query.getField()), query.getValue());
                }
            }
        });
    }

    protected void delete(final Object entry) throws StoreException {
        execute(new InternalCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                session.delete(entry);

                return null;
            }
        });
    }


    // ~ DAO Methods ////////////////////////////////////////////////////////////////
    protected HibernateWorkflowEntry loadEntry(final long entryId) throws StoreException {
        return (HibernateWorkflowEntry) execute(new InternalCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                return session.load(HibernateWorkflowEntry.class, new Long(entryId));
            }
        });
    }

    protected void save(final Object entry) throws StoreException {
        execute(new InternalCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                session.save(entry);
                return null;
            }
        });
    }
    protected void saveOrUpdate(final Object entry) throws StoreException {
            execute(new InternalCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    session.saveOrUpdate(entry);
                    session.flush();
                    return null;
                }
            });
        }


    private String getFieldName(int field) {
        switch (field) {
            case FieldExpression.ACTION: // actionId
                return "actionId";

            case FieldExpression.CALLER:
                return "caller";

            case FieldExpression.FINISH_DATE:
                return "finishDate";

            case FieldExpression.OWNER:
                return "owner";

            case FieldExpression.START_DATE:
                return "startDate";

            case FieldExpression.STEP: // stepId
                return "stepId";

            case FieldExpression.STATUS:
                return "status";

            case FieldExpression.STATE:
                return "state";

            case FieldExpression.NAME:
                return "workflowName";

            case FieldExpression.DUE_DATE:
                return "dueDate";

            default:
                return "1";
        }
    }

    protected Class getQueryClass(com.opensymphony.workflow.query.Expression expr, Collection classesCache) {
        if (classesCache == null) {
            classesCache = new HashSet();
        }

        if (expr instanceof FieldExpression) {
            FieldExpression fieldExpression = (FieldExpression) expr;

            switch (fieldExpression.getContext()) {
                case FieldExpression.CURRENT_STEPS:
                    classesCache.add(HibernateCurrentStep.class);

                    break;

                case FieldExpression.HISTORY_STEPS:
                    classesCache.add(HibernateHistoryStep.class);

                    break;

                case FieldExpression.ENTRY:
                    classesCache.add(HibernateWorkflowEntry.class);

                    break;

                default:
                    throw new QueryNotSupportedException("Query for unsupported context " + fieldExpression.getContext());
            }
        } else {
            NestedExpression nestedExpression = (NestedExpression) expr;

            for (int i = 0; i < nestedExpression.getExpressionCount(); i++) {
                com.opensymphony.workflow.query.Expression expression = nestedExpression.getExpression(i);

                if (expression.isNested()) {
                    classesCache.add(getQueryClass(nestedExpression.getExpression(i), classesCache));
                } else {
                    classesCache.add(getQueryClass(expression, classesCache));
                }
            }
        }

        if (classesCache.size() > 1) {
            throw new QueryNotSupportedException("Store does not support nested queries of different types (types found:" + classesCache + ")");
        }

        return (Class) classesCache.iterator().next();
    }

    private Criterion buildExpression(WorkflowQuery query) throws StoreException {
        if (query.getLeft() == null) {
            if (query.getRight() == null) {
                return getExpression(query); // leaf node
            } else {
                throw new StoreException("Invalid WorkflowQuery object.  QueryLeft is null but QueryRight is not.");
            }
        } else {
            if (query.getRight() == null) {
                throw new StoreException("Invalid WorkflowQuery object.  QueryLeft is not null but QueryRight is.");
            }

            int operator = query.getOperator();
            WorkflowQuery left = query.getLeft();
            WorkflowQuery right = query.getRight();

            switch (operator) {
                case WorkflowQuery.AND:
                    return Expression.and(buildExpression(left), buildExpression(right));

                case WorkflowQuery.OR:
                    return Expression.or(buildExpression(left), buildExpression(right));

                case WorkflowQuery.XOR:
                    throw new QueryNotSupportedException("XOR Operator in Queries not supported by " + this.getClass().getName());

                default:
                    throw new QueryNotSupportedException("Operator '" + operator + "' is not supported by " + this.getClass().getName());
            }
        }
    }

    protected Criterion buildNested(NestedExpression nestedExpression) {
        Criterion full = null;

        for (int i = 0; i < nestedExpression.getExpressionCount(); i++) {
            Criterion expr;
            com.opensymphony.workflow.query.Expression expression = nestedExpression.getExpression(i);

            if (expression.isNested()) {
                expr = buildNested((NestedExpression) nestedExpression.getExpression(i));
            } else {
                FieldExpression sub = (FieldExpression) nestedExpression.getExpression(i);
                expr = queryComparison(sub);

                if (sub.isNegate()) {
                    expr = Expression.not(expr);
                }
            }

            if (full == null) {
                full = expr;
            } else {
                switch (nestedExpression.getExpressionOperator()) {
                    case NestedExpression.AND:
                        full = Expression.and(full, expr);

                        break;

                    case NestedExpression.OR:
                        full = Expression.or(full, expr);
                }
            }
        }

        return full;
    }

    protected Criterion queryComparison(FieldExpression expression) {
        int operator = expression.getOperator();

        switch (operator) {
            case FieldExpression.EQUALS:
                return Expression.eq(getFieldName(expression.getField()), expression.getValue());

            case FieldExpression.NOT_EQUALS:
                return Expression.not(Expression.like(getFieldName(expression.getField()), expression.getValue()));

            case FieldExpression.GT:
                return Expression.gt(getFieldName(expression.getField()), expression.getValue());

            case FieldExpression.LT:
                return Expression.lt(getFieldName(expression.getField()), expression.getValue());

            default:
                return Expression.eq(getFieldName(expression.getField()), expression.getValue());
        }
    }

    //~ Inner Interfaces ///////////////////////////////////////////////////////

    // ~ Internal Interfaces /////////////////////////////////////////////////////
    // Template method pattern to delegate implementation of Session 
    // management to subclasses
    protected interface InternalCallback {
        public Object doInHibernate(Session session) throws HibernateException, StoreException;
    }

    //add by chris
    ////////////////////////////////////////////////////////////////

    protected void update(final Object entry) throws StoreException {
        execute(new InternalCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                session.update(entry);
                return null;
            }
        });
    }


    protected List getHibernateCurrentStepList(final long entryId) throws StoreException {
        final List list = new ArrayList();
        execute(new InternalCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(HQL_CURRENT_STEP_BY_ENTRYID);
                query.setLong(0, new Long(entryId));
                list.addAll(query.list());
                return null;
            }
        });
        return list;
    }

    protected long[] getCurrentPreviousIdList(final long stepId) throws StoreException {
        final List list = new ArrayList();
        execute(new InternalCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(HQL_CURRENT_STEP_PREVID_BY_STEPID);
                query.setLong(0, new Long(stepId));
                list.addAll(query.list());
                return null;
            }
        });

        long[] prevIds = new long[list.size()];
        int i = 0;


        for (Iterator iterator = list.iterator();
             iterator.hasNext();) {
            Long aLong = (Long) iterator.next();
            prevIds[i] = aLong.longValue();
            i++;
        }

        return prevIds;
    }

    protected List getHibernateHistoryStepList(final long entryId) throws StoreException {
        final List list = new ArrayList();
        execute(new InternalCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(HQL_HISTORY_STEP_BY_ENTRYID);
                query.setLong(0, new Long(entryId));
                list.addAll(query.list());
                return null;
            }
        });
        return list;
    }

    protected long[] getHistoryPreviousIdList(final long stepId) throws StoreException {
        final List list = new ArrayList();
        execute(new InternalCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(HQL_HISTORY_STEP_PREVID_BY_STEPID);
                query.setLong(0, new Long(stepId));
                list.addAll(query.list());
                return null;
            }
        });

        long[] prevIds = new long[list.size()];
        int i = 0;

        for (Iterator iterator = list.iterator();
             iterator.hasNext();) {
            Long aLong = (Long) iterator.next();
            prevIds[i] = aLong.longValue();
            i++;
        }

        return prevIds;
    }

    protected void deleteCurrentStepPrev(final long stepId) throws StoreException {
        execute(new InternalCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(HQL_CURRENT_STEP_PREV_BY_STEPID);
                query.setLong(0, new Long(stepId));
                query.executeUpdate();
                return null;
            }
        });
    }

    protected void deleteHistoryStepPrev(final long stepId) throws StoreException {
        execute(new InternalCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(HQL_HISTORY_STEP_PREV_BY_STEPID);
                query.setLong(0, new Long(stepId));
                query.executeUpdate();
                return null;
            }
        });
    }
}
