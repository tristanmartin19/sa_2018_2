package Servlets;

import toolbox.calculator;
import toolbox.datahandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/ShopsAnzeigenServlet")
public class ShopsAnzeigenServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        String shopName = "-1";
        int catId = 0;
        int poiId = 0;
        int distance = 0;

        calculator calc = new calculator();

        // Get Filter Values
        if(request.getParameter("ShopName") != null) {
            shopName = request.getParameter("ShopName");
        }

        if(request.getParameter("Category") != null) {
            catId = Integer.parseInt(request.getParameter("Category"));
        }

        if(request.getParameter("POI") != null) {
            poiId = Integer.parseInt(request.getParameter("POI"));
        }

        if(request.getParameter("distace") != null) {
            distance = Integer.parseInt(request.getParameter("distace"));
        }

        try {
            datahandler dh = new datahandler();
            Connection connection = dh.connectToDB();
            ResultSet resultSet;
            Statement statement = connection.createStatement();


            resultSet = dh.getShops(connection, shopName, catId, poiId , distance, datahandler.orderBy.shop_id);
// Todo: Anpassen und shops richtig ausgeben
            response.setContentType("text/html");

           /* writer.println("<input class=\"form-control\" id=\"searchFilter\" type=\"text\" placeholder=\"Search..\" " +
                    "onkeyup = \"searchContact()\">");*/
            writer.println("<table id=\"ShopTable\" class = \"table table-striped table-hover table-bordered \" >");
            writer.println("<tr>");
            writer.println("</tr>");
            writer.println("<thead>");
            writer.println("<tr>");
            writer.println("<th>Shop Name</th>");
            writer.println("<th>Homepage</th>");
            writer.println("<th>Entfernung von Rathaus</th>");
            writer.println("</tr>");
            writer.println("</thead>");
            writer.println("<tbody id=\"ArtikelTable\">");
            while (resultSet.next()) {
                writer.println("<tr>");
                writer.println("<td>" + resultSet.getString("name") +"</td>");
                writer.println("<td> "+ resultSet.getString("homepage") +"</td>");
                writer.println("<td> " + calc.calcDistance( resultSet.getDouble("longitude"),
                        resultSet.getDouble("latitude"), 15.4386693 ,47.0703614   )+ "</td>");

                writer.println("</tr>");
            }
            writer.println("</tbody>");

        } catch (SQLException e) {
            e.printStackTrace();
            writer.println("Datenbank Fehler!"
                    + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            writer.println("Database Driver not Found! ");
        } catch (Exception e) {
            e.printStackTrace();
            writer.println(e.getMessage());

        }
    }
}
