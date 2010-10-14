
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");

        session.setAttribute("username", username);
        response.sendRedirect("nav.jsp");

%>