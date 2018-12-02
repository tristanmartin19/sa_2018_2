package toolbox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class search {
    int id;
    String search_name;
    String name;
    String category;
    int distance;
    String poi;
    datahandler data_handler;

    public void setSearch(String fav_name, String new_search_name, String new_name, String new_category, int new_distance, String new_poi) {
        this.search_name = fav_name;
        this.name = new_name;
        this.category = new_category;
        this.distance = new_distance;
        this.poi = new_poi;
    }

    public void editSearch(String new_search_name, String new_name, String new_category, int new_distance, String new_poi) {
        setSearch(new_search_name, new_search_name, new_name, new_category, new_distance, new_poi);

        try {
            Connection connection = data_handler.connectToDB();

            ResultSet category_r = data_handler.getCategory(connection, category);
            category_r.first();
            int int_category = category_r.getInt("category_name");

            ResultSet result_poi;
            result_poi = data_handler.getPointOfInterest(connection, poi);
            int integer_poi = result_poi.getInt("poi_id");



            data_handler.EditFavorite(connection, id, search_name, int_category, name, integer_poi, distance);
        }
        catch (SQLException ex1) {}
        catch (ClassNotFoundException ex2) {}

    }

    public void deleteSearch() {
        try {
            Connection connection = data_handler.connectToDB();
            data_handler.DeleteFavorite(connection, id );
        }
        catch (SQLException ex1) {}
        catch (ClassNotFoundException ex2){}
    }

    public List<search> getAllSearches() {

        List<search> searches = new ArrayList<search>();

        try {
            Connection connection = data_handler.connectToDB();
            ResultSet result_searches;
            result_searches = data_handler.getAllSearches(connection);



            while (result_searches.next())
            {
                int integer_category = result_searches.getInt("category_id");
                int integer_poi = result_searches.getInt("poi_id");

                ResultSet category_r = data_handler.getCategory(connection, integer_category);
                category_r.first();
                String string_category = category_r.getString("category_name");

                ResultSet pointofinterrest_r = data_handler.getPointOfInterest(connection, integer_poi);
                pointofinterrest_r.first();
                String string_poi = pointofinterrest_r.getString("points_of_interest.name");


                search new_search = new search(result_searches.getInt("favourite_id"),
                        result_searches.getString("favourites.name"),
                        result_searches.getString("searched_shop_name"),
                       string_category ,
                        result_searches.getInt("distance_to_poi"),
                        string_poi
                        );
                searches.add(new_search);

            }


        }
        catch (SQLException ex1) {}
        catch (ClassNotFoundException ex2) {}

        return searches;
    }

    public void addSearch() {
        try {
            Connection connection = data_handler.connectToDB();
            int integer_category = 0;
            int integer_poi = 0;

            ResultSet result_category;
            result_category = data_handler.getCategory(connection, category);
            if (result_category.first()) {
                integer_category = result_category.getInt("category_id");
            }

            ResultSet result_poi;
            result_poi = data_handler.getPointOfInterest(connection, poi);
            if (result_poi.first()) {
                integer_poi = result_poi.getInt("poi_id");
            }



            int new_id = data_handler.AddFavorite(connection, search_name, integer_category, name, integer_poi, distance);
            id = new_id;

            connection.close();
        }
        catch (SQLException ex1) {
            String error = ex1.getMessage();
        }
        catch (ClassNotFoundException ex2) {
            String error = ex2.getMessage();
        }
    }


    public List<shop> getShops(String name, String category,
                               String poi, int distance) {



        List<shop> shops = new ArrayList<shop>();
        int category_number = 0;
        int poi_number = 0;

        try {
            Connection connection = data_handler.connectToDB();

            //give category and poi integer number, if they were selected
            if (!category.equals("")) {
                ResultSet category_result = data_handler.getCategory(connection, category);
                category_result.first();
                category_number = category_result.getInt("category_id");
            }
            else { //search for all categories
                category_number = 0;
            }


            if (!poi.equals(""))
            {
                ResultSet poi_result = data_handler.getPointOfInterest(connection,poi);
                poi_result.first();
                poi_number = poi_result.getInt("poi_id");
            }




            ResultSet results  = data_handler.getShops(connection, name, category_number,
                    poi_number, distance, datahandler.orderBy.name);

            while (results.next())
            {
                //make a new shop
                shop new_shop = new shop(   results.getString("name"),
                        results.getDouble("longitude"),
                        results.getDouble("latitude"),
                        results.getString("category_name"),
                        results.getString("homepage"),
                        results.getInt("shop_id"),
                        data_handler);
                shops.add(new_shop);
            }

            return shops;

        }
        catch (ClassNotFoundException ex1){}
        catch (SQLException ex2) {
            String error = ex2.getMessage();
        }

        return shops;
    }

    public List<shop> getAllShops() {

       List<shop> shops = new ArrayList<shop>();

        try {
            Connection connection = data_handler.connectToDB();
            ResultSet results  = data_handler.getAllShops(connection, datahandler.orderBy.name);

            while (results.next())
            {
                //make a new shop
                shop new_shop = new shop(   results.getString("name"),
                                            results.getDouble("longitude"),
                                            results.getDouble("latitude"),
                                            results.getString("category_name"),
                                            results.getString("homepage"),
                                            results.getInt("shop_id"),
                                            data_handler);
                shops.add(new_shop);
            }

            return shops;

        }
        catch (ClassNotFoundException ex1){}
        catch (SQLException ex2) {}

        return null;
    }

    public search(int id, String search_name,  String name, String category, int distance, String poi) {
        this.id = id;
        this.search_name = search_name;
        this.name = name;
        this.category = category;
        this.distance = distance;
        this.poi = poi;
        this.data_handler = new datahandler();
    }

    public String getSearchName() {
        return search_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }

    public String toString() {
        String print_search_name = search_name;

        if (search_name.equals(""))
            print_search_name = "<No name specified>";

        String buffer =  print_search_name +"\nSearch for " + category + " with name '"+ name + "'\nLocated " + distance + "m near to location '" + poi+"'";
        return buffer;
    }
}
