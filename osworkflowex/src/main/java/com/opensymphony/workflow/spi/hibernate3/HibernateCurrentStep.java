package com.opensymphony.workflow.spi.hibernate3;

/**
 * This class exists to seperate the persistence of the Steps.
 *   By seperating out the Current Step from the Previous
 ?* Step classes, they can be easily written into seperate tables.
 ?* @see {@link com.opensymphony.workflow.spi.hibernate.HibernateHistoryStep}
 */
public class HibernateCurrentStep extends HibernateStep {
    //~ Constructors ///////////////////////////////////////////////////////////

    public HibernateCurrentStep() {
    }

    public HibernateCurrentStep(HibernateStep step) {
        super(step);
    }
}
