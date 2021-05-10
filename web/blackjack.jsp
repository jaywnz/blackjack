<%-- 
    Document   : blackjack
    Created on : 4/05/2021, 9:42:32 PM
    Author     : Jay
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Blackjack</title>
        <script src="main.js" type="text/javascript" defer></script>
    </head>
    <body>
        <h1>Welcome to Blackjack</h1>
        <div id="usercards"></div>
        <div id="dealercards"></div>
        
        <div id="usercarddata">${userHand}</div>
        <div id="dealercarddata">${dealerHand}</div>
        
        <c:if test="${whoseTurn == 'user' || whoseTurn == 'computer'}">
        <form action="jack/move/hit" method="post">
            <input type="button" value="Hit">        
        </form>
        <form action="jack/move/stand" method="post">
            <input type="button" value="Stand">
        </form>
        <a href="jack/stats">Game Statistics</a>
        
        </c:if>
        <c:if test="${whoseTurn == null}">
        <form action="jack/start" method="post">
            <input type="submit" value="Start New Game">
        </form>
        </c:if>
    </body>
</html>