package toolbox;

public class calculator {

    double EarthRadius = 6378.388;

    public double calcDistance(double lon1, double lat1, double lon2, double lat2) {
        // Calculates the distance between 2 points by using the "seitencosinussatz"
        // to get the distance in meters multiplied with the Earth radiant.
        // Java is calculating in degrees, so convert to it by using pi/180.
        // Formula from https://www.kompf.de/gps/distcalc.html.
        double dist;
        dist = EarthRadius * 1000 * 3.1415 / 180 * Math.acos(Math.sin(lat1) * Math.sin(lat2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1));

        return dist;
    }

}
