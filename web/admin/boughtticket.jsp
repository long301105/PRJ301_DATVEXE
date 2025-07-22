<%-- 
    Document   : boughtticket
    Created on : Jul 22, 2025, 4:04:49 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO" %>
<%@page import="model.TicketDTO" %>
<%@page import="utils.AuthUtils" %>
<%@page import="dao.TicketDAO" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bought Ticket Page</title>
    </head>
    <body>
        <%
          if(AuthUtils.isLoggedIn(request)){
             UserDTO user = AuthUtils.getCurrentUser(request);
             String message = (String) request.getAttribute("message");
             List<TicketDTO> boughtlist= (List<TicketDTO>) request.getAttribute("boughtlist");
             if(boughtlist==null && boughtlist.isEmpty()){ %>
        <h3><%=message%></h3>
        <%    
           } else {
        %>
        <table>
            <thead>
                <tr>
                    <th>Ticket ID</th>
                    <th>Bookeing ID</th>
                    <th>ScheduleSeatID</th>
                    <th>PassengerName</th>
                    <th>PassengerPhone</th>
                </tr>
            </thead>
              <tbody>
                        <% for(TicketDTO p: boughtlist) { %>
                        <tr>
                            <td data-label="Ticket ID"><%=p.getTicketID()%></td>
                            <td data-label="Bookeing ID" ><%=p.getBookingID()%></td>
                            <td data-label="ScheduleSeatID"><%=p.getScheduleSeatID()%></td>
                            <td data-label="PassengerName"><%=p.getPassengerName()%></td>
                            <td data-label="PassengerPhone" ><%=p.getPassengerPhone()%></td>
                            
                           
                            <% } %>
                        </tr>
                        
                    </tbody>
        </table>
        <%}%>
        <% } %>

    </body>
</html>
