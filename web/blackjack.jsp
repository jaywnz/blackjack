<%-- 
    Document   : blackjack
    Created on : 4/05/2021, 9:42:32 PM
    Author     : Jay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Blackjack</title>
    </head>
    <body>
        <h1>Welcome to Blackjack</h1>
        <div id="cards"></div>
        <form action="jack/start" method="post">
            <input type="submit" value="Start New Game">              
        </form>
    </body>
</html>
