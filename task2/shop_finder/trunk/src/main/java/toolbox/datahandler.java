package toolbox;

import java.sql.*;

public class datahandler {

    public enum orderBy { shop_id, name, distance }
    private calculator Calc = new calculator();


    public Connection connectToDB () throws SQLException, ClassNotFoundException {
        String Username = "root";
        String Pwd = "password";
        String URL = "jdbc:mysql://localhost/sa_shop_finder";
        String BugfixURL = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&" +
                "useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                URL + BugfixURL , Username, Pwd);

        return connection;
    }

    public ResultSet getAllShops(Connection connection) throws SQLException{

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from shops " +
                "INNER JOIN shop_has_categories category on shops.shop_id = category.shop_id " +
                "INNER join shop_categories sc on category.category_id = sc.category_id ");


        return resultSet;
    }

    public ResultSet getShops(Connection connection, String shopName, int categoryID, int poiID , int distance ,
                              orderBy orderBy) throws SQLException{

        ResultSet resultSetPOI = getKooPOI(connection, poiID);
        ResultSet resultSet = null;
        Statement statement = connection.createStatement();

        double poiLong = 0;
        double poiLat = 0;

        String SQLCalcDistance = "";

        String sql_category_part = "";

        if (distance != 0){

            while (resultSetPOI.next()) {
                poiLat = resultSetPOI.getDouble("latitude");
                poiLong = resultSetPOI.getDouble("longitude");
            }

            SQLCalcDistance = "( 6378.388 * 1000 * pi() / 180  * acos(sin(latitude) * sin(" + poiLat + ") + " +
                    "cos(latitude) * cos( " + poiLat + " ) * cos( " + poiLong + " - longitude)))";

        }




        if ( categoryID != 0 && poiID != 0){
            // Get shops with name and cat arroind a POI
            // Calculate the Range arround the POI

            // Get the Distnce for each shop and compare it with the max dist to the POI in the Query
            resultSet = statement.executeQuery("SELECT *,  " +
                    "" + SQLCalcDistance + " as distance  from shops " +
                    "INNER JOIN shop_has_categories category on shops.shop_id = category.shop_id " +
                    "INNER join shop_categories sc on category.category_id = sc.category_id " +
                    "where shops.name like '%" + shopName + "%' and sc.category_id =" + categoryID + " " +
                    "and "  + SQLCalcDistance + " <= "  + distance +
                    " order by " + orderBy.toString() );

        }
        else if (shopName != "" &&  poiID != 0  ){
            resultSet = statement.executeQuery("SELECT *," +
                    "" + SQLCalcDistance + " as distance from shops " +
                    "INNER JOIN shop_has_categories category on shops.shop_id = category.shop_id " +
                    "INNER join shop_categories sc on category.category_id = sc.category_id " +
                    "where shops.name like '%" + shopName + "%' " +
                    "and "  + SQLCalcDistance + " <= "  + distance +
                    " order by " + orderBy.toString() );
        }

        else if ( poiID != 0){
            // Get shops with name and cat arroind a POI
            // Calculate the Range arround the POI
            // Get the Distnce for each shop and compare it with the max dist to the POI in the Query

            orderBy = datahandler.orderBy.distance;
            resultSet = statement.executeQuery("SELECT *, " + SQLCalcDistance + " as distance from shops " +
                    "INNER JOIN shop_has_categories category on shops.shop_id = category.shop_id " +
                    "INNER join shop_categories sc on category.category_id = sc.category_id " +
                    "where " + SQLCalcDistance + " <= "  + distance +
                    " order by " + orderBy.toString() );
        }
        else if (shopName != "" && categoryID != 0 ){
            resultSet = statement.executeQuery("SELECT *  from shops " +
                    "INNER JOIN shop_has_categories category on shops.shop_id = category.shop_id " +
                    "INNER join shop_categories sc on category.category_id = sc.category_id " +
                    "where shops.name like '%" + shopName + "%' and sc.category_id =" + categoryID + " " +
                    "order by " + orderBy.toString() );
        }
        else {
            resultSet = statement.executeQuery("SELECT *  from shops " +
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

    public ResultSet getCategory(Connection connection, String name) throws SQLException{

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from shop_categories WHERE name_category = '"+name+"'");

        return resultSet;

    }

    public ResultSet getPointOfInterest(Connection connection, String name) throws SQLException{

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from points_of_interest WHERE name ='"+name+"'");

        return resultSet;

    }

    public ResultSet getPOI(Connection connection) throws SQLException{

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from points_of_interest ");

        return resultSet;
    }

    public ResultSet getKooPOI(Connection connection, int poiID) throws SQLException{

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT longitude, latitude from points_of_interest " +
                "where poi_id = "+ poiID);

        return resultSet;
    }


    public void AddShop(Connection connection, String name, long osm_id,  double longitude, double latitude,
                           String homepage ) throws SQLException{
        // Todo: Test when UI is ready
        Statement statement = connection.createStatement();
        statement.executeQuery("insert into shops (osm_id, longitude, latitude, name, homepage) " +
                "values " + osm_id + " , " + longitude + ", " + latitude + " , ' " + name +" ' , ' " + homepage + " ' ");


    }

    public void AddFavorite(Connection connection, String favName, int searchedCatID, String shopName,
                            int searchedPOIId, int MaxDistance) throws SQLException{
        // Todo: Test when UI is ready
        Statement statement = connection.createStatement();
        statement.executeQuery("insert into favourites (name, category_id, searched_shop_name, poi_id," +
                " distance_to_poi) " +
                "values '" + favName + "' , " + searchedCatID + ", '" + shopName + "' ,  " + searchedPOIId +"  ,  " +
                MaxDistance + "  ");

    }

    public void EditShop(Connection connection, int shopID ,String name, long osm_id,  double longitude,
                           double latitude, String homepage ) throws SQLException{
        // Todo: Test when UI is ready
        Statement statement = connection.createStatement();

        statement.executeQuery("UPDATE shops " +
                "SET longitude = " + longitude + ", latitude = " + latitude + " , " +
                "name = ' " + name + "  ' , homepage = ' " + homepage + " ' " +
                "WHERE shop_id = " + shopID );

    }

    public void EditFavorite(Connection connection, int favoriteID, String favName, int searchedCatID, String shopName,
                             int searchedPOIId, int MaxDistance  ) throws SQLException{
        // Todo: Test when UI is ready
        Statement statement = connection.createStatement();
        statement.executeQuery("UPDATE favourites " +
                "SET name = '" + favName + "' , category_id = " + searchedCatID + ", " +
                "searched_shop_name = '" + shopName + "' , " + "poi_id = " + searchedPOIId + " " +
                " , distance_to_poi = " + MaxDistance + " " +
                "WHERE favourite_id = " + favoriteID );


    }

    public void DeleteShop(Connection connection, int shopID ) throws SQLException{

        // Deletes the Shop from the Shop List, deletes all enterys from the Shop categories list
        // and deletes the Shops Category if this was the last shop

        // Todo: Test
        Statement statement = connection.createStatement();
        ResultSet rsCategories;
        ResultSet rsCategory;
        // Search for Connected Categories
        rsCategories = statement.executeQuery(" SELECT id,  category_id, shop_id from shop_has_categories " +
                "where shop_id = " + shopID );

        // Check if this is the last shop of the Connected categories and delete them if it is so
        while (rsCategories.next()) {
            int category_id = rsCategories.getInt("category_id");
            int shop_id = rsCategories.getInt("shop_id");

            rsCategory = statement.executeQuery("select id from shop_has_categories " +
                    "where category_id = " + category_id);

            while (rsCategory.next()){
                if (rsCategory.isFirst() && rsCategory.isLast()){
                    // No more shops left -> delete this Cat
                    statement.execute("delete from shop_categories " +
                            "where category_id = " + category_id );
                }
                else
                    continue;
            }



            // Done searching for related cats, delete this Row
            statement.execute("delete from shop_has_categories " +
                    "where shop_id = " + shop_id );

        }

        statement.execute("delete from shops " +
                "where shop_id = " + shopID );




    }

    public void DeleteFavorite(Connection connection, int favoriteID ) throws SQLException{

        // Todo: Test when UI is ready
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from favourites " +
                "where favourite_id = " + favoriteID  );

    }


}
