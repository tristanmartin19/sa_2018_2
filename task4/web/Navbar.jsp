<%--
  Created by IntelliJ IDEA.
  User: Alexa
  Date: 21.08.2018
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Navbar</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css"
          integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ"
          crossorigin="anonymous">


</head>
<body>


<nav class="navbar navbar-expand-md bg-dark navbar-dark">
    <a class="navbar-brand" href="index.jsp">Shop Finder</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav">

            <li class="nav-item">
                <a class="nav-link" href="\index.jsp">Browse Shops</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="">Edit Shops</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="">Edit Favorites</a>
            </li>

        </ul>

    </div>
</nav>



<%
    // get the State
    int JSPServerstate = 0;
    if (session.getAttribute("serverState") != null) {
        JSPServerstate = Integer.parseInt(session.getAttribute("serverState").toString());
    }

    // Get the Server successMessage
    String JSPserverMessage = "Failed";
    if (session.getAttribute("serverMessage") != null) {
        JSPserverMessage = session.getAttribute("serverMessage").toString();
    }
%>

<script>


    function ServletState(){
        var serverState = <%=JSPServerstate%>;
        var serverMessage = "<%=JSPserverMessage%>";
        //$("#result").html("ServerState: " +serverState);
        if (serverState == 1) {
            $("#successStrong").html("Angelegt!");
            $("#successText").html(serverMessage);
            document.getElementById("success").removeAttribute("hidden");
        }
        if (serverState == 2) {
            $("#failStrong").html("Anlegen Fehlgeschlagen!");
            $("#failText").html(serverMessage);
            document.getElementById("fail").removeAttribute("hidden");
        }
    }


</script>
</body>
</html>
