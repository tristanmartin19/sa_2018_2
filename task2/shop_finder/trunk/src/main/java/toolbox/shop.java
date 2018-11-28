package toolbox;

import java.sql.Connection;
import java.sql.SQLException;

public class shop {

    private String name;
    private double longitude;
    private double latitude;
    private String category;
    private String homepage;
    private int id;
    private datahandler data_handler;

    public shop(String name, double longitude, double latitude, String category, String homepage, int id, datahandler data_handler_) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.category = category;
        this.homepage = homepage;
        this.id = id;
        this.data_handler = data_handler_;
    }

    private void setShopData(String new_name, double new_longitude, double new_latitude, String new_homepage) {
        name = new_name;
        longitude = new_longitude;
        latitude = new_latitude;
        homepage = new_homepage;
    }

    public void addShop() {
        try {
            Connection connection = data_handler.connectToDB();
            data_handler.AddShop(connection, name, 0, longitude, latitude, homepage,
                    data_handler.getCategoryID(connection, category));
        }
        catch (SQLException ex1) {}
        catch (ClassNotFoundException ex2) {}
    }

    public void editShop(String new_name, double new_longitude, double new_latitude, String new_homepage) {
        setShopData(new_name, new_longitude, new_latitude, new_homepage);

        try {
            Connection connection = data_handler.connectToDB();
            data_handler.EditShop(connection, id,name, 0, longitude, latitude, homepage,
                    data_handler.getCategoryID(connection, category));
        }
        catch (SQLException ex1) {}
        catch (ClassNotFoundException ex2) {}
    }

    public void deleteShop() {
        try {
            Connection connection = data_handler.connectToDB();
            data_handler.DeleteShop(connection, id);
        }
        catch (SQLException ex1) {}
        catch (ClassNotFoundException ex2) {}

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        String buffer = name + " (" + category + ")\n    Lon: " + longitude + "  Lat: " + latitude + "\n    Homepage: " +
            homepage;
        return buffer;
    }
}
