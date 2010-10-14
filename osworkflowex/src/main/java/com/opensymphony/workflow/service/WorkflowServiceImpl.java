package com.opensymphony.workflow.service;

import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.config.SpringConfiguration;
import com.opensymphony.workflow.config.WorkflowConfig;
import com.opensymphony.workflow.dao.WorkflowDAO;
import com.opensymphony.workflow.vo.DocumentationVO;

import java.util.List;

/**
 * @author chris.chen
 */
public class WorkflowServiceImpl implements WorkflowService {
    private WorkflowDAO workflowDAO;
    private SpringConfiguration osworkflowConfiguration;

    public void setWorkflowDAO(WorkflowDAO workflowDAO) {
        this.workflowDAO = workflowDAO;
    }

    public void setOsworkflowConfiguration(SpringConfiguration osworkflowConfiguration) {
        this.osworkflowConfiguration = osworkflowConfiguration;
    }

    public long doInitialize(String un, String title, String content) throws Exception {
        Workflow wf = new BasicWorkflow(un);
        wf.setConfiguration(this.osworkflowConfiguration);
        long wf_id = -1;
        try {
            wf_id = wf.initialize(WorkflowConfig.WORKFLOW_NAME, WorkflowConfig.INITIALIZE_ACTION_ID, null);
            this.workflowDAO.saveDocumentation(wf_id, title, content);
        }
        catch (Exception e) {
            throw e;
        }
        return wf_id;
    }

    public void doAction(Workflow wf, long wf_id, int step_id, int action_id, String un, String title, String content, String opinion) throws
            Exception {
        try {
            //wf.setConfiguration(this.osworkflowConfiguration);
            this.workflowDAO.updateDocumentation(wf_id, title, content);
            this.workflowDAO.saveDocumentationOpinion(wf_id, step_id, action_id, un, opinion);
        }
        catch (Exception e) {
            throw e;
        }
    }

    public DocumentationVO getDocByWorkflowId(long wf_id) {
        return this.workflowDAO.getDocByWorkflowId(wf_id);
    }

    public long[] getAvailableWorkflowInstances() {
        return this.workflowDAO.getAvailableWorkflowInstances();
    }

    public List getDocumentationOpinions(long entry_id) {
        return this.workflowDAO.getDocumentationOpinions(entry_id);
    }
}