package toolbox;

import java.sql.*;

public class datahandler {

    public enum orderBy { shop_id, name, distance };

    public Connection connectToDB () throws SQLException, ClassNotFoundException {
        String Username = "root";
        String Pwd = "root";
        String URL = "jdbc:mysql://localhost/sa_shop_finder ";
        String BugfixURL = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&" +
                "useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                URL + BugfixURL , Username, Pwd);

        return connection;
    }

    public ResultSet getAllShops(Connection connection) throws SQLException{

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from shops order by name  ");
        return resultSet;
    }

    public ResultSet getShops(Connection connection, String shopName, int categoryID, int poiID , int distance , orderBy orderBy) throws SQLException{

        Statement statement = connection.createStatement();
        ResultSet resultSet = null;

        if (shopName != "" && categoryID != 0 && poiID != 0){
            // Get shops with name and cat arroind a POI
            // Calculate the Range arround the POI

            // Get the Distnce for each shop and compare it with the max dist to the POI in the Query
        }
        else if (shopName != "" && categoryID != 0 ){
            resultSet = statement.executeQuery("SELECT shops.shop_id, shops.name, homepage, longitude, latitude  from shops " +
                    "INNER JOIN shop_has_categories category on shops.shop_id = category.shop_id " +
                    "INNER join shop_categories sc on category.category_id = sc.category_id " +
                    "where shops.name like '%" + shopName + "%' and sc.category_id =" + categoryID + " " +
                    "order by " + orderBy.toString() );
            return resultSet;
        }

        else if (shopName == "" && poiID != 0 ){
            resultSet = statement.executeQuery("SELECT shops.shop_id, shops.name, homepage, longitude, latitude  from shops " +
                    "INNER JOIN shop_has_categories category on shops.shop_id = category.shop_id " +
                    "INNER join shop_categories sc on category.category_id = sc.category_id " +
                    "where shops.name like '%" + shopName + "%'" +
                    "order by " + orderBy.toString() );
            return resultSet;
        }

        else {
            resultSet = statement.executeQuery("SELECT shops.shop_id, shops.name, homepage, longitude, latitude  from shops " +
                    "INNER JOIN shop_has_categories category on shops.shop_id = category.shop_id " +
                    "INNER join shop_categories sc on category.category_id = sc.category_id " +
                    "where shops.name like '%" + shopName + "%' or sc.category_id =" + categoryID + " " +
                    "order by " + orderBy.toString());
        }
        return resultSet;
    }


    public ResultSet getCategories(Connection connection) throws SQLException{

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from shop_categories ");
        return resultSet;
    }

    public ResultSet getPOI(Connection connection) throws SQLException{

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from points_of_interest ");
        return resultSet;
    }


    public void AddShop(Connection connection, String name, long osm_id,  double longitude, double latitude,
                           String homepage ) throws SQLException{
        Statement statement = connection.createStatement();
        statement.executeQuery("insert into shops (osm_id, longitude, latitude, name, homepage) " +
                "values " + osm_id + " , " + longitude + ", " + latitude + " , ' " + name +" ' , ' " + homepage + " ' ");
    }

    public void AddFavorite(Connection connection) throws SQLException{
        // Todo: Tabelle anlegen und Query Anlegen
    }

    public void EditShop(Connection connection, int shopID ,String name, long osm_id,  double longitude,
                           double latitude, String homepage ) throws SQLException{

        Statement statement = connection.createStatement();
        statement.executeQuery("UPDATE shops " +
                "SET osm_id = " + osm_id + " , longitude = " + longitude + ", latitude = " + latitude + " , " +
                "name = ' " + name + "  ' , homepage = ' " + homepage + " ' " +
                "WHERE shop_id = " + shopID );
    }

    public void EditFavorite(Connection connection, int favoriteID ,String favoriteName, int categoryID ,
                               int distance  ) throws SQLException{

        // Todo: Fav Shops anlegen und query entsprechend tabelle anpassen!
        Statement statement = connection.createStatement();
        /*statement.executeQuery("UPDATE shops " +
                "SET osm_id = " + osm_id + " , longitude = " + longitude + ", latitude = " + latitude + " , " +
                "name = ' " + name + "  ' , homepage = ' " + homepage + " ' " +
                "WHERE shop_id = " + shopID );*/
    }

    public void DeleteShop(Connection connection, int shopID ) throws SQLException{

        // Deletes the Shop from the Shop List, deletes all enterys from the Shop categories list
        // and deletes the Shops Category if this was the last shop

        // Todo: Test
        Statement statement = connection.createStatement();
        ResultSet rsCategories;
        ResultSet rsCategory;
        // Search for Connected Categories
        rsCategories = statement.executeQuery(" SELECT id,  category_id from shop_has_categories " +
                "where shop_id = " + shopID );
        // Check if this is the last shop of the Connected categories and delete them if it is so
        while (rsCategories.next()) {
            rsCategory = statement.executeQuery("select id from shop_has_categories " +
                    "where category_id = " + rsCategories.getInt("category_id"));

            while (rsCategory.next()){
                if (rsCategory.isFirst() && rsCategory.isLast()){
                    // No more shops left -> delete this Cat
                    statement.execute("delete from shop_categories " +
                            "where category_id = " + rsCategories.getInt("category_id") );
                }
                else
                    continue;
            }

            // Done searching for related cats, delete this Row
            statement.execute("delete from shop_has_categories " +
                    "where category_id = " + rsCategories.getInt("category_id") );

        }

        statement.execute("delete from shops " +
                "where shop_id = " + shopID );

    }

    public void DeleteFavorite(Connection connection, int favoriteID ) throws SQLException{

        // Todo: Fav Shops anlegen und query entsprechend tabelle anpassen!
        Statement statement = connection.createStatement();
        /*statement.executeQuery("UPDATE shops " +
                "SET osm_id = " + osm_id + " , longitude = " + longitude + ", latitude = " + latitude + " , " +
                "name = ' " + name + "  ' , homepage = ' " + homepage + " ' " +
                "WHERE shop_id = " + shopID );*/
    }


}
