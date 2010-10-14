package com.opensymphony.workflow.dao;

import com.opensymphony.workflow.config.WorkflowConfig;
import com.opensymphony.workflow.vo.DocumentationVO;
import com.opensymphony.workflow.vo.DocumentationOpinionVO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author chris.chen
 */
public class WorkflowDAOImpl extends JdbcDaoSupport implements WorkflowDAO {
    public void saveDocumentation(long wf_id, String title, String content) {
        this.getJdbcTemplate().update("insert into os_doc (wf_id,title,content) values (?,?,?)",
                new Object[]{wf_id, title, content});
    }

    public void updateDocumentation(long wf_id, String title, String content) {
        this.getJdbcTemplate().update("update os_doc set title =?,content =? where wf_id =?",
                new Object[]{title, content, wf_id});
    }

    public DocumentationVO getDocByWorkflowId(long wf_id) {
        List list =
                this.getJdbcTemplate().queryForList("select * from os_doc where wf_id = ?", new Object[]{wf_id});
        DocumentationVO vo = new DocumentationVO();
        if (list != null && !list.isEmpty()) {
            Map map = (Map) list.get(0);
            vo.setWf_id(wf_id);
            vo.setTitle((String) map.get("title"));
            vo.setContent((String) map.get("content"));
        }
        return vo;
    }

    public long[] getAvailableWorkflowInstances() {
        List list =
                this.getJdbcTemplate().queryForList("select id from os_wfentry where state = ? and name= ? ", new Object[]{new Integer(1), WorkflowConfig.WORKFLOW_NAME});
        long[] l = new long[1];
        if (list != null && !list.isEmpty()) {
            l = new long[list.size()];
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                l[i] = ((Long) map.get("id")).longValue();
            }
        }
        return l;
    }

    public void saveDocumentationOpinion(long entry_id, int step_id, int action_id, String caller, String opinion) {
        String sql = "insert into os_doc_opinion (entry_id,step_id,action_id,caller,opinion,opinion_time) values (?,?,?,?,?,?)";
        this.getJdbcTemplate().update(sql, new Object[]{
                new Long(entry_id),
                null,
                new Integer(action_id),
                caller,
                opinion,
                new Date()
        });
    }

    public List getDocumentationOpinions(long entry_id) {
        String sql = "select * from os_doc_opinion where entry_id = ?";
        final List list = new ArrayList();
        this.getJdbcTemplate().query(sql, new Object[]{new Long(entry_id)}, new RowCallbackHandler() {

            public void processRow(ResultSet rset) throws SQLException {
                DocumentationOpinionVO vo = new DocumentationOpinionVO();
                vo.setId(rset.getLong("id"));
                vo.setEntry_id(rset.getLong("entry_id"));
                vo.setStep_id(rset.getInt("step_id"));
                vo.setAction_id(rset.getInt("action_id"));
                vo.setCaller(rset.getString("caller"));
                vo.setOpinion(rset.getString("opinion"));
                vo.setOpinion_time(rset.getTimestamp("opinion_time"));

                list.add(vo);
            }
        });
        return list;
    }
}
