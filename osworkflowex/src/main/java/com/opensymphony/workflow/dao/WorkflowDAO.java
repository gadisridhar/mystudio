package com.opensymphony.workflow.dao;

import org.springframework.dao.DataAccessException;
import com.opensymphony.workflow.vo.DocumentationVO;

import java.util.List;

/**
 * @author chris.chen
 */
public interface WorkflowDAO {

    /**
     * 保存一份文档
     *
     * @param wf_id   工作流程编号
     * @param title   文档标题
     * @param content 文档内容
     * @throws DataAccessException
     */
    void saveDocumentation(long wf_id, String title, String content) ;

    /**
     * 更新一份文档
     *
     * @param wf_id   工作流程编号
     * @param title   文档标题
     * @param content 文档内容
     * @throws DataAccessException
     */

    void updateDocumentation(long wf_id, String title, String content) ;

    /**
     * 根据工作流程编号返回一个文档对象
     *
     * @param wf_id 工作流程编号
     * @return DocumentationVO
     * @throws DataAccessException
     */
    DocumentationVO getDocByWorkflowId(long wf_id) ;

    /**
     * 取得有效流程实例列表
     *
     * @return long[] 有效流程实例列表
     * @throws DataAccessException
     */
    long[] getAvailableWorkflowInstances() ;

    /**
     * 保存一份审批意见
     *
     * @param entry_id  流程实例编号
     * @param step_id   步骤编号
     * @param action_id 动作编号
     * @param caller    调用者
     * @param opinion   审批意见
     * @throws DataAccessException
     */
    void saveDocumentationOpinion(long entry_id, int step_id, int action_id, String caller, String opinion);

    /**
     * 得到某一实例某一步骤上面的审批意见列表
     *
     * @param entry_id 流程实例编号
     * @return List 审批意见列表
     * @throws DataAccessException
     */
    List getDocumentationOpinions(long entry_id) ;
}
