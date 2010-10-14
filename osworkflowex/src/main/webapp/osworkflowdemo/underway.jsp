<%@ page import="com.opensymphony.workflow.Workflow,
                 org.springframework.context.ApplicationContext,
                 org.springframework.web.context.support.WebApplicationContextUtils,
                 com.opensymphony.workflow.config.Configuration,
                 com.opensymphony.workflow.config.SpringConfiguration,
                 com.opensymphony.workflow.basic.BasicWorkflow,
                 com.opensymphony.workflow.basic.BasicWorkflow,
                 java.util.*,
                 com.opensymphony.workflow.query.WorkflowQuery"%>
<%
     ApplicationContext cxt = WebApplicationContextUtils.getWebApplicationContext(this.getServletConfig().getServletContext());
    Configuration conf = (SpringConfiguration) cxt.getBean("osworkflowConfiguration");
    Workflow wf = new BasicWorkflow("test");
    wf.setConfiguration(conf);
    
    WorkflowQuery queryLeft = new WorkflowQuery(WorkflowQuery.OWNER, WorkflowQuery.CURRENT, WorkflowQuery.EQUALS, "test");
    WorkflowQuery queryRight = new WorkflowQuery(WorkflowQuery.STATUS, WorkflowQuery.CURRENT, WorkflowQuery.EQUALS, "Underway");
    WorkflowQuery query = new WorkflowQuery(queryLeft, WorkflowQuery.AND, queryRight);
    List workflows = wf.query(query);
    for (Iterator iterator = workflows.iterator(); iterator.hasNext();) {
        Long wfId = (Long) iterator.next();
%>
    <li><a href="test.jsp?id=<%= wfId %>"><%= wfId %></a></li>
<%
    }
%>

<%@ include file="nav.jsp" %>