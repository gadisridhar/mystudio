package com.opensymphony.workflow.service;

import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.vo.DocumentationVO;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author chris.chen
 */
public interface WorkflowService {


    /**
     * 初始化一个工作流
     *
     * @param un      当前用户
     * @param title   文档标题
     * @param content 文档内容
     * @return 当前工作流编号
     * @throws Exception
     */
    public long doInitialize(String un, String title, String content) throws Exception;

    /**
     * 执行动作
     *
     * @param wf        Workflow
     * @param wf_id     工作流程编号
     * @param step_id   步骤编号
     * @param action_id 动作编号
     * @param un        当前调用者
     * @param title     文档标题
     * @param content   文档内容
     * @param opinion   文档审批意见
     * @throws Exception
     */
    public void doAction(Workflow wf, long wf_id, int step_id, int action_id, String un, String title, String content, String opinion)throws Exception;

    /**
     * 根据工作流程编号返回一个文档对象
     *
     * @param wf_id 工作流程编号
     * @return DocumentationVO
     * @throws org.springframework.dao.DataAccessException
     *
     */
    DocumentationVO getDocByWorkflowId(long wf_id);

    /**
     * 取得有效流程实例列表
     *
     * @return long[] 有效流程实例列表
     * @throws DataAccessException
     */
    long[] getAvailableWorkflowInstances();

    /**
     * 得到某一实例某一步骤上面的审批意见列表
     *
     * @param entry_id 流程实例编号
     * @return List 审批意见列表
     * @throws DataAccessException
     */
    List getDocumentationOpinions(long entry_id);
}
