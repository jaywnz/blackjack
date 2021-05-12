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
        <link rel="stylesheet" href="main.css">
    </head>
    <body>
        <h1>Welcome to Blackjack</h1>
        <h4>Computer Hand</h4>
        <div id="dealercards"></div>
        <h4>User Hand</h4>
        <div id="usercards"></div>
        <c:if test="${whoseTurn == 'user'}">
            <form action="jack/move/hit" method="post">
                <input type="submit" value="Hit">        
            </form>
            <form action="jack/move/stand" method="post">
                <input type="submit" value="Stand">
            </form>
        </c:if>
        <c:if test="${whoseTurn == 'computer'}">
            <p>User total: ${userHandTotal}, Computer total: ${dealerHandTotal}</p>
            <p id="winner">Winner: ${winner}</p>
        </c:if>
        <c:if test="${whoseTurn == null or whoseTurn == 'computer'}">
            <form action="jack/start" method="post">
                <input type="submit" value="Start New Game">
            </form>
        </c:if>
        <button onclick="getStats('jack/stats')">Show Game Statistics</button>
        <p id="stats"></p>
    </body>
    <script type="text/javascript">
        setTimeout(function () {
            let winCheck = document.getElementById("winner");
            if (winCheck !== null) {
                document.getElementById("hide").removeAttribute("id");
            }
        }, 500);
    </script>
</html>