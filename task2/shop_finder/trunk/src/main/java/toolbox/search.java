package toolbox;

public class search {
    String name;
    String category;
    int distance;
    String poi;

    public search(String name, String category, int distance, String poi) {
        this.name = name;
        this.category = category;
        this.distance = distance;
        this.poi = poi;
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
