<%@ page import="com.opensymphony.workflow.Workflow,
                 org.springframework.context.ApplicationContext,
                 org.springframework.web.context.support.WebApplicationContextUtils,
                 com.opensymphony.workflow.config.Configuration,
                 com.opensymphony.workflow.config.SpringConfiguration,
                 com.opensymphony.workflow.basic.BasicWorkflow"%>

<%
   // Workflow wf = new BasicWorkflow((String) session.getAttribute("username"));
   // long id = wf.initialize("example", 100, null);
    
    ApplicationContext cxt = WebApplicationContextUtils.getWebApplicationContext(this.getServletConfig().getServletContext());
    Configuration conf = (SpringConfiguration) cxt.getBean("osworkflowConfiguration");
    Workflow wf = new BasicWorkflow("test");
    wf.setConfiguration(conf);
   // Map inputs = new HashMap();
   // inputs.put("docTitle", request.getParameter("title"));
   long id =   wf.initialize("example", 100, null);
%>

New workflow entry <a href="test.jsp?id=<%=id%>">#<%=id%></a> created and initialized!

<%@ include file="nav.jsp" %>