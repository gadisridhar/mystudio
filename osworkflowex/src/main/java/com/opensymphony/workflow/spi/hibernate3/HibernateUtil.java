package com.opensymphony.workflow.spi.hibernate3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Chris
 * @version $Id$
 */
public class HibernateUtil {
    private static Log log = LogFactory.getLog(HibernateUtil.class);

    public static final SessionFactory sessionFactory;

    static {
        try {
     // Create the SessionFactory
            Configuration conf = new Configuration().configure();
            sessionFactory = conf.buildSessionFactory();
        } catch (Throwable ex) {
            log.error("创建SessionFactory失败：", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

//使用ThreadLocal实现线程安全
    public static final ThreadLocal LocalSession = new ThreadLocal();

    public static Session currentSession() throws HibernateException {
        Session s = (Session) LocalSession.get();
// Open a new Session, if this Thread has none yet
        if (s == null) {
            s = sessionFactory.openSession();
            LocalSession.set(s);
        }
        return s;
    }

    public static void closeSession() throws HibernateException {
        Session s = (Session) LocalSession.get();
        LocalSession.set(null);
        if (s != null)
            s.close();
    }
}
