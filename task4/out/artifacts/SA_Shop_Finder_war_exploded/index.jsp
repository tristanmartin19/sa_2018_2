<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="toolbox.datahandler" %>
<%@ page import="java.sql.Connection" %><%--
  Created by IntelliJ IDEA.
  User: Alexa
  Date: 21.08.2018
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shop Finder</title>

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
<body onload="LoadShopTable()">
<div id="navbar">
    <%@include file="Navbar.jsp" %>
</div>
<div class="container-fluid">


<h1>Shop Finder</h1> <br>
Wenn sie ein geschäft suchen sind sie bei uns Richtig! <br>

    <div class="row">
        Filter <br>

        <br> <label for = "inpName" >Shop Name</label> <br>
        <input type="text" name="ShopName" id = "inpName" class="form-control" >


        <label for = "selectCat" >Shop Category</label> <br>
        <select name="Categories" id="selectCat" class="custom-select" onchange="setCat()">
            <option value="0">Auswählen</option>
            <%
                Connection connection = null;
                datahandler dh = new datahandler();
                try {
                    connection= dh.connectToDB();
                    ResultSet resultSet;
                    resultSet= dh.getCategories(connection);
                    while (resultSet.next())
                    {
            %><option value= <%=resultSet.getInt("category_id") %> ><%=resultSet.getString("name")  %></option>
            <%
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } %>
        </select>

        <label for="selectPoi">Shop Arround POI</label> <br>
    <select name="POI" id="selectPoi" class="custom-select" onchange="setCat()">
        <option value="0">Auswählen</option>
        <%
            try {
                connection= dh.connectToDB();
                ResultSet resultSet;
                resultSet= dh.getPOI(connection);
                while (resultSet.next())
                {
        %><option value= <%=resultSet.getInt("poi_id") %> ><%=resultSet.getString("name")  %></option>
        <%
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } %>
    </select>

        <br> <label for = "inpDist" >Distance</label> <br>
        <input type="text" name="distace" id = "inpDist" class="form-control" >

        <input type="button" class="btn-info" value="Search!" onclick="LoadShopTable()">



    </div>

    <br>


    <div id="Shops">
        Loading...
    </div>
</div>





<script>
    var xmlHttpShowShops;
    xmlHttpShowShops = new XMLHttpRequest();



    function  LoadShopTable(){
        document.getElementById("Shops").innerHTML = "Loading. .. ";


        if(xmlHttpShowShops === null) {
            alert("Browser doesn't support AJAX");
            return;
        }
        var ShopName = document.getElementById("inpName").value;
        var selectCat = document.getElementById("selectCat");
        var cat = selectCat.options[selectCat.selectedIndex].value;
        var poi = document.getElementById("selectPoi").selectedIndex;
        var distance = document.getElementById("inpDist").value;

        var url = "/ShopsAnzeigenServlet" +"?ShopName=" + ShopName +"&Category=" + cat ;
        xmlHttpShowShops.open("Get", url, true);
        xmlHttpShowShops.send();
        xmlHttpShowShops.addEventListener("readystatechange", resultLoadShopTable, false);
    }
    function resultLoadShopTable() {
        if (xmlHttpShowShops.readyState === 4) {
            document.getElementById("Shops").innerHTML = xmlHttpShowShops.responseText.toString();
        }
    }


</script>
</body>
</html>
