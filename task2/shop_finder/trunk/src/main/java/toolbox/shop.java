package toolbox;

public class shop {
    String name;
    double longitude;
    double latitude;
    String category;
    String homepage;
    int id;

    public shop(String name, double longitude, double latitude, String category, String homepage, int id) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.category = category;
        this.homepage = homepage;
        this.id = id;
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
