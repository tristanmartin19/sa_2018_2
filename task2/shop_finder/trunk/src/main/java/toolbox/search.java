package toolbox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class search {
    String name;
    String category;
    int distance;
    String poi;
    datahandler data_handler;


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
                        results.getString("name_category"),
                        results.getString("homepage"),
                        results.getInt("shop_id"),
                        data_handler);
                shops.add(new_shop);
            }

            return shops;

        }
        catch (ClassNotFoundException ex1){}
        catch (SQLException ex2) {}

        return shops;
    }

    public List<shop> getAllShops() {

       List<shop> shops = new ArrayList<shop>();

        try {
            Connection connection = data_handler.connectToDB();
            ResultSet results  = data_handler.getAllShops(connection);

            while (results.next())
            {
                //make a new shop
                shop new_shop = new shop(   results.getString("name"),
                                            results.getDouble("longitude"),
                                            results.getDouble("latitude"),
                                            results.getString("name_category"),
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

    public search(String name, String category, int distance, String poi) {
        this.name = name;
        this.category = category;
        this.distance = distance;
        this.poi = poi;
        this.data_handler = new datahandler();
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
        String buffer = name + ", " + category + ", " + distance + ", " + poi;
        return buffer;
    }
}
